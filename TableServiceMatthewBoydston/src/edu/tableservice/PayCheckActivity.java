package edu.tableservice;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import edu.tableservice.data.Amount;
import edu.udallas.tableservice.R;


public class PayCheckActivity extends Activity {
	public static final String EXTRA_SUBTOTAL= "edu.tableservice.PayCheckActivity.doublesubtotal";
	public static final String EXTRA_MESSAGE= "edu.tableservice.PayCheckActivity.doublesubtotal";
	String toast;

	
	private TextView mTotalTxtView;	
	private TextView mSubtotalTxtView;
	private double mSubtotal;
	private EditText mEditTxt;
	private Button mFifteen;
	private Button mEighteen;
	private Button mTwenty;
	private Button mOther;
	private Button mOk;
	private Button mSign;
	private LinearLayout mOtherTipLayout;
	private double mTip;
	private Amount mTotalAmount;
	private Amount mSubAmount;
	private Amount mTipAmount;
	private int mIntTipAmount;

	private BroadcastReceiver receiver = new BroadcastReceiver(){
		
		@Override
		public void onReceive(Context context, Intent intent){
			//Bundle bundle = intent.getExtras();
			
			//toast = bundle.getString(PaymentTransactionService.EXTRA_MESSAGE);
			toast = intent.getStringExtra(PaymentTransactionService.EXTRA_MESSAGE);
        	Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_SHORT).show();

		}
	};		
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pay_check);
		this.registerReceiver(receiver, new IntentFilter(PaymentTransactionService.ACTION_TRANSACTION_COMPLETE));
		
		mSubtotal = getIntent().getDoubleExtra(EXTRA_SUBTOTAL, 99.99);
		mSubAmount = new Amount(mSubtotal);
		mSubtotalTxtView = (TextView)findViewById(R.id.subtotalString);
		//mSubtotalTxtView.setText("$" + String.valueOf(mSubtotal));
		mSubtotalTxtView.setText(mSubAmount.getDollarDisplayString());
		
		mTotalTxtView = (TextView)findViewById(R.id.totalString);
		mTotalTxtView.setText(mSubAmount.getDollarDisplayString());
		
		mEditTxt = (EditText)findViewById(R.id.edit_text);

		mOtherTipLayout = (LinearLayout)findViewById(R.id.otherTip_layout);
		
		//Buttons and their listeners
		mFifteen = (Button)findViewById(R.id.fifteen_Button);
		mFifteen.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
				mTipAmount = Amount.getPercent(mSubAmount, .15);
				mTotalAmount = Amount.add(mSubAmount, mTipAmount);				
				mTotalTxtView.setText(mTotalAmount.getDollarDisplayString());
				mIntTipAmount = 15;
		
			}
		});		
		mEighteen = (Button)findViewById(R.id.eighteen_Button);
		mEighteen.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				mTipAmount = Amount.getPercent(mSubAmount, .18);
				mTotalAmount = Amount.add(mSubAmount, mTipAmount);				
				mTotalTxtView.setText(mTotalAmount.getDollarDisplayString());
				mIntTipAmount = 18;
				
			}
		});
		mTwenty = (Button)findViewById(R.id.twenty_Button);
		mTwenty.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				mTipAmount = Amount.getPercent(mSubAmount, .20);
				mTotalAmount = Amount.add(mSubAmount, mTipAmount);				
				mTotalTxtView.setText(mTotalAmount.getDollarDisplayString());
				mIntTipAmount = 20;
				
			}
		});
		mOther = (Button)findViewById(R.id.other_Button);
		mOther.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				mOtherTipLayout.setVisibility(View.VISIBLE);

			}
		});
		mOk = (Button)findViewById(R.id.ok_button);
		mOk.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//checks to see if edittext is empty. if it is subtotal is displayed and the edittext and ok button dissappear again 
				if(mEditTxt.getText().toString().equals("")){
					mOtherTipLayout.setVisibility(View.GONE);
					mTotalTxtView.setText(mSubAmount.getDollarDisplayString());					
					return;
				}
				String temp = mEditTxt.getText().toString();
				mTip = 	Double.valueOf(temp);
				mTipAmount = new Amount(mTip);
				//mTip = double value of tip amount
				//mSubAmount.getRawValue(); = subtotal amount
				mIntTipAmount = (int) ((mTip / mSubAmount.getRawValue()) * 100);
				
				mTotalAmount = Amount.add(mSubAmount, mTipAmount);
				mTotalTxtView.setText(mTotalAmount.getDollarDisplayString());				
			}
		});
		mSign = (Button)findViewById(R.id.sign_button);
		mSign.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(PayCheckActivity.this, SignatureActivity.class);
				startActivity(i);
				
				//Intent i = new Intent(PayCheckActivity.this, PaymentTransactionService.class);
				//i.putExtra(PaymentTransactionService.EXTRA_TIP_AMOUNT_INTEGER, mIntTipAmount);
				//startService(i);
				
	        	//Toast.makeText(getApplicationContext(), R.string.sign_toast, Toast.LENGTH_SHORT).show();
				
	        	//Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_SHORT).show();
	        	
				//unregisterReceiver(receiver);
				finish();
			}
		});		
		

	}//end onCreate
	

	

}//end class
