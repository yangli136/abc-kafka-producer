package org.abcframework.kafka.producer.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

@Configuration
@PropertySource("classpath:kafka-producer-${app.stack}.properties")
public class TestKafkaProducerConfiguration<K, V> {
  @SuppressWarnings("unused")
  private static final Logger LOGGER =
      LoggerFactory.getLogger(TestKafkaProducerConfiguration.class);

  @Bean("kafkaProducerConfiguration")
  @ConfigurationProperties(prefix = "abc.kafka")
  @Validated
  public KafkaProducerConfiguration<K, V> kafkaProducerConfiguration() {
    return new KafkaProducerConfiguration<>();
  }
}
