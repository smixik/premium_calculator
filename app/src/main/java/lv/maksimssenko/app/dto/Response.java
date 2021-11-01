package lv.maksimssenko.app.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    @JsonProperty
    private BigDecimal premium;

    @JsonProperty
    private String error;

}
