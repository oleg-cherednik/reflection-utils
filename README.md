[![Maven Central](https://maven-badges.herokuapp.com/maven-central/ru.oleg-cherednik.utils.reflection/reflection-utils/badge.svg)](https://maven-badges.herokuapp.com/maven-central/ru.oleg-cherednik.utils.reflection/reflection-utils)
[![javadoc](https://javadoc.io/badge2/ru.oleg-cherednik.utils.reflection/reflection-utils/javadoc.svg)](https://javadoc.io/doc/ru.oleg-cherednik.utils.reflection/reflection-utils)
[![java1.8](https://badgen.net/badge/java/1.8/blue)](https://badgen.net/)
[![travis-ci](https://travis-ci.com/oleg-cherednik/reflection-utils.svg?branch=dev)](https://travis-ci.com/oleg-cherednik/reflection-utils)
[![circle-ci](https://circleci.com/gh/oleg-cherednik/reflection-utils/tree/dev.svg?style=shield)](https://app.circleci.com/pipelines/github/oleg-cherednik/reflection-utils)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.txt)
[![codecov](https://codecov.io/gh/oleg-cherednik/reflection-utils/branch/dev/graph/badge.svg?token=OGJF0VP4G6)](https://codecov.io/gh/oleg-cherednik/reflection-utils)
[![Known Vulnerabilities](https://snyk.io//test/github/oleg-cherednik/reflection-utils/badge.svg?targetFile=build.gradle)](https://snyk.io//test/github/oleg-cherednik/reflection-utils?targetFile=build.gradle)
[![Codacy Quality](https://app.codacy.com/project/badge/Grade/f4fe6d775eed4daa936620bb173052ae)](https://www.codacy.com/gh/oleg-cherednik/reflection-utils/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=oleg-cherednik/reflection-utils&amp;utm_campaign=Badge_Grade)    

# Reflection Utils
> Set of utilities for JVM to make work with reflection much more easy

## Features

## Gradle

```groovy
compile 'ru.oleg-cherednik.utils.reflection:reflection-utils:1.0'
```

## Maven

```xml
<dependency>
    <groupId>ru.oleg-cherednik.utils.reflection</groupId>
    <artifactId>reflection-utils</artifactId>
    <version>1.0</version>
</dependency>
```                                                    
## Usage 

To simplify usage of _reflection-utils_, there're following classes:
* [ConstructorUtils](#constructorutils) - working with constructors;
* [MethodUtils](#methodutils) - working with methods;
* [FieldUtils](#fieldutils) - working with fields;
* [EnumUtils](#enumutils) - working with enums;
* [InvokeUtils](#constructorutils) - working with accessible objects;

### ConstructorUtils

This is a class definition for examples:
```java
public class Person {
    
    private String name = "defaultName";
    private int age = -1;
    private boolean marker;

    public Person() {}

    public Person(String name) {
        this.name = name;
    }
    
    public Person(String name, int age) {
        this.name = name;             
        this.age = age;
    }

    public Person(String name, int age, boolean marker) {
        this.name = name;             
        this.age = age;                               
        this.marker = marker;
    }

}

``` 

#### Invoke a constructor with no arguments

```java
Person person = ConstructorUtils.invokeConstructor(Person.class);
```
>```
>person.name = "defaultName"
>``` 

#### Invoke a constructor with exactly one argument

```java
Person person = ConstructorUtils.invokeConstructor(Person.class,
                                                   String.class, "anna");
```
>```
>person.name = "anna"
>```        

#### Invoke a constructor with exactly two arguments

```java
Person person = ConstructorUtils.invokeConstructor(Person.class,
                                                   String.class, "peter",
                                                   int.class, 71);
```
>```
>person.name = "peter"
>person.age = 71
>```

#### Invoke a constructor with exactly three arguments

```java
Person person = ConstructorUtils.invokeConstructor(Person.class,
                                                   String.class, "marvin",
                                                   int.class, 91,
                                                   boolean.class, true); 
```
>```
>person.name = "marvin"
>person.age = 91
>person.marker = true
>```

#### Invoke a constructor with many arguments

```java
Person person = ConstructorUtils.invokeConstructor(Person.class,
                                                   new Class<?>[] { String.class, int.class, boolean.class },
                                                   new Object[] { "marvin", 91, true });
```
>```
>person.name = "marvin"
>person.age = 91
>person.marker = true
>```

### MethodUtils


### FieldUtils


### EnumUtils


### InvokeUtils

### Links

*   Home page: https://github.com/oleg-cherednik/reflection-utils

*   Maven:
    *   **central:** https://mvnrepository.com/artifact/ru.oleg-cherednik.utils.reflection/reflection-utils
    *   **download:** https://repo1.maven.org/maven2/ru/oleg-cherednik/utils/reflection/reflection-utils/
