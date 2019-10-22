package org.abcframework.kafka.producer.embedded;

import java.util.Iterator;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
@EmbeddedKafka(topics = {"singleTopic1", "singleTopic2"})
public class TwoTopicsTests {
  private static final Logger LOGGER = LoggerFactory.getLogger(TwoTopicsTests.class);

  @Autowired EmbeddedKafkaBroker broker;

  @Test
  void testTwoTopic() {
    Map<String, Object> producerProps = KafkaTestUtils.producerProps(broker);
    KafkaProducer<Integer, String> producer = new KafkaProducer<>(producerProps);
    producer.send(new ProducerRecord<>("singleTopic1", 1, "foo1"));
    producer.send(new ProducerRecord<>("singleTopic2", 1, "foo2"));
    producer.close();

    Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("twoTopics", "false", broker);
    consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    KafkaConsumer<Integer, String> consumer = new KafkaConsumer<>(consumerProps);
    broker.consumeFromAllEmbeddedTopics(consumer);

    ConsumerRecords<Integer, String> records = KafkaTestUtils.getRecords(consumer);
    Iterator<ConsumerRecord<Integer, String>> iterator = records.iterator();
    while (iterator.hasNext()) {
      ConsumerRecord<Integer, String> record = iterator.next();
      LOGGER.info("record:{}", record);
    }
    consumer.close();
  }
}
