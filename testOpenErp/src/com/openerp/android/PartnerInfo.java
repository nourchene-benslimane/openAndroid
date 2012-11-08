package com.openerp.android;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.openerp.adapter.AddressAdapter;
import com.openerp.model.ResPartner;
import com.openerp.model.ResPartnerAddress;

public class PartnerInfo extends Activity{
	private TextView _name;
	private TextView _website;
	private Button _afficherBonDeCommande;
	private Button _nouveauBonDeCommande;
	
	private int id;
	private ResPartner partner;
	List<ResPartnerAddress> addressList;
	private ListView lvAddress;
	AddressAdapter addressListAdapter;
	
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner_info);
    	Log.i("class PartnerInfo","onCreate debut");
        Bundle receivedPartner = this.getIntent().getExtras();
        Log.i("class PartnerInfo","after getExtras");
        
        id = receivedPartner.getInt("id");
        Log.i("class PartnerInfo","id received= "+id);
            
        _name = (TextView) findViewById(R.id.partnerName);
        _website = (TextView) findViewById(R.id.partnerWebsite);
        lvAddress = (ListView) findViewById(R.id.addressListView);
        _afficherBonDeCommande = (Button) findViewById(R.id.buttonAfficherBon);
       
        if (!MenuPartners.db_manager.hasSaleOrders(id)) 
    	   _afficherBonDeCommande.setVisibility(View.GONE);
        
        
        _nouveauBonDeCommande = (Button) findViewById(R.id.buttonNouveauBon);
        
        partner= MenuPartners.db_manager.getResPartnerWithId(id);
        addressList=partner.getAddress();
        _name.setText(partner.getName());
        _website.setText(partner.getWebsite());  
        _afficherBonDeCommande.setOnClickListener(btnViewCommandeListener);
        _nouveauBonDeCommande.setOnClickListener(btnNewCommandeListener);
        
        addressListAdapter = new AddressAdapter(this, addressList);
        lvAddress.setAdapter(addressListAdapter);
        addressListAdapter.notifyDataSetChanged();
        
        
       // partnerListAdapter = new PartnerAdapter(context, listePartners);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_partner_info, menu);
        return true;
    }
    
    private View.OnClickListener btnViewCommandeListener = new View.OnClickListener() {

        @Override

        public void onClick(View v) {
        	//ajouter l'id en bundle
        	Bundle _partnerId = new Bundle();
        	_partnerId.putInt("id", id);
        	
        	Intent intent = new Intent(PartnerInfo.this,PartnerSaleOrderList.class);
        	intent.putExtras(_partnerId);
        	startActivity(intent); 
          
        }

    };

    private View.OnClickListener btnNewCommandeListener = new View.OnClickListener() {

        @Override

        public void onClick(View v) {
        	//ajouter l'id en bundle
        	Bundle _partnerId = new Bundle();
        	_partnerId.putInt("id", id);
        	
        	Intent intent = new Intent(PartnerInfo.this,PartnerNewSaleOrder.class);
        	intent.putExtras(_partnerId);
        	startActivity(intent); 
          
        }

    };

}
