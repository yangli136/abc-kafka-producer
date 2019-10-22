package org.abcframework.kafka.producer.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@ComponentScan(
    basePackages = {"org.abcframework.kafka"},
    excludeFilters =
        @ComponentScan.Filter(
            type = FilterType.REGEX,
            pattern = "org\\.abcframework\\..\\.demo..*"))
@EnableAspectJAutoProxy
@EnableAsync
@EnableScheduling
@SpringBootApplication
public class KafkaProducerApplication {

  public static void main(final String[] args) {
    SpringApplication.run(KafkaProducerApplication.class, args);
  }
}
