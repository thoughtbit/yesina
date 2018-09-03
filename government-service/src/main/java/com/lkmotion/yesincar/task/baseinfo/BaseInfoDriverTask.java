package com.lkmotion.yesincar.task.baseinfo;

import com.lkmotion.yesincar.dto.baseinfo.BaseInfoDriverDto;
import com.lkmotion.yesincar.mapper.BaseInfoMapper;
import com.lkmotion.yesincar.task.AbstractSupervisionTask;
import com.yipin.data.upload.proto.OTIpcDef;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author lizhaoteng
 * @date 2018/8/29
 **/
@Component
@RequiredArgsConstructor
@Slf4j
public class BaseInfoDriverTask extends AbstractSupervisionTask {

    @NonNull
    private BaseInfoMapper baseInfoDriverMapper;

    /**
     * 监听到插入操作
     *
     * @param id 主键
     * @return 是否为合法上报数据
     */
    @Override
    public boolean insert(Integer id) {
        ipcType = OTIpcDef.IpcType.baseInfoDriver;
        BaseInfoDriverDto baseInfoDriver = baseInfoDriverMapper.getBaseInfoDriver(id);
        messageMap.put("DriverPhone", baseInfoDriver.getDriverPhone());
        messageMap.put("DriverGender", baseInfoDriver.getDriverGender());
        messageMap.put("DriverBirthday", baseInfoDriver.getDriverBirthday());
        messageMap.put("DriverNation", baseInfoDriver.getDriverNation());
        messageMap.put("DriverMaritalStatus", baseInfoDriver.getDriverMaritalStatus());
        messageMap.put("drverLanguageLevel", baseInfoDriver.getDrverLanguageLevel());
        messageMap.put("DriverEducation", baseInfoDriver.getDriverEducation());
        messageMap.put("DriverCensus", baseInfoDriver.getDriverCensus());
        messageMap.put("DriverAddress", baseInfoDriver.getDriverAddress());
        messageMap.put("driverContactAddress", baseInfoDriver.getDriverContactAddress());
        messageMap.put("Licenseld", baseInfoDriver.getLicenseld());
        messageMap.put("GetDriverLicenseDate", baseInfoDriver.getGetDriverLicenseDate());
        messageMap.put("DriverLicenseOn", baseInfoDriver.getDriverLicenseOn());
        messageMap.put("DriverLicenseOff", baseInfoDriver.getDriverLicenseOff());
        messageMap.put("TaxiDriver", baseInfoDriver.getTaxiDriver());
        messageMap.put("CertificateNo", baseInfoDriver.getCertificateNo());
        messageMap.put("NetworkCarIssueOrganization", baseInfoDriver.getNetworkCarIssueOrGanization());
        messageMap.put("NetworkCarlssueDate", baseInfoDriver.getNetworkCarlssueDate());
        messageMap.put("GetNetworkCarProoDate", baseInfoDriver.getGetNetworkCarProoDate());
        messageMap.put("NetworkCarProofOn", baseInfoDriver.getNetworkCarProofOn());
        messageMap.put("NetworkCarProofOff", baseInfoDriver.getNetworkCarProofOff());
        messageMap.put("RegisterDate", baseInfoDriver.getRegisterDate());
        messageMap.put("CommercialType", baseInfoDriver.getCommercialType());
        messageMap.put("ContractCompany", baseInfoDriver.getContractCompany());
        messageMap.put("ContractOn", baseInfoDriver.getContractOn());
        messageMap.put("ContractOff", baseInfoDriver.getContractOff());
        messageMap.put("State", baseInfoDriver.getState());
        messageMap.put("Flag", 1);
        messageMap.put("UpdateTime", now());

        return true;
    }

