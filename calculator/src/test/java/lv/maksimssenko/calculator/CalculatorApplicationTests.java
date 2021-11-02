package lv.maksimssenko.calculator;

import lv.maksimssenko.calculator.dto.*;
import lv.maksimssenko.calculator.service.PremiumCalculator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import(CalculatorApplicationTests.TestConfig.class)
class CalculatorApplicationTests {

    @SpringBootConfiguration
    @ComponentScan("lv.maksimssenko.calculator")
    public static class TestConfig {
    }

    @Autowired
    private PremiumCalculator premiumCalculator;

    @Test
    void contextLoads() {
        assertNotNull(premiumCalculator);
    }

    @Test
    void calculate_shouldReturnPremiumSum_whenDataGiven() {
        PolicySubObject sObject1 = new PolicySubObject("TV", BigDecimal.valueOf(100), RiskType.FIRE.toString());
        PolicySubObject sObject2 = new PolicySubObject("TV2", BigDecimal.valueOf(8), RiskType.THEFT.toString());

        PolicyObject object = new PolicyObject("House", Arrays.asList(sObject1, sObject2));

        Policy policy = new Policy("N1-2", PolicyStatus.APPROVED.toString(), new ArrayList<>(Collections.singletonList(object)));

        assertEquals(BigDecimal.valueOf(2.28), premiumCalculator.calculate(policy));

        PolicySubObject sObject3 = new PolicySubObject("TV3", BigDecimal.valueOf(400), RiskType.FIRE.toString());
        PolicySubObject sObject4 = new PolicySubObject("TV4", BigDecimal.valueOf(94.51), RiskType.THEFT.toString());

        PolicyObject object2 = new PolicyObject("Villa", Arrays.asList(sObject3, sObject4));

        policy.getObjects().add(object2);

        assertEquals(BigDecimal.valueOf(17.13), premiumCalculator.calculate(policy));

        policy.getObjects().forEach(o -> o.setSubObjects(null));
        assertEquals(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN), premiumCalculator.calculate(policy));
    }

    @Test
    public void calculate_whenExceptionThrown_thenAssertionSucceeds() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            premiumCalculator.calculate(null);
        });

        assertTrue(exception.getMessage().contains("Policy must be provided!"));

        Policy policy = new Policy("N1-23-33", PolicyStatus.REGISTERED.toString(), new ArrayList<>());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            premiumCalculator.calculate(policy);
        });

        assertTrue(exception.getMessage().contains("Policy objects must be provided!"));


        PolicySubObject sObject1 = new PolicySubObject("TV", BigDecimal.valueOf(100), null);
        PolicySubObject sObject2 = new PolicySubObject("NoteBook", BigDecimal.valueOf(8), RiskType.THEFT.toString());

        PolicyObject object = new PolicyObject("Flat", Arrays.asList(sObject1, sObject2));

        policy.getObjects().add(object);

        exception = assertThrows(IllegalArgumentException.class, () -> {
            premiumCalculator.calculate(policy);
        });

        assertTrue(exception.getMessage().contains("Unsupported policy sub-object risk type!"));

    }


}
