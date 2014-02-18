package ValterHibernateValidationExample;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.junit.runners.Parameterized.Parameters;
import pl.oakfusion.valter.ValidationTestBase;

import java.util.List;

public class UserTest extends ValidationTestBase<User> {

	public UserTest(String description, User bean, Object violationsCount) {
		super(description, bean, violationsCount);
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
