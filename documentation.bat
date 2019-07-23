@ECHO OFF
ECHO Recreating doc folder...
IF EXIST "doc" (
	RMDIR /S /Q doc
)
IF NOT EXIST "sources.txt" (
	DIR /B /S *.java > sources.txt
)
MKDIR doc
ECHO Generating documentation...
javadoc -d doc -encoding UTF-8 -exclude test -cp lib\hamcrest-core-1.3.jar;lib\junit-4.13-beta-3.jar;lib\JUnitParams.jar @sources.txt
ECHO Finished
