
# Kafka Installation

## Download Kafka
[Kafka download site](http://kafka.apache.org/downloads.html)

## Setting Up Kafka
1. Go to your Kafka config directory. For me its  
```
C:\apps\kafka\kafka_2.12-2.3.0\config
```
2. Edit the file “server.properties.”
3. Find and edit the line from
```
log.dirs=/tmp/kafka-logs
```
to
```
log.dirs=C:\apps\kafka\kafka_2.12-2.3.0\kafka-logs
```
4. If your ZooKeeper is running on some other machine or cluster you can edit  _“zookeeper._connect_:2181”_to your custom IP and port. Also the Kafka port and broker.id are configurable in this file. Leave other settings as is.
5. Your Kafka will run on default port 9092 and connect to ZooKeeper’s default port, 2181.

## Running a Kafka Server

_Important: Please ensure that your ZooKeeper instance is up and running before starting a Kafka server._

1. Go to your Kafka installation directory: _C:\apps\kafka\kafka_2.12-2.3.0_
2. Open a command prompt
3. Now type `.\bin\windows\kafka-server-start.bat .\config\server.properties`  and press Enter.

.\bin\windows\kafka-server-start.bat .\config\server.properties

if you see an error:
```
\Java\jre1.8.0_31\lib\ext\QTJava.zip was unexpected at this time
```
you can fix it by 
```
set classpath=
```
![Image title](https://dzone.com/storage/temp/957943-6.png)

4. If everything went fine, your command prompt will look like this:

![Image title](https://dzone.com/storage/temp/958053-7b.png "Image title")  

5. Now your Kafka Server is up and running, you can create topics to store messages. 

## Creating Topics

1. Open a new command prompt in the location  _C:\apps\kafka\kafka_2.12-2.3.0\bin\windows_

1. Type the following command and hit Enter:
```
kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic default-kafka-topic
```
## Creating a Producer and Consumer to Test Server

1. Open a new command prompt in the location  _C:\apps\kafka\kafka_2.12-2.3.0\bin\windows_

2. To start a producer type the following command:
```
kafka-console-producer.bat --broker-list localhost:9092 --topic test-highpriority-topic
```
3. Again open a new command prompt in the same location as  _C:\apps\kafka\kafka_2.12-2.3.0\bin\windows_

4. Now start a consumer by typing the following command:
```
kafka-console-consumer.bat --zookeeper localhost:2181 --topic test-highpriority-topic
```
5. Now you will have two command prompts, like the image below:

![Image title](https://dzone.com/storage/temp/957951-9.png)

6. Now type anything in the producer command prompt and press Enter, and you should be able to see the message in the other consumer command prompt.

![Image title](https://dzone.com/storage/temp/957952-10.png)

7. If you are able to push and see your messages on the consumer side, you are done with Kafka setup.

## Some Other Useful Commands

1.  List Topics: `kafka-topics.bat --list --zookeeper localhost:2181`
2.  Describe Topic: `kafka-topics.bat --describe --zookeeper localhost:2181 --topic [Topic Name]`
3.  Read messages from the beginning: `kafka-console-consumer.bat --zookeeper localhost:2181 --topic [Topic Name] --from-beginning`
4.  Delete Topic: `kafka-run-class.bat kafka.admin.TopicCommand --delete --topic [topic_to_delete] --zookeeper localhost:2181`



fix for error:
" Failed to clean up log for __consumer_offsets" 

Edit the server.properties file located in the C:\apps\kafka\kafka_2.12-2.3.0\config folder, and modify "log.retention.hours=168" to "log.retention.hours=-1"
Add a line "log.cleaner.enable=false" at the end of the file.
Verify that optional parameters log.retention.bytes, log.retention.minutes and log.retention.ms are not set.  If they are set, change them to -1.

[link](https://community.microstrategy.com/s/article/Kafka-could-not-be-started-due-to-Failed-to-clean-up-log-for-consumer-offsets-in-MicroStrategy-10-x?language=en_US)

