[![Maven Central](https://maven-badges.herokuapp.com/maven-central/ru.oleg-cherednik.utils.reflection/reflection-utils/badge.svg)](https://maven-badges.herokuapp.com/maven-central/ru.oleg-cherednik.utils.reflection/reflection-utils)
[![javadoc](https://javadoc.io/badge2/ru.oleg-cherednik.utils.reflection/reflection-utils/javadoc.svg)](https://javadoc.io/doc/ru.oleg-cherednik.utils.reflection/reflection-utils)
[![java8](https://badgen.net/badge/java/8+/blue)](https://badgen.net/)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.txt)
      
![travis-ci](https://travis-ci.com/oleg-cherednik/reflection-utils.svg?branch=master)
![circle-ci](https://circleci.com/gh/oleg-cherednik/reflection-utils/tree/master.svg?style=shield)
[![codacy-quality](https://app.codacy.com/project/badge/Grade/f4fe6d775eed4daa936620bb173052ae?branch=master)](https://app.codacy.com/gh/oleg-cherednik/reflection-utils/dashboard?branch=master)

<details><summary>develop</summary>
<p>

[![travis-ci](https://travis-ci.com/oleg-cherednik/reflection-utils.svg?branch=dev)](https://travis-ci.com/oleg-cherednik/reflection-utils)
[![circle-ci](https://circleci.com/gh/oleg-cherednik/reflection-utils/tree/dev.svg?style=shield)](https://app.circleci.com/pipelines/github/oleg-cherednik/reflection-utils)
[![codecov](https://codecov.io/gh/oleg-cherednik/reflection-utils/branch/dev/graph/badge.svg?token=OGJF0VP4G6)](https://codecov.io/gh/oleg-cherednik/reflection-utils)
[![vulnerabilities](https://snyk.io//test/github/oleg-cherednik/reflection-utils/badge.svg?targetFile=build.gradle)](https://snyk.io//test/github/oleg-cherednik/reflection-utils?targetFile=build.gradle)
[![codacy-quality](https://app.codacy.com/project/badge/Grade/f4fe6d775eed4daa936620bb173052ae?branch=dev)](https://app.codacy.com/gh/oleg-cherednik/reflection-utils/dashboard?branch=dev)

</p>
</details>  

# reflection-utils
> Set of utilities for JVM to make work with reflection much easier

## Gradle

```groovy
implementation 'ru.oleg-cherednik.utils.reflection:reflection-utils:1.0'
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

    public String getArgZero() {
        return "args_0";
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

    public static String getStaticArgZero() {
        return "static_args_0";
    }

    public static String getStaticArgOne(int one) {
        return "static_args_" + one;
    }

    public static String getStaticArgTwo(int one, String two) {
        return "static_args_" + one + '_' + two; 
    }

    public static String getStaticArgThree(int one, String two, boolean three) {
        return "static_args_" + one + '_' + two + '_' + three; 
    }

}

Person person = new Person();
```

</p>
</details>

### ConstructorUtils

<details><summary>details</summary>
<p>

#### Class object is available to use 

##### Invoke a constructor with no arguments for a given class

```java
Person person = ConstructorUtils.invokeConstructor(Person.class);
```

##### Invoke a constructor with exactly one argument for a given class

```java
Person person = ConstructorUtils.invokeConstructor(Person.class,
                                                   String.class, "anna");
```

##### Invoke a constructor with exactly two arguments for a given class

```java
Person person = ConstructorUtils.invokeConstructor(Person.class,
                                                   String.class, "peter",
                                                   int.class, 71);
```

##### Invoke a constructor with exactly three arguments for a given class

```java
Person person = ConstructorUtils.invokeConstructor(Person.class,
                                                   String.class, "marvin",
                                                   int.class, 91,
                                                   boolean.class, true); 
```

##### Invoke a constructor with many arguments for a given class

```java
Person person = ConstructorUtils.invokeConstructor(Person.class,
                                  new Class<?>[] { String.class, int.class, boolean.class },
                                  new Object[] { "marvin", 91, true });
```

#### Class object is not available and use full class name as string

Define variable with a canonical class name for `Person`:
```java
String canonicalName = "ru.olegcherednik.utils.reflection.data.Person";
// canonicalName == Person.class.getCanonicalName()
``` 

##### Invoke a constructor with no arguments for class with a given class name

```java
Person person = ConstructorUtils.invokeConstructor(canonicalName);
```
##### Invoke a constructor with exactly one argument for class with a given class name

```java
Person person = ConstructorUtils.invokeConstructor(canonicalName,
                                                   String.class, "anna");
```    

##### Invoke a constructor with exactly two arguments for class with a given class name

```java
Person person = ConstructorUtils.invokeConstructor(canonicalName,
                                                   String.class, "peter",
                                                   int.class, 71);
```

##### Invoke a constructor with exactly three arguments for class with a given class name

```java
Person person = ConstructorUtils.invokeConstructor(canonicalName,
                                                   String.class, "marvin",
                                                   int.class, 91,
                                                   boolean.class, true); 
```

##### Invoke a constructor with any number of arguments for class with a given class name

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

In case of `method` with a given name is not exist in the given class, then
the parent class will be used to find the `method` etc. 

#### Invoke not static method with a given name and no arguments

```java                                                                             
String argZero = MethodUtils.invokeMethod(person, "getArgZero");
// argZero == "args_0"
```

#### Invoke not static method with a given name and exactly 1 argument

```java                      
String argOne = MethodUtils.invokeMethod(person, "getArgOne"
                                         int.class, 1);
// argOne == "args_1"
```   

#### Invoke not static method with a given name and exactly 2 arguments

```java                      
String argTwo = MethodUtils.invokeMethod(person, "getArgTwo"
                                         int.class, 1,
                                         String.class, "x2");
// argTwo == "args_1_x2"
```

#### Invoke not static method with a given name and exactly 3 arguments

```java                      
String argThree = MethodUtils.invokeMethod(person, "getArgThree"
                                           int.class, 1,
                                           String.class, "x2",
                                           boolean.class, true);
// argThree == "args_1_x2_true"
```

#### Invoke not static method with a given name and any number of arguments

```java                      
String argThree = MethodUtils.invokeMethod(person, "getArgThree"
                                           new Class<?>[] { int.class, String.class, boolean.class },
                                           new Object[] { 1, "x2", true });
// argThree == "args_1_x2_true"
```

#### Invoke not static method with given arguments

```java                      
Method method = person.getClass().getDeclaredMethod("getArgThree");
String argThree = MethodUtils.invokeMethod(person, method, 1, "x2", true);
// argThree == "args_1_x2_true"
```

#### Invoke static method with a given name and no arguments

```java                                                                             
String staticArgZero = MethodUtils.invokeMethod(Person.class, "getStaticArgZero");
// staticArgZero == "static_args_0"
```

#### Invoke static method with a given name and exactly 1 argument

```java                      
String staticArgOne = MethodUtils.invokeMethod(Person.class, "getStaticArgOne"
                                               int.class, 1);
// staticArgOne == "static_args_1"
```   

#### Invoke static method with a given name and exactly 2 arguments

```java                      
String staticArgTwo = MethodUtils.invokeMethod(Person.class, "getStaticArgTwo"
                                               int.class, 1,
                                               String.class, "x2");
// staticArgTwo == "static_args_1_x2"
```

#### Invoke static method with a given name and exactly 3 arguments

```java                      
String staticArgThree = MethodUtils.invokeMethod(Person.class, "getStaticArgThree"
                                                 int.class, 1,
                                                 String.class, "x2",
                                                 boolean.class, true);
// staticArgThree == "static_args_1_x2_true"
```

#### Invoke static method with a given name and any number of arguments

```java                      
String staticArgThree = MethodUtils.invokeMethod(Person.class, "getStaticArgThree"
                                               new Class<?>[] { int.class, String.class, boolean.class },
                                               new Object[] { 1, "x2", true });
// staticArgThree == "args_1_x2_true"
```

#### Invoke static method with given arguments

```java                      
Method method = Person.class.getDeclaredMethod("getStaticArgThree");
String staticArgThree = MethodUtils.invokeMethod(method, 1, "x2", true);
// staticArgThree == "args_1_x2_true"
```

#### Retrieve method return type

```java                      
Method method = Person.class.getDeclaredMethod("getArgZero");
Class<?> cls = MethodUtils.getReturnType(method);
// cls == String.class
```

In case it's possible that given `method` could be `null` then default value can be provided:

```java
Class<?> cls = MethodUtils.getReturnType(null, int.class);
// cls == int.class
```

</p>
</details>

### FieldUtils

<details><summary>details</summary>
<p>

In case of `filed` with a given name is not exist in the given class, then the
parent class will be used to find the `field` etc. 

#### Get the value of the non-static field

```java
Field field = person.getClass().getDeclaredField("name");
String name = FieldUtils.getFieldValue(person, field);
// name == "defaultName"
```

#### Get the value of the non-static field with a given name
```java
String name = FieldUtils.getFieldValue(person, "name");
// name == "defaultName"
```

#### Get the value of the static field

```java
Field field = Person.class.getDeclaredField("AUTO");
String auto = FieldUtils.getFieldValue(person, field);
// auto == "ferrari"
```

#### Get the value of the static field with a given name
```java
String auto = FieldUtils.getFieldValue(Person.class, "AUTO");
// auto == "ferrari"
```

#### Set given value to the non-static field

```java
Field field = person.getClass().getDeclaredField("name");
FieldUtils.setFieldValue(person, field, "anna");
// person.name == "anna"
```

#### Set given value to the non-static field with a given name
```java
FieldUtils.getFieldValue(Person.class , "name", "anna");
// person.name == "anna"
```

#### Call given consumer for the non-static field 

```java
Field field = person.getClass().getDeclaredField("name");
Consumer<Field> task = f -> f.set(person, "anna")
FieldUtils.setFieldValue(field, task);
// person.name == "anna" 
```

#### Call given consumer for the non-static field with a given name 

```java
Consumer<Field> task = f -> f.set(person, "anna")
FieldUtils.setFieldValue(person, "name", task);
// person.name == "anna" 
```

#### Set given value to the static field

```java
Field field = Prson.getClass().getDeclaredField("AUTO");
FieldUtils.setStaticFieldValue(field, "mercedes");
// Person.AUTO == "mercedes"
```

#### Set given value to the static field with a given name

```java
FieldUtils.setStaticFieldValue(Person.class, "AUTO", "mercedes");
// Person.AUTO == "mercedes"
```

#### Call given consumer for the static field 

```java
Field field = Person.class.getDeclaredField("AUTO");
Consumer<Field> task = f -> f.set(null, "mercedes")
FieldUtils.setStaticFieldValue(field, task);
// Person.AUTO == "mercedes"           
```

#### Call given consumer for the static field with a given name 

```java
Consumer<Field> task = f -> f.set(null, "mercedes")
FieldUtils.setStaticFieldValue(Person.class, "AUTO", task);
// Person.AUTO == "mercedes"
```

#### Retrieve filed value type

```java                      
Field field = person.getClass().getDeclaredField("name");
Class<?> cls = FieldUtils.getType(field);
// cls == String.class
```

In case it's possible that given `field` could be `null` then default value can be provided:

```java
Class<?> cls = FieldUtils.getType(null, int.class);
// cls == int.class
```

#### Check if given field exists or not

The class contains a set of methods to check if given class exists or not. Methods do the
same job, but accept different input parameters.
 
```java
String className = Data.class.getName();
boolean existInClass = FieldUtils.isFieldExist(className, "name");  // true
boolean existInParent = FieldUtils.isFieldExistIncludeParents(className, "baseName");  // true                      
```

There're same method but with `Class<?>` as input parameter.
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
