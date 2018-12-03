package com.horatiuj.quote.dal.dao;

import com.horatiuj.quote.transferwise.TransferWiseResponse;
import lombok.Data;

@Data
public class PaymentLog {
    private final String id;
    private final User user;
    private final TransferWiseResponse transferWiseResponse;
}
