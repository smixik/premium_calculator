package lv.maksimssenko.calculator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PolicyObject {
    private String name;
    private List<PolicySubObject> subObjects;
}
