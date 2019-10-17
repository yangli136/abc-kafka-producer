package org.abcframework.kafka.producer.service.impl;

import org.abcframework.kafka.producer.boot.impl.KafkaPublisherIntegrationProduder1Impl;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Qualifier("defaultProducerFutureCallback")
@Service
public class DefaultProducerFutureCallback<K, V>
    implements ListenableFutureCallback<SendResult<K, V>> {
  private static final Logger LOGGER =
      LoggerFactory.getLogger(KafkaPublisherIntegrationProduder1Impl.class);

  @Override
  public void onSuccess(SendResult<K, V> result) {
    final ProducerRecord<K, V> record = result.getProducerRecord();
    if (record != null) {
      LOGGER.info("Sent record:{}", record);
    } else {
      LOGGER.info(
          "Sent SendResult with a null record. RecordMetadata:{}", result.getRecordMetadata());
    }
  }

  @Override
  public void onFailure(Throwable ex) {
    LOGGER.info("Unable to send a message.", ex);
  }
}
