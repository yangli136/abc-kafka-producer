package org.abcframework.kafka.producer.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.abcframework.kafka.producer.configuration.KafkaProducerConfiguration.KafkaProducerProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@EnableAutoConfiguration
@SpringBootTest(
    classes = {TestKafkaProducerConfiguration.class, ValidationApplicationContext.class},
    properties = {"app.stack=e0"})
@SpringJUnitConfig
public class KafkaProducerConfigurationTest {
  @Autowired KafkaProducerConfiguration<?, ?> kafkaPropertiesConfiguration;

  @Test
  public void testConfigurationParameters() {
    final List<KafkaProducerProperties> producerList = kafkaPropertiesConfiguration.getProducers();
    assertThat(producerList).isNotEmpty();
    assertThat(producerList.size()).isEqualTo(3);
    assertThat(producerList.get(0).getProducerId()).isEqualTo("producer0");
    assertThat(producerList.get(1).getProducerId()).isEqualTo("producer1");
    assertThat(producerList.get(2).getProducerId()).isEqualTo("producer2");
  }
}
