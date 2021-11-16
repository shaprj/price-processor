package com.price.processor.subscriber;

import com.price.processor.PriceProcessor;

public abstract class OnlyOnPriceSubscriber implements PriceProcessor {

    @Override
    public void subscribe(PriceProcessor priceProcessor) {
        throw new UnsupportedOperationException("subscribe is not implementd for OnlyOnPriceSubscriber children");
    }

    @Override
    public void unsubscribe(PriceProcessor priceProcessor) {
        throw new UnsupportedOperationException("unsubscribe is not implementd for OnlyOnPriceSubscriber children");
    }
}
