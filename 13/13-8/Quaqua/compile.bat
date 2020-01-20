set java.library.path=%java.library.path%;lib\libquaqua.jnilib

javac -classpath %CLASSPATH%;lib\quaqua.jar;lib\swing-layout.jar; *.java

pause