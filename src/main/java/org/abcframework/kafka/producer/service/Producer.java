package org.abcframework.kafka.producer.service;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

public interface Producer<K, V> {
  public ListenableFuture<SendResult<K, V>> send(K messageKey, V payload);

  public ListenableFuture<SendResult<K, V>> send(Long timestamp, K key, V data);

  public ListenableFuture<SendResult<K, V>> send(ProducerRecord<K, V> record);
}
