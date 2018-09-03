package com.lkmotion.yesincar.task;

import com.lkmotion.yesincar.constatnt.IdentityEnum;
import com.lkmotion.yesincar.dto.ResponseResult;
import com.lkmotion.yesincar.dto.phone.PhoneInfoDto;
import com.lkmotion.yesincar.dto.phone.PhoneInfoView;
import com.lkmotion.yesincar.exception.ParameterException;
import com.lkmotion.yesincar.util.RestTemplateHepler;
import com.lkmotion.yesincar.util.ServiceAddress;
import com.yipin.data.upload.proto.OTIpcDef;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.client.RestTemplate;

import javax.jms.MapMessage;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 上报任务的基类
 *
 * @author ZhuBin
 * @date 2018/8/24
 */
@Slf4j
public abstract class AbstractSupervisionTask implements SupervisionTask {
    /**
     * 监听到插入操作
     *
     * @param id 主键
     * @return 是否为合法上报数据
     */
    public abstract boolean insert(Integer id);

    /**
     * 监听到更新操作
     *
     * @param id 主键
     * @return 是否为合法上报数据
     */
    public abstract boolean update(Integer id);

    /**
     * 监听到删除操作
     *
     * @param id 主键
     * @return 是否为合法上报数据
     */
    public abstract boolean delete(Integer id);

    /**
     * 数据上报
     */
    public void send() {
        try {
            if (null == ipcType) {
                throw new ParameterException("ipcType为空");
            }

            setCommonContent();
            jmsTemplate.send(generalQueue, session -> {
                MapMessage message = session.createMapMessage();
                message.setString("ipcType", ipcType.name());
                message.setObject("messageMap", messageMap);
                return message;
            });
            log.info("JMS发送ipcType={}, data={}", ipcType, messageMap);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("JMS发送异常：", e);
        }
    }

    /**
     * 设置共同消息
     */
    private void setCommonContent() {
        //注册地行政区划代码
        messageMap.put("Address", 110100);
    }

    /**
     * 获取解密后的电话号码
     *
     * @param identity 身份类型
     * @param recordId 主键
     * @return 解密后的电话号码
     */
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    protected String getPhoneNumber(IdentityEnum identity, int recordId) {
        try {
            PhoneInfoDto dto = new PhoneInfoDto();
            dto.setIdType(identity.getCode());
            dto.setInfoList(Collections.singletonList(new PhoneInfoView().setId(recordId)));

            String url = serviceAddress.getAccountAddress() + "/phone/getPhoneList";
            PhoneInfoDto phoneInfoDto = RestTemplateHepler.parse(restTemplate.postForObject(url, dto, ResponseResult.class), PhoneInfoDto.class);
            return phoneInfoDto.getInfoList().stream().findFirst().get().getPhone();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 存放消息
     */
    protected final Map<String, Object> messageMap = new HashMap<>();

    /**
     * 消息类型
     */
    protected OTIpcDef.IpcType ipcType;

    @Autowired
    private ServiceAddress serviceAddress;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ActiveMQQueue generalQueue;

    @Autowired
    private JmsTemplate jmsTemplate;

}
