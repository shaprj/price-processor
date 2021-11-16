package com.price.processor.preprocessor;

import com.price.processor.model.PriceEvent;

public interface PricePreProcessor {

    void proceedAndEmitIfNeeded(PriceEvent e);
}
