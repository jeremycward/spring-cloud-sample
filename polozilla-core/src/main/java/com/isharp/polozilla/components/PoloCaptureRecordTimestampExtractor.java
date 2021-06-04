package com.isharp.polozilla.components;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import org.apache.kafka.streams.processor.TimestampExtractor;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class PoloCaptureRecordTimestampExtractor implements TimestampExtractor {
    public static final DateTimeFormatter fmt  = DateTimeFormatter.ofPattern("yyyy-MM-dd:HHmm:ss");

    @Override
    public long extract(ConsumerRecord<Object, Object> record, long partitionTime) {
        String key = (String) record.key();
        LocalDateTime ld = LocalDateTime.parse(key,fmt);
        return ld.toInstant(ZoneOffset.UTC).toEpochMilli();
    }


}
