package com.openerp.android;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.j256.ormlite.dao.ForeignCollection;
import com.openerp.adapter.ProductsSpinnerAdapter;
import com.openerp.db.DatabaseManager;
import com.openerp.model.Product;
import com.openerp.model.ProductTaxes;
import com.openerp.model.ProductUom;
import com.openerp.model.SaleOrder;
import com.openerp.model.SaleOrderLine;

public class NewSaleOrderLine extends Activity {
	private Spinner _product;
	private Button _create;
	private EditText _qte;
	private EditText _discount;
	
	Context context;
	List<Product> products;
	ProductsSpinnerAdapter spinAdapter;
	//donn�es necessaires � la cr�ation d'une saleOrderLine
	String name;
	Product product;
	ProductUom productUom;
	double qte;
	double priceU;
	double discount;
	double priceSubT;
	static String createD;
	SaleOrder saleOrder;
	
	
	
	
	int saleOrderId; //re�u par bundle
	ProductTaxes taxe = null;
	double taxAmount=0; 

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_sale_order_line);
		context=this;

		//r�cup�rer l'id du saleOrdre pass� par l'intent
		Bundle receivedSaleOrderId = this.getIntent().getExtras();
        saleOrderId = receivedSaleOrderId.getInt("id");
		//cr�er le saleOrder
        saleOrder = DatabaseManager.getInstance().getSaleOrderWithId(saleOrderId);
        
		
		//set View
		_product=(Spinner)findViewById(R.id.spinnerProducts);
		_create = (Button) findViewById(R.id.line_create);
		_qte = (EditText)findViewById(R.id.editQte);
		_discount = (EditText)findViewById(R.id.editDiscount);

		
		
		
			//spinner
		products = DatabaseManager.getAllProduct();
		spinAdapter = new ProductsSpinnerAdapter(products, this);
		_product.setAdapter(spinAdapter);
		_product.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				product = (Product) _product.getItemAtPosition(position);
				
				Log.i("Partner Selected","name= "+product.getName()+" id= "+product.getId());

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});


		_create.setOnClickListener(new View.OnClickListener() {
			@SuppressWarnings("unchecked")
			public void onClick(View v) {
				SaleOrderLine line = new SaleOrderLine(); 
				getData();
				if (!checkStock(product, qte)){
					//afficher message d'erreur;
					
				}
				else {
			
				//remplir par donn�es
				addDataToSaleOrderLine(line);
				Log.i("","");
				//ecrire sur la base
				DatabaseManager.getInstance().addSaleOrderLine(line);
				//r�cup�rer l'id
				Log.i("id de la ligne cr�e","id= " + line.getId());
				
				List<SaleOrderLine> lines = saleOrder.getLines();
				lines.add(line);
				saleOrder.setLines((ForeignCollection<SaleOrderLine>) lines);
				DatabaseManager.getInstance().updateSaleOrder(saleOrder);
				
				
				NewSaleOrder.orderLines.add(line);
				Log.i("saleOrderLine","nb lines= "+NewSaleOrder.orderLines.size());
				
				Bundle line_id = new Bundle();
				line_id.putInt("id", line.getId());
				Intent i = new Intent (context,NewSaleOrder.class);
				i.putExtras(line_id);
				startActivity(i);
				finish();
				
				}

			}
		});


	}


	Boolean checkStock(Product selectedProduct, double qte){
		if (qte>selectedProduct.getQty_available()){
			new AlertDialog.Builder(context)
			.setMessage("Quantit� limit�e en stock")
			.setTitle("Erreur")
			.setCancelable(true)
			.setNeutralButton(android.R.string.cancel,
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton){}
			})
			.show();
			return false;
		}
				
				
		else 
			if(selectedProduct.getQty_available()==0) {
			new AlertDialog.Builder(context)
			.setMessage("Produit non disponible en stock")
			.setTitle("Erreur")
			.setCancelable(true)
			.setNeutralButton(android.R.string.cancel,
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton){}
			})
			.show();
			return false;
		}
			
		return true;
	}
	
	void getData(){
		//recuperation des donn�es
		name = product.getName();
//		saleOrder dans l'activity new SaleOrder
		
		productUom = product.getProductUom();
		qte = Double.parseDouble(_qte.getText().toString());
		//verifier si la qte command�e ne d�passe pas la quantit� disponible en stock
		priceU = product.getList_price();
		discount = Double.parseDouble(_discount.getText().toString());
		Time time = new Time ();
		time.setToNow();
		createD = time.toString();
		Log.i("time",createD);
		
		if (DatabaseManager.getInstance().hasTaxe(product.getId()))
			taxe=DatabaseManager.getProductTaxesWithProductId(product.getId());
	 
		
		//calculer
		if (taxe!= null) taxAmount = taxe.getTaxAmount();
		Log.i("taxe","val= " +taxAmount);
	
		double prixTotal = priceU*qte;
		//calculer le discount total
		double sommeDiscount;
		sommeDiscount = (prixTotal*discount)/100;
		//calculer la taxe totale
		double sommeTaxe;
		sommeTaxe = (prixTotal*taxAmount)/100;
		priceSubT = prixTotal+sommeTaxe - sommeDiscount; //prix final apr�s taxe et r�duction
		
		
	}
	void addDataToSaleOrderLine(SaleOrderLine line){
		
		line.setName(name);
		line.setSaleOrder(saleOrder); //la valeur sera affect�e � la cr�ation du saleOrder
		line.setProduct(product);
		line.setProductUom(productUom); 
		line.setProductUomQty(qte);
		line.setPriceUnit(priceU);
		line.setDiscount(discount);
		line.setPriceSubTotal(priceSubT);
		line.setCreateDate(createD);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_new_sale_order_line, menu);
		return true;
	}


}
