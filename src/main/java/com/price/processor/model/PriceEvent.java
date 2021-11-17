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
    private long timestamp;

    @Override
    public String toString() {
        return ccyPair.equals("EURRUB") ?
                String.format(" ==!!!SLOW!!!== PriceEvent(%s, %.2f)", ccyPair, rate) :
                String.format("PriceEvent(%s, %.2f)", ccyPair, rate);
    }
}
