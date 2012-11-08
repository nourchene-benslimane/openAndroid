package com.openerp.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.openerp.android.R;
import com.openerp.model.ResPartnerAddress;

public class AddressAdapter extends BaseAdapter{
	List<ResPartnerAddress> catalogue;
	LayoutInflater inflater;
	public AddressAdapter(Context context, List<ResPartnerAddress> list){
		inflater = LayoutInflater.from(context);
		this.catalogue = list;
	}
	
	@Override
	public int getCount(){
		return catalogue.size();
	}
	
	@Override
	public Object getItem(int position){
		return catalogue.get(position);
	}
	
	public long getItemId(int position){
		return position;
	}
	
	
	private class ViewHolder {
		// declarer les TextView
		TextView _name;
		TextView _type;
		TextView _function;
		TextView _mail;
		TextView _phone;
		TextView _mobile;
		TextView _fax;
		TextView _address;
	}
	
	public View getView(int position, View  convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.child_address_layout, null);
			holder._name=(TextView)convertView.findViewById(R.id.setName);
			holder._type=(TextView)convertView.findViewById(R.id.setType);
			holder._function=(TextView)convertView.findViewById(R.id.setFunction);
			holder._mail=(TextView)convertView.findViewById(R.id.setMail);
			holder._phone=(TextView)convertView.findViewById(R.id.setPhone);
			holder._mobile=(TextView)convertView.findViewById(R.id.setMobile);
			holder._fax=(TextView)convertView.findViewById(R.id.setFax);
			holder._address=(TextView)convertView.findViewById(R.id.setAddress);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder._name.setText(catalogue.get(position).getName());
		holder._type.setText(catalogue.get(position).getType());
		holder._function.setText(catalogue.get(position).getFunction());
		holder._mail.setText(catalogue.get(position).getEmail());
		holder._phone.setText(catalogue.get(position).getPhone());
		holder._mobile.setText(catalogue.get(position).getMobile());
		holder._fax.setText(catalogue.get(position).getFax());
		
		if (catalogue.get(position).getStreet2()=="false")
			holder._address.setText(catalogue.get(position).getStreet()+"\n"+catalogue.get(position).getZip()+" "+catalogue.get(position).getCity());
		else holder._address.setText(catalogue.get(position).getStreet()+"\n"+catalogue.get(position).getStreet2()+"\n"+catalogue.get(position).getZip()+" "+catalogue.get(position).getCity());
		
		return convertView;
		
	}
}
