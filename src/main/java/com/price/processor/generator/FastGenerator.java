package com.price.processor.generator;

import com.price.processor.model.PriceEvent;
import com.price.processor.preprocessor.PricePreProcessor;
import com.price.processor.utils.GeneratorHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class FastGenerator {

    @Value("#{new Double('${price.rate.low}')}")
    private Double low;

    @Value("#{new Double('${price.rate.high}')}")
    private Double high;

    @Autowired
    private PricePreProcessor preProcessor;

    @Scheduled(fixedRate = 10)
    void generate() {
        PriceEvent e = GeneratorHelper.build(high, low, this::getFastCcy);
        preProcessor.proceedAndEmitIfNeeded(e);
    }

    private String getFastCcy() {
        return "EURUSD";
    }
}
