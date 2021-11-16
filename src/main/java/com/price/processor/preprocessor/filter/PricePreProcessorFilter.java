package com.price.processor.preprocessor.filter;

import com.price.processor.model.PriceEvent;

public interface PricePreProcessorFilter {

    boolean filtered(PriceEvent e);

    String name();

}
