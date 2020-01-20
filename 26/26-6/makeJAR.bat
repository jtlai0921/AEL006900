rem set OLD_CLASSPATH=%CLASSPATH%
rem set CLASSPATH=.;C:\jdk1.6.0\jre\lib\rt.jar;

rem set OLD_PATH=%PATH%
rem set PATH=C:\jdk1.6.0\bin

jar cvfm DesktopDemo.jar manifest.txt *.class images

pause

rem set CLASSPATH=%OLD_CLASSPATH%
rem set PATH=%OLD_PATH%
