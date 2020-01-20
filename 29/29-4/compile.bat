rem
rem JDIC 20061102 for Microsoft Windows 
rem 
set JDIC_PACKAGER_JAVAHOME=C:\jdk1.6.0\jdic
set CLASSPATH=%CLASSPATH%;%JDIC_PACKAGER_JAVAHOME%\jdic.jar;
set PATH=%PATH%;%JDIC_PACKAGER_JAVAHOME%\jdic.dll;%JDIC_PACKAGER_JAVAHOME%\tray.dll;

rem
rem JDIC 0.9.5 for Cross Platform 
rem 
rem set CLASSPATH=%CLASSPATH%;%JDIC_PACKAGER_JAVAHOME%\lib\jdic.jar;%JDIC_PACKAGER_JAVAHOME%\lib\windows\x86\jdic_native.jar;%JDIC_PACKAGER_JAVAHOME%\lib\jdic_stub_windows.jar;%JDIC_PACKAGER_JAVAHOME%\lib\jdic_native_applet.jar;%JDIC_PACKAGER_JAVAHOME%\lib\windows\x86\jdic_stub.jar;%JDIC_PACKAGER_JAVAHOME%\lib\windows\x86\packager.jar;
rem set PATH=%PATH%;%JDIC_PACKAGER_JAVAHOME%\lib\windows\x86\jdic.dll;%JDIC_PACKAGER_JAVAHOME%\lib\windows\x86\tray.dll;
rem set java.library.path=%JDIC_PACKAGER_JAVAHOME%
rem echo %java.library.path%

javac *.java

pause
