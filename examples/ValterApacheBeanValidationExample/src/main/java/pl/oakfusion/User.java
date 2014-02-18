package pl.oakfusion;

import org.apache.bval.constraints.Email;
import org.apache.bval.constraints.NotEmpty;

public class User {

	@NotEmpty
	String name;

	@Email
	String email;

	public User(String name, String email) {
		this.name = name;
		this.email = email;
	}
}
