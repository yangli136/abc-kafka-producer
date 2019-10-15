# ZooKeeper Installation

Download and extract ZooKeeper using 7-zip from [zookeeper download site](http://zookeeper.apache.org/releases.html)

1. Go to your ZooKeeper config directory. For me its C:\apps\zookeeper\apache-zookeeper-3.5.5-bin\conf
1. Rename file “zoo_sample.cfg” to “zoo.cfg”
1. Open zoo.cfg in any text editor
1. Find and edit dataDir=/tmp/zookeeper to dataDir=C:\apps\zookeeper\apache-zookeeper-3.5.5-bin\data
1. add the following line to the end of the file:
  admin.serverPort=8020
1. Add an entry in the System Environment Variables as we did for Java.
    * Add ZOOKEEPER_HOME = C:\apps\zookeeper\apache-zookeeper-3.5.5-bin to the System Variables.
    * Edit the System Variable named “Path” and add ;%ZOOKEEPER_HOME%\bin; 
1. You can change the default Zookeeper port in zoo.cfg file (Default port 2181).
1. Switch to directory:
   C:\apps\zookeeper\apache-zookeeper-3.5.5-bin\bin
   
   Run ZooKeeper by opening a new cmd and type zkserver.

[zookeeper admin guide](https://zookeeper.apache.org/doc/r3.5.4-beta/zookeeperAdmin.html#sc_adminserver_config)


[source](https://dzone.com/articles/running-apache-kafka-on-windows-os)