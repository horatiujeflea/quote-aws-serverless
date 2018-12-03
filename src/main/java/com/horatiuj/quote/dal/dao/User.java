package com.horatiuj.quote.dal.dao;

import lombok.Data;

@Data
public class User {
    private final String id;
    private final String firstName;
    private final String lastName;
    private final String payoutCurrency;
}
