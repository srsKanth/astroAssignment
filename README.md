# This is maven based java project to automate Webbasd applicaiton using Selenium and TestNg

#Project Structure
      1. Framework specific reusable methods are kept in src/main/java 
          a. Core package contains reusable methods related Selenium (Web interactions) and TestNg
          b. Utils package contains reusable methods related to File handling 
      2. Application specific page class, tests and test suites are kept in src/main/test
      3. Global parameters like browsertype, applicaiton urls are kept in globalProperties file (could be find in root folder)

#Testing framework
      1. Followed page object model and created page classes for every pages 
      2. All locators and methods related to specific page kept in page class
      3. Page class extends SeleniumMethods class (where reusable web interaction methos are kept (eg, click, type))
      4. Test cases are grouped using @Test and kept in suite level (can create separate java class for different test suite)
      4. Test suite extends TestngBase class to make use of Testng listener

#How to execute
run 'mvn clean test' from project root folder
