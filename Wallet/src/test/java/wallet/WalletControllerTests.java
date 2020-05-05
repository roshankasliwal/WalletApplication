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
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
import wallet.model.FundTransferRequest;
import wallet.model.SignupRequest;
import wallet.service.CustomerService;
import wallet.service.WalletService;
@FixMethodOrder(MethodSorters.JVM)
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class WalletControllerTests {
	
	@LocalServerPort
 	int randomServerPort;
	@Autowired
	CustomerService customerService;
	@BeforeEach
	public void createAccount() {
		SignupRequest request=new  SignupRequest(); 
		  request.setMobileNumber(new Long("9999988888"));
		  request.setEmailId("test@gmail.com"); 
		  request.setName("test");
		  request.setPassword("test"); 
		  customerService.saveCustomer(request);
	}
	
	@AfterEach
	public void deleteAccount() {
		customerService.deleteCustomerById(new Long("9999988888"));
	}
	
	@Test 
	  public void testAmountDepositSuccess() { 
		
		  RestTemplate restTemplate = new RestTemplate();      
		  Long walletNumber= getWalletNumber(new Long("9999988888"));	  
		  Double balance=getBalance(new Long("9999988888"));
		  Map<String, String> params = new HashMap<>();
	      params.put("walletNumber",walletNumber.toString());
	      params.put("amount","5000");
	      
	      ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:"+randomServerPort+"/wallet/deposit/{walletNumber}/{amount}"
	    		  ,String.class,params);

	      Assertions.assertEquals(200,response.getStatusCodeValue());
	      Double updatedBalance=getBalance(new Long("9999988888"));
		  assertEquals(updatedBalance,balance+5000);
	}
	@Test 
	  public void testAmountWithdrawSuccess()  { 
		
		  RestTemplate restTemplate = new RestTemplate();      
		  Long walletNumber= getWalletNumber(new Long("9999988888"));	  
		  
		  testAmountDepositSuccess();
		  Double balance=getBalance(new Long("9999988888"));
		  
		  Map<String, String> params = new HashMap<>();
	      params.put("walletNumber",walletNumber.toString());
	      params.put("amount","500");
	      
	      ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:"+randomServerPort+"/wallet/withdraw/{walletNumber}/{amount}"
	    		  ,String.class,params);

	      Assertions.assertEquals(200,response.getStatusCodeValue());
	      Double updatedBalance=getBalance(new Long("9999988888"));
		  assertEquals(updatedBalance,balance-500);
		  
		  }
	@Test 
	  public void testAmountWithdrawfailure_lessAmountInAccount() { 
		
		  RestTemplate restTemplate = new RestTemplate();      
		  Long walletNumber= getWalletNumber(new Long("9999988888"));	  
		  Double balance=getBalance(new Long("9999988888"));
		  Map<String, String> params = new HashMap<>();
	      params.put("walletNumber",walletNumber.toString());
	      params.put("amount","50000");
	      
	      ResponseEntity<String> response =null;
	      try {
	      response=restTemplate.getForEntity("http://localhost:"+randomServerPort+"/wallet/withdraw/{walletNumber}/{amount}"
	    		  ,String.class,params);
	      }
	      catch(Exception e) {
	    	  Double updatedBalance=getBalance(new Long("9999988888"));
			  assertEquals(updatedBalance,balance);  
	      }
	      
		  }
	@Test
	  public void testFundTransferSuccess() throws URISyntaxException { 
		 
		  RestTemplate restTemplate = new RestTemplate();
		  String baseUrl = "http://localhost:"+randomServerPort+"/wallet/fundTransfer";
	      URI uri = new URI(baseUrl);  
	      
		  SignupRequest request=new  SignupRequest(); 
		  request.setMobileNumber(new Long("8888888888"));
		  request.setEmailId("test2@gmail.com"); 
		  request.setName("test2");
		  request.setPassword("test2"); 
		  customerService.saveCustomer(request);
		  
		  long walletNumberReceiver=getWalletNumber(new Long("8888888888"));
		  long walletNumberSender=getWalletNumber(new Long("9999988888"));
		  
		  testAmountDepositSuccess();
		  double senderBalance=getBalance(new Long("9999988888"));
		  double receiverBalance=getBalance(new Long("8888888888"));
		  
		  FundTransferRequest fundTransferRequest=new FundTransferRequest();
		  fundTransferRequest.setSenderWalletNumber(walletNumberSender);
		  fundTransferRequest.setReceiverWalletNumber(walletNumberReceiver);
		  fundTransferRequest.setAmount(new Double("200"));
		  
		  HttpHeaders headers = new HttpHeaders();
		  headers.setContentType(MediaType.APPLICATION_JSON);
		  headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		  HttpEntity<FundTransferRequest> entity = new HttpEntity<>(fundTransferRequest, headers);
		  
		  ResponseEntity response = restTemplate.exchange(uri,
		  HttpMethod.POST, entity, String.class);
		
		  double updatedSenderBalance=getBalance(new Long("9999988888"));
		  double updatedReceiverBalance=getBalance(new Long("8888888888"));

		  Assertions.assertEquals(200, response.getStatusCodeValue());
		  assertEquals(updatedSenderBalance,senderBalance-200);
		  assertEquals(updatedReceiverBalance,receiverBalance+200);
		  
		  customerService.deleteCustomerById(new Long("8888888888"));
		  }

	@Test
	  public void testFundTransferFailure() throws URISyntaxException { 
		 
		  RestTemplate restTemplate = new RestTemplate();
		  String baseUrl = "http://localhost:"+randomServerPort+"/wallet/fundTransfer";
	      URI uri = new URI(baseUrl);  
	      
		  long walletNumberReceiver=122345;//wrong Account Number
		  long walletNumberSender=getWalletNumber(new Long("9999988888"));
		  
		  FundTransferRequest fundTransferRequest=new FundTransferRequest();
		  fundTransferRequest.setSenderWalletNumber(walletNumberSender);
		  fundTransferRequest.setReceiverWalletNumber(walletNumberReceiver);
		  fundTransferRequest.setAmount(new Double("200"));
		  
		  HttpHeaders headers = new HttpHeaders();
		  headers.setContentType(MediaType.APPLICATION_JSON);
		  headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		  HttpEntity<FundTransferRequest> entity = new HttpEntity<>(fundTransferRequest, headers);
		  
		  ResponseEntity response =null;
		  try {
		  response=restTemplate.exchange(uri,HttpMethod.POST, entity, String.class);
		  }
		  catch(Exception e) {
			  assertThat(e.getClass(), is(HttpClientErrorException.NotFound.class));
		  }
		  
		  }

	  public long getWalletNumber(long mobileNumber) {
			Customer customer=customerService.findCustomerByMobileNumber(mobileNumber);
			return customer.getWallet().getWalletNumber();
		}
	  public double getBalance(long mobileNumber) {
			Customer customer=customerService.findCustomerByMobileNumber(mobileNumber);
			return customer.getWallet().getBalance();
		}

}
