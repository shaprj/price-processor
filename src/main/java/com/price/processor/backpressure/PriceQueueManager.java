package com.price.processor.backpressure;

import com.price.processor.model.PriceEvent;

public interface PriceQueueManager {

    void push(PriceEvent e);

}
