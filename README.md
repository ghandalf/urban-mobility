## README

The aim of the project is to grab and expose the Urban Mobility data from major cities in this world. One day!!! we never know will be 
for an other world.

I also use this project for experimental uses.


#### This repository

 * *urban-mobility*: the main container of the project. It contents the parent pom file.

    * *domain*: contain the objects of the domain (DDD) that can be used by our project and third party.

    * *repository*: CRUD on the data. Need database configuration, we use liquibase to keep track of modifications.
                    Look in scripts/postgresql, will help you to build the database/user/tables 

    * *handler*: (autonomous daemon running every 3 minutes.) 
				It contains city's specific data handling. 
				It has to manipulate feeds like GBFS and GTFS. 
				It has to normalize the data format from those cities use the repository level to save it.

    * *service*:	Use to execute business rules.
  
    * *rest-api*: Expose for our application or third parties the data.


* *Version*: 0.0.9


#### Set up

You need to make sure you have the latest jdk install, take a look in the urban-mobility pom.xml, 
to find out the current jdk in used.<br>
    `<java.version>jdk-11.0.1</java.version>`

I use [toolchains](https://maven.apache.org/guides/mini/guide-using-toolchains.html) so here is my my file under ~/.m2/toolchains.xml

```
<?xml version="1.0" encoding="UTF8"?>

<toolchains>

  <toolchain>
    <type>jdk</type>
    <provides>
      <id>jdk-10.0.2</id>
      <version>jdk-10.0.2</version>
      <vendor>oracle</vendor>
    </provides>
    <configuration>
      <jdkHome>/data/app/programs/java/jdk-10.0.2</jdkHome>
    </configuration>
  </toolchain>

  <toolchain>
    <type>jdk</type>
    <provides>
      <id>jdk-11.0.1</id>
      <version>jdk-11.0.1</version>
      <vendor>oracle</vendor>
    </provides>
    <configuration>
      <jdkHome>/data/links/jdk</jdkHome>
    </configuration>
  </toolchain>

</toolchains>

```

At compilation, you will see those lines:

```
[INFO] --- maven-toolchains-plugin:1.1:toolchain (default) @ parent ---
[INFO] Required toolchain: jdk [ vendor='oracle' version='jdk-11.0.1' ]
[INFO] Found matching toolchain for type jdk: JDK[/data/links/jdk]

```
Nice isn't it?


**Local configuration**

Make sure that you have the latest jdk install.


```
	open a terminal
	move in your workspace where you want the project
	execute those commands:
	    git clone https://github.com/ghandalf/urban-mobility.git
	    mvn compile
	    mvn eclipse:eclipse | mvn idea:idea // Eclipse or IntelliJ GUI configuration
	    mvn clean install -DskipTests // will deploy each artifacts in your local repository
```



#### Contribution guidelines

* Code review
* Other guidelines

#### Technical advice

I am trying to respect the standard in my work.

**[SOLID](https://en.wikipedia.org/wiki/SOLID)**

```
	Single responsibility principle
		a class should have only a single responsibility (i.e. changes to only one part of the software's specification 
		should be able to affect the specification of the class).
	Open/closed principle
		"software entities … should be open for extension, but closed for modification."
	Liskov substitution principle
		"objects in a program should be replaceable with instances of their subtypes without altering the correctness of that program."
	Interface segregation principle
		"many client-specific interfaces are better than one general-purpose interface."[4]
	Dependency inversion principle
		one should "depend upon abstractions, [not] concretions."
```

#### Resources

* Repo owner: Ghandalf
* Community: Slack urban-mobility

#### GC garbage collector output with maven

Since [maven 3.3.1](https://maven.apache.org/docs/3.3.1/release-notes.html#JVM_and_Command_Line_Options), we can create a sub directory .mvn on our project add a jvm.config file.

I activated the log for the garbage collector in handler and rest-api modules. Take a look at [Enabled Logging with JVM](https://docs.oracle.com/javase/9/tools/java.htm#JSWOR-GUID-BE93ABDC-999C-4CB5-A88B-1994AAAC74D5).

#### Todo

- Activate the gc output -- DONE
- Fix the module-info.java for each modules.
- Automate the database/user/tables creation with liquibase.
- Put in place Jenkins's Pipeline
- Create docker container
- Create kubernates deployer
