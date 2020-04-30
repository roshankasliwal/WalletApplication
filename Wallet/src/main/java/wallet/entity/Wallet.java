package wallet.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.validator.constraints.UniqueElements;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
@AllArgsConstructor
@EqualsAndHashCode

@Entity
@Table(name = "wallet_master")
public class Wallet  {
	
	  @Id
	  @Column(name = "wallet_number")
	  private long walletNumber;
	  	  
	  @Column(name = "balance")
	  private double balance;	
	  
	  @Column(name = "local_date")
	  private LocalDate localdate;
	  
	  @OneToMany(cascade = CascadeType.ALL)
	  private Set<Transaction> transactions=new HashSet<Transaction>();

	  @OneToOne(cascade = CascadeType.ALL)
	  @JsonBackReference
	  private Customer customer;
	  		  
}