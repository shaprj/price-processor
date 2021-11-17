package com.price.processor.preprocessor;

import com.price.processor.backpressure.PriceQueueManager;
import com.price.processor.model.PriceEvent;
import com.price.processor.preprocessor.filter.PricePreProcessorFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class InMemoryPricePreProcessor implements PricePreProcessor {

    @Autowired
    private List<PricePreProcessorFilter> filters;

    @Autowired
    PriceQueueManager queueManager;

    private AtomicInteger eventsCount = new AtomicInteger(0);

    @Override
    public void proceedAndEmitIfNeeded(PriceEvent e) {

        if(eventsCount.get() > 20){
            return;
        }

        for (PricePreProcessorFilter filter : filters) {
            if (filter.filtered(e)) {
                return;
            }
        }

        eventsCount.incrementAndGet();

        log.info("Event {} successfully preprocessed and emited to PriceThrottler", e);
        queueManager.push(e);
    }
}
