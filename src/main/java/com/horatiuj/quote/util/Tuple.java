package com.horatiuj.quote.util;

import lombok.Data;

@Data
public class Tuple<X, Y> {
    private final X x;
    private final Y y;
}