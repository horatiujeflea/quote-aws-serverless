package com.horatiuj.quote.transferwise;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransferWiseResponse {
    private Integer id;
    private String source;
    private String target;
    private Double sourceAmount;
    private Double targetAmount;
    private Double rate;
    private Double fee;
}
