package dtos;

import org.hibernate.validator.constraints.NotEmpty;

public class UtenteDTO {

	@NotEmpty
	private String email;

	@NotEmpty
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