    /**
     * 监听到更新操作
     *
     * @param id 主键
     * @return 是否为合法上报数据
     */
    @Override
    public boolean update(Integer id) {
        BaseInfoDriverDto baseInfoDriver = baseInfoDriverMapper.getBaseInfoDriver(id);
        messageMap.put("DriverPhone", baseInfoDriver.getDriverPhone());
        messageMap.put("DriverGender", baseInfoDriver.getDriverGender());
        messageMap.put("DriverBirthday", baseInfoDriver.getDriverBirthday());
        messageMap.put("DriverNation", baseInfoDriver.getDriverNation());
        messageMap.put("DriverMaritalStatus", baseInfoDriver.getDriverMaritalStatus());
        messageMap.put("drverLanguageLevel", baseInfoDriver.getDrverLanguageLevel());
        messageMap.put("DriverEducation", baseInfoDriver.getDriverEducation());
        messageMap.put("DriverCensus", baseInfoDriver.getDriverCensus());
        messageMap.put("DriverAddress", baseInfoDriver.getDriverAddress());
        messageMap.put("driverContactAddress", baseInfoDriver.getDriverContactAddress());
        messageMap.put("Licenseld", baseInfoDriver.getLicenseld());
        messageMap.put("GetDriverLicenseDate", baseInfoDriver.getGetDriverLicenseDate());
        messageMap.put("DriverLicenseOn", baseInfoDriver.getDriverLicenseOn());
        messageMap.put("DriverLicenseOff", baseInfoDriver.getDriverLicenseOff());
        messageMap.put("TaxiDriver", baseInfoDriver.getTaxiDriver());
        messageMap.put("CertificateNo", baseInfoDriver.getCertificateNo());
        messageMap.put("NetworkCarIssueOrganization", baseInfoDriver.getNetworkCarIssueOrGanization());
        messageMap.put("NetworkCarlssueDate", baseInfoDriver.getNetworkCarlssueDate());
        messageMap.put("GetNetworkCarProoDate", baseInfoDriver.getGetNetworkCarProoDate());
        messageMap.put("NetworkCarProofOn", baseInfoDriver.getNetworkCarProofOn());
        messageMap.put("NetworkCarProofOff", baseInfoDriver.getNetworkCarProofOff());
        messageMap.put("RegisterDate", baseInfoDriver.getRegisterDate());
        messageMap.put("CommercialType", baseInfoDriver.getCommercialType());
        messageMap.put("ContractCompany", baseInfoDriver.getContractCompany());
        messageMap.put("ContractOn", baseInfoDriver.getContractOn());
        messageMap.put("ContractOff", baseInfoDriver.getContractOff());
        messageMap.put("State", baseInfoDriver.getState());
        messageMap.put("Flag", 2);
        messageMap.put("UpdateTime", now());

        return true;
    }

    /**
     * 监听到删除操作
     *
     * @param id 主键
     * @return 是否为合法上报数据
     */
    @Override
    public boolean delete(Integer id) {
        BaseInfoDriverDto baseInfoDriver = baseInfoDriverMapper.getBaseInfoDriver(id);
        messageMap.put("DriverPhone", baseInfoDriver.getDriverPhone());
        messageMap.put("DriverGender", baseInfoDriver.getDriverGender());
        messageMap.put("DriverBirthday", baseInfoDriver.getDriverBirthday());
        messageMap.put("DriverNation", baseInfoDriver.getDriverNation());
        messageMap.put("DriverMaritalStatus", baseInfoDriver.getDriverMaritalStatus());
        messageMap.put("drverLanguageLevel", baseInfoDriver.getDrverLanguageLevel());
        messageMap.put("DriverEducation", baseInfoDriver.getDriverEducation());
        messageMap.put("DriverCensus", baseInfoDriver.getDriverCensus());
        messageMap.put("DriverAddress", baseInfoDriver.getDriverAddress());
        messageMap.put("driverContactAddress", baseInfoDriver.getDriverContactAddress());
        messageMap.put("Licenseld", baseInfoDriver.getLicenseld());
        messageMap.put("GetDriverLicenseDate", baseInfoDriver.getGetDriverLicenseDate());
        messageMap.put("DriverLicenseOn", baseInfoDriver.getDriverLicenseOn());
        messageMap.put("DriverLicenseOff", baseInfoDriver.getDriverLicenseOff());
        messageMap.put("TaxiDriver", baseInfoDriver.getTaxiDriver());
        messageMap.put("CertificateNo", baseInfoDriver.getCertificateNo());
        messageMap.put("NetworkCarIssueOrganization", baseInfoDriver.getNetworkCarIssueOrGanization());
        messageMap.put("NetworkCarlssueDate", baseInfoDriver.getNetworkCarlssueDate());
        messageMap.put("GetNetworkCarProoDate", baseInfoDriver.getGetNetworkCarProoDate());
        messageMap.put("NetworkCarProofOn", baseInfoDriver.getNetworkCarProofOn());
        messageMap.put("NetworkCarProofOff", baseInfoDriver.getNetworkCarProofOff());
        messageMap.put("RegisterDate", baseInfoDriver.getRegisterDate());
        messageMap.put("CommercialType", baseInfoDriver.getCommercialType());
        messageMap.put("ContractCompany", baseInfoDriver.getContractCompany());
        messageMap.put("ContractOn", baseInfoDriver.getContractOn());
        messageMap.put("ContractOff", baseInfoDriver.getContractOff());
        messageMap.put("State", baseInfoDriver.getState());
        messageMap.put("Flag", 3);
        messageMap.put("UpdateTime", now());

        return true;
    }
}
