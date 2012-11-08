package com.openerp.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.openerp.android.R;
import com.openerp.model.SaleOrder;

public class SaleOrderAdapter extends BaseAdapter implements Filterable{

	List<SaleOrder> annuaire;
	LayoutInflater inflater;
	private Filter saleOrderFilter;
	List<SaleOrder> f_items; //new
	List<SaleOrder> ref_items; //new
	
	public SaleOrderAdapter (Context context, List<SaleOrder> list){
		inflater = LayoutInflater.from(context);
		this.annuaire = list;
		this.f_items = new ArrayList<SaleOrder>(list); //new
		this.ref_items = new ArrayList<SaleOrder>(list); //new
	}
	
	@Override
	public int getCount(){
		return annuaire.size();
	}
	
	@Override
	public Object getItem(int position){
		return annuaire.get(position);
	}
	
	@Override
	public long getItemId(int position){
		return position;
	}
	
	
	private class ViewHolder {
		TextView order_partner;
		TextView order_date;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public View getView(int position, View  convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_bon_de_commande, null);
			holder.order_partner=(TextView)convertView.findViewById(R.id.bon_partner_name);
			holder.order_date=(TextView)convertView.findViewById(R.id.bon_date);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.order_partner.setText(annuaire.get(position).getResPartner().getName());
		holder.order_date.setText(annuaire.get(position).getDateOrder().toGMTString());
		
		return convertView;
		
	}
	
	public void add(SaleOrder item){
        annuaire.add(item);
    }
	
	@Override
	public Filter getFilter() {
		if (saleOrderFilter == null)
			saleOrderFilter = new SaleOrderFilter();

		return saleOrderFilter;
	}
	
	private class SaleOrderFilter extends Filter {
		
			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				FilterResults results = new FilterResults();
				
				String prefix = constraint.toString().toLowerCase();
				if (prefix == null || prefix.length() == 0) {
					//new 
					Log.i("Adapter Filter"," pas de valeur à rechercher");
					 ArrayList<SaleOrder> arrayList = new ArrayList<SaleOrder>(ref_items);
	                 results.values = arrayList;
	                 results.count = arrayList.size();
				}
				else
				{
					final ArrayList<SaleOrder> arrayList = new ArrayList<SaleOrder>(ref_items);
					 int count = arrayList.size();
					 final  ArrayList<SaleOrder> nArrayList = new  ArrayList<SaleOrder>(count);
					 
	                for (int i=0; i<count; i++)
	                {
	                    final SaleOrder rp = arrayList.get(i);
	                    final String value = rp.getResPartner().getName().toLowerCase();
	                    final String value_ =rp.getDateOrder().toString().toLowerCase();
	                    if ((value.contains(prefix))||(value_.contains(prefix)))
	                    {
	                        nArrayList.add(rp);
	                    }
	                }
	                results.values = nArrayList;
	                results.count = nArrayList.size();
				}
				

				return results;
			
			}
		
			@SuppressWarnings("unchecked")
			@Override
			protected void publishResults(CharSequence constraint,
					FilterResults results) {

				f_items = (ArrayList<SaleOrder>)results.values;
				annuaire.clear();
				int count = f_items.size();
	            for (int i=0; i<count; i++)
	            {
	                SaleOrder pkmn = (SaleOrder)f_items.get(i);
	                add(pkmn);
	            }
	            
	            if (f_items.size() > 0)
	                notifyDataSetChanged();
	            else
	            {
	                annuaire=new ArrayList<SaleOrder>(ref_items);
	                notifyDataSetInvalidated();
	            }
		}
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
