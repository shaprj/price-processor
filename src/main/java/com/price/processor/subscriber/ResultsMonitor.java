package com.price.processor.subscriber;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
@Slf4j
public class ResultsMonitor {

    @Autowired
    private List<OnlyOnPriceSubscriber> subscriberList;

    private ConcurrentMap<String, List<String>> resultsMap = new ConcurrentHashMap<>();

    synchronized public void notify(String ccyPair, double rate, String subscriber) {
        final String sRate = String.format("%.2f", rate);
        final String key = ccyPair + sRate;
        if (!resultsMap.containsKey(key) || resultsMap.get(key) == null) {
            resultsMap.put(key, new ArrayList<>());
        }
        List<String> array = resultsMap.get(key);
        array.add(subscriber);
//        log.info("Monitor: {}: RatesChanged: {} -> {}", subscriber, ccyPair, sRate);
        if (array.size() % subscriberList.size() == 0) {
            log.info("!!!!!!!!!Closed rate = {} for all {} subscribers!!!!!! ", key, subscriberList.size());
        }
    }
}
