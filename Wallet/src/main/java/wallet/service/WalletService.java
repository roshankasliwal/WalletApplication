package wallet.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import wallet.entity.Customer;
import wallet.entity.Transaction;
import wallet.entity.Wallet;
import wallet.exception.AccountAlreadyExitsException;
import wallet.exception.LowBalanceException;
import wallet.exception.UserNotFoundException;
import wallet.model.FundTransferRequest;
import wallet.repository.CustomerRepository;
import wallet.repository.WalletRepository;

@Service
public class WalletService {
	@Autowired
	WalletRepository walletRepository;
	
	public void deposit(Long walletNumber, double amount) {
		
		Transaction transaction=new Transaction();
		transaction.setWalletId(walletNumber);
		transaction.setAmount(amount);
		transaction.setType("Credited");
		transaction.setLocalDateTime(LocalDateTime.now());
		transaction.setTransactionId(UUID.randomUUID().toString());
		
		Wallet wallet=	walletRepository.findById(walletNumber)
				.orElseThrow(()->new UserNotFoundException("Incorrect Account Number"));
		
		Set<Transaction> transactions=wallet.getTransactions();
		transactions.add(transaction);
		wallet.setTransactions(transactions);
		wallet.setBalance(wallet.getBalance()+amount);
		walletRepository.save(wallet);
	
	}
	
	public void withdraw(Long walletNumber, double amount) {
		
		Transaction transaction=new Transaction();
		transaction.setWalletId(walletNumber);
		transaction.setAmount(amount);
		transaction.setType("Debited");
		transaction.setLocalDateTime(LocalDateTime.now());
		transaction.setTransactionId(UUID.randomUUID().toString());
		
		
		Wallet wallet=	walletRepository.findById(walletNumber)
				.orElseThrow(()->new UserNotFoundException("Incorrect Account Number"));
		
		if(wallet.getBalance()>=amount)
		{
			Set<Transaction> transactions=wallet.getTransactions();
			transactions.add(transaction);
			wallet.setTransactions(transactions);
			wallet.setBalance(wallet.getBalance()-amount);
			walletRepository.save(wallet);		
		}
		else {
			throw new LowBalanceException("Low Balance !!!   Account Balance : "+wallet.getBalance());
		}
		
	}
	
	public void transferFund(FundTransferRequest fundTransferRequest) {
		
		String uuid=UUID.randomUUID().toString();
		Transaction transaction1=new Transaction();
		
		Wallet senderWallet=walletRepository.findById(fundTransferRequest.getSenderWalletNumber())
				.orElseThrow(()->new UserNotFoundException("Incorrect Account Number of sender"));
		
		transaction1.setWalletId(fundTransferRequest.getSenderWalletNumber());
		transaction1.setAmount(fundTransferRequest.getAmount());
		transaction1.setType("Sent to "+fundTransferRequest.getReceiverWalletNumber());
		transaction1.setLocalDateTime(LocalDateTime.now());
		transaction1.setTransactionId(uuid);
		
		if(senderWallet.getBalance()>=fundTransferRequest.getAmount()) {
		
			Set<Transaction> transactions=senderWallet.getTransactions();
			transactions.add(transaction1);
			senderWallet.setTransactions(transactions);
			senderWallet.setBalance(senderWallet.getBalance()-fundTransferRequest.getAmount());
			
			Wallet receiverWallet=walletRepository.findById(fundTransferRequest.getReceiverWalletNumber())
					.orElseThrow(()->new UserNotFoundException("Incorrect Account Number of receiver "));
			
			Transaction transaction2=new Transaction();
			transaction2.setTransactionId(uuid);
			transaction2.setWalletId(fundTransferRequest.getReceiverWalletNumber());
			transaction2.setAmount(fundTransferRequest.getAmount());
			transaction2.setType("Received from "+fundTransferRequest.getSenderWalletNumber());
			transaction2.setLocalDateTime(LocalDateTime.now());
		
			transactions=receiverWallet.getTransactions();
			transactions.add(transaction2);
			receiverWallet.setTransactions(transactions);
			receiverWallet.setBalance(receiverWallet.getBalance()+fundTransferRequest.getAmount());
			
			walletRepository.save(senderWallet);
			walletRepository.save(receiverWallet);
			
		}
	  else {
			throw new LowBalanceException("Low Balance !!! Account Balance:"+senderWallet.getBalance());
		}
		
	}

	public List<Transaction> getTransactions(Long walletNumber){
		
		ArrayList<Transaction>trans=new ArrayList<>(walletRepository.findById(walletNumber).get().getTransactions());
		
		Comparator<Transaction> comp=(b1,b2)->{
				 LocalDateTime ldt1=b1.getLocalDateTime();
				 LocalDateTime ldt2=b2.getLocalDateTime();
				 int comparison=ldt1.compareTo(ldt2);
				 return comparison;
		};
			Collections.sort(trans,comp);
	return trans;
	}
}
