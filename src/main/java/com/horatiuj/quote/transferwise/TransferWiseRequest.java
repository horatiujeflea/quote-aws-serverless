package com.horatiuj.quote.transferwise;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.horatiuj.quote.core.Constants.FIXED_RATE_TYPE;
import static com.horatiuj.quote.core.Constants.PROFILE_ID;
import static com.horatiuj.quote.core.CurrencyType.*;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransferWiseRequest {
    private final String profile = PROFILE_ID;
    private final String source = GBP.toString();
    private String target;
    private final String rateType = FIXED_RATE_TYPE;
    private Double sourceAmount;

    public TransferWiseRequest(String target, Double sourceAmount) {
        this.target = target;
        this.sourceAmount = sourceAmount;
    }
}
