package com.lkmotion.yesincar.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.lkmotion.yesincar.constant.PayConst;
import com.lkmotion.yesincar.constant.PayEnum;
import com.lkmotion.yesincar.constant.PayTypeEnum;
import com.lkmotion.yesincar.constant.TradeTypeEnum;
import com.lkmotion.yesincar.entity.PassengerWalletRecord;
import com.lkmotion.yesincar.service.AlipayService;
import com.lkmotion.yesincar.service.PassengerWalletService;
import com.lkmotion.yesincar.util.BigDecimalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author chaopengfei
 */
@Service
@Slf4j
public class AlipayServiceImpl implements AlipayService {

    @Value("${callback}")
    private String callback;

    /**
     * 商户秘钥
     */
    @Value("${alipay.app-private-key}")
    private String alipayAppPrivateKey ;

    /**
     * 支付宝公钥
     */
    @Value("${alipay.public-key}")
    private String alipayPublicKey;

    /**
     * 商户APPID
     */
    @Value("${alipay.app-id}")
    private String alipayAppId ;

    /**
     * 商家和支付宝签约的产品码，为固定值QUICK_MSECURITY_PAY
     */
    @Value("${alipay.product-code}")
    private String alipayProductCode ;

    /**
     * 支付宝网关地址
     */
    @Value("${alipay.gateway}")
    private String alipayGateway ;

    /**
     * 交易关闭时间
     */
    @Value("${alipay.close-trade-time}")
    private String alipayCloseTradeTime ;

    @Autowired
    private PassengerWalletService passengerWalletService;

    @Override
    public String prePay(Integer yid , Double capital, Double giveFee, String source,Integer rechargeType,Integer orderId){
        try {
            //生成充值记录
            PassengerWalletRecord passengerWalletRecord = passengerWalletService.createWalletRecord(yid,capital,giveFee,
                    PayTypeEnum.ALIPAY.getCode(),
                    TradeTypeEnum.RECHARGE.getCode(),"支付宝充值",orderId,PayEnum.UN_PAID.getCode());
            // 实例化客户端
            AlipayClient alipayClient = new DefaultAlipayClient(alipayGateway, alipayAppId, alipayAppPrivateKey, "json",
                    "UTF-8",
                    alipayPublicKey, "RSA2");
            // 实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
            AlipayTradeAppPayRequest alipayTradeAppPayRequest = new AlipayTradeAppPayRequest();

            AlipayTradeAppPayModel model = createModel(passengerWalletRecord.getId()+"",yid,capital,giveFee,source,rechargeType);
            alipayTradeAppPayRequest.setBizModel(model);
            // 支付宝回调服务器接口
            String notifyUrl = callback + "/alipay/callback";
            alipayTradeAppPayRequest.setNotifyUrl(notifyUrl);
            // 这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(alipayTradeAppPayRequest);
            // payData 可以直接传给客户端发起支付请求，无需再做处理。
            String payData = response.getBody();
            return payData;
        }catch (Exception e ){
            return "";
        }
    }

    private AlipayTradeAppPayModel createModel(String rechargeId,Integer yid,Double capital,Double giveFee,String source,Integer rechargeType){

        // SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        // 对一笔交易的具体描述信息，回调的时候用。yid_capital_giveFee
        model.setBody(yid + PayConst.UNDER_LINE + capital + PayConst.UNDER_LINE + giveFee + PayConst.UNDER_LINE + rechargeType +
                PayConst.UNDER_LINE + rechargeId);
        // 交易关闭时间
        model.setTimeoutExpress(alipayCloseTradeTime);
        // 销售产品码
        model.setProductCode(alipayProductCode);
        model.setSubject(source + "用户充值");
        model.setOutTradeNo(rechargeId);
        model.setTotalAmount(BigDecimalUtil.DoubleToString(capital));

        return model;
    }

    @Override
    public Boolean checkAlipaySign(Map<String, String> params) {
        Boolean flag = false;
        try {
            flag = AlipaySignature.rsaCheckV1(params, alipayPublicKey, "UTF-8", "RSA2");
            log.info("sdk签名验证："+flag);
        } catch (AlipayApiException e) {
            e.printStackTrace();
            log.error("警告：支付宝--【支付回调验签出现异常】,订单号：" + params.get("out_trade_no"), e);
        }
        return flag;
    }

    @Override
    public boolean callback(Map<String, String> params) {
        boolean flag = false;
        try {
            // 持久化 todo
            // this.persistentZfbPayInfo(params);
            String body = params.get("body");
            String[] bodyArray = body.split(PayConst.UNDER_LINE);
            // 用户Id
            Integer yid = Integer.parseInt(bodyArray[0]);
            // 本金
            Double capital = Double.parseDouble(bodyArray[1]);
            //赠费
            Double giveFee = Double.parseDouble(bodyArray[2]);
            //充值类型
            Integer rechargeType = Integer.parseInt(bodyArray[3]);
            Integer rechargeId = Integer.parseInt(bodyArray[4]);
            // 支付宝交易号
            String tradeNo = params.get("trade_no");
            // 支付宝用户号 （非支付宝账号）
            String sellerUserid = params.get("seller_id");
            // 订单支付金额
            String totalFee = params.get("total_amount");
            // 获取订单号
            String outTradeNo = params.get("out_trade_no");
            // 交易状态
            String tradeStatus = params.get("trade_status");
            if (PayConst.TRADE_SUCCESS.equals(tradeStatus)) {

                passengerWalletService.handleCallBack(rechargeType,rechargeId);
                log.info("支付宝回调--支付宝充值，充值记录ID: " + outTradeNo);
                flag = true;

            } else {
                log.info("支付宝回调--交易失败--交易场景：" + "，订单ID: " + outTradeNo);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }
}
