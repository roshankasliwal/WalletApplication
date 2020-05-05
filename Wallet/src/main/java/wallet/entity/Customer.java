package wallet.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor

@Entity
@Table(name = "customer_master")
public class Customer {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id",length = 10)
    private int id;
	 
    @NotNull
	@Column(name = "mobile_number",length = 10)
	private long mobileNumber;
	
	@NotNull
	@Column(name = "name", length = 50)
	private String name;
	
	@NotNull
	private String password;
	
	@Email
	@Column(name = "email_id",length=40)
	private String emailId;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="wallet_id")
	@JsonManagedReference
	private Wallet wallet;
	
	
}
