package edu.tableservice;

import android.app.IntentService;
import android.content.Intent;

public class PaymentTransactionService extends IntentService {

	public static final String EXTRA_TIP_AMOUNT_INTEGER = "tip_amount";
	public static final String EXTRA_MESSAGE = "message";
	public static final String ACTION_TRANSACTION_COMPLETE = "edu.udallas.tableservice.PaymentTransationService.complete";

	public PaymentTransactionService() {
		super("pts");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		int tipAmount = intent.getIntExtra(EXTRA_TIP_AMOUNT_INTEGER, 0);
		
		int lengthOfWait = 10;
		if (tipAmount > 10) {
			lengthOfWait = 5;
		}
		if (tipAmount >= 20) {
			lengthOfWait = 1;
		}
		
		for (int i = 0; i < lengthOfWait; ++i) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		Intent resultIntent = new Intent(ACTION_TRANSACTION_COMPLETE);
		String message = tipAmount < 10 ? "What a cheapskate" : "Thanks!";
		resultIntent.putExtra(EXTRA_MESSAGE, message);
		
		sendBroadcast(resultIntent);
	}
	


}
