package com.price.processor.generator;

import com.price.processor.model.PriceEvent;
import com.price.processor.preprocessor.PricePreProcessor;
import com.price.processor.utils.GeneratorHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SlowGenerator {

    @Value("#{new Double('${price.rate.low}')}")
    private Double low;

    @Value("#{new Double('${price.rate.high}')}")
    private Double high;

    @Autowired
    private PricePreProcessor preProcessor;

    @Scheduled(fixedRate = 30_000)
    void generate() {
        PriceEvent e = GeneratorHelper.build(high, low, this::getSlowCcy);
        preProcessor.proceedAndEmitIfNeeded(e);
    }

    private String getSlowCcy() {
        return "EURRUB";
    }

}
