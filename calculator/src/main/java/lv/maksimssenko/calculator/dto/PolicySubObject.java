package lv.maksimssenko.calculator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PolicySubObject {
    private String name;
    private BigDecimal sumInsured;
    private String riskType;
}
