package com.lkmotion.yesincar.service.impl;

import com.lkmotion.yesincar.constant.PayConst;
import com.lkmotion.yesincar.constant.PayEnum;
import com.lkmotion.yesincar.constant.PayTypeEnum;
import com.lkmotion.yesincar.constant.TradeTypeEnum;
import com.lkmotion.yesincar.dto.ResponseResult;
import com.lkmotion.yesincar.dto.ScanPayQueryReqData;
import com.lkmotion.yesincar.entity.PassengerWalletRecord;
import com.lkmotion.yesincar.entity.ScanPayResData;
import com.lkmotion.yesincar.entity.WeixinXmlPayRequest;
import com.lkmotion.yesincar.request.PayResultRequest;
import com.lkmotion.yesincar.response.PayResultResponse;
import com.lkmotion.yesincar.response.WeixinPayResponse;
import com.lkmotion.yesincar.service.PassengerWalletService;
import com.lkmotion.yesincar.service.WeixinPayService;
import com.lkmotion.yesincar.util.BigDecimalUtil;
import com.lkmotion.yesincar.util.RandomStringGenerator;
import com.lkmotion.yesincar.util.Signature;
import com.lkmotion.yesincar.util.XstreamUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static com.lkmotion.yesincar.constant.PayConst.RETURN_CODE_SUCCESS;

/**
 * @author chaopengfei
 * @date 2018/8/16
 */
@Slf4j
@Service
public class WeixinPayServiceImpl implements WeixinPayService {

    @Value("${callback}")
    private String callback;

    @Value("${wxpay.appId}")
    private String appId;

    @Value("${wxpay.mchId}")
    private String mchId;

    @Value("${wxpay.key}")
    private String key;

    @Value("${wxpay.unifiedorder.api}")
    private String unifiedorderApi;

    @Value("${wxpay.query.api}")
    private String queryApi;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PassengerWalletService passengerWalletService;

    @Override
    public WeixinPayResponse prePay(Integer yid , Double capital, Double giveFee, String source,Integer rechargeType,Integer orderId){

        //生成充值记录
        PassengerWalletRecord passengerWalletRecord = passengerWalletService.createWalletRecord(yid,capital,giveFee,
                PayTypeEnum.ALIPAY.getCode(),
                TradeTypeEnum.RECHARGE.getCode(),"微信充值",orderId,PayEnum.UN_PAID.getCode());

        String body = "weixin pay";
        Integer rechargeId = passengerWalletRecord.getId();
        String outTradeNo = rechargeId + "_" + RandomStringGenerator.getRandomStringByLength(4);
        int totalFee = (int) BigDecimalUtil.mul(String.valueOf(capital), String.valueOf(100));
        String spBillCreateIp;
        try {
            spBillCreateIp = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            spBillCreateIp = "";
        }
        String notifyUrl = callback + "/weixinPay/callback";
        String tradeType = "APP";
        String openid = "";

        String separator = PayConst.UNDER_LINE;
        String attach = yid + separator + capital + separator + giveFee + separator + rechargeType + separator + rechargeId;
        WeixinXmlPayRequest wxOrder = new WeixinXmlPayRequest(body, outTradeNo, totalFee, spBillCreateIp, notifyUrl, tradeType, openid,
                attach,appId,mchId,key);
        ScanPayResData scanPayResData = unifiedOrder(wxOrder);

        return createWXPayResponse(scanPayResData,outTradeNo);

    }

