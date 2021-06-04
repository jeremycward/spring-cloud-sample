package com.isharp.ploniexfeed;


import org.apache.kafka.streams.KeyValue;

import java.util.function.Consumer;
public interface PoloniexRawMessageConsumer {
    Consumer<KeyValue<String,String>> whenReady();
}
