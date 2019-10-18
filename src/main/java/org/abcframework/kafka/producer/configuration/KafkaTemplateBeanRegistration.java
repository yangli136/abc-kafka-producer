package org.abcframework.kafka.producer.configuration;

import java.util.ArrayList;
import java.util.List;

import org.abcframework.common.configuration.bean.SpringBeanRegistrationBase;
import org.abcframework.kafka.producer.configuration.KafkaProducerConfiguration.KafkaProducerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.ProducerListener;

public class KafkaTemplateBeanRegistration<K, V> extends SpringBeanRegistrationBase {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = LoggerFactory.getLogger(KafkaTemplateBeanRegistration.class);

  private final KafkaProducerConfiguration<K, V> kafkaProducerConfiguration;
  private final ProducerListener<K, V> producerListener;

  public KafkaTemplateBeanRegistration(
      ApplicationContext applicationContext,
      KafkaProducerConfiguration<K, V> kafkaProducerConfiguration,
      ProducerListener<K, V> producerListener) {
    super(applicationContext);
    this.kafkaProducerConfiguration = kafkaProducerConfiguration;
    this.producerListener = producerListener;
  }

  public List<KafkaTemplate<K, V>> registerKafkaTemplateList() {
    final BeanDefinitionRegistry registry = this.getBeanDefinitionRegistry();

    final List<KafkaTemplate<K, V>> kafkaTemplateList = new ArrayList<>();
    final List<KafkaProducerProperties> producerProperties =
        this.kafkaProducerConfiguration.getProducers();
    for (KafkaProducerProperties kafkaProducerProperties : producerProperties) {
      final ProducerFactory<K, V> producerFactory =
          this.kafkaProducerConfiguration.producerFactory(kafkaProducerProperties);
      BeanDefinitionBuilder definitionBuilder =
          BeanDefinitionBuilder.genericBeanDefinition(KafkaTemplate.class)
              .addConstructorArgValue(producerFactory)
              .addConstructorArgValue(true);
      String beanName =
          this.kafkaProducerConfiguration.getKafkaTemplateBeanPrefix()
              + "."
              + kafkaProducerProperties.getProducerId();
      registry.registerBeanDefinition(beanName, definitionBuilder.getBeanDefinition());
      @SuppressWarnings("unchecked")
      final KafkaTemplate<K, V> bean = (KafkaTemplate<K, V>) applicationContext.getBean(beanName);
      bean.setProducerListener(producerListener);
      kafkaTemplateList.add(bean);
    }
    return kafkaTemplateList;
  }
}
