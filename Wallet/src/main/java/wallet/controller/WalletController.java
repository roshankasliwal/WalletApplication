package wallet.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import wallet.entity.Transaction;
import wallet.entity.Wallet;
import wallet.model.FundTransferRequest;
import wallet.service.WalletService;

@RestController
@RequestMapping("/wallet")
@CrossOrigin("http://localhost:4200")
public class WalletController {
	
	@Autowired
	WalletService walletService;
	
	@GetMapping("/deposit/{walletNumber}/{amount}")
	public ResponseEntity deposit(@PathVariable Long walletNumber,@PathVariable Double amount) {
		walletService.deposit(walletNumber, amount);
		return  ResponseEntity.ok().build();
	}
	
	@GetMapping("/withdraw/{walletNumber}/{amount}")
	public ResponseEntity withdraw(@PathVariable Long walletNumber,@PathVariable Double amount) {
		walletService.withdraw(walletNumber, amount);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/fundTransfer")
	public ResponseEntity fundTransfer(@RequestBody FundTransferRequest fundTransferRequest)
	{
		walletService.transferFund(fundTransferRequest);
		return ResponseEntity.ok().build();
	}
	@GetMapping("/transactions/{walletNumber}")
	public ResponseEntity<List<Transaction>> getTransactions(@PathVariable Long walletNumber){
		return ResponseEntity.ok(walletService.getTransactions(walletNumber));
	}
}
