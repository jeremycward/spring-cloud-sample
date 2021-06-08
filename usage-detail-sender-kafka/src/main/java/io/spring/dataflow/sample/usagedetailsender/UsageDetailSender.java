package io.spring.dataflow.sample.usagedetailsender;

import java.util.Random;
import java.util.function.Supplier;

import io.spring.dataflow.sample.UsageDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UsageDetailSender {

    private String[] users = {"user1", "user2", "user3", "user4", "user5"};
    private static final Logger logger = LoggerFactory.getLogger(UsageDetailSender.class);

    public UsageDetailSender() {
        logger.info("starting UsageDetailSender");
    }

    @Bean
    public Supplier<UsageDetail> sendEvents() {
        return () -> {

            UsageDetail usageDetail = new UsageDetail();
            usageDetail.setUserId(this.users[new Random().nextInt(5)]);
            usageDetail.setDuration(new Random().nextInt(300));
            usageDetail.setData(new Random().nextInt(700));
            return usageDetail;
        };
    }
}