# APIs Test Automation Framework - REST Assured

The framework is crafted to run automation tests using REST Assured which is a Java library for testing RESTful APIs.<br />
The framework consists of the following: Java, Maven, TestNG, REST Assured, and Extent Reports. As well as other libraries like Hamcrest Assertion library, AssertJ, org.apache.poi, javax.mail and so on.

## How to use the framework properly:

Since the framework is based on TestNG framework so each test suite is represented by a testng xml file that points to the respective suite java class which contains all tests’ objects along with TestNG annotations that lead to perform the test in a proper way like ```@BeforeTest```, ```@AfterTest```, ```@AfterMethod```, ```@Test``` and so on.

### Test Suites

The test suites xml files are set in the pom.xml file within ```maven-surefire-plugin``` then ```configuration``` then ```suiteXmlFiles``` tags.
  
### Reporting Class

We have a customized reporting class using Extent Reports tool.
The class also has an xml file that points to it.
The class contains the sending Gmail properties and it’s configured to aggregate the test results, generate the report for each suite and send them by email to the stakeholders.

### Execution
Using maven commands, the execution should be conducted by two jobs as the following:
1. The first job/task is for running all test suites listed in the pom.xml file along with the Preconditions class using Maven command:<br /> ```mvn clean verify```
2. The second job/task is for executing the Reporting class and to be performed after the first one is completely finished using Maven command:<br /> ```mvn test -Dsurefire.suiteXmlFiles=src/main/resources/Reporting.xml```
