# OPC-UA Stack

A high-performance and open-source OPC-UA stack implementation.

Note: this is just a *stack* implementation (channels, serialization, structures, security). If you're looking to build a client or server, try the [OPC-UA SDK](https://github.com/inductiveautomation/opc-ua-sdk).

Maven
--------

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

Contact kevin@inductiveautomation.com for more information.


License
--------

Apache License, Version 2.0
