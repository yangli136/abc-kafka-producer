package org.abcframework.kafka.producer.embedded;

import org.abcframework.kafka.producer.configuration.KafkaProducerBeanConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.annotation.EnableKafka;

@Configuration
@EnableKafka
@PropertySource("classpath:kafka-producer-random-partition.properties")
public class KafkaProducerBeanConfigurationRandomPartitions<K, V>
    extends KafkaProducerBeanConfiguration {
  //  @SuppressWarnings("unused")
  //  private static final Logger LOGGER =
  // LoggerFactory.getLogger(KafkaProducerBeanRegistration.class);
  //
  //  @Autowired private ApplicationContext applicationContext;
  //
  //  @Bean("kafkaProducerConfiguration")
  //  @ConfigurationProperties(prefix = "abc.kafka")
  //  @Validated
  //  public KafkaProducerConfiguration<K, V> kafkaProducerConfiguration() {
  //    return new KafkaProducerConfiguration<>();
  //  }
  //
  //  @SuppressWarnings({"rawtypes", "unchecked"})
  //  @Bean("kafkaTemplateList")
  //  @DependsOn("kafkaProducerConfiguration")
  //  public List<KafkaTemplate> registerKafkaTemplateList() {
  //    KafkaTemplateBeanRegistration kafkaTemplateBeanRegistration =
  //        new KafkaTemplateBeanRegistration(applicationContext, kafkaProducerConfiguration());
  //    return kafkaTemplateBeanRegistration.registerKafkaTemplateList();
  //  }
  //
  //  @SuppressWarnings("rawtypes")
  //  @Bean("kafkaProducerList")
  //  @DependsOn("kafkaTemplateList")
  //  public List<KafkaProducer> registerKafkaProducerList(
  //      ListenableFutureCallback<SendResult<K, V>> callback) {
  //    KafkaProducerBeanRegistration<K, V> kafkaProducerBeanRegistration =
  //        new KafkaProducerBeanRegistration<>(
  //            applicationContext, kafkaProducerConfiguration(), callback);
  //    return kafkaProducerBeanRegistration.registerKafkaProducerList();
  //  }
}
