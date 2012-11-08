package com.openerp.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        
        Button menuClientButton = (Button) findViewById(R.id.buttonClients);
        Button menuProduitButton = (Button) findViewById(R.id.ButtonProduits);
        Button menuBonDeCommandeButton = (Button) findViewById(R.id.ButtonBonsCommande);
        Button btn = (Button)findViewById(R.id.button1);
        btn.setOnClickListener(btnListener);
        
        
        menuClientButton.setOnClickListener(btnClientListener);
        menuProduitButton.setOnClickListener(btnProduitListener);
        menuBonDeCommandeButton.setOnClickListener(btnCommandeListener);
        
    }

    private View.OnClickListener btnClientListener = new View.OnClickListener() {

        @Override

        public void onClick(View v) {
        	Intent intent = new Intent(HomeActivity.this,MenuPartners.class);
        	startActivity(intent); 
          
        }

    };
    
    
    private View.OnClickListener btnProduitListener = new View.OnClickListener() {

        @Override

        public void onClick(View v) {
        	Intent intent = new Intent(HomeActivity.this, MenuProducts.class);
        	startActivity(intent);          
        }

    };
    
    
    
    private View.OnClickListener btnCommandeListener = new View.OnClickListener() {

        @Override

        public void onClick(View v) {
        	Intent intent = new Intent(HomeActivity.this, MenuCommande.class);
        	startActivity(intent); 
        }

    };
    
    private View.OnClickListener btnListener = new View.OnClickListener() {

        @Override

        public void onClick(View v) {
        	Intent intent = new Intent(HomeActivity.this, NewSaleOrder.class);
        	startActivity(intent); 
        }

    };
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_activity, menu);
        return true;
    }

    
}
