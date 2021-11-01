package lv.maksimssenko.app.controller;

import lv.maksimssenko.app.dto.Response;
import lv.maksimssenko.calculator.dto.Policy;
import lv.maksimssenko.calculator.service.PremiumCalculator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculatorController {

    private final PremiumCalculator premiumCalculator;

    public CalculatorController(PremiumCalculator premiumCalculator) {
        this.premiumCalculator = premiumCalculator;
    }

    @PostMapping("/calculate")
    public Response calculate(@RequestBody(required = false) Policy policy) {
        Response resp = new Response();
        try {
            resp.setPremium(premiumCalculator.calculate(policy));
        } catch (IllegalArgumentException e) {
            resp.setError(e.getLocalizedMessage());
        }
        return resp;
    }
}
