# OPC-UA Stack

A high-performance and open-source OPC-UA stack implementation.

Note: this is just a *stack* implementation (channels, serialization, structures, security). If you're looking to build a client or server, try the [OPC-UA SDK](https://github.com/inductiveautomation/opc-ua-sdk).

Running the Example
--------
Certificate validation is implemented in the server stack and so upon running the ClientServerExample for the first time you'll probably see a stack trace containing this exception:

```java
Caused by: com.inductiveautomation.opcua.stack.core.UaException: security checks failed
	at com.inductiveautomation.opcua.stack.client.handlers.UaTcpClientAcknowledgeHandler.onError(UaTcpClientAcknowledgeHandler.java:162)
	at com.inductiveautomation.opcua.stack.client.handlers.UaTcpClientAcknowledgeHandler.decode(UaTcpClientAcknowledgeHandler.java:89)
```

You'll now find a "security" folder in whatever you've configured your working directory as when running the example. Inside that folder, you should find "rejected", "revocation", and "trusted" folders. Move the client certificate in the "rejected" folder to the "trusted" folder and run the example again.

Maven
--------

#### Repository
```xml
<repository>
    <id>oss-sonatype</id>
    <name>oss-sonatype</name>
    <url>https://oss.sonatype.org/content/repositories/snapshots/</url>
</repository>
```

#### Stack Server
```xml
<dependency>
    <groupId>com.inductiveautomation.opcua</groupId>
    <artifactId>stack-server</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

#### Stack Client
```xml
<dependency>
    <groupId>com.inductiveautomation.opcua</groupId>
    <artifactId>stack-client</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

Supported Features
--------

#### Transport Protocols
* OPC-UA TCP

#### Data Encoding
* OPC-UA Binary
 
#### Security Profiles
* None
* Basic128Rsa15
* Basic256
* Basic256Sha256

##### Not Supported
* SOAP/HTTP/HTTPS Transport
* XML Data Encoding


Get Help
--------

Contact kevinherron@gmail.com for more information.


License
--------

Apache License, Version 2.0
