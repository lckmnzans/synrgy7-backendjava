package com.synrgy.binarfud.Binarfud.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

public class KafkaTopicConfig {
    @Bean
    public NewTopic messagNewTopic() {
        return TopicBuilder
            .name("order-status")
            .build();
    }

    @Bean
    public NewTopic notifNewTopic() {
        return TopicBuilder
            .name("promo-notif")
            .build();
    }
}
