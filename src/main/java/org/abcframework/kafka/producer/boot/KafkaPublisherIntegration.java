package org.abcframework.kafka.producer.boot;

public interface KafkaPublisherIntegration {

  void send(String messageKey, String payload);
}
