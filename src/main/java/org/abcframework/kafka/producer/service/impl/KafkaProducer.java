package org.abcframework.kafka.producer.service.impl;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.abcframework.kafka.producer.service.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.validation.annotation.Validated;

@Validated
public class KafkaProducer<K, V> implements Producer<K, V> {
  private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);

  @NotNull private final ListenableFutureCallback<SendResult<K, V>> callback;
  @NotNull private final KafkaTemplate<K, V> kafkaTemplate;

  @Min(-1)
  private final int partition;

  @NotBlank private final String topic;

  public KafkaProducer(
      KafkaTemplate<K, V> kafkaTemplate,
      String topic,
      int partition,
      ListenableFutureCallback<SendResult<K, V>> callback) {
    this.callback = callback;
    this.kafkaTemplate = kafkaTemplate;
    this.topic = topic;
    this.partition = partition;
  }

  @Override
  public ListenableFuture<SendResult<K, V>> send(K messageKey, V payload) {
    LOGGER.info("************* partition:{}", partition);
    final ListenableFuture<SendResult<K, V>> future;
    if (partition < 0) {
      LOGGER.info("************* send to random partition...");
      future = kafkaTemplate.send(topic, messageKey, payload);
    } else {
      LOGGER.info("************* send to partition:{}", partition);
      future = kafkaTemplate.send(topic, partition, messageKey, payload);
    }

    future.addCallback(this.callback);

    return future;
  }

  @Override
  public ListenableFuture<SendResult<K, V>> send(Long timestamp, Object key, Object data) {
    throw new UnsupportedOperationException("Method not supported yet.");
  }

  @Override
  public ListenableFuture<SendResult<K, V>> send(ProducerRecord<K, V> record) {
    throw new UnsupportedOperationException("Method not supported yet.");
  }
}
