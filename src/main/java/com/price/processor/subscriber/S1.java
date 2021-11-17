package com.price.processor.subscriber;

import com.price.processor.PriceProcessor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Service
public class S1 extends OnlyOnPriceSubscriber {

    private static AtomicBoolean firstTime = new AtomicBoolean(true);

    @Autowired
    private PriceProcessor processor;

    @Autowired
    private ResultsMonitor monitor;

    @PostConstruct
    void subscribe() {
        processor.subscribe(this);
    }

    @SneakyThrows
    @Override
    public void onPrice(String ccyPair, double rate) {

        Thread.sleep(30000);

        monitor.notify(ccyPair, rate, "S1");
    }
}
