package com.openerp.adapter;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.openerp.android.R;
import com.openerp.model.Product;

public class ProductsSpinnerAdapter extends BaseAdapter implements SpinnerAdapter  {
	private final List<Product> content;
	private final Activity activity;
	
	public ProductsSpinnerAdapter(List<Product> content,
			Activity activity) {
			super();
			this.content = content;
			this.activity = activity;
			}
	
	public int getCount() {
		return content.size();
		}
	public Product getItem(int position) {
		return content.get(position);
		}
	public long getItemId(int position) {
		return position;
		}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		final LayoutInflater inflater = activity.getLayoutInflater();
		final View spinnerEntry = inflater.inflate(R.layout.product_spinner_item, null);
		final TextView productName = (TextView) spinnerEntry
				.findViewById(R.id.spinnerEntryProductName);
			
				final Product currentEntry = content.get(position);
				productName.setText("["+currentEntry.getQty_available()+"]"+currentEntry.getName());
				return spinnerEntry;

	}
	
	
	
}
