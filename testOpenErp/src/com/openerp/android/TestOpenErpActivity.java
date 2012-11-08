package com.openerp.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.openerp.connection.OpenErpConnection;

public class TestOpenErpActivity extends Activity {
	
	public int uid = 1;
	public int init;
	public int uidOp;
	//private String DB_NAME;
	//private String USERNAME;
	static OpenErpConnection openerpconn; 
	//private static final String TAG = "MainActivity class";

	
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
        Button boutonGo =(Button) findViewById(R.id.buttonStart);
        boutonGo.setOnClickListener(btngoListener);
        
    }
        
    private View.OnClickListener btngoListener = new View.OnClickListener() {

        @Override

        public void onClick(View v) {
 
        	Intent intent = new Intent(TestOpenErpActivity.this, ConnectionScreen.class);
        	startActivity(intent);
        }

    };
        
    
}