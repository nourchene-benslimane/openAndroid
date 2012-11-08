package com.openerp.android;

import java.util.List;

import com.openerp.adapter.SaleOrderAdapter;
import com.openerp.db.DatabaseManager;
import com.openerp.model.SaleOrder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SearchView.OnQueryTextListener;

public class PartnerSaleOrderList extends Activity implements SearchView.OnQueryTextListener{
	ProgressDialog progressGetSaleOrder;
	DatabaseManager db_manager;
	private static Context context;
	static SearchView searchBar ;
	public static List<SaleOrder> listeSaleOrders = null;
	private static ListView saleOrderListView ;  
	static SaleOrderAdapter saleOrderListAdapter ;
	
	int id;
	
	static Handler handlerGetSaleOrders = new Handler(){
		public void handleMessage(Message msg){
			saleOrderListAdapter = new SaleOrderAdapter(context, listeSaleOrders);
			saleOrderListView.setAdapter(saleOrderListAdapter);
			saleOrderListView.setTextFilterEnabled(true);
			setupSearchView();
			saleOrderListView.setOnItemClickListener(new OnItemClickListener() {
   			 @Override
		        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				 SaleOrder order = (SaleOrder) saleOrderListView.getItemAtPosition(position);
			    	Log.i("class MenuOrder","getItemAtPosition");
			    	Bundle _saleOrder = new Bundle();
			    	Log.i("class MenuOrder","creating bundle");
			    	_saleOrder.putInt("id", order.getId());
			    	
			    	
			    	Log.i("class MenuOrder","id = "+order.getId());

			    	Intent i = new Intent(context, BonDeCommande_.class);
			    	i.putExtras(_saleOrder);
			    	Log.i("class MenuOrder","putExtra done");
			    	context.startActivity(i);
			 }
		});
			
		}
	};
	
	private static void setupSearchView() {
    	Log.d("setupSearchView","*****setupSearchView*****");
    	searchBar.setIconifiedByDefault(false);
    	searchBar.setOnQueryTextListener((OnQueryTextListener) context);
    	searchBar.setSubmitButtonEnabled(false);
    	searchBar.setQueryHint(context.getString(R.string.hint));
    }
    public boolean onQueryTextChange(String newText) {
    	saleOrderListAdapter.getFilter().filter(newText.toString());

        return true;
    }
    public boolean onQueryTextSubmit(String query) {
        return false;
    }
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner_sale_order_list);
        
        saleOrderListView = (ListView)findViewById(R.id.p_listSaleOrder);
        searchBar = (SearchView)findViewById(R.id.p_searchSaleOrder);
        
        Bundle receivedOrder = this.getIntent().getExtras();
        id = receivedOrder.getInt("id");
        
        
        context= this;
        progressGetSaleOrder = new ProgressDialog(context);
        progressGetSaleOrder.setMessage("préparation de la liste des bons de commande en cours..");
        progressGetSaleOrder.show();
        progressGetSaleOrder.setCancelable(false);
        
        new Thread(new Runnable() {
        	@Override
      	   
			public void run() {
				//background thread process
				
				db_manager = DatabaseManager.getInstance();
				Log.i("getting saleOrders","getting saleOrders list");

				listeSaleOrders = db_manager.getSaleOrdersWithPartnerId(id); //listeSaleOrders=db_manager.getSaleOrdersWithPartnerId(id)
				Log.i("getting saleOrders","after getAllSaleOrder");
				
				handlerGetSaleOrders.sendEmptyMessage(0);
				progressGetSaleOrder.dismiss();
				Log.i("getting saleOrders","run out");
				
			}
        }).start();
        
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_partner_sale_order_list, menu);
        return true;
    }

    
}
