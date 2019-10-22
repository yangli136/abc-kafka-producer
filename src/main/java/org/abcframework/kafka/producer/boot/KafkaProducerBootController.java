package org.abcframework.kafka.producer.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@ConditionalOnProperty(name = "web.test.enabled", havingValue = "true", matchIfMissing = false)
@RestController
public class KafkaProducerBootController {
  private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducerBootController.class);

  @Autowired
  @Qualifier("producer0")
  private KafkaPublisherIntegration kafkaPublisherIntegration;

  @GetMapping("send")
  public String send(
      @RequestParam(required = true) String key, @RequestParam(required = true) String message) {
    LOGGER.info("sending >>>");

    kafkaPublisherIntegration.send(key, message);
    return "OK - key:" + key + ", message:" + message + " sent.";
  }
}
