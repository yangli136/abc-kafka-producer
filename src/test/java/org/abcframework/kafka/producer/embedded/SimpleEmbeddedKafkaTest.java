package org.abcframework.kafka.producer.embedded;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
@EmbeddedKafka(
    count = 1,
    controlledShutdown = true,
    topics = {"domain-events"})
public class SimpleEmbeddedKafkaTest {

  private static final String TOPIC = "domain-events";

  @Autowired private EmbeddedKafkaBroker embeddedKafkaBroker;

  private Consumer<String, String> consumer;

  @BeforeEach
  public void setUp() {
    Map<String, Object> configs =
        new HashMap<>(KafkaTestUtils.consumerProps("consumer", "false", embeddedKafkaBroker));
    consumer =
        new DefaultKafkaConsumerFactory<>(
                configs, new StringDeserializer(), new StringDeserializer())
            .createConsumer();
    embeddedKafkaBroker.consumeFromAnEmbeddedTopic(consumer, TOPIC);
  }

  @AfterEach
  public void tearDown() {
    consumer.close();
  }

  @Test
  public void kafkaSetup_withTopic_ensureSendMessageIsReceived() {
    // Arrange
    Map<String, Object> configs = new HashMap<>(KafkaTestUtils.producerProps(embeddedKafkaBroker));
    Producer<String, String> producer =
        new DefaultKafkaProducerFactory<>(configs, new StringSerializer(), new StringSerializer())
            .createProducer();

    // Act
    producer.send(new ProducerRecord<>(TOPIC, "my-aggregate-id", "{\"event\":\"Test Event\"}"));
    producer.flush();

    // Assert
    ConsumerRecord<String, String> singleRecord = KafkaTestUtils.getSingleRecord(consumer, TOPIC);
    assertThat(singleRecord).isNotNull();
    assertThat(singleRecord.key()).isEqualTo("my-aggregate-id");
    assertThat(singleRecord.value()).isEqualTo("{\"event\":\"Test Event\"}");
  }
}
