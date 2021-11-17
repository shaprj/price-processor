package com.price.processor.utils;

import com.price.processor.model.PriceEvent;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class GeneratorHelper {
    public static long getCurrentTimekstamp() {
        return LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
    }

    public static String generateCcy() {
        final List<String> currencies = List.of("USD", "EUR", "CHF", "KZT", "TJS", "BTC", "ETH", "LTC", "XRP", "QRK");
        return String.format("%s%s", getRandomArrayIndex(currencies), getRandomArrayIndex(currencies));
    }

    public static double generateRate(double high, double low) {
        return (new Random().nextDouble() * (high - low)) + low;
    }

    public static <T> T getRandomArrayIndex(List<T> list) {
        return list.get(new Random().nextInt(list.size()));
    }

    public static PriceEvent build(double high, double low, Supplier<String> ccyNameGenerator) {
        PriceEvent e = PriceEvent
                .builder()
                .ccyPair(ccyNameGenerator.get())
                .rate(generateRate(high, low))
                .timestamp(getCurrentTimekstamp())
                .build();
        return e;
    }
}
