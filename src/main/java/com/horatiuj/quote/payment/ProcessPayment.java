package com.horatiuj.quote.payment;

import com.horatiuj.quote.transferwise.GetTransferWiseQuote;
import com.horatiuj.quote.transferwise.TransferWiseRequest;
import com.horatiuj.quote.transferwise.TransferWiseResponse;
import com.horatiuj.quote.dal.dao.PaymentLog;
import com.horatiuj.quote.dal.dao.User;
import com.horatiuj.quote.dal.payment_log.SavePaymentLog;
import com.horatiuj.quote.dal.user.GetUser;
import com.horatiuj.quote.util.Tuple;
import lombok.Builder;
import lombok.Data;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class ProcessPayment implements Consumer<ProcessPayment.Input> {
    @Override
    public void accept(ProcessPayment.Input input) {
        Objects.requireNonNull(input);
        input.getUser.apply(input.getUserId())
                .map(u -> new Tuple<>(u, input.getTransferWiseQuote.apply(new TransferWiseRequest(u.getPayoutCurrency(), input.getPaymentAmount()))))
                .filter(t -> t.getY().isPresent())
                .map(t -> new PaymentLog(input.getPaymentId(), t.getX(), t.getY().get()))
                .ifPresent(input.savePaymentLog);
    }

    @Builder
    @Data
    public static class Input {
        private final String userId;
        private final String paymentId;
        private final Double paymentAmount;
        @Builder.Default
        private final Function<String, Optional<User>> getUser = new GetUser();
        @Builder.Default
        private final Function<TransferWiseRequest, Optional<TransferWiseResponse>> getTransferWiseQuote = new GetTransferWiseQuote();
        @Builder.Default
        private final Consumer<PaymentLog> savePaymentLog = new SavePaymentLog();
    }
}
