package org.abcframework.kafka.producer.embedded;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.test.EmbeddedKafkaBroker;

@Configuration
public class Config {

  @Value("${abc.kafka.default.topic}")
  private String topic;

  @Value("${abc.kafka.default.port}")
  private int port;

  @Bean("broker0")
  public EmbeddedKafkaBroker broker() throws IOException {
    return new EmbeddedKafkaBroker(1, true, topic).kafkaPorts(this.port);
  }

  public int getPort() {
    return port;
  }
}
