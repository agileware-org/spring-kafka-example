package org.agileware.codename;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
@EnableKafka
public class KafkaConfig {

	@Bean
	public ReplyingKafkaTemplate<String, ?, ?> replyingTemplate(ProducerFactory<String, ?> factory, ConcurrentMessageListenerContainer<String, ?> repliesContainer) {
		return new ReplyingKafkaTemplate<>(factory, repliesContainer);
	}

	@Bean
	public KafkaTemplate<String, ?> replyTemplate(ProducerFactory<String, ?> producerFactory, ConcurrentKafkaListenerContainerFactory<String, ?> factory) {
		KafkaTemplate<String, ?> kafkaTemplate = new KafkaTemplate<>(producerFactory);
		factory.setReplyTemplate(kafkaTemplate);
		return kafkaTemplate;
	}

	@Bean
	public ConcurrentMessageListenerContainer<String, ?> repliesContainer(ConcurrentKafkaListenerContainerFactory<String, ?> containerFactory) {
		ConcurrentMessageListenerContainer<String, ?> repliesContainer = containerFactory.createContainer("replies");
		repliesContainer.getContainerProperties().setGroupId("request.replies");
		repliesContainer.setAutoStartup(false);
		return repliesContainer;
	}
}
