## Differential Equations Assignment

#### Written in Java 8 with JavaFX library
also uses org.ow2.asm library for runtime function build

##### How to build
Ensure that your JAVA_HOME environment variable points to a valid JDK 8 installation folder.
Do following after:
````
$ git clone git@github.com:potemin1999/de-assignment.git
$ cd de-assignment
$ ./gradlew build
````

##### Version 0.1
* can solve hardcoded DE with Euler, Improved Euler and Runge-Kutta methods
* provides my own implementation of graph drawer
* can translate String to Function (look [Function2Generator](https://github.com/potemin1999/de-assignment/blob/master/src/main/com/ilya/de/math/function/Function2Generator.java))

##### Version 0.2
* fully-functional UI added
* global truncation error for each method
* improvements of graph visualization 

##### Version 0.3
* 1st release version
* bug fixes and improvements
