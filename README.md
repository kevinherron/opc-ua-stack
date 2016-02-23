# OPC-UA Stack

[![Join the chat at https://gitter.im/digitalpetri/opc-ua-stack](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/digitalpetri/opc-ua-stack?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

A high-performance and open-source OPC-UA stack implementation.

Note: this is just a *stack* implementation (channels, serialization, structures, security). If you're looking to build a client or server, try the [OPC-UA Client SDK](https://github.com/digitalpetri/ua-client-sdk) or [OPC-UA Server SDK](https://github.com/digitalpetri/ua-server-sdk).

Running the Example
--------
Certificate validation is implemented in the server stack and so upon running the ClientServerExample for the first time you'll probably see a stack trace containing this exception:

```java
Caused by: com.digitalpetri.opcua.stack.core.UaException: security checks failed
	at UaTcpClientAcknowledgeHandler.onError(UaTcpClientAcknowledgeHandler.java:162)
	at UaTcpClientAcknowledgeHandler.decode(UaTcpClientAcknowledgeHandler.java:89)
```

You'll now find a "security" folder in whatever you've configured your working directory as when running the example. Inside that folder, you should find "rejected", "revocation", and "trusted" folders. Move the client certificate in the "rejected" folder to the "trusted" folder and run the example again.

Maven
--------

Releases are available from Maven Central:

#### Stack Server
```xml
<dependency>
    <groupId>com.digitalpetri.opcua</groupId>
    <artifactId>stack-server</artifactId>
    <version>1.0.5</version>
</dependency>
```

#### Stack Client
```xml
<dependency>
    <groupId>com.digitalpetri.opcua</groupId>
    <artifactId>stack-client</artifactId>
    <version>1.0.5</version>
</dependency>
```

The latest snapshots are available from the Sonatype snapshot repository:
```xml
<repository>
    <id>oss-sonatype</id>
    <name>oss-sonatype</name>
    <url>https://oss.sonatype.org/content/repositories/snapshots/</url>
</repository>
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