    private Boolean checkParam(String reqXml) {
        try {
            return Signature.checkIsSignValidFromResponseString(reqXml,key);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean callback(String reqXml){
        Boolean flag = false;

        Boolean checkFlag = checkParam(reqXml);
        if (checkFlag){
            ScanPayResData scanPayResData = (ScanPayResData)XstreamUtil.xmlToObject(reqXml, ScanPayResData.class);
            log.info("attach : " + scanPayResData.getAttach());
            String[] attach = scanPayResData.getAttach().split("_");

            // 用户Id
            Integer yid = Integer.parseInt(attach[0]);
            // 本金
            Double capital = Double.parseDouble(attach[1]);
            //赠费
            Double giveFee = Double.parseDouble(attach[2]);
            //充值类型
            Integer rechargeType = Integer.parseInt(attach[3]);
            //充值流水好
            Integer rechargeId = Integer.parseInt(attach[4]);

            passengerWalletService.handleCallBack(rechargeType,rechargeId);

            flag = true;
        }else{
            log.info("微信签名验证失败");
        }
        return flag;

    }

    /**
     * 下发微信订单
     */
    private ScanPayResData unifiedOrder(WeixinXmlPayRequest request) {
        log.info("下发微信订单--订单号：" + request.getOutTradeNo() + "交易金额：" + request.getTotalFee() + "交易时间："
                + request.getTimeStart());
        String postDataXML = XstreamUtil.objectToXml(request);
        log.info("微信统一下单请求xml："+postDataXML);
        String responseBody = restTemplate.postForObject(unifiedorderApi,postDataXML,String.class);

        try {
            responseBody = new String(responseBody.getBytes(StringHttpMessageConverter.DEFAULT_CHARSET),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        log.info("微信统一下单返回结果：" + responseBody);
        ScanPayResData scanPayResData = XstreamUtil.xmlToObject(responseBody, ScanPayResData.class);

        if (scanPayResData.getReturnCode().equals(RETURN_CODE_SUCCESS)) {
            if (scanPayResData.getResultCode().equals(RETURN_CODE_SUCCESS)) {
                return scanPayResData;
            } else {
                // 业务问题 已经支付
                if (PayConst.ERROR_ORDER_PAID.equals(scanPayResData.getErrCode())) {
                    log.info("订单已支付");
                } else if (PayConst.ERROR_OUT_TRADE_NO_USED.equals(scanPayResData.getErrCode())) {
                    log.info("订单号重复");
                } else if (PayConst.ERROR_ORDER_CLOSED.equals(scanPayResData.getErrCode())) {
                    log.info("订单已关闭");
                }
            }
        }
        return null;

    }

    private WeixinPayResponse createWXPayResponse(ScanPayResData scanPayResData, String outTradeNo) {
        WeixinPayResponse payInfo = new WeixinPayResponse();

        payInfo.setNonceStr(scanPayResData.getNonceStr());
        payInfo.setSignType("MD5");
        payInfo.setAppId(scanPayResData.getAppid());
        payInfo.setTimeStamp(System.currentTimeMillis() / 1000);
        payInfo.setPrepayId(scanPayResData.getPrepayId());
        payInfo.setPartnerId(scanPayResData.getMchId());
        payInfo.setOutTradeNo(outTradeNo);
        payInfo.setPackageData("Sign=WXPay");
        payInfo.setPaySign(Signature.getIOSSign(scanPayResData.getAppid(), String.valueOf(payInfo.getTimeStamp()),
                scanPayResData.getNonceStr(), scanPayResData.getPrepayId(), scanPayResData.getMchId(),key));

        return payInfo;
    }

    /**
     * 查询支付结果
     * @param payResultRequest
     * @return
     */
    @Override
    public ResponseResult payResult(PayResultRequest payResultRequest){

        ScanPayQueryReqData scanPayQueryReqData = new ScanPayQueryReqData(null, payResultRequest.getOutTradeNo(),
                appId,mchId,key);

        ScanPayResData scanPayQueryResData = orderQuery(scanPayQueryReqData);
        if (null != scanPayQueryResData){
            if (PayConst.RETURN_CODE_SUCCESS.equals(scanPayQueryResData.getTrade_state())) {
                log.info("查询支付结果 - 订单号：" + payResultRequest.getOutTradeNo() + "微信支付平台响应支付成功");
                return ResponseResult.success(new PayResultResponse(PayConst.PAY_SUCCESS_STATUS));
            } else if (PayConst.NOTPAY.equals(scanPayQueryResData.getTrade_state())) {
                log.info("查询支付结果 - 订单号：" + payResultRequest.getOutTradeNo() + "微信支付平台响应未支付");
                return ResponseResult.success(new PayResultResponse(PayConst.RE_TRY_STATUS));
            }
        }
        return ResponseResult.success(new PayResultResponse(PayConst.NO_ORDER_STATUS));
    }

    public ScanPayResData orderQuery(ScanPayQueryReqData request) {
        String postDataXML = XstreamUtil.objectToXml(request);
        log.info("微信查询支付状态请求xml："+postDataXML);
        String responseBody = restTemplate.postForObject(queryApi,postDataXML,String.class);

        try {
            responseBody = new String(responseBody.getBytes(StringHttpMessageConverter.DEFAULT_CHARSET),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        log.info("微信查询支付状态返回结果：" + responseBody);
        ScanPayResData scanPayQueryResData = XstreamUtil.xmlToObject(responseBody, ScanPayResData.class);

        if (scanPayQueryResData.getReturnCode().equals(PayConst.RETURN_CODE_SUCCESS)) {
            if (scanPayQueryResData.getResultCode().equals(PayConst.RETURN_CODE_SUCCESS)) {
                return scanPayQueryResData;
            }
        }
        return null;
    }
}