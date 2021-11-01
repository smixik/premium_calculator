package lv.maksimssenko.calculator.service;

import lv.maksimssenko.calculator.dto.Policy;

import java.math.BigDecimal;

public interface PremiumCalculator {
    BigDecimal calculate(Policy policy) throws IllegalArgumentException;
}
