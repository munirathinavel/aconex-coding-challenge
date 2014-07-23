aconex-coding-challenge
=======================

Exercise project given by Aconex - Pradeep's attempt at creating a simple gedcom parser

<img src="https://travis-ci.org/pkumar/aconex-coding-challenge.svg?branch=master" alt="Travis CI Status"/>    https://travis-ci.org/pkumar/aconex-coding-challenge

Why Maven?
=======================
Convention over configuration, enough said. 
Just in case, you don't have Maven, instructions here: http://maven.apache.org/guides/getting-started/maven-in-five-minutes.html 

How to run tests and the CLI?
=======================
Clone the project and run "mvn clean test" or "mvn clean verify". For your convenince, I have linked the project to Travis CI and the build status is displayed above. 
Gedcom CLI is the class containing main method to invoke. Run without varargs to convert the sample TXT file in the project. Provide 2 arguments to the main if you want to specify the input TXT file and output XML file. 


Choice of project
=======================
GEDCOM parser is chosen as the project could be achieved with Java's rich XML parsing /File handling library, while the other projects might be achieved best with third-party libraries like Joda-Time

Design and Approach
=======================
## Packages : ##

- com.pktr.gedcom.model - Model class for the root element and child and subchild elements, ParentNode and ChildNode and constants. 
- com.pktr.gedcom.process - Contains 2 classes XMLCreator which will have the interfacing method to run the parser. NodeParser contains the logic to convert the TXT contents into Map object representing the XML. 
- com.pktr.gedcom.util - Common utility classes for String, File and XML operations. 



Bugs (known issues) and shortcomings
=====================================
- Node Parser is not robust. 
- Test coverage for negative scenarios. 
