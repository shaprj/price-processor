package com.price.processor.subscriber;

import com.price.processor.PriceProcessor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Slf4j
@Service
public class S3 extends OnlyOnPriceSubscriber {

    @Autowired
    private ResultsMonitor monitor;

    @Autowired
    private PriceProcessor processor;

    @PostConstruct
    void subscribe() {
        processor.subscribe(this);
    }

    @SneakyThrows
    @Override
    public void onPrice(String ccyPair, double rate) {

        Thread.sleep(30000);

        monitor.notify(ccyPair, rate, "S3");
    }
}
