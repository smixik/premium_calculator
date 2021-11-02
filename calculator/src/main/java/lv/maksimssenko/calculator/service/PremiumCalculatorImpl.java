package lv.maksimssenko.calculator.service;

import lv.maksimssenko.calculator.dto.Policy;
import lv.maksimssenko.calculator.dto.PolicySubObject;
import lv.maksimssenko.calculator.dto.RiskType;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.stream.Collectors;

@Service
class PremiumCalculatorImpl implements PremiumCalculator {

    @Override
    public BigDecimal calculate(Policy policy) throws IllegalArgumentException {

        validate(policy);

        Map<RiskType, BigDecimal> subObjectsGroupedByRisk = policy.getObjects().stream()
                .flatMap(ob -> ob.getSubObjects().stream())
                .filter(sOb -> sOb.getSumInsured() != null)
                .collect(Collectors.toMap(sOb -> RiskType.valueOf(sOb.getRiskType()), PolicySubObject::getSumInsured, BigDecimal::add));

        return subObjectsGroupedByRisk.entrySet().stream()
                .map(entry -> premiumSumByRiskType(entry.getKey(), entry.getValue()))
                .reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_EVEN);
    }

    private BigDecimal premiumSumByRiskType(RiskType riskType, BigDecimal totalSum){
        BigDecimal result = BigDecimal.ZERO;
        switch (riskType){
            case FIRE:
                result = totalSum.multiply(totalSum.compareTo(BigDecimal.valueOf(100)) > 0
                        ? BigDecimal.valueOf(0.024)
                        : BigDecimal.valueOf(0.014));
                break;
            case THEFT:
                result = totalSum.multiply(totalSum.compareTo(BigDecimal.valueOf(15)) >= 0
                        ? BigDecimal.valueOf(0.05)
                        : BigDecimal.valueOf(0.11));
                break;
            // implement new RISK here
        }
        return result;
    }

    private void validate(Policy policy) {
        if (policy == null) {
            throw new IllegalArgumentException("Policy must be provided!");
        } else if (CollectionUtils.isEmpty(policy.getObjects())){
            throw new IllegalArgumentException("Policy objects must be provided!");
        } else if (policy.getObjects()
                .stream().flatMap(o -> o.getSubObjects().stream())
                .anyMatch(s -> !EnumUtils.isValidEnum(RiskType.class, s.getRiskType()))){
            throw new IllegalArgumentException("Unsupported policy sub-object risk type!");
        }
    }
}
