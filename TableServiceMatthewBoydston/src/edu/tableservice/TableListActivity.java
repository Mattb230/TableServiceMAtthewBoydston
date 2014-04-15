package edu.tableservice;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import edu.tableservice.data.Check;
import edu.udallas.tableservice.R;

public class TableListActivity extends Activity {

    private ListView mListView;
    
	private TableListAdapter mAdapter;
	
	private FetchChecks mDownloadTask;
	
	private ArrayList<Check> mCheckList = new ArrayList<Check>();


	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_list);
        //method to start the download
        startDownload();
        
        // Find the ListView, create an adapter that reads our list of checks,
        // and connect the two
        mListView = (ListView)findViewById(R.id.listView);
        mAdapter = new TableListAdapter(this, mCheckList);
        mListView.setAdapter(mAdapter);
        
        mListView.setOnItemClickListener(new OnItemClickListener() {
        	@Override
        	public void onItemClick(AdapterView<?> adapterView, View v, 
        	 int position, long id) {
        	TableListAdapter.Holder holder = (TableListAdapter.Holder)v.getTag();
        	//Toast.makeText(getApplicationContext(), "Refresh", Toast.LENGTH_SHORT).show();
        	//YourData theThing = holder.data;
        	// Now do something
        	Intent i = new  Intent(TableListActivity.this, PayCheckActivity.class);
        	double subTotal = holder.subTotal;
        	i.putExtra(PayCheckActivity.EXTRA_SUBTOTAL, subTotal);
        	startActivity(i);
        	}
        	});
    }
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	//downloads the data and stores in in mCheckList
	private void startDownload(){
		//http://tableservice.eu.pn/checks.json
		if(mDownloadTask == null){
			mDownloadTask = new FetchChecks(){
				@Override
				protected void onPostExecute(ArrayList<Check> result){
					super.onPostExecute(result);
					mCheckList.clear();
					mCheckList.addAll(result);
					mAdapter.notifyDataSetChanged();
					mDownloadTask = null;					
					findViewById(R.id.progress).setVisibility(View.GONE);

					
				}//end onPostExecute
			};
			findViewById(R.id.progress).setVisibility(View.VISIBLE);
	        mDownloadTask.execute("http://tableservice.eu.pn/checks.json");

		}
	}//end startDownload
  //////////////////////////////////////////////////////////////////////////////////////////////////  
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.table_list, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    		int id = item.getItemId();
    		
    		if (id == R.id.action_refresh) {
       			//Toast.makeText(this, "Refresh", Toast.LENGTH_SHORT).show();
    			startDownload();
    			return true;
    		}
    		return super.onOptionsItemSelected(item);
    }
   
    
}
