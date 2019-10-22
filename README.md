_README FILE_

---
# Kafka Producer Utilities

### Spring boot based kafka producer utility library. It handles multiple Kafka producer configurations.

User only needs to provide Kafka configuration properties, then a producers are ready to be used without having to know
Kafka details.

## Usage

in kafka-producer.properties

```
abc.kafka.producers[0].producerId=producer0
abc.kafka.producers[0].bootstrapServers=localhost:9092
abc.kafka.producers[0].topic=test-highpriority-topic
abc.kafka.producers[0].partition=-1
abc.kafka.producers[0].keySerializer=org.springframework.kafka.support.serializer.JsonSerializer
abc.kafka.producers[0].valueSerializer=org.springframework.kafka.support.serializer.JsonSerializer
abc.kafka.producers[0].sslTrustStoreEnabled=false
abc.kafka.producers[0].securityProtocol=SSL
abc.kafka.producers[0].sslTrustStoreLoc=/jks/e1/kafka01-client-truststore.jks
abc.kafka.producers[0].sslTrustStorePwd=kafka123
abc.kafka.producers[0].sslKeyStoreLoc=/jks/e1/kafka.jks
abc.kafka.producers[0].sslKeyStorePwd=amex123
abc.kafka.producers[0].sslKeyPwd=amex123
```

A Producer bean could be used in a service:

```
@Service
@Validated
@DependsOn({"registerKafkaProducerList"})
public class KafkaPublisherIntegrationImpl implements KafkaPublisherIntegration {

  private final Producer<String, String> producer;

  public KafkaPublisherIntegrationImpl(@Qualifier("producer0") @Valid Producer<String, String> producer) {
    this.producer = producer;
  }

  @Override
  public void send(final String key, final String message) {
    this.producer.send(key, message);
  }
}
```

### Partition distribution with producers

If abc.kafka.producers[0].partition=-1
The publisher will send messages to random partitions.

### Error Handling:

If there is an error sending a message, the exception will be logged. This is default behavior.
If more sophisticated error handling required for message producer, a new bean could be created to implement:
ListenableFutureCallback<SendResult<K, V>>.

Mark this bean as @Primary, the callback bean will be injected into producers.

# Run in local

```
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Dweb.test.enabled=true -Dapp.stack=e0"
mvn -o spring-boot:run -Dspring-boot.run.jvmArguments="-Dweb.test.enabled=true -Dapp.stack=e0"
```

Bring up a web browser:

```
http://localhost:8090/send
```

Each time a refresh of the page will send out one new message.

If you run maven in command line, follow the Maven installation instructions here:

```
https://maven.apache.org/install.html
```
