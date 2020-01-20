set JAVA_HOME=C:\jdk1.6.0

set JAVAMAIL=%JAVA_HOME%\jre\lib\ext
set JAF=%JAVA_HOME%\jre\lib\ext
set OLD_CLASSPATH=%CLASSPATH%
set CLASSPATH=%CLASSPATH%;%JAVAMAIL%\mail.jar;%JAF%\activation.jar;

java JavaMail

set CLASSPATH=%OLD_CLASSPATH%

pause