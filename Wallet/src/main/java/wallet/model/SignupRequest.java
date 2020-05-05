package wallet.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignupRequest {
	private long mobileNumber;
	private String name;
	private String password;
	private String emailId;
}
