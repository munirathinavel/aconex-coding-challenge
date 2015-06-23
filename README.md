aconex-coding-challenge
=======================

Exercise project given by Aconex - Pradeep's attempt at creating a simple gedcom parser

<img src="https://travis-ci.org/pkumar/aconex-coding-challenge.svg?branch=master" alt="Travis CI Status"/>    https://travis-ci.org/pkumar/aconex-coding-challenge

[ ![Codeship Status for pkumar/aconex-coding-challenge](https://codeship.com/projects/98873550-fba9-0132-df81-1658e61aa1b1/status?branch=master)](https://codeship.com/projects/87225)

Why Maven?
=======================
Convention over configuration, enough said. 
Just in case, you don't have Maven, instructions here: http://maven.apache.org/guides/getting-started/maven-in-five-minutes.html 

How to run tests and the CLI?
=======================
Clone the project and run "mvn clean test" or "mvn clean verify". For your convenince, I have linked the project to Travis CI and the build status is displayed above. 
Gedcom CLI is the class containing main method to invoke. Run without varargs to convert the sample TXT file in the project. Provide 2 arguments to the main if you want to specify the input TXT file and output XML file. 
Place the input TXT and output XML file alongside POM file and execute:
$ mvn exec:java -Dexec.mainClass="com.pktr.gedcom.GedcomCLI" -Dexec.args="GEDCOMInput.txt GEDCOMOutput.xml"


Choice of project
=======================
GEDCOM parser is chosen as the project could be achieved with Java's rich XML parsing /File handling library, while the other projects might be achieved best with third-party libraries like Joda-Time

Design and Approach
=======================

## Packages : ##

- com.pktr.gedcom.model - Model class for the root element and child and subchild elements, ParentNode and ChildNode and constants. 
- com.pktr.gedcom.process - Contains 2 classes XMLCreator which will have the interfacing method to run the parser. NodeParser contains the logic to convert the TXT contents into Map object representing the XML. 
- com.pktr.gedcom.util - Common utility class for String, File and XML operations. 

## Coding Best Practices : ##

- TDD approach followed. 
- Mockito and Hamcrest libraries are used along with Junit 4. 
- OOP concepts applied as and when required
- Other regular best Java practices (without PMD violations)


Bugs (known issues) and shortcomings
=====================================
- Node Parser is not robust. 
- Test coverage for negative scenarios. 
