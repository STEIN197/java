@ECHO off
IF EXIST "common.jar" (
	DEL common.jar
)
ECHO Building a jar archive...
jar -cf common.jar bin\*
ECHO Finished
