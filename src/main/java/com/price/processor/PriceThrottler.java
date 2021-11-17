package com.price.processor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@Primary
@Slf4j
public class PriceThrottler implements PriceProcessor {

    private Map<UUID, PriceProcessor> subscriberMap = new ConcurrentHashMap<>();

    @Override
    public void onPrice(String ccyPair, double rate) {
        subscriberMap.values().parallelStream().forEach(p -> {
            if (p == this) {
                return;
            }
            p.onPrice(ccyPair, rate);
        });
    }

    @Override
    public void subscribe(PriceProcessor priceProcessor) {
        final UUID uuid = UUID.randomUUID();
        subscriberMap.put(uuid, priceProcessor);
        log.info("Subscribed processor with uuid: {}", uuid);
    }

    @Override
    public void unsubscribe(PriceProcessor priceProcessor) {
        final List<UUID> uuidList = subscriberMap.keySet().stream()
                .filter(k -> subscriberMap.get(k).equals(priceProcessor))
                .collect(Collectors.toList());
        uuidList.forEach(uuid -> {
            subscriberMap.remove(uuid);
            log.info("Subscribed processor with uuid: {}", uuid);
        });
    }
}
