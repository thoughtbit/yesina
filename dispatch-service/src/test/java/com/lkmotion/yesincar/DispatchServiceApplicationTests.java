package com.lkmotion.yesincar;

import com.lkmotion.yesincar.mapper.DriverBaseInfoMapper;
import com.lkmotion.yesincar.service.DispatchService;
import com.lkmotion.yesincar.service.DriverService;
import com.lkmotion.yesincar.task.impl.OrderTask;
import com.lkmotion.yesincar.util.ServiceAddress;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class DispatchServiceApplicationTests {
    @Autowired
    private DriverService driverService;
    @Autowired
    private DispatchService dispatchService;
    @Autowired
    private ServiceAddress serviceAddress;
    @Autowired
    private DriverBaseInfoMapper driverBaseInfoMapper;

    @Test
    public void testServerAddress() {
        log.info(serviceAddress.get("map"));
        log.info(serviceAddress.get("message"));
        log.info(serviceAddress.get("order"));
    }

    @Test
    public void testD() {
        log.info(driverBaseInfoMapper.selectByPrimaryKey(15).toString());
    }

    @Test
    public void tt() {
        try {
            Class c = Class.forName("com.lkmotion.yesincar.data.DriverData");
            log.info(c.getName());
            log.info(c.getTypeName());

            log.info(c.getSimpleName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test() {
        DispatchService.ins().test(10);
    }

    @Test
    public void contextLoads() {
        OrderTask orderTask = new OrderTask();
        // orderTask.forceSendOrder(1);
    }

}
