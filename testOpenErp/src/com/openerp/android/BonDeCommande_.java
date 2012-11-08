package com.openerp.android;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.openerp.db.DatabaseManager;
import com.openerp.model.SaleOrder;
import com.openerp.model.SaleOrderLine;

public class BonDeCommande_ extends Activity {

	TextView _name;
	TextView _state;
	TextView _dateOrder;
	TextView _amountTaxe;
	TextView _resPartner;
		//waiting for a table declaration here
	

	private int id;
	private SaleOrder order;
	private List<SaleOrderLine> lines;
	Context context;
	TableLayout table;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bon_de_commande_client);
        
        Bundle receivedOrder = this.getIntent().getExtras();
        id = receivedOrder.getInt("id");
        order = DatabaseManager.getInstance().getSaleOrderWithId(id);
        lines= order.getLines();
        context = this;
        
        _name = (TextView)findViewById(R.id.bon_name);
        _state = (TextView)findViewById(R.id.bon_state);
        _dateOrder = (TextView)findViewById(R.id.bon_date);
        _amountTaxe = (TextView)findViewById(R.id.bon_taxe);
        _resPartner = (TextView)findViewById(R.id.bon_partner);
        
        _name.setText(order.getName());
        _state.setText(order.getState());
        _dateOrder.setText(order.getDateOrder().toString());
        _amountTaxe.setText(order.getAmounTax().toString());
        _resPartner.setText(order.getResPartner().getName());
        
        
        
        table = (TableLayout) findViewById(R.id.table_ordre);
        //create the table
        //table = new TableLayout(this);  
        table.setStretchAllColumns(true); 
        table.setShrinkAllColumns(true);
       
        //title row
        TableRow rowTitle = new TableRow(this);
        rowTitle.setGravity(Gravity.CENTER_HORIZONTAL);
	        //produit
	        TextView produit = new TextView(this);
	        produit.setText("produit");
	        rowTitle.addView(produit);
	        //qte
	        TextView qte = new TextView(this);
	        qte.setText("Qte");
	        rowTitle.addView(qte);
	        //uom
	        TextView uom = new TextView(this);
	        uom.setText("Unité");
	        rowTitle.addView(uom);
	        //discount
	        TextView discount = new TextView(this);
	        discount.setText("discount");
	        rowTitle.addView(discount);
	        //priceU
	        TextView priceU = new TextView(this);
	        priceU.setText("Prix U");
	        rowTitle.addView(priceU);
	        //subTotal
	        TextView subTotal = new TextView(this);
	        subTotal.setText("Total");
	        rowTitle.addView(subTotal);
      table.addView(rowTitle);
	        
        //le reste des lignes
        for (SaleOrderLine l:lines){
        	Log.i("lines", "line id="+l.getId());
        	//appel a la fonction qui va extraire chaque ligne et la traiter
        	treatment(l);
        }
        
        
        
        //appel a la fonction qui va extraire chaque ligne et la traiter
        
        
        
        
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_bon_de_commande_client, menu);
        return true;
    }

    public void treatment (SaleOrderLine l)
    {
    	Log.i("treatment fct","***in***");
    	TableRow r = new TableRow(context);
    	Log.i("treatment fct","new row");
    	 //produit
        TextView produit = new TextView(this);
        produit.setText(l.getProduct().getName());
        r.addView(produit);
        Log.i("treatment fct","row.produit ok");
        //qte
        TextView qte = new TextView(this);
        qte.setText(l.getProductUomQty().toString());
        r.addView(qte);
        Log.i("treatment fct","row.qte ok");
        //uom
        TextView uom = new TextView(this);
        uom.setText(l.getProductUom().getName());
        r.addView(uom);
        Log.i("treatment fct","row.uom ok");
        //discount
        TextView discount = new TextView(this);
        discount.setText(l.getDiscount().toString());
        r.addView(discount);
        Log.i("treatment fct","row.discount ok");
        //priceU
        TextView priceU = new TextView(this);
        priceU.setText(l.getPriceUnit().toString());
        r.addView(priceU);
        Log.i("treatment fct","row.priceU ok");
        //subTotal
        TextView subTotal = new TextView(this);
        subTotal.setText(l.getPriceSubTotal().toString());
        r.addView(subTotal);
        Log.i("treatment fct","row.subT ok");
        table.addView(r);
        Log.i("treatment fct","table.addRow ok");
        Log.i("treatment fct","***out***");
    }
}
