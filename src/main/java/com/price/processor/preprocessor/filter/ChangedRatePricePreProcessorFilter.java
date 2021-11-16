package com.price.processor.preprocessor.filter;

import com.price.processor.model.PriceEvent;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChangedRatePricePreProcessorFilter implements PricePreProcessorFilter {

    private Map<String, Double> lastValueMap = new ConcurrentHashMap<>();

    @Override
    public boolean filtered(PriceEvent e) {
        if (lastValueMap.containsKey(e.getCcyPair())) {
            if (lastValueMap.get(e.getCcyPair()) == e.getRate()) {
                return true;
            }
        }
        lastValueMap.put(e.getCcyPair(), e.getRate());
        return false;
    }

    @Override
    public String name() {
        return "ChangedRatePricePreProcessorFilter";
    }
}
