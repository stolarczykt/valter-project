package pl.oakfusion;

import org.apache.bval.constraints.Email;
import org.apache.bval.constraints.NotEmpty;
import org.junit.runners.Parameterized.Parameters;
import pl.oakfusion.valter.ValidationTestBase;

import java.util.List;

public class UserTest extends ValidationTestBase<User> {

	public UserTest(String description, User bean, Object violationsCount) {
		super(description, bean, violationsCount);
	}

	@Parameters(name = DEFAULT_TESTS_NAME_PATTERN)
	public static List<Object[]> params() {

		User validBean = new User("Valter", "valter@valtersky.val");
		return forBean(validBean)
				.field("name").shouldFailWith(NotEmpty.class).when("").withDescription("empty name")
				.field("email").shouldFailWith(Email.class).when("valter@").withDescription("wrong mail")
				.field("email").shouldFailOnce().when("valter@").withDescription("one violation on email field")
				.toList();
	}
}
