# Clear Trip Booking
Created a framework using Selenium-Java and TestNG in a POM format.

Used a build automation tools as Maven.

Also have included a .jenkinsfile that can be scheduled to run in jenkings.

POM description:

src/main/java

Created a Base Page that can be extended to other page to reuse the methods and properties.

Created a config package to use the test data for testing.

Created a listener package-->class to use that for reporting purpose.

Created a utility class and method for data driven testing

Created a pages package to include all the automation pages to write all the methods and properties of the respective class

Created a

src/test/java

to write a test class that can be utilized for running the test cases.

Created a

src/test/resources

folder to include the testng.xml that is been utilized for running the xml suite in pom.xml

Created a pom.xml file to include all the dependency that will be installed automatically by maven.

For Test Report I have generator 2 reports excluding the auto-generated report of emailable and testNg reports.

Extent Report
Allure Report
Also written a code for recording the test cases that can be utilize for checking all the stetps followed.

Also generated a log file consisting all the steps the test case have navigated to.

SET UP THE PROJECT

I have provided the code in zip file.

Please un Zip the code and paste somewhere in your PC.

Open Command prompt in window/ Terminal in OS.

Copy the path of the project and use

$ cd

$ mvn test

To generate allure report

$ allure generate allure-results

To view allure report

$ allure serve allure-results
