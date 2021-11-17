package com.price.processor.preprocessor.filter;

import com.price.processor.model.PriceEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class FrequencyPricePreProcessorFilter implements PricePreProcessorFilter {

    @Value("#{new Integer('${frequency.preprocessor.timelimit}')}")
    private Integer timeLimit;

    private Map<String, Long> lastTimestampMap = new ConcurrentHashMap<>();

    @Override
    public boolean filtered(PriceEvent e) {
        if (lastTimestampMap.containsKey(e.getCcyPair())) {
            if (Math.abs(e.getTimestamp() - lastTimestampMap.get(e.getCcyPair())) >= timeLimit) {
                lastTimestampMap.put(e.getCcyPair(), e.getTimestamp());
                return false;
            } else {
                return true;
            }
        }
        lastTimestampMap.put(e.getCcyPair(), e.getTimestamp());
        return true;
    }

    @Override
    public String name() {
        return "FrequencyPricePreProcessorFilter";
    }
}
