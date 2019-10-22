package org.abcframework.kafka.producer.boot.impl;

import javax.validation.Valid;
import org.abcframework.kafka.producer.boot.KafkaPublisherIntegration;
import org.abcframework.kafka.producer.service.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@DependsOn({"kafkaProducerList"})
@Qualifier("producer0")
@Service
@Validated
public class KafkaPublisherIntegrationImpl implements KafkaPublisherIntegration {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = LoggerFactory.getLogger(KafkaPublisherIntegrationImpl.class);

  private final Producer<String, String> producer;

  public KafkaPublisherIntegrationImpl(
      @Qualifier("producer0") @Valid Producer<String, String> producer) {
    this.producer = producer;
  }

  @Override
  public void send(final String key, final String message) {
    this.producer.send(key, message);
  }
}
