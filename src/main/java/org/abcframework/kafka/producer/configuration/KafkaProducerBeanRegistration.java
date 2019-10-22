package org.abcframework.kafka.producer.configuration;

import java.util.ArrayList;
import java.util.List;
import org.abcframework.common.configuration.bean.SpringBeanRegistrationBase;
import org.abcframework.kafka.producer.configuration.KafkaProducerConfiguration.KafkaProducerProperties;
import org.abcframework.kafka.producer.service.impl.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;

public class KafkaProducerBeanRegistration<K, V> extends SpringBeanRegistrationBase {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducerBeanRegistration.class);

  private final KafkaProducerConfiguration<K, V> kafkaProducerConfiguration;
  private final ListenableFutureCallback<SendResult<K, V>> callback;

  public KafkaProducerBeanRegistration(
      ApplicationContext applicationContext,
      KafkaProducerConfiguration<K, V> kafkaProducerConfiguration,
      ListenableFutureCallback<SendResult<K, V>> callback) {
    super(applicationContext);
    this.kafkaProducerConfiguration = kafkaProducerConfiguration;
    this.callback = callback;
  }

  @SuppressWarnings("rawtypes")
  public List<KafkaProducer> registerKafkaProducerList() {
    final List<KafkaProducer> kafkaProducerList = new ArrayList<>();
    final List<KafkaProducerProperties> producerProperties =
        this.kafkaProducerConfiguration.getProducers();
    final BeanDefinitionRegistry registry = this.getBeanDefinitionRegistry();
    for (KafkaProducerProperties kafkaProducerProperties : producerProperties) {

      String beanName =
          this.kafkaProducerConfiguration.getKafkaTemplateBeanPrefix()
              + "."
              + kafkaProducerProperties.getProducerId();
      @SuppressWarnings("unchecked")
      KafkaTemplate<String, Object> kafkaTemplate =
          (KafkaTemplate<String, Object>) applicationContext.getBean(beanName);

      BeanDefinitionBuilder definitionBuilder =
          BeanDefinitionBuilder.genericBeanDefinition(KafkaProducer.class)
              .addConstructorArgValue(kafkaTemplate)
              .addConstructorArgValue(kafkaProducerProperties.getTopic())
              .addConstructorArgValue(kafkaProducerProperties.getPartition())
              .addConstructorArgValue(this.callback);
      registry.registerBeanDefinition(
          kafkaProducerProperties.getProducerId(), definitionBuilder.getBeanDefinition());
      kafkaProducerList.add(
          (KafkaProducer) applicationContext.getBean(kafkaProducerProperties.getProducerId()));
    }
    return kafkaProducerList;
  }
}
