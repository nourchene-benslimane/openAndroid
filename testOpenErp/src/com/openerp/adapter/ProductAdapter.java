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
import com.openerp.model.Product;

public class ProductAdapter extends BaseAdapter implements Filterable{

	List<Product> catalogue;
	LayoutInflater inflater;
	private Filter productFilter;
	List<Product> f_items; //new
	List<Product> ref_items; //new
	
	
	public ProductAdapter(Context context, List<Product> list){
		inflater = LayoutInflater.from(context);
		this.catalogue = list;
		this.f_items = new ArrayList<Product>(list); //new
		this.ref_items = new ArrayList<Product>(list); //new
	}
	
	@Override
	public int getCount(){
		return catalogue.size();
	}
	
	@Override
	public Object getItem(int position){
		return catalogue.get(position);
	}
	
	@Override
	public long getItemId(int position){
		return position;
	}
	
	
	private class ViewHolder {
		TextView productName;
		TextView productCode;
	}
	
	@Override
	public View getView(int position, View  convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_product, null);
			holder.productName=(TextView)convertView.findViewById(R.id.product_name);
			holder.productCode=(TextView)convertView.findViewById(R.id.product_code);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.productName.setText(catalogue.get(position).getName());
		holder.productCode.setText(catalogue.get(position).getCode());
		
		return convertView;
		
	}
	
	//new
    public void add(Product item){
        catalogue.add(item);
    }

	
	
	//ajouter le filtre
	@Override
	public Filter getFilter() {
		if (productFilter == null)
			productFilter = new PartnerFilter();

		return productFilter;
	}
	
	private class PartnerFilter extends Filter {
		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			FilterResults results = new FilterResults();
			
			String prefix = constraint.toString().toLowerCase();
			if (prefix == null || prefix.length() == 0) {
				//new 
				Log.i("Adapter Filter"," pas de valeur à rechercher");
				 ArrayList<Product> arrayList = new ArrayList<Product>(ref_items);
                 results.values = arrayList;
                 results.count = arrayList.size();
			}
			else
			{
				final ArrayList<Product> arrayList = new ArrayList<Product>(ref_items);
				 int count = arrayList.size();
				 final  ArrayList<Product> nArrayList = new  ArrayList<Product>(count);
				 
                for (int i=0; i<count; i++)
                {
                    final Product rp = arrayList.get(i);
                    final String value = rp.getName().toLowerCase();

                    if ((value.startsWith(prefix))||(rp.getCode().toLowerCase().startsWith(prefix)))
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

			f_items = (ArrayList<Product>)results.values;
			catalogue.clear();
			int count = f_items.size();
            for (int i=0; i<count; i++)
            {
                Product pkmn = (Product)f_items.get(i);
                add(pkmn);
            }
            
            if (f_items.size() > 0)
                notifyDataSetChanged();
            else
            {
                catalogue=new ArrayList<Product>(ref_items);
                notifyDataSetInvalidated();
            }
	}
	
	
	
	
	}
	
	
	
	
	
	
	
	
}
