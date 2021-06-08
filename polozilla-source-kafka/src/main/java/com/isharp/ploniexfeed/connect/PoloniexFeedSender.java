package com.isharp.ploniexfeed.connect;

import org.apache.kafka.streams.KeyValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;

import java.util.function.Supplier;

public class PoloniexFeedSender {
    private static final Logger logger = LoggerFactory.getLogger(PoloniexFeedSender.class);


    public PoloniexFeedSender() {
        logger.info("Starting up feed sender");
    }

    @Bean
    public Supplier<KeyValue<String,String>> poloniexRawMessageRecieved() {
        return () -> {
            try {
                Thread.sleep(1000);
                logger.info("Sending a value to polo feeed");
                return KeyValue.pair("timestampkey_" + Long.toString(System.currentTimeMillis()),"valuepart");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
    }

}
