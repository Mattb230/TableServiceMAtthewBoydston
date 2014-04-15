package edu.tableservice.data;

import java.util.ArrayList;
import android.util.Log;

/**
 * A simplistic place to store data in our sample app
 * 
 * In the real world, you would want to store your data in a better
 * place, like a ContentProvider
 */
public class DataStore {

	private static final String TAG = "DataStore";

	/**
	 * The list of all checks
	 */
	public static ArrayList<Check> CHECKS = new ArrayList<Check>();

	public static void addItem(Check check) {
		CHECKS.add(check);
	}
	
	public static void clear() {
		Log.d(TAG, "DataStore CLEAR");
		CHECKS.clear();
	}

}
