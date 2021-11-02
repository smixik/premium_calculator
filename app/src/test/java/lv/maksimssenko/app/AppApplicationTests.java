package lv.maksimssenko.app;

import lv.maksimssenko.app.controller.CalculatorController;
import lv.maksimssenko.app.dto.Response;
import lv.maksimssenko.calculator.dto.Policy;
import lv.maksimssenko.calculator.dto.PolicyObject;
import lv.maksimssenko.calculator.dto.PolicySubObject;
import lv.maksimssenko.calculator.dto.RiskType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AppApplicationTests {

    @Autowired
    private CalculatorController controller;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void contextLoads() {
        assertNotNull(controller);
    }

    @Test
    public void givenDataIsValidPolicy_whenDataIsPosted_thenResponseIsNotNullWithPremiumSum() {

        PolicySubObject sObject1 = new PolicySubObject("TV", BigDecimal.valueOf(100), RiskType.FIRE.toString());
        PolicySubObject sObject2 = new PolicySubObject("TV2",BigDecimal.valueOf(8),RiskType.THEFT.toString());
        PolicyObject object = new PolicyObject("House", Arrays.asList(sObject1, sObject2));
        Policy policy = new Policy("NR1-23","SomeStatus",new ArrayList<>(Collections.singletonList(object)));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Policy> requestEntity = new HttpEntity<>(policy, headers);
        ResponseEntity<Response> result = restTemplate.postForEntity("http://localhost:" + port + "/calculate", requestEntity, Response.class);

        assertNotNull(result.getBody());
        assertEquals(BigDecimal.valueOf(2.28), result.getBody().getPremium());

        PolicySubObject sObject3 = new PolicySubObject("Car", BigDecimal.valueOf(400),RiskType.FIRE.toString());
        PolicySubObject sObject4 = new PolicySubObject("Bicycle",BigDecimal.valueOf(94.51),RiskType.THEFT.toString());
        PolicyObject object1 = new PolicyObject("Garage", Arrays.asList(sObject3, sObject4));
        policy.getObjects().add(object1);

        requestEntity = new HttpEntity<>(policy, headers);
        result = restTemplate.postForEntity("http://localhost:" + port + "/calculate", requestEntity, Response.class);

        assertNotNull(result.getBody());
        assertEquals(BigDecimal.valueOf(17.13), result.getBody().getPremium());
    }



}
