package com.price.processor.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(toBuilder = true)
public class PriceEvent {
    private String ccyPair;
    private double rate;

    @Override
    public String toString() {
        return String.format("PriceEvent(%s, %.2f)", ccyPair, rate);
    }
}
