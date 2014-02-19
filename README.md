![Valter's logo](https://github.com/oakfusion/valter-project/raw/master/valter.png)


valter-project
==============

Valter exists to test JSR303 validated beans. Thanks to Valter it's possible to create JUnit parametrized tests for such beans.

##Requirements
Valter requires three libraries to be included to the project where You will be using it:
- JUnit - optimum: v4.11+, minimum: v4.0, but tests doesn't have their names,
- SLF4J,
- library with JSR303 bean's validator (in example hibernate validator).

##Usage
Using Valter can be described in three steps:

Step 1. Create bean with fields annotated with JSR303 annotations.

Step 2. Create test class witch extends `pl.oakfusion.valter.ValidationTestBase` class and create constructor matching super.

Step 3. Implement public static method which returns `List<Object[]>`. Use Valter's fluent interface to create this list.

   **Note:** This method must be annotated with `@Parameters` annotation, because JUnit parametrized runner will use it to run tests cases.


**Example:**

```java
public class UserTest extends ValidationTestBase<User> {

	public UserTest(String description, User bean, Object expected) {
		super(description, bean, expected);
	}

	@Parameters(name = DEFAULT_TESTS_NAME_PATTERN)
	public static List<Object[]> params() {

		User validBean = new User("Valter", "valter@valtersky.val", 26);

		return forBean(validBean)
				.field("name").shouldFailWith(NotEmpty.class).when("").withDescription("empty name")
				.field("email").shouldFailWith(Email.class).when("email@").withDescription("wrong email")
				.field("email").shouldFailOnce().when("valter@").withDescription("one violation on email field")
				.field("age").shouldFailWith(Range.class).when(2).withDescription("out of range")
				.toList();
	}
}
```

**Note:** if You are using JUnit older than 4.11 You need to remove `(name = DEFAULT_TESTS_NAME_PATTERN)`.


##Valter's fluent interface.
Creating tests cases for parametrized test should be done by Valter's fluent interface.
Example usage can looks as follow:


```java
forBean(validBean)  //passing valid bean object to Valter
	.field("name")  //passing field name which annotations You want to test
	.shouldFailWith(NotEmpty.class) //there You can call three methods, depends of it what You want to test: count of violations or class of annotation which will fail
	.when("")       //passing value which would cause violation
	.withDescription("empty name") //setting up name of the test case
	.toList();      building List<Object[]>
```

In this repo You can find two examples of Valter usage. One is for [Hibernate validator](https://github.com/oakfusion/valter-project/tree/master/examples/ValterHibernateValidationExample) and the other one for [Apache bean validator](https://github.com/oakfusion/valter-project/tree/master/examples/ValterApacheBeanValidationExample).

