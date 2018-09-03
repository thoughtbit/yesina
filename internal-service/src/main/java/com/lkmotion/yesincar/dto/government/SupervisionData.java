package com.lkmotion.yesincar.dto.government;

import com.lkmotion.yesincar.constatnt.CodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 上报对象DTO
 *
 * @author ZhuBin
 * @date 2018/8/23
 */
@Data
@Accessors(chain = true)
public class SupervisionData<T> implements Serializable {

    @Getter
    @AllArgsConstructor
    public enum OperationType implements CodeEnum {
        /**
         * 插入操作
         */
        Insert(0, "insert"),
        /**
         * 更新操作
         */
        Update(1, "update"),
        /**
         * 删除操作
         */
        Delete(2, "delete");

        private int code;
        private String name;
    }

    private OperationType operationType;
    private String className;
    private Integer id;
}
