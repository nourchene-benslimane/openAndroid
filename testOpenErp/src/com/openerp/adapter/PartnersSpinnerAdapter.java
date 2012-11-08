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
import com.openerp.model.ResPartner;

public class PartnersSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {
	private final List<ResPartner> content;
	private final Activity activity;
	
	public PartnersSpinnerAdapter(List<ResPartner> content,
			Activity activity) {
			super();
			this.content = content;
			this.activity = activity;
			}
	public int getCount() {
		return content.size();
		}

		public ResPartner getItem(int position) {
		return content.get(position);
		}

		public long getItemId(int position) {
		return position;
		}
	
	
		public View getView(int position, View convertView, ViewGroup parent) {
			final LayoutInflater inflater = activity.getLayoutInflater();
			final View spinnerEntry = inflater.inflate(R.layout.partner_spinner_item, null);
			final TextView contactName = (TextView) spinnerEntry
					.findViewById(R.id.spinnerEntryPartnerName);
				
					final ResPartner currentEntry = content.get(position);
					contactName.setText(currentEntry.getName());
					return spinnerEntry;
	
		}
	

}
