package wallet.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import javax.swing.text.DefaultEditorKit.CutAction;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import wallet.entity.Customer;
import wallet.entity.Wallet;
import wallet.exception.AccountAlreadyExitsException;
import wallet.exception.UserNotFoundException;
import wallet.model.SignupRequest;
import wallet.repository.CustomerRepository;
import wallet.repository.WalletRepository;

@Service
public class CustomerService {

	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	WalletRepository walletRepository;
	
	
	WalletService walletService=new WalletService();
//	@Transactional 
	public void saveCustomer(SignupRequest request) {
		
		
		Customer customer=new Customer();
		customer.setName(request.getName());
		customer.setMobileNumber(request.getMobileNumber());
		customer.setEmailId(request.getEmailId());
		customer.setPassword(request.getPassword());
		
		 Wallet wallet=new Wallet();
		  
		  Random rand = new Random();
		  long num = rand.nextInt(9000000) + 1000000;
		  
		  wallet.setWalletNumber(num); 
		  wallet.setBalance(0);
		  wallet.setLocaldate(LocalDate.now());
		  wallet.setCustomer(customer);
		
		  	customerRepository.save(customer);
			walletRepository.save(wallet);
			customer.setWallet(wallet);
			customerRepository.save(customer);
			
	//	customerRepository.updateUser(wallet,customer.getId());
		
	}
	
	public List<Customer> findAllCustomer(){
		return customerRepository.findAll();
	}
	
	public Customer findCustomerById(Integer id) {
		return customerRepository.findById(id)
	    		.orElseThrow(()-> new UserNotFoundException("Customer Not Found For Id:  "+id));   
	}
	
	public Customer findCustomerByMobileNumber(Long id) {
		Customer customer= customerRepository.findAll().stream()
				  .filter(c->c.getMobileNumber()==id)
				  .map(c->c)	 
				  .findFirst().orElseThrow(()->new UserNotFoundException("Customer Not Found For Mobile Number: "+id));
		return customer;

	}
	
	public void deleteCustomerById(Long mobileNumber){
		Integer id = customerRepository.findAll().stream()
				  .filter(c->c.getMobileNumber()==mobileNumber)
				  .map(c->c.getId())	 
				  .findFirst().orElseThrow(()->new UserNotFoundException("Mobile Number Not Registered: "+mobileNumber));
		customerRepository.deleteById(id);
	}

	public boolean isCustomerExits(Long mobileNumber) {
		List<Customer> customers=customerRepository.findAll();
		boolean check=false;
		for(Customer customer:customers) {
			if(customer.getMobileNumber()==mobileNumber) {
				check=true;
				break;
			}
		}
		return check;
	}

	public  void changePassword(Long mobileNumber, String newPassword) {
		Customer customer=findCustomerByMobileNumber(mobileNumber);
		if(customer!=null) {
			customer.setPassword(newPassword);
			customerRepository.save(customer);
		}
	}
}
