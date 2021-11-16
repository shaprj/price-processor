package com.price.processor.generator;

import com.price.processor.model.PriceEvent;
import com.price.processor.preprocessor.PricePreProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class Generator {

    @Value("#{new Double('${price.rate.low}')}")
    private Double low;

    @Value("#{new Double('${price.rate.high}')}")
    private Double high;

    @Autowired
    private PricePreProcessor preProcessor;

    @Scheduled(fixedRate = 10)
    void generate() {
        PriceEvent e = build();
        preProcessor.proceedAndEmitIfNeeded(e);
    }

    private PriceEvent build() {
        PriceEvent e = PriceEvent
                .builder()
                .ccyPair(generateCcy())
                .rate(generateRate())
                .build();
        return e;
    }

    private String generateCcy() {
        final List<String> currencies = List.of("RUR", "USD", "EUR", "CHF", "KZT", "TJS", "BTC", "ETH", "LTC", "XRP", "QRK");
        return String.format("%s%s", getRandomArrayIndex(currencies), getRandomArrayIndex(currencies));
    }

    private double generateRate() {
        return (new Random().nextDouble() * (high - low)) + low;
    }

    private <T> T getRandomArrayIndex(List<T> list) {
        return list.get(new Random().nextInt(list.size()));
    }

}
