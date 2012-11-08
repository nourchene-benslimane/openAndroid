package com.openerp.android;

import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.openerp.adapter.PartnersSpinnerAdapter;
import com.openerp.db.DatabaseManager;
import com.openerp.model.ResPartner;
import com.openerp.model.SaleOrder;
import com.openerp.model.SaleOrderLine;

public class NewSaleOrder extends Activity {

	private TextView _date;
	private Button _selectDate;
	private Spinner _partner;
	private EditText _ref;
	private int _order_day;
	private int _order_month;
	private int _order_year;
	
	Button newLine;
	Button createOrder;
	Button deleteOrder;
	
	Date order_date;
	
	
	Context context;
	ResPartner partner;
	public static List<SaleOrderLine> orderLines; //populated when button CREATE is clicked
	SaleOrder order;

	List<ResPartner> partnerList;//inutile pour l'instant
	PartnersSpinnerAdapter spinAdapter;
	//select the order date
	static final int DATE_DIALOG_ID = 1;

	private DatePickerDialog.OnDateSetListener pDateSetListener =
			new DatePickerDialog.OnDateSetListener() {

		@SuppressWarnings("deprecation")
		public void onDateSet(DatePicker view, int year, 
				int monthOfYear, int dayOfMonth) {
			_order_year = year;
			_order_month = monthOfYear;
			_order_day = dayOfMonth;
			order_date = new Date(_order_year,_order_month,_order_day);
			updateDisplay();
		}
	};

	private void updateDisplay() {
		_date.setText(
				new StringBuilder()
				.append(_order_day).append("/")
				// Month is 0 based so add 1
				.append(_order_month + 1).append("/")
				.append(_order_year).append(" "));
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, 
					pDateSetListener,
					_order_year, _order_month, _order_day);
		}
		return null;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_sale_order);
		context = this;
		//set view
		_date = (TextView)findViewById(R.id.order_date); 
		_selectDate = (Button)findViewById(R.id.order_button_date);
		_partner = (Spinner)findViewById(R.id.order_edit_partner);
		_ref = (EditText) findViewById(R.id.order_edit_ref);

		
		newLine = (Button)findViewById(R.id.order_addLine);
		createOrder = (Button)findViewById(R.id.order_createOrder);
		deleteOrder = (Button)findViewById(R.id.order_delete);

		//set_date
		_selectDate.setOnClickListener(new View.OnClickListener() {
			@SuppressWarnings("deprecation")
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);
			}
		});

		final Calendar cal = Calendar.getInstance();
		_order_year = cal.get(Calendar.YEAR);
		_order_month = cal.get(Calendar.MONTH);
		_order_day = cal.get(Calendar.DAY_OF_MONTH);
		
		updateDisplay();

		//set_partner
		partnerList = DatabaseManager.getInstance().getAllResPartner();
		orderLines = new ArrayList<SaleOrderLine>(); //inutile pour l'instant
		
		
		spinAdapter = new PartnersSpinnerAdapter(partnerList, this);
		_partner.setAdapter(spinAdapter);

		_partner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				partner = (ResPartner) _partner.getItemAtPosition(position);
				Log.i("Partner Selected","name= "+partner.getName()+" id= "+partner.getId());
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});


		//create Order
		createOrder.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				order = new SaleOrder();
				order.setResPartner(partner);
				//vérifier données
				if (checkData()){
				order.setName(_ref.getText().toString());
				order.setDateOrder(order_date);
				DatabaseManager.getInstance().addSaleOrder(order);
				Log.i("add saleOrder","id= "+order.getId());
				//rendre les boutons visibles une fois le bon de commande créé
				deleteOrder.setVisibility(0);
				newLine.setVisibility(0);
				
				}
				else {
					new AlertDialog.Builder(context)
					.setMessage("Vérifier les données")
					.setTitle("Erreur")
					.setCancelable(true)
					.setNeutralButton(android.R.string.ok,
							new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton){}
					})
					.show();
				}
			}
		});
		
		
		
		
		//delete Order
		deleteOrder.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DatabaseManager.getInstance().deleteSaleOrder(order);
				Intent i = new Intent(context,HomeActivity.class);
				context.startActivity(i);
				finish();
			}
		});
		
		
		
		//new Line
		newLine.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
				Bundle _saleOrder = new Bundle();
		    	_saleOrder.putInt("id", order.getId());
				Intent i = new Intent(context,NewSaleOrderLine.class);
				i.putExtras(_saleOrder);
				context.startActivity(i);

			}
		});

	}


	public boolean checkData(){
		if ((_ref.getText().toString().isEmpty())|(_date.getText().toString().isEmpty())){
			return false;
		}
		return true;
		
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_new_sale_order, menu);
		return true;
	}


}
