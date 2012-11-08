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
import com.openerp.model.ResPartner;

public class PartnerAdapter extends BaseAdapter implements Filterable  {
	

	List<ResPartner> annuaire;
	List<ResPartner> f_items; //new
	List<ResPartner> ref_items; //new
	
	LayoutInflater inflater;
	private Filter partnerFilter;
	
	
	public PartnerAdapter(Context context, List<ResPartner> list){
		inflater = LayoutInflater.from(context);
		this.annuaire = list;
		this.f_items = new ArrayList<ResPartner>(list); //new
		this.ref_items = new ArrayList<ResPartner>(list); //new
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
		TextView partnerName;
		TextView partnerRef;
	}
	
	@Override
	public View getView(int position, View  convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_partner, null);
			holder.partnerName=(TextView)convertView.findViewById(R.id.partner_name);
			holder.partnerRef=(TextView)convertView.findViewById(R.id.partner_ref);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.partnerName.setText(annuaire.get(position).getName());
		holder.partnerRef.setText(annuaire.get(position).getRef());
		
		return convertView;
		
	}
	
	
	//new
    public void add(ResPartner item){
        annuaire.add(item);
    }

	//créer le filtre
	
	@Override
	public Filter getFilter() {
		//Methode n°1
		if (partnerFilter == null)
			partnerFilter = new PartnerFilter();

		return partnerFilter;
		
		
	}
	
	private class PartnerFilter extends Filter {
		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			//1ere methode 
			FilterResults results = new FilterResults(); //ok
			// We implement here the filter logic
			
			String prefix = constraint.toString().toLowerCase();
			if (prefix == null || prefix.length() == 0) {
				//new 
				Log.i("Adapter Filter"," pas de valeur à rechercher");
				 ArrayList<ResPartner> arrayList = new ArrayList<ResPartner>(ref_items);
                 results.values = arrayList;
                 results.count = arrayList.size();

			}
			else {
				//new 
				 final ArrayList<ResPartner> arrayList = new ArrayList<ResPartner>(ref_items);
				 int count = arrayList.size();
				 final  ArrayList<ResPartner> nArrayList = new  ArrayList<ResPartner>(count);
				 
                 for (int i=0; i<count; i++)
                 {
                     final ResPartner rp = arrayList.get(i);
                     final String value_name = rp.getName().toLowerCase();
                     final String value_ref = rp.getRef().toLowerCase();

                     if ( (value_name.startsWith(prefix))||(value_ref.startsWith(prefix)))
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
		protected void publishResults(CharSequence constraint, FilterResults results) {

			//new
			f_items = (ArrayList<ResPartner>)results.values;
			annuaire.clear();
			int count = f_items.size();
            for (int i=0; i<count; i++)
            {
                ResPartner pkmn = (ResPartner)f_items.get(i);
                add(pkmn);
            }
            
            if (f_items.size() > 0)
                notifyDataSetChanged();
            else
            {
                annuaire=new ArrayList<ResPartner>(ref_items);
                notifyDataSetInvalidated();
            }
			
			
			
			//old
			// Now we have to inform the adapter about the new list filtered
			
			/*if (results.count == 0)
				notifyDataSetInvalidated();
			else {
				annuaire = (List<ResPartner>) results.values;
				Log.i("PARTNERLIST SIZE ","valeur après filtre= "+annuaire.size());
				notifyDataSetChanged();
			}*/

		}

	}
	

}
