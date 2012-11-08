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
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

import com.openerp.adapter.ProductAdapter;
import com.openerp.db.DatabaseManager;
import com.openerp.model.Product;

public class MenuProducts extends Activity implements SearchView.OnQueryTextListener  {
	ProgressDialog progressGetProducts;
	private static Context context;
	public static DatabaseManager db_manager;
	
	static List<Product> listeProducts = null;
	private static ListView productsListView ;  
	static SearchView searchProduct;
	static ProductAdapter productListAdapter;
	static Handler handlerGetPartners = new Handler(){
    	public void handleMessage(Message msg){
    		//code du handler
    		//préparer la listView
    		productListAdapter = new ProductAdapter(context, listeProducts);
            
    		productsListView.setAdapter(productListAdapter);
            
    		//productListAdapter.notifyDataSetChanged();
    		productsListView.setTextFilterEnabled(true);
    		setupSearchView();
    		productsListView.setOnItemClickListener(new OnItemClickListener() {
   			 @Override
   		        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

   		    	Product product = (Product) productsListView.getItemAtPosition(position);
   		    	Log.i("class MenuProduct","getItemAtPosition");
   		    	Bundle _product = new Bundle();
   		    	Log.i("class MenuProduct","creating bundle");
   		    	_product.putInt("id", product.getId());
   		    	
   		    	
   		    	Log.i("class MenuProduct","id = "+product.getId());

   		    	Intent i = new Intent(context, ProductInfo.class);
   		    	i.putExtras(_product);
   		    	Log.i("class MenuProduct","putExtra done");
		    	context.startActivity(i);
		 }
	});


}
};    	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_products);
        
        
        productsListView=(ListView) findViewById(R.id.listViewProducts);
        searchProduct = (SearchView)findViewById(R.id.searchViewProduct);
             
        context= this;

        progressGetProducts = new ProgressDialog(context);
        progressGetProducts.setMessage("préparation de la liste des produits en cours..");
        progressGetProducts.show();
        progressGetProducts.setCancelable(false);
    	new Thread(new Runnable() {
    		
    		@Override
     	   
			public void run() {
				//background thread process
				
				//operation d'extraction de la liste des produits
				Log.i("getting products list","run start");
				//fonction d'appel
				db_manager = DatabaseManager.getInstance();
				Log.i("getting Products list","dbm get instance done");

				listeProducts = DatabaseManager.getAllProduct();
				Log.i("getting products list","listeProducts after dbm function");
				
				handlerGetPartners.sendEmptyMessage(0);
				progressGetProducts.dismiss();
				Log.i("getting Partners list","run out");
				
			}
		}).start();
        
        
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu_products, menu);
        return true;
    }

    private static void setupSearchView() {
    	searchProduct.setIconifiedByDefault(false);
    	searchProduct.setOnQueryTextListener((OnQueryTextListener) context);
    	searchProduct.setSubmitButtonEnabled(false);
    	searchProduct.setQueryHint(context.getString(R.string.hint));
    }
    public boolean onQueryTextChange(String newText) {
    	productListAdapter.getFilter().filter(newText.toString());
    	//productsListView.setFilterText(newText.toString());
    	
       /* if (TextUtils.isEmpty(newText)) {
        	productsListView.clearTextFilter();
        } else {
        	productsListView.setFilterText(newText.toString());
        
        }*/
        return true;
    }
    public boolean onQueryTextSubmit(String query) {
        return false;
    }
}
