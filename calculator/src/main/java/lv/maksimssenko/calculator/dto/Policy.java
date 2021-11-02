package lv.maksimssenko.calculator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Policy {
    private String number;
    private String status;
    private List<PolicyObject> objects;
}
