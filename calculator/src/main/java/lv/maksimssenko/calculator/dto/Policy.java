package lv.maksimssenko.calculator.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.EnumUtils;

import java.util.List;

@Data
@NoArgsConstructor
public class Policy {
    private String number;
    private String status;
    private PolicyStatus statusEnum;
    private List<PolicyObject> objects;

    public Policy(String number, PolicyStatus statusEnum, List<PolicyObject> objects) {
        this.number = number;
        this.statusEnum = statusEnum;
        this.objects = objects;
    }

    public void setStatus(String status) {
        this.status = status;
        if(status != null && EnumUtils.isValidEnum(PolicyStatus.class, status.toUpperCase())){
            this.statusEnum = PolicyStatus.valueOf(status.toUpperCase());
        }
    }
}
