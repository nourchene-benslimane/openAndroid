package com.openerp.android;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

import com.openerp.model.Product;

public class ProductInfo extends Activity {
	private TextView _name;
	private TextView _code;
	private TextView _categ;
	private TextView _price;
	private TextView _qty;
	
	
	private int id;
	private Product product;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_info);
    	Log.i("class ProductInfo","onCreate debut");
        Bundle receivedProduct = this.getIntent().getExtras();
        Log.i("class ProductInfo","after getExtras");
        
        id = receivedProduct.getInt("id");
        Log.i("class ProductInfo","id received= "+id);
        
        
        _name = (TextView) findViewById(R.id.prodName);
        _code = (TextView) findViewById(R.id.prodCode);
        _categ = (TextView) findViewById(R.id.prodCateg);
        _price = (TextView) findViewById(R.id.prodPrice);
        _qty = (TextView) findViewById(R.id.prodQte);
        
        
        Log.i("ProductInfo","product creation");
        product= MenuProducts.db_manager.getProductWithId(id);
        Log.i("ProductInfo","product created, name ="+product.getName());
        _name.setText(product.getName());
        _code.setText(product.getCode());
        _categ.setText(product.getCateg_name());
        _price.setText(String.valueOf(product.getList_price()));
        _qty.setText(String.valueOf(product.getQty_available()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_product_info, menu);
        return true;
    }

    
}
