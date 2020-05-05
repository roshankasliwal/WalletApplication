package wallet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.assertj.core.api.AssertFactory;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import wallet.entity.Customer;
import wallet.exception.AccountAlreadyExitsException;
import wallet.model.LoginRequest;
import wallet.model.SignupRequest;
import wallet.service.CustomerService;
@FixMethodOrder(MethodSorters.JVM)
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
class CustomerControllerTests {
	
		 @LocalServerPort
		 	int randomServerPort;
		 @Autowired
			CustomerService customerService;
	  
	@Test() 
	  public void testAddCustomerSuccess() throws URISyntaxException { 
	
		  RestTemplate restTemplate = new RestTemplate();
		  String baseUrl = "http://localhost:"+randomServerPort+"/customer/signup";
	      URI uri = new URI(baseUrl);  
	      
		  SignupRequest request=new  SignupRequest(); 
		  request.setMobileNumber(new Long("1234567890"));
		  request.setEmailId("test@gmail.com"); 
		  request.setName("test");
		  request.setPassword("test"); 
		  
		  HttpHeaders headers = new HttpHeaders();
		  headers.setContentType(MediaType.APPLICATION_JSON);
		  headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		  HttpEntity<SignupRequest> entity = new HttpEntity<>(request, headers);
		  
		  ResponseEntity response = restTemplate.exchange(uri,
		  HttpMethod.POST, entity, String.class);
		 
		  Assertions.assertEquals(200, response.getStatusCodeValue());
		  customerService.deleteCustomerById(new Long("1234567890"));
	 }
	  //Account Already exits Exception
	@Test() 
	  public void testAddCustomerFailure() throws URISyntaxException { 
		
		  RestTemplate restTemplate = new RestTemplate();
		  String baseUrl = "http://localhost:"+randomServerPort+"/customer/signup";
	      URI uri = new URI(baseUrl);
	      
		  
	      SignupRequest request=new  SignupRequest(); 
		  request.setMobileNumber(new Long("9999988888"));
		  request.setEmailId("test@gmail.com"); 
		  request.setName("test");
		  request.setPassword("test"); 
		  
		  HttpHeaders headers = new HttpHeaders();
		  headers.setContentType(MediaType.APPLICATION_JSON);
		  headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		  HttpEntity<SignupRequest> entity = new HttpEntity<>(request, headers);
		  ResponseEntity response=null;
		  
		  try {
			  response = restTemplate.exchange(uri,HttpMethod.POST, entity, String.class);
		  }
		  catch(Exception e) {
			  assertThat(e.getClass(), is(HttpClientErrorException.BadRequest.class));
			  
		  }
		  
		  }
	  @Test 
	  public void testLoginSuccess() throws URISyntaxException { 
		
		  RestTemplate restTemplate = new RestTemplate();
		  String baseUrl = "http://localhost:"+randomServerPort+"/customer/login";
	      URI uri = new URI(baseUrl);  
	      
		  LoginRequest request=new LoginRequest(); 
		  request.setMobileNumber(new Long("9999988888"));
		  request.setPassword("test"); 
		  
		  HttpHeaders headers = new HttpHeaders();
		  headers.setContentType(MediaType.APPLICATION_JSON);
		  headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		  HttpEntity<LoginRequest> entity = new HttpEntity<>(request, headers);
		  
		  ResponseEntity<Boolean> response = restTemplate.exchange(uri,
		  HttpMethod.POST, entity, Boolean.class);
		
		  Assertions.assertEquals(true, response.getBody());
	  	}
	  
	  @Test 
	  public void testLoginfailure_forWrongPassword() throws URISyntaxException { 
		
		  RestTemplate restTemplate = new RestTemplate();
		  String baseUrl = "http://localhost:"+randomServerPort+"/customer/login";
	      URI uri = new URI(baseUrl);  
	      
	
		  LoginRequest request=new LoginRequest(); 
		  request.setMobileNumber(new Long("9999988888"));
		  request.setPassword("wrongpassword"); 
		  
		  HttpHeaders headers = new HttpHeaders();
		  headers.setContentType(MediaType.APPLICATION_JSON);
		  headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		  HttpEntity<LoginRequest> entity = new HttpEntity<>(request, headers);
		  
		  ResponseEntity<Boolean> response = restTemplate.exchange(uri,
		  HttpMethod.POST, entity, Boolean.class);
		
		  Assertions.assertEquals(false, response.getBody());
		  
	  }
	  @Test 
	  public void testLoginfailure_forWrongUserId() throws URISyntaxException { 
		
		  RestTemplate restTemplate = new RestTemplate();
		  String baseUrl = "http://localhost:"+randomServerPort+"/customer/login";
	      URI uri = new URI(baseUrl);  
	      
		  LoginRequest request=new LoginRequest(); 
		  request.setMobileNumber(new Long("12345678"));
		  request.setPassword("test"); 
		  
		  HttpHeaders headers = new HttpHeaders();
		  headers.setContentType(MediaType.APPLICATION_JSON);
		  headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		  HttpEntity<LoginRequest> entity = new HttpEntity<>(request, headers);
		  
		  ResponseEntity<Boolean> response = null;
		  try {
			  response = restTemplate.exchange(uri,HttpMethod.POST, entity, Boolean.class);
		  }
		  catch(Exception e) {
			  assertThat(e.getClass(), is(HttpClientErrorException.NotFound.class));
		  }
		  
		  }

}
