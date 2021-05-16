[![Maven Central](https://maven-badges.herokuapp.com/maven-central/ru.oleg-cherednik.utils.reflection/reflection-utils/badge.svg)](https://maven-badges.herokuapp.com/maven-central/ru.oleg-cherednik.utils.reflection/reflection-utils)
[![javadoc](https://javadoc.io/badge2/ru.oleg-cherednik.utils.reflection/reflection-utils/javadoc.svg)](https://javadoc.io/doc/ru.oleg-cherednik.utils.reflection/reflection-utils)
[![java1.8](https://badgen.net/badge/java/1.8/blue)](https://badgen.net/)
[![travis-ci](https://travis-ci.com/oleg-cherednik/reflection-utils.svg?branch=dev)](https://travis-ci.com/oleg-cherednik/reflection-utils)
[![circle-ci](https://circleci.com/gh/oleg-cherednik/reflection-utils/tree/dev.svg?style=shield)](https://app.circleci.com/pipelines/github/oleg-cherednik/reflection-utils)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.txt)
[![codecov](https://codecov.io/gh/oleg-cherednik/reflection-utils/branch/dev/graph/badge.svg?token=OGJF0VP4G6)](https://codecov.io/gh/oleg-cherednik/reflection-utils)
[![Known Vulnerabilities](https://snyk.io//test/github/oleg-cherednik/reflection-utils/badge.svg?targetFile=build.gradle)](https://snyk.io//test/github/oleg-cherednik/reflection-utils?targetFile=build.gradle)
[![Codacy Quality](https://app.codacy.com/project/badge/Grade/f4fe6d775eed4daa936620bb173052ae)](https://www.codacy.com/gh/oleg-cherednik/reflection-utils/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=oleg-cherednik/reflection-utils&amp;utm_campaign=Badge_Grade)    

# reflection-utils
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
*   [ConstructorUtils](#constructorutils) - working with `Constructor`;
*   [MethodUtils](#methodutils) - working with `Method`;
*   [FieldUtils](#fieldutils) - working with `Field`;
*   [EnumUtils](#enumutils) - working with `Enum`;
*   [AccessibleObjectUtils](#accessibleobjectutils) - working with `AccessibleObject`;

<details><summary>Person class for examples</summary>
<p>

```java   
package ru.olegcherednik.utils.reflection.data;

public class Person {         

    private static final String AUTO = "ferrari";
    
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

    public String getCity() {
        return "Saint-Petersburg";
    }

    public String getArgOne(int one) {
        return "args_" + one;
    }

    public String getArgTwo(int one, String two) {
        return "args_" + one + '_' + two; 
    }

    public String getArgThree(int one, String two, boolean three) {
        return "args_" + one + '_' + two + '_' + three; 
    }

}
```
</p>
</details>

### ConstructorUtils

<details><summary>details</summary>
<p>

#### Class object is available to use 

##### Invoke a constructor with no arguments for given class

```java
Person person = ConstructorUtils.invokeConstructor(Person.class);
```

##### Invoke a constructor with exactly one argument for given class

```java
Person person = ConstructorUtils.invokeConstructor(Person.class,
                                                   String.class, "anna");
```

##### Invoke a constructor with exactly two arguments for given class

```java
Person person = ConstructorUtils.invokeConstructor(Person.class,
                                                   String.class, "peter",
                                                   int.class, 71);
```

##### Invoke a constructor with exactly three arguments for given class

```java
Person person = ConstructorUtils.invokeConstructor(Person.class,
                                                   String.class, "marvin",
                                                   int.class, 91,
                                                   boolean.class, true); 
```

##### Invoke a constructor with many arguments for given class

```java
Person person = ConstructorUtils.invokeConstructor(Person.class,
                                  new Class<?>[] { String.class, int.class, boolean.class },
                                  new Object[] { "marvin", 91, true });
```

#### Class object is not available and use full class name as string

Define variable with canonical class name for `Person`:
```java
String canonicalName = "ru.olegcherednik.utils.reflection.data.Person";
// canonicalName == Person.class.getCanonicalName()
``` 

##### Invoke a constructor with no arguments for class with given classname

```java
Person person = ConstructorUtils.invokeConstructor(canonicalName);
```
##### Invoke a constructor with exactly one argument for class with given classname

```java
Person person = ConstructorUtils.invokeConstructor(canonicalName,
                                                   String.class, "anna");
```    

##### Invoke a constructor with exactly two arguments for class with given classname

```java
Person person = ConstructorUtils.invokeConstructor(canonicalName,
                                                   String.class, "peter",
                                                   int.class, 71);
```

##### Invoke a constructor with exactly three arguments for class with given classname

```java
Person person = ConstructorUtils.invokeConstructor(canonicalName,
                                                   String.class, "marvin",
                                                   int.class, 91,
                                                   boolean.class, true); 
```

##### Invoke a constructor with any number of arguments for class with given classname

```java
Person person = ConstructorUtils.invokeConstructor(canonicalName,
                                  new Class<?>[] { String.class, int.class, boolean.class },
                                  new Object[] { "marvin", 91, true });
```

#### Constructor object is available to use

##### Invoke a given constructor providing zero arguments

```java
Constructor<Person> constructor = Person.class.getDeclaredConstructor();
Person person = ConstructorUtils.invokeConstructor(constructor); 
```

##### Invoke a given constructor providing one argument

```java                                                                 
Constructor<Person> constructor = Person.class.getDeclaredConstructor(String.class);
Person person = ConstructorUtils.invokeConstructor(constructor, "anna");
```    

##### Invoke a given constructor providing two arguments

```java                                                                             
Constructor<Person> constructor = Person.class.getDeclaredConstructor(String.class,
                                                                      int.class);
Person person = ConstructorUtils.invokeConstructor(constructor, "peter", 71);
```

##### Invoke a given constructor providing three arguments

```java                                                                                        
Constructor<Person> constructor = Person.class.getDeclaredConstructor(String.class,
                                                                      int.class,
                                                                      boolean.class);
Person person = ConstructorUtils.invokeConstructor(constructor, "marvin", 91, true); 
```
</p>
</details>

### MethodUtils

<details><summary>details</summary>
<p>

Assume that instance of `Person` is declared:

```java
Person person = new Person();
```

#### Invoke not static method with given name and no arguments

```java                                                                             
String city = MethodUtils.invokeMethod(person, "getCity");
// city == "Saint-Ptersburg"
```

#### Invoke not static method with given name and exactly 1 argument

```java                      
String argOne = MethodUtils.invokeMethod(person, "getArgOne"
                                         int.class, 1);
// argOne == "args_1"
```   

#### Invoke not static method with given name and exactly 2 arguments

```java                      
String argTwo = MethodUtils.invokeMethod(person, "getArgTwo"
                                         int.class, 1,
                                         String.class, "x2");
// argTwo == "args_1_x2"
```

#### Invoke not static method with given name and exactly 3 arguments

```java                      
String argThree = MethodUtils.invokeMethod(person, "getArgThree"
                                           int.class, 1,
                                           String.class, "x2",
                                           boolean.class, true);
// argThree == "args_1_x2_true"
```

#### Invoke not static method with given name and any number of arguments

```java                      
String argThree = MethodUtils.invokeMethod(person, "getArgThree"
                                           new Class<?>[] { int.class, String.class, boolean.class },
                                           new Object[] { 1, "x2", true });
// argThree == "args_1_x2_true"
```

#### Invoke not static given method with given arguments

```java                      
Method method = person.getClass().getDeclaredMethod("getArgThree");
String argThree = MethodUtils.invokeMethod(person, method, 1, "x2", true);
// argThree == "args_1_x2_true"
```

</p>
</details>

### FieldUtils

<details><summary>details</summary>
<p>
</p>
</details>

### EnumUtils

<details><summary>details</summary>
<p>

#### Add new constant to the given enum

```java 
enum CarBrand {
    BMW,
    MERCEDES
}

EnumUtils.addConstant(CarBrand.class, "AUDI");
```
</p>
</details>

### AccessibleObjectUtils

<details><summary>details</summary>
<p>

#### Invoke consumer on the accessible object

Use consumer to do any activity on the given accessible object and no return any value.

```java
Person person = new Person();
Field field = person.getClass().getDeclaredField("name");
Consumer<Field> task = f -> f.set(person, "oleg");
AccessibleObjectUtils.invokeConsumer(field, task);
```

#### Invoke function on the accessible object

Use function to do any activity on the given accessible object and return a value

```java
Person person = new Person();  
Method method = person.getClass().getDeclaredMethod("getCity");
Function<Method, String> task = m -> (String)m.invoke(person); 
String city = AccessibleObjectUtils.invokeFunction(field, task);
```

#### Invoke not static accessible object

An accessible object could be either `Field` or `Method`.

```java
Person person = new Person();  
Field field = data.getClass().getDeclaredField("name");
AccessibleObjectUtils.invoke(person, field);
```

#### Invoke static accessible object

An accessible object could be either `Field` or `Method`.

```java
Field field = data.getClass().getDeclaredField("AUTO");
String auto1 = AccessibleObjectUtils.invoke(field);
String auto2 = AccessibleObjectUtils.invoke(null, field);    // alternative
```
</p>
</details>

### Links

*   Home page: https://github.com/oleg-cherednik/reflection-utils

*   Maven:
    *   **central:** https://mvnrepository.com/artifact/ru.oleg-cherednik.utils.reflection/reflection-utils
    *   **download:** https://repo1.maven.org/maven2/ru/oleg-cherednik/utils/reflection/reflection-utils/
