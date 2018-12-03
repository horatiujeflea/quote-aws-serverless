package com.horatiuj.quote.payment;

import com.horatiuj.quote.core.CurrencyType;
import com.horatiuj.quote.dal.dao.PaymentLog;
import com.horatiuj.quote.dal.dao.User;
import com.horatiuj.quote.transferwise.TransferWiseResponse;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class ProcessPaymentTest {

    private ProcessPayment c;

    @BeforeEach
    void setUp() {
        c = new ProcessPayment();
    }

    @Test
    void happyFlowTest() {
        final User user = new User("userId123", "John", "Doe", CurrencyType.GBP.toString());
        TransferWiseResponse twresp = new TransferWiseResponse(1, CurrencyType.GBP.toString(), CurrencyType.EUR.toString(), 500.0, 560.0, 0.9, 3.5);
        PaymentLog paymentLog = new PaymentLog("paymentId123", user, twresp);

        c.accept(ProcessPayment.Input.builder()
                .paymentAmount(500.0)
                .paymentId("paymentId123")
                .userId("userId123")
                .getUser(userId -> Optional.of(user))
                .getTransferWiseQuote(request -> Optional.of(twresp))
                .savePaymentLog(pl -> Assert.assertEquals(pl, paymentLog))
                .build());
    }

}