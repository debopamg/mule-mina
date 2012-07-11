









WELCOME
=======
Congratulations you have just created a new Mule transport!

This wizard created a number of new classes and resources useful for Mule transports.  Each of the created files
contains documentation and _todo_ items where necessary.  You'll need to look at each of the classes and other files and
address the _todo_ items in the files. Here is an overview of what was created.

./pom.xml:
A maven project descriptor that describes how to build this project.  If you enabled this project for the
MuleForge, this file will contain additional information about the project on MuleForge.

./assembly.xml:
A maven assembly descriptor that defines how this project will be packaged when you make a release.

./LICENSE.txt:
The open source license text for this project.

-----------------------------------------------------------------
./src/main/java/org/mule/transport/mina/i18n/MinaMessages.java:

The MinaMessages java class contains methods for access i18n messages embedded in your java code.

-----------------------------------------------------------------
./src/main/resources/META-INF/services/org/mule/i18n/mina-messages.properties

These message properties contain i18n strings used by MinaMessages.java.


-----------------------------------------------------------------
./src/main/java/org/mule/transport/mina/MinaConnector.java

The connector for this transport. This is used for configuing common properties on endpoints for this transport
and initialising shared resources.

-----------------------------------------------------------------
./src/main/java/org/mule/transport/mina/MinaEndpointURIBuilder.java

The class responsible for parsing custom endpoints for this transport.

-----------------------------------------------------------------
./src/main/java/org/mule/transport/mina/MinaInboundTransformer.java

This transformer should convert the inbound message into a type consumable by Mule.  For example, in the case of JMS this
class would would convert a JMSMessage to a String, object, Map, etc depending on the time of message.  If your transport
does not have a specific message type you do not need this class.

-----------------------------------------------------------------
./src/main/java/org/mule/transport/mina/MinaOutboundTransformer.java

This transformer should convert the otbound message into a type supported by the underlying technology.  For example,
in the case of JMS this class would would convert a MuleMessage to a JMSMessage.  If your transport
does not have a specific message type you do not need this class.


-----------------------------------------------------------------
./src/main/java/org/mule/transport/mina/MinaMessageDispatcher.java

This part of the transport responsible for outbound endpoints (client).  This class should implement the logic needed to
dispatch messages over the underlying transport.

-----------------------------------------------------------------
./src/main/java/org/mule/transport/mina/MinaMessageDispatcherFactory.java

The factory used to create MinaMessageDispatcher instances.
-----------------------------------------------------------------
./src/main/java/org/mule/transport/mina/MinaMessageReceiver.java

This part of the transport responsible for inbound endpoints.  This class should implement the logic need to
receive messages from the underlying transport.  Mule supports polling receivers, that pull events from the transport, but
users can implement listener interfaces to have events pushed to the receiver.


-----------------------------------------------------------------
./src/main/resources/META-INF/mule-mina.xsd

The configuration schema file for this module. All configuration elements should be defined in this schema.

-----------------------------------------------------------------
./src/main/resources/META-INF/spring.schemas

Contains a mapping of the Namespace URI for this projects schema.

-----------------------------------------------------------------
./src/main/resources/META-INF/spring.handlers

Contains a mapping of the namespace handler to use for the schema in this project.

-----------------------------------------------------------------
./src/main/java/org/mule/transport/mina/config/MinaNamespaceHandler.java

The implmentation of the namespace handler used to parse elements defined in mule-mina.xsd.

TESTING
=======

This  project also contains test classes that can be run as part of a test suite.
-----------------------------------------------------------------
./src/test/java/org/mule/transport/mina/MinaTestCase.java

This is an example functional test case.  The test will work as is, but you need to configure it to actually test your
code.  For more information about testing see: http://www.mulesoft.org/documentation/display/MULE3USER/Functional+Testing.

-----------------------------------------------------------------
./src/test/resources/mina-functional-test-config.xml

Defines the Mule configuration for the MinaTestCase.java.

-----------------------------------------------------------------
./src/test/java/org/mule/transport/mina/MinaConnectorTestCase.java

The unit test case for testing the connecotr object for this transport.

-----------------------------------------------------------------
./src/test/java/org/mule/transport/mina/MinaEndpointTestCase.java

The unit test case for testing the endpoint builder object for this transport.

-----------------------------------------------------------------
./src/test/java/org/mule/transport/mina/MinaMessageReceiverTestCase.java

The unit test case for testing the message receiver object for this transport.

-----------------------------------------------------------------
./src/test/java/org/mule/transport/mina/MinaNamespaceHandlerTestCase.java

A test case that is used to test each of the configuration elements inside your mule-mina.xsd schema file.

-----------------------------------------------------------------
./src/test/resources/mina-namespace-config.xml

The configuration file for the MinaNamespaceHandlerTestCase.java testcase.


ADDITIONAL RESOURCES
====================
Everything you need to know about getting started with Mule can be found here:
http://www.mulesoft.org/documentation/display/MULE3INSTALL/Hello,+Mule!

There further useful information about extending Mule here:
http://www.mulesoft.org/documentation/display/MULE3USER/Introduction+to+Extending+Mule

We recommend you read the page on writing Mule transports if you have not done so already:
http://www.mulesoft.org/documentation/display/MULE3USER/Creating+Transports

There is also detailed information about creating Mule configuration schemas here:
http://www.mulesoft.org/documentation/display/MULE3USER/Creating+a+Custom+XML+Namespace

For information about working with Mule inside and IDE with maven can be found here:
http://www.mulesoft.org/documentation/display/MULE3INSTALL/Setting+Up+Eclipse+for+Use+with+Maven

Remember if you get stuck you can try getting help on the Mule user list:
http://www.mulesoft.org/email-lists

Also, MuleSoft, the company behind Mule, offers 24x7 support options:
http://www.mulesoft.com/enterprise-subscriptions-and-support

Enjoy your Mule ride!

The Mule Team

--------------------------------------------------------------------
This project was auto-generated by the mule-transport-archetype.

artifactId=mule-transport-mina
description=mina connector to mule
muleVersion=3.2.1
hasCustomSchema=y
hasReceiver=y
hasDispatcher=y
hasRequestor=n
hasCustomMessageFactory=n
hasBootstrap=n
hasTransactions=n
hasCustomTransactions=n
inboundTransformer=n
outboundTransformer=n
ModuleType=Transport
transports=tcp,vm
modules=client

version=1.0-SNAPSHOT
groupId=org.mule.transports
basedir=/home/debopamg/workspace/other-projects
--------------------------------------------------------------------
