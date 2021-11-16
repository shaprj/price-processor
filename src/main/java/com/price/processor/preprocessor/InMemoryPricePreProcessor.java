package com.price.processor.preprocessor;

import com.price.processor.backpressure.PriceQueueManager;
import com.price.processor.model.PriceEvent;
import com.price.processor.preprocessor.filter.PricePreProcessorFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class InMemoryPricePreProcessor implements PricePreProcessor {

    @Autowired
    private List<PricePreProcessorFilter> filters;

    @Autowired
    PriceQueueManager queueManager;

    @Override
    public void proceedAndEmitIfNeeded(PriceEvent e) {
        for (PricePreProcessorFilter filter : filters) {
            if (filter.filtered(e)) {
                log.info("Event {} filtered by {} ", e, filter.name());
                return;
            }
        }
        log.info("Event {} successfully preprocessed and emited to PriceThrottler", e);
        queueManager.push(e);
    }
}
