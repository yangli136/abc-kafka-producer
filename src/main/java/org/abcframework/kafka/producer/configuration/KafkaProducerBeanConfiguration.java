package org.abcframework.kafka.producer.configuration;

import java.util.List;

import org.abcframework.kafka.producer.service.impl.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.validation.annotation.Validated;

@Configuration
@EnableKafka
@PropertySource("classpath:kafka-producer-${app.stack}.properties")
public class KafkaProducerBeanConfiguration<K, V> {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducerBeanRegistration.class);

  @Autowired private ApplicationContext applicationContext;

  @Bean("kafkaProducerConfiguration")
  @ConfigurationProperties(prefix = "abc.kafka")
  @Validated
  public KafkaProducerConfiguration<K, V> kafkaProducerConfiguration() {
    return new KafkaProducerConfiguration<>();
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  @Bean("kafkaTemplateList")
  @DependsOn("kafkaProducerConfiguration")
  public List<KafkaTemplate> registerKafkaTemplateList(ProducerListener<K, V> producerListener) {
    KafkaTemplateBeanRegistration kafkaTemplateBeanRegistration =
        new KafkaTemplateBeanRegistration(
            applicationContext, kafkaProducerConfiguration(), producerListener);
    return kafkaTemplateBeanRegistration.registerKafkaTemplateList();
  }

  @SuppressWarnings("rawtypes")
  @Bean("kafkaProducerList")
  @DependsOn("kafkaTemplateList")
  public List<KafkaProducer> registerKafkaProducerList(
      ListenableFutureCallback<SendResult<K, V>> callback) {
    KafkaProducerBeanRegistration<K, V> kafkaProducerBeanRegistration =
        new KafkaProducerBeanRegistration<>(
            applicationContext, kafkaProducerConfiguration(), callback);
    return kafkaProducerBeanRegistration.registerKafkaProducerList();
  }
}
