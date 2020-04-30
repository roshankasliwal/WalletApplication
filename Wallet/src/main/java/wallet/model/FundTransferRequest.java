package wallet.model;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class FundTransferRequest {
	
	@NotNull private long senderWalletNumber;
	
	@NotNull private long receiverWalletNumber;
	
	@NotNull private double amount;
}
