package com.isharp.ploniexfeed.connect;

import org.apache.kafka.streams.KeyValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Supplier;

@Configuration
public class PoloniexFeedSender {
    private static final Logger logger = LoggerFactory.getLogger(PoloniexFeedSender.class);


   private final WebsocketLIstener websocketLIstener = new WebsocketLIstener();



    public PoloniexFeedSender() {
        logger.info("Starting up feed sender");
    }

    @Bean
    public WebsocketLIstener getWebsocketLIstener() {
        return websocketLIstener;
    }

    @Bean
    public Supplier<KeyValue<String,String>> poloniexRawMessageRecieved() {
        return () -> {
            try {
                logger.info("Waiting to take");
                String msg = websocketLIstener.getQ().take();
                logger.info("took message {}",msg);
                return KeyValue.pair("timestampkey_" + Long.toString(System.currentTimeMillis()),msg);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
    }

}
