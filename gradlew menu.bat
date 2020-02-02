@ECHO off
goto init

:init
CLS
ECHO Select a task
ECHO A: setupdecompworkspace
ECHO B: eclipse
ECHO C: build
ECHO D: runClient
ECHO E: Exit
CHOICE /C:ABCDE /N
if %ERRORLEVEL% == 1 goto setupdecompworkspace
if %ERRORLEVEL% == 2 goto eclipse
if %ERRORLEVEL% == 3 goto build
if %ERRORLEVEL% == 4 goto runClient
if %ERRORLEVEL% == 5 goto exittask

:setupdecompworkspace
CLS
cmd.exe /c "gradlew.bat setupdecompworkspace"
PAUSE
goto init

:eclipse
CLS
cmd.exe /c "gradlew.bat eclipse"
PAUSE
goto init

:build
CLS
cmd.exe /c "gradlew.bat build"
PAUSE
goto init

:runClient
CLS
cmd.exe /c "gradlew.bat runClient"
PAUSE
goto init


:exittask
CHOICE /M "Are you sure?"
if %ERRORLEVEL% == 1 EXIT
if %ERRORLEVEL% == 2 goto init