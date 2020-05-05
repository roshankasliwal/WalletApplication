package wallet.model;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@Setter
public class FundTransferRequest {
	
	@NotNull private long senderWalletNumber;
	
	@NotNull private long receiverWalletNumber;
	
	@NotNull private double amount;
}
