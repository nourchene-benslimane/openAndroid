package com.openerp.android;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

import com.openerp.adapter.PartnerAdapter;
import com.openerp.db.DatabaseManager;
import com.openerp.model.ResPartner;

public class MenuPartners extends Activity implements SearchView.OnQueryTextListener {
	ProgressDialog progressGetPartners;
	private static Context context;
	public static DatabaseManager db_manager;
	
	static SearchView searchPartner;
	static List<ResPartner> listePartners = null;
	private static ListView partnersListView ;  
	static PartnerAdapter partnerListAdapter ;
	
	 static Handler handlerGetPartners = new Handler(){
	    	public void handleMessage(Message msg){
	    		partnerListAdapter = new PartnerAdapter(context, listePartners);
	    		partnersListView.setAdapter(partnerListAdapter);
	    		partnersListView.setTextFilterEnabled(true);
	    		setupSearchView();
	    		partnersListView.setOnItemClickListener(new OnItemClickListener() {
	    			 @Override
	    		        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	    				 ResPartner partner = (ResPartner) partnersListView.getItemAtPosition(position);
	    			    	Log.i("class MenuPartner","getItemAtPosition");
	    			    	Bundle _partner = new Bundle();
	    			    	Log.i("class MenuPartner","creating bundle");
	    			    	_partner.putInt("id", partner.getId());
	    			    	
	    			    	
	    			    	Log.i("class MenuPartner","id = "+partner.getId());

	    			    	Intent i = new Intent(context, PartnerInfo.class);
	    			    	i.putExtras(_partner);
	    			    	Log.i("class MenuPartner","putExtra done");
	    			    	context.startActivity(i);
	    			 }
	    		});
	    	
	    	}
	    };    	
	
    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_client);
        
        partnersListView = (ListView) findViewById(R.id.listViewPartners);
        
        context= this;
        searchPartner=(SearchView)findViewById(R.id.searchPartner);

        
        progressGetPartners = new ProgressDialog(context);
        progressGetPartners.setMessage("préparation de la liste des clients en cours..");
        progressGetPartners.show();
        progressGetPartners.setCancelable(false);

        
    	Log.i("onCreate","after search view ");
    	new Thread(new Runnable() {
			@Override
    	   
			public void run() {
				//background thread process
				
				//operation d'extraction de la liste des partners
				Log.i("getting Partners list","run start");
				//fonction d'appel
				db_manager = DatabaseManager.getInstance();
				Log.i("getting Partners list","dbm get instance done");

				listePartners = DatabaseManager.getInstance().getAllResPartner();
				Log.i("getting Partners list","listePartners after dbm function");
				Log.i("PARTNERLIST SIZE ","valeur à la creation= "+listePartners.size());
				
				handlerGetPartners.sendEmptyMessage(0);
				progressGetPartners.dismiss();
				Log.i("getting Partners list","run out");
				
				
			}
		}).start();
        
        
	}
    
    
		private static void setupSearchView() {
	    	Log.d("setupSearchView","*****setupSearchView*****");
	    	searchPartner.setIconifiedByDefault(false);
	    	searchPartner.setOnQueryTextListener((OnQueryTextListener) context);
	    	searchPartner.setSubmitButtonEnabled(false);
	    	searchPartner.setQueryHint(context.getString(R.string.hint));
	    }
	    public boolean onQueryTextChange(String newText) {
	    	partnerListAdapter.getFilter().filter(newText.toString());

	        return true;
	    }
	    public boolean onQueryTextSubmit(String query) {
	        return false;
	    }
     
}
