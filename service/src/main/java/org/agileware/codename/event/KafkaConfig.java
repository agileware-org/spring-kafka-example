package org.agileware.codename.event;

import org.apache.kafka.common.TopicPartition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.util.backoff.FixedBackOff;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class KafkaConfig {
	@Bean
	public SeekToCurrentErrorHandler errorHandler(KafkaOperations<Object, Object> template) {
		return new SeekToCurrentErrorHandler(new DeadLetterPublishingRecoverer(template, (cr, e) -> new TopicPartition(cr.topic() + ".dlt", cr.partition())), new FixedBackOff(1000L, 2));
	}
}
