package lv.maksimssenko.calculator.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class PolicyObject {

    private String name;
    private List<PolicySubObject> subObjects;

    public PolicyObject(String name, List<PolicySubObject> subObjects) {
        this.name = name;
        this.subObjects = subObjects == null ? new ArrayList<>() : subObjects;
    }

}
