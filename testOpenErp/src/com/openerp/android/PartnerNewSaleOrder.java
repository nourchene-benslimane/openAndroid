package com.openerp.android;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.openerp.db.DatabaseManager;
import com.openerp.model.ResPartner;
import com.openerp.model.SaleOrder;

public class PartnerNewSaleOrder extends Activity {

	private TextView _date;
	private Button _selectDate;
	private int _order_day;
	private int _order_month;
	private int _order_year;
	private EditText _partner;
	int partnerId;
	Button newLine;
	Context context;
	ResPartner partner;
	SaleOrder order;
	static final int DATE_DIALOG_ID = 1;
	private DatePickerDialog.OnDateSetListener pDateSetListener =
			new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, 
				int monthOfYear, int dayOfMonth) {
			_order_year = year;
			_order_month = monthOfYear;
			_order_day = dayOfMonth;
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
        setContentView(R.layout.activity_partner_new_sale_order);
        context = this;
        
        Bundle receivedOrder = this.getIntent().getExtras();
        partnerId = receivedOrder.getInt("id");
        
        _selectDate = (Button)findViewById(R.id.p_order_button_date);
        newLine = (Button)findViewById(R.id.p_order_addLine);
        _partner = (EditText)findViewById(R.id.p_editPartner);
        _date = (TextView)findViewById(R.id.p_order_date); 
        
        //setPartner
        partner = DatabaseManager.getInstance().getResPartnerWithId(partnerId);
        _partner.setText(partner.getName());
        
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
        
        
    		newLine.setOnClickListener(new View.OnClickListener() {
    			public void onClick(View v) {
    				//Bundle _saleOrder = new Bundle();
    		    	//_saleOrder.putInt("id", order.getId());
    				Intent i = new Intent(context,NewSaleOrderLine.class);
    				context.startActivity(i);

    			}
    		});

        
        
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_partner_new_sale_order, menu);
        return true;
    }

    
}
