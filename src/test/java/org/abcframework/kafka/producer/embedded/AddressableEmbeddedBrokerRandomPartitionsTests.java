package org.abcframework.kafka.producer.embedded;

import java.util.Iterator;
import java.util.Map;
import org.abcframework.kafka.producer.boot.KafkaPublisherIntegration;
import org.abcframework.kafka.producer.boot.impl.KafkaPublisherIntegrationImpl;
import org.abcframework.kafka.producer.boot.impl.KafkaPublisherIntegrationProduder1Impl;
import org.abcframework.kafka.producer.service.impl.DefaultProducerFutureCallback;
import org.abcframework.kafka.producer.service.impl.DefaultProducerListener;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@EnableAutoConfiguration
@SpringJUnitConfig
@SpringBootTest(
    classes = {
      Config.class,
      KafkaProducerBeanConfigurationRandomPartitions.class,
      KafkaPublisherIntegrationImpl.class,
      KafkaPublisherIntegrationProduder1Impl.class,
      DefaultProducerFutureCallback.class,
      DefaultProducerListener.class
    },
    properties = {"app.stack=e0"})
public class AddressableEmbeddedBrokerRandomPartitionsTests {
  private static final Logger LOGGER =
      LoggerFactory.getLogger(AddressableEmbeddedBrokerRandomPartitionsTests.class);

  @Value("${abc.kafka.default.topic}")
  private String topic;

  @Autowired private EmbeddedKafkaBroker broker;

  @Autowired
  @Qualifier("producer0")
  KafkaPublisherIntegration kafkaPublisherIntegration;

  @Autowired
  @Qualifier("producer1")
  KafkaPublisherIntegration kafkaPublisherIntegration1;

  @Test
  public void testOneConsumer() throws Exception {
    Map<String, Object> consumerProps = KafkaTestUtils.consumerProps(topic, "false", this.broker);
    consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    consumerProps.put(
        ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
        "org.apache.kafka.common.serialization.StringDeserializer");
    consumerProps.put(
        ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
        "org.apache.kafka.common.serialization.StringDeserializer");
    Consumer<String, String> consumer = new KafkaConsumer<>(consumerProps);
    this.broker.consumeFromAnEmbeddedTopic(consumer, topic);

    for (int i = 0; i < 100; i++) {
      this.kafkaPublisherIntegration.send("k0" + i, "v0" + i);
      this.kafkaPublisherIntegration1.send("k1" + i, "v1" + i);
    }

    ConsumerRecords<String, String> record = KafkaTestUtils.getRecords(consumer);
    Iterator<ConsumerRecord<String, String>> it0 = record.iterator();
    while (it0.hasNext()) {
      ConsumerRecord<String, String> r0 = it0.next();
      LOGGER.info("#################### r0:{}", r0);
    }
    ConsumerRecords<String, String> record1 = KafkaTestUtils.getRecords(consumer);
    Iterator<ConsumerRecord<String, String>> it1 = record1.iterator();
    while (it1.hasNext()) {
      ConsumerRecord<String, String> r1 = it1.next();
      LOGGER.info("#################### r1:{}", r1);
    }

    consumer.close();
  }
}
