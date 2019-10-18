package org.abcframework.kafka.producer.configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotBlank;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SslConfigs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;

public class KafkaProducerConfiguration<K, V> {
  private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducerConfiguration.class);
  private List<KafkaProducerProperties> producers = new ArrayList<>();
  private String kafkaTemplateBeanPrefix;

  public List<KafkaProducerProperties> getProducers() {
    return Collections.unmodifiableList(producers);
  }

  public void setProducers(List<KafkaProducerProperties> producers) {
    this.producers = new ArrayList<>(producers);
  }

  public String getKafkaTemplateBeanPrefix() {
    return kafkaTemplateBeanPrefix;
  }

  public void setKafkaTemplateBeanPrefix(String kafkaTemplateBeanPrefix) {
    this.kafkaTemplateBeanPrefix = kafkaTemplateBeanPrefix;
  }

  public ProducerFactory<K, V> producerFactory(
      final KafkaProducerProperties kafkaProducerProperties) {
    return new DefaultKafkaProducerFactory<>(producerConfigurationMap(kafkaProducerProperties));
  }

  private Map<String, Object> producerConfigurationMap(
      KafkaProducerProperties kafkaProducerProperties) {
    Map<String, Object> configProps = new HashMap<>();
    configProps.put(
        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProducerProperties.getBootstrapServers());
    configProps.put(
        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, kafkaProducerProperties.getKeySerializer());
    configProps.put(
        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, kafkaProducerProperties.getValueSerializer());
    if (kafkaProducerProperties.isSslTrustStoreEnabled()) {
      configProps.put(
          CommonClientConfigs.SECURITY_PROTOCOL_CONFIG,
          kafkaProducerProperties.getSecurityProtocol());
      configProps.put(
          SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, kafkaProducerProperties.getSslTrustStoreLoc());
      configProps.put(
          SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, kafkaProducerProperties.getSslTrustStorePwd());
      configProps.put(
          SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, kafkaProducerProperties.getSslKeyStoreLoc());
      configProps.put(
          SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, kafkaProducerProperties.getSslKeyStorePwd());
      configProps.put(SslConfigs.SSL_KEY_PASSWORD_CONFIG, kafkaProducerProperties.getSslKeyPwd());
    }
    LOGGER.info("configProps:{}", configProps);
    return configProps;
  }

  @Override
  public String toString() {
    return "KafkaProducerConfiguration [producers="
        + producers
        + ", kafkaTemplateBeanPrefix="
        + kafkaTemplateBeanPrefix
        + "]";
  }

  public static class KafkaProducerProperties {

    @NotBlank private String producerId;
    @NotBlank private String bootstrapServers;
    private String keySerializer;
    private String valueSerializer;
    @NotBlank private String topic;
    private int partition;
    private boolean sslTrustStoreEnabled;
    private String securityProtocol;
    private String sslTrustStoreLoc;
    private String sslTrustStorePwd;
    private String sslKeyStoreLoc;
    private String sslKeyStorePwd;
    private String sslKeyPwd;

    public String getProducerId() {
      return producerId;
    }

    public void setProducerId(String producerId) {
      this.producerId = producerId;
    }

    public String getBootstrapServers() {
      return bootstrapServers;
    }

    public void setBootstrapServers(String bootstrapServers) {
      this.bootstrapServers = bootstrapServers;
    }

    public String getKeySerializer() {
      return keySerializer;
    }

    public void setKeySerializer(String keySerializer) {
      this.keySerializer = keySerializer;
    }

    public String getValueSerializer() {
      return valueSerializer;
    }

    public void setValueSerializer(String valueSerializer) {
      this.valueSerializer = valueSerializer;
    }

    public String getTopic() {
      return topic;
    }

    public void setTopic(String topic) {
      this.topic = topic;
    }

    public int getPartition() {
      return partition;
    }

    public void setPartition(int partition) {
      this.partition = partition;
    }

    public boolean isSslTrustStoreEnabled() {
      return sslTrustStoreEnabled;
    }

    public void setSslTrustStoreEnabled(boolean sslTrustStoreEnabled) {
      this.sslTrustStoreEnabled = sslTrustStoreEnabled;
    }

    public String getSecurityProtocol() {
      return securityProtocol;
    }

    public void setSecurityProtocol(String securityProtocol) {
      this.securityProtocol = securityProtocol;
    }

    public String getSslTrustStoreLoc() {
      return sslTrustStoreLoc;
    }

    public void setSslTrustStoreLoc(String sslTrustStoreLoc) {
      this.sslTrustStoreLoc = sslTrustStoreLoc;
    }

    public String getSslTrustStorePwd() {
      return sslTrustStorePwd;
    }

    public void setSslTrustStorePwd(String sslTrustStorePwd) {
      this.sslTrustStorePwd = sslTrustStorePwd;
    }

    public String getSslKeyStoreLoc() {
      return sslKeyStoreLoc;
    }

    public void setSslKeyStoreLoc(String sslKeyStoreLoc) {
      this.sslKeyStoreLoc = sslKeyStoreLoc;
    }

    public String getSslKeyStorePwd() {
      return sslKeyStorePwd;
    }

    public void setSslKeyStorePwd(String sslKeyStorePwd) {
      this.sslKeyStorePwd = sslKeyStorePwd;
    }

    public String getSslKeyPwd() {
      return sslKeyPwd;
    }

    public void setSslKeyPwd(String sslKeyPwd) {
      this.sslKeyPwd = sslKeyPwd;
    }

    @Override
    public String toString() {
      return "KafkaProducerProperties [producerId="
          + producerId
          + ", bootstrapServers="
          + bootstrapServers
          + ", keySerializer="
          + keySerializer
          + ", valueSerializer="
          + valueSerializer
          + ", topic="
          + topic
          + ", partition="
          + partition
          + ", sslTrustStoreEnabled="
          + sslTrustStoreEnabled
          + ", securityProtocol="
          + securityProtocol
          + ", sslTrustStoreLoc="
          + sslTrustStoreLoc
          + ", sslTrustStorePwd="
          + sslTrustStorePwd
          + ", sslKeyStoreLoc="
          + sslKeyStoreLoc
          + ", sslKeyStorePwd="
          + sslKeyStorePwd
          + ", sslKeyPwd="
          + sslKeyPwd
          + "]";
    }
  }
}
