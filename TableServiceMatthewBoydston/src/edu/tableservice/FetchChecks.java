package edu.tableservice;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.tableservice.data.Check;

import android.os.AsyncTask;

public class FetchChecks extends AsyncTask<String, Void, ArrayList<Check>> {

	//private static final String TAG_RESTAURANT = "restaurant";
	//private static final String TAG_RESTAURANTID = "restaurantId";
	//private static final String TAG_SERVERID = "serverId";
	private static final String TAG_CHECKS = "checks";
	private static final String TAG_TABLEID = "tableId";
	private static final String TAG_TABLENAME = "tableName";
	private static final String TAG_ITEMS = "items";
	private static final String TAG_DESC = "desc";
	private static final String TAG_PRICEUSD = "priceUSD";
	
	JSONArray checks = null;
	JSONArray items = null;
	Check check = null;
	
	
	@Override
	protected ArrayList<Check> doInBackground(String... params) {
		ArrayList<Check> list = null;
		
		//Download the Data
		String data = downloadData(params[0]);
		
		//parse the data 
		list = parseCheckList(data);

		return list;
	}

/////////////////////////////////////////////////////////////////////////////////////////////////
//method to create HttpConnection, download it, and store it in a string
	private String downloadData(String urlString) {
		try {
			URL url = new URL(urlString);
			
			
			//Create a HttpConnection to perform a get
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoInput(true);
			connection.setConnectTimeout(10000);
			
			//connect
			connection.connect();
			
			// Grab the input stream to read the file
			InputStream responseInStream = connection.getInputStream();
            int responseCode = connection.getResponseCode();			
			
            if(responseCode == 200){
            	// Read the stream into a string using a Reader + StirngBuilder
            	BufferedInputStream bis = new BufferedInputStream(responseInStream);
            	
            	BufferedReader r = new BufferedReader(new InputStreamReader(bis));
            	StringBuilder builder = new StringBuilder();
            	String line;
            	while ((line = r.readLine()) != null) {
            		builder.append(line);
            		//builder.append('\n');
            	}
            	
            	return builder.toString();            	
            }//end if
			
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//end catch
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//end catch
		
		//exception or problem if execution made it here. return null
		return null;
	}//end downloadData
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private ArrayList<Check> parseCheckList(String data) {
		ArrayList<Check> mlist = new ArrayList<Check>();

		try {
			//parent JSOn object
			JSONObject jsonObject = new JSONObject(data);
			
			//string to hold the data fields of the parent JSON object
			//String restaurant = jsonObject.getString(TAG_RESTAURANT);
			//String restaurantId = jsonObject.getString(TAG_RESTAURANTID);			
			//String serverId = jsonObject.getString(TAG_SERVERID);
			
			//outer loop gets checks data (table id and table name)
			checks = jsonObject.getJSONArray(TAG_CHECKS);
			for(int i = 0; i < checks.length(); i++){
				JSONObject c = checks.getJSONObject(i);
				
				String tableId = c.getString(TAG_TABLEID);
				String tableName = c.getString(TAG_TABLENAME);
				
				//create new check to hold data
				check = new Check(Long.parseLong(tableId), tableName);
				
				//get items data (menu choice and price)
				items = c.getJSONArray(TAG_ITEMS);
				for(int j = 0; j < items.length(); j++){
					JSONObject it = items.getJSONObject(j);
					
					String desc = it.getString(TAG_DESC);
					String priceUSD = it.getString(TAG_PRICEUSD);
					//add items to the check
					check.addItem(desc, Double.parseDouble(priceUSD) );

				}//end for
				
				//add check to the list
				mlist.add(check);
				
			}//end for 
			return mlist;
		} 
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}//end parseCheckList
///////////////////////////////////////////////////////////////////////////////////////////	
}
