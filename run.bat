@ECHO off
IF "%1" == "test" (
	ECHO Running the tests...
	java -cp .\bin;lib\junit-4.13-beta-3.jar;lib\hamcrest-core-1.3.jar org.junit.runner.JUnitCore %2
) ELSE (
	java -cp bin Main
)