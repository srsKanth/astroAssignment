# This is maven based java project build to automate the given tests using Selenium and TestNg

#Project Structure
      1. Framework specific reusable methods are kept in src/main/java 
          a. Core packae contains reusable methods related Selenium and TestNg
          b. Utils package contains the Java related, File handling reusable methods
      2. Application specific page class, tests and test suite kept in src/main/test
      3. Global parameters like browsertype, applicaiton urls are kept in globalProperties file (could be find in root folder)
      
      
#How to execute
 simply run 'mvn clean test' from project root folder
 
#Where to view reports
  Currently reports are being shown in console itself