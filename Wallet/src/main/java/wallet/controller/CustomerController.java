package wallet.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import wallet.entity.Customer;
import wallet.entity.Wallet;
import wallet.exception.AccountAlreadyExitsException;
import wallet.exception.UserNotFoundException;
import wallet.model.LoginRequest;
import wallet.model.SignupRequest;
import wallet.repository.CustomerRepository;
import wallet.repository.WalletRepository;
import wallet.service.CustomerService;

@RestController
@RequestMapping("/customer")
@CrossOrigin("http://localhost:4200")
public class CustomerController {
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	WalletRepository walletRepository;
	
	@PostMapping("/signup")
	public ResponseEntity signup(@RequestBody SignupRequest request) {
		
		if(!customerService.isCustomerExits(request.getMobileNumber())) {
			customerService.saveCustomer(request);
		}
		else
		{
			throw new AccountAlreadyExitsException(" Mobile Number Already Exits!!! Mobile Number:"+request.getMobileNumber());
		}
		
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/search/{mobileNumber}")
	public ResponseEntity<Customer> searchCustomerByMobileNumber(@PathVariable Long mobileNumber) {	
		return ResponseEntity.ok(customerService.findCustomerByMobileNumber(mobileNumber));
	}
	
	
	//not in use just for checking purpose
	@GetMapping("/findAll")
	public ResponseEntity<List<Customer>> findAllCustomer(){
		return ResponseEntity.ok(customerService.findAllCustomer());
	}
	
	//not in use just for checking purpose 
	@DeleteMapping("/delete/{mobileNumber}")
	public ResponseEntity<List<Customer>> deleteCustomer(@PathVariable Long mobileNumber){
		customerService.deleteCustomerById(mobileNumber);
		return ResponseEntity.ok(customerService.findAllCustomer());
	}
	
	@GetMapping("/forgot/{mobileNumber}/{newPassword}")
	public ResponseEntity changePassword(@PathVariable Long mobileNumber,@PathVariable String newPassword) {
		customerService.changePassword(mobileNumber,newPassword);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/login")
	public Boolean login(@RequestBody LoginRequest request){
		Customer customer =customerService.findCustomerByMobileNumber(request.getMobileNumber());
			if(customer.getMobileNumber()==request.getMobileNumber()) {
				if(customer.getPassword().equals(request.getPassword()))
					return true;
				else
					return false;
		}
		else 
			return false;
	}
	
}
