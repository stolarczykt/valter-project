package ValterHibernateValidationExample;


import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Range;

public class User {

    @NotEmpty
    private String name;

    @Email
    private String email;

    @Range(min=16, max=150)
    private int age;

    public User(String notEmptyField, String emailField, int age) {
        this.name = notEmptyField;
        this.email = emailField;
        this.age = age;
    }
}
