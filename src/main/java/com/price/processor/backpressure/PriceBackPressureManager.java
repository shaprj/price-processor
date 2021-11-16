package com.price.processor.backpressure;

import com.price.processor.PriceProcessor;
import com.price.processor.model.PriceEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

@Service
@Slf4j
public class PriceBackPressureManager implements PriceQueueManager {

    @Autowired
    private PriceProcessor priceThrottler;

    @Value("#{new Integer('${thread-pool.size}')}")
    private Integer threadPoolSize;

    private ExecutorService executorService;

    private BlockingQueue<PriceEvent> queue = new LinkedBlockingQueue(200);

    @Override
    public void push(PriceEvent e) {

        queue.add(e);
    }

    @PostConstruct
    void init() {
        executorService = Executors.newFixedThreadPool(threadPoolSize);
        System.out.println(1);
    }

    @Scheduled(fixedRate = 10)
    void startLoop() {
        runWithThreadPool(() -> {
            final PriceEvent e;
            try {
                e = queue.take();
                runWithThreadPool(() -> priceThrottler.onPrice(e.getCcyPair(), e.getRate()));
            } catch (InterruptedException ex) {
                log.error(ex.getMessage(), ex);
            }
        });
    }

    private void runWithThreadPool(Runnable runnable) {
        executorService.submit(runnable);
    }
}
