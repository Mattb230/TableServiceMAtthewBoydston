package edu.tableservice.data;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Amount is a wrapper class to deal with currency.  Like Strings, amounts are immutable, 
 * once their value is set, it cannot be changed
 * 
 * If you need to do an operation, such as add, you will get a new Amount object
 * 
 * @author josh.ehlke
 *
 */
public class Amount {

	private static NumberFormat sDollarFormatter = NumberFormat.getCurrencyInstance(new Locale("en_US"));
	
	private double usDollarValue;
	
	public Amount(double usDollarValue) {
		this.usDollarValue = usDollarValue;
	}
	
	public String getDollarDisplayString() {
		return sDollarFormatter.format(usDollarValue);
	}
	
	public double getRawValue() {
		return usDollarValue;
	}
	
	public static Amount getPercent(Amount amount, double percent) {
	    // Calculate the closest value, rounding to 2 digits
	    double value = amount.usDollarValue * percent;
	    value *= 100;
	    value = Math.round(value);
	    value /= 100;
		return new Amount(value);
	}
	
	public Amount add(Amount other) {
	    return new Amount(this.usDollarValue + other.usDollarValue);
	}
	
	public static Amount add(Amount one, Amount two) {
		return new Amount(one.usDollarValue + two.usDollarValue);
	}
}
