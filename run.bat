@ECHO off
IF "%1" == "test" (
	IF "%2" == "" (
		ECHO Running the tests...
	) ELSE (
		ECHO Running single test...
		java -cp .\bin;lib\junit-4.13-beta-3.jar;lib\hamcrest-core-1.3.jar org.junit.runner.JUnitCore %2
	)
) ELSE (
	java -cp bin Main
)
ECHO Finished
