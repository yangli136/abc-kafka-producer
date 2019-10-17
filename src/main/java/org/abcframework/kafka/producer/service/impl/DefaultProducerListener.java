package org.abcframework.kafka.producer.service.impl;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.LoggingProducerListener;
import org.springframework.stereotype.Service;

@Service
public class DefaultProducerListener<K, V> extends LoggingProducerListener<K, V> {
  private static final Logger LOGGER = LoggerFactory.getLogger(DefaultProducerListener.class);

  @Override
  public void onSuccess(
      String topic, Integer partition, K key, V value, RecordMetadata recordMetadata) {
    LOGGER.info(
        "sent a message. topic:{}, partition:{}, key:{}, value:{}, recordMetadata:{}",
        topic,
        partition,
        key,
        value,
        recordMetadata);
  }
}
