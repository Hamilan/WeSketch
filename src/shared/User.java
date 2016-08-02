package shared;

/**
 * This class represents the contact information of a User of the system 
 * @author Hamilan
 */
public class User {
	public long id;
	public String login;
	public String password;
	public String name;
	public String lastname;
	public String email;
	public String phone;
	public char sex;

	public User() {
	}

	public User(int id, String login, String password, String name, String lastname, String email, String phone, char sex) {
		this.login = login;
		this.password= password;
		this.name = name;
		this.lastname = lastname;
		this.email = email;
		this.phone = phone;
		this.sex = sex;
	}
	
}
