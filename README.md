# client-java
signalGo client version for java

SignalGo is a library for Cross-Platform developers that makes it incredibly simple to add real-time web functionality to your applications. What is "real-time web" functionality? It's the ability to have your server-side code push content to the connected clients as it happens, in real-time. like WCF and SignalR!on the other hand you have bi-direction socket connection that both client and server can call each other!one more intresting thing is you can user signalGo .Net server verion for your backend and java version for client, and conversely!
# Download
la
get latest version jar from [here](https://github.com/SignalGo/client-java/releases) and add to your project!
 for maven project add following dependencies to your project pom.xml file:
 ```xml
<dependencies>
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.8.1</version>
        </dependency>
        <dependency>
            <groupId>com.fasterxml.jackson.datatype</groupId>
            <artifactId>jackson-datatype-joda</artifactId>
            <version>2.8.1</version>
        </dependency>
        <dependency>
            <groupId>com.google.guava</groupId>
            <artifactId>guava</artifactId>
            <version>18.0</version>
            <type>jar</type>
        </dependency>
```
and for gradle:
```groovy
    compile 'com.fasterxml.jackson.core:jackson-core:2.8.1'
    compile 'com.fasterxml.jackson.core:jackson-databind:2.8.1'
    compile group: 'com.google.guava', name: 'guava', version: '19.0'
    compile('com.fasterxml.jackson.datatype:jackson-datatype-joda:2.8.1') {
        exclude module: 'joda-time'
    }
```

For more detailed usage, check out it out [here](https://github.com/SignalGo/client-java/wiki)

# Pull Requests
I welcome all pull requests. Here are some basic rules of your request:
  1. Match coding style (braces, spacing, etc.)
  2. If its a feature, bugfix, or anything please only change code to what you specify.
  3. Please keep PR titles easy to read and descriptive of changes, this will make them easier to merge :)
  
# Special Thanks To:
  1.  [Ali Yousefi](https://github.com/Ali-YousefiTelori) for .Net version of SingalGo

# License
  Copyright (C) 2007 Free Software Foundation, Inc. <http://fsf.org/>
  Everyone is permitted to copy and distribute verbatim copies
  of this license document, but changing it is not allowed.
  This version of the GNU Lesser General Public License incorporates
  the terms and conditions of version 3 of the GNU General Public
  License, supplemented by the additional permissions listed [here](https://github.com/SignalGo/client-java/blob/master/LICENSE).

# Maintained By
[mehdi akbarian](https://github.com/makbn) ([@makbn](https://www.twitter.com/makbn))

