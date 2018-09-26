# README #

The aim of the project is to grab and expose the Urban Mobility data.

Maven is configure to take care of the sub projects and dependencies.
The domain sub project could be use by third party application.


### What is this repository for? ###

* urban-mobility: the main container of the project.
  - urban-domain: contain the objects of the domain (DDD) that can be used by our project and third party.
  - urban-repository: The aim of the repository is to read, interprete the transportion feeds like GBFS and GTFS, and finally save the data into a database for future used.
* Version: 0.0.1-SNAPSHOT

### How do I get set up? ###

* git clone https://
* move under the project and execute for Eclipse user mvn eclipse:eclipse
* Eclipse Configuration: import in Eclipse the main project as a maven project.
* Dependencies: they are manage by maven, don't bother by moving from one directories to another.
* How to run tests: open a command prompt, move under the parent project, execute mvn test
* Deployment instructions: Once Jenkins's Pipeline will be configure, you will have nothing to do, except push your code on GitHub
* Retrieve binary code: the jar file must be taken from Binary Source Code as usual.

### Contribution guidelines ###

* Code review
* Other guidelines

### Who do I talk to? ###

* Repo owner: Francis Ouellet
* Community: Slack urban-mobility
