# assignment
This is a small console application to validate and make get api call

1. To run this solution following needs to be installed on you system
		Java 8
		Gradle 3.4
2. To build this application, open terminal and go to assignment folder
		gradle clean build
		Once build successfully completed, executable jar will be available at /build/libs/assignment-1.0-SNAPSHOT.jar
3. To run unit test cases, open terminal and go to assignment folder
		gradle test
		Test report will be generated at build/reports/tests/test/
		To view report open build/reports/tests/test/index.html file on brouwser
4. To runn this application
	1.	If you have already build the project successfully, you can skip this step. Otherwise please build project as
			gradle clean build
			Once build successfully completed, executable jar will be available at /build/libs/ assignment-1.0-SNAPSHOT.jar
	2. Run following command to run the application
		java -jar build/libs/assignment-1.0-SNAPSHOT.jar
	3. Following message will be shown to user
		Please enter each URL on new line :
	4. Enter URLs on separate line
	5. Press double enter to view the report on console
	6. To run the application again run the jar again as mentioned in Step 4.2
