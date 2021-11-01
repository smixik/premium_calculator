package lv.maksimssenko.calculator.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.EnumUtils;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class PolicySubObject {
    private String name;
    private BigDecimal sumInsured;
    private String riskType;
    private RiskType riskTypeEnum;

    public PolicySubObject(String name, BigDecimal sumInsured, RiskType riskTypeEnum) {
        this.name = name;
        this.sumInsured = sumInsured;
        this.riskTypeEnum = riskTypeEnum;
    }

    public void setRiskType(String riskType) {
        this.riskType = riskType;
        if (riskType != null && EnumUtils.isValidEnum(RiskType.class, riskType.toUpperCase())) {
            this.riskTypeEnum = RiskType.valueOf(riskType.toUpperCase());
        }
    }
}
