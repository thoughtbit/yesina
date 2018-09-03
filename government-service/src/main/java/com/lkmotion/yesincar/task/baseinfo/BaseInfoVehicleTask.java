package com.lkmotion.yesincar.task.baseinfo;

import com.lkmotion.yesincar.dto.baseinfo.BaseInfoVehicleDto;
import com.lkmotion.yesincar.mapper.BaseInfoMapper;
import com.lkmotion.yesincar.task.AbstractSupervisionTask;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 车辆基本信息
 *
 * @author lizhaoteng
 * @date 2018/8/29
 **/
@Component
@RequiredArgsConstructor
@Slf4j
public class BaseInfoVehicleTask extends AbstractSupervisionTask {

    @NonNull
    private BaseInfoMapper baseInfoMapper;

    @Override
    public boolean insert(Integer id) {
        return combine(baseInfoMapper.getBaseInfoVehicle(id), 1);
    }

    /**
     * 监听到更新操作
     *
     * @param id 主键
     * @return 是否为合法上报数据
     */
    @Override
    public boolean update(Integer id) {
        return combine(baseInfoMapper.getBaseInfoVehicle(id), 2);
    }

    /**
     * 监听到删除操作
     *
     * @param id 主键
     * @return 是否为合法上报数据
     */
    @Override
    public boolean delete(Integer id) {
        return combine(baseInfoMapper.getBaseInfoVehicle(id), 3);
    }

    /**
     * 组装上报数据
     *
     * @param dto  车辆基本信息DTO
     * @param flag 操作标识
     * @return 是否为合法上报数据
     */
    private boolean combine(BaseInfoVehicleDto dto, int flag) {
        messageMap.put("VehicleNo", dto.getPlateNumber());
        messageMap.put("PlateColor", dto.getPlateColor());
        messageMap.put("Seats", dto.getSeats());
        messageMap.put("Brand", dto.getBrand());
        messageMap.put("Model", dto.getModel());
        messageMap.put("VehicleType", dto.getCarBaseType());
        messageMap.put("OwnerName ", dto.getCarOwner());
        messageMap.put("VehicleColor", dto.getColor());
        messageMap.put("EngineId ", dto.getEngineNumber());
        messageMap.put("VIN", dto.getVinNumber());
        messageMap.put("CertifyDateA", formatDateTime(dto.getRegisterTime(), DateTimePatternEnum.Date));
        messageMap.put("FuelType", dto.getFuelType());
        messageMap.put("EngineDisplace", dto.getEngineCapacity());
        messageMap.put("TransAgency", dto.getTransportIssuingAuthority());
        messageMap.put("TransArea ", dto.getBusinessArea());
        messageMap.put("TransDateStart", formatDateTime(dto.getTransportCertificateValidityStart(), DateTimePatternEnum.Date));
        messageMap.put("TransDateStop", formatDateTime(dto.getTransportCertificateValidityEnd(), DateTimePatternEnum.Date));
        messageMap.put("CertifyDateB", formatDateTime(dto.getFirstRegisterTime(), DateTimePatternEnum.Date));
        messageMap.put("FixState", dto.getStateOfRepair());
        messageMap.put("CheckState", dto.getAnnualAuditStatus());
        messageMap.put("FeePrintId", dto.getInvoicePrintingEquipmentNumber());
        messageMap.put("GPSBrand", dto.getGpsBrand());
        messageMap.put("gpsModel", dto.getGpsModel());
        messageMap.put("gpsInstallTime", formatDateTime(dto.getGpsInstallTime(), DateTimePatternEnum.Date));
        messageMap.put("RegisterDate ", formatDateTime(dto.getReportTime(), DateTimePatternEnum.Date));
        messageMap.put("Commercial-Type", dto.getServiceType());
        messageMap.put("FareType ", dto.getChargeTypeCode());
        messageMap.put("State", dto.getUseStatus());
        messageMap.put("Flag", flag);
        messageMap.put("UpdateTime", now());

        return true;
    }

}
