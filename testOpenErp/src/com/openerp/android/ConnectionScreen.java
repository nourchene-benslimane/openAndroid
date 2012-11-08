package com.openerp.android;

import java.util.ArrayList;
import java.util.List;

import org.xmlrpc.android.XMLRPCClient;
import org.xmlrpc.android.XMLRPCException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.openerp.connection.OpenErpConnection;
import com.openerp.db.DatabaseManager;


public class ConnectionScreen extends Activity{
	private static List<String> data;
	private static Spinner _dbselect;
	private EditText _username;
	private Button boutonDeConnexion;
	private EditText _password ;
	private static ArrayAdapter<String> dataAdapter;
	private static Context context;
	
	private ProgressDialog progressDialog;
	private static ProgressDialog progressDBinit;
	
	static OpenErpConnection openerpconn; 
	private static int init;
	public static String username;
	public static String password;
	public static String database;
	public static int userid;
	
	
	
	static Handler handlerDB = new Handler(){
    	public void handleMessage(Message msg) {
    		Log.i("handlerDB","start");
    		dataAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item, data);
    	    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    		_dbselect.setAdapter(dataAdapter);
    		            
		}
    };

    static Handler handlerData = new Handler(){
		public void handleMessage(Message msg) {
			Log.i("handlerData","msg="+msg.what);
    		if (msg.what == -2){
    			Toast.makeText(context, "LOGIN FAILED-XMLRPCException", Toast.LENGTH_LONG).show();
    		}else if (msg.what == -3){
    			Toast.makeText(context, "LOGIN FAILED-Exception", Toast.LENGTH_LONG).show();
    		}else {
        		Log.i("handlerData", "Successful connection to openerp and trying to initialize databse ");
        		initEmbeddedDB();
    		}
		}
    };
    
    static Handler handlerDBinit = new Handler(){
    	public void handleMessage(Message msg){
    		//code du handler
    		Log.i("handlerDBinit","database initialisation successful");
        	
    		Intent intent = new Intent(context, HomeActivity.class);
    		context.startActivity(intent);
    	}
    };
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
    		setContentView(R.layout.connection_screen);
    		context = this;
    		progressDialog = new ProgressDialog(this);
    		progressDBinit= new ProgressDialog(this);
    		
    		_username = (EditText) findViewById(R.id.EditTextUsername);
    		_dbselect = (Spinner) findViewById(R.id.spinnerDB);
    		_password = (EditText) findViewById(R.id.EditTextPassword);
    		
    		boutonDeConnexion = (Button) findViewById(R.id.buttonConnect);
            boutonDeConnexion.setOnClickListener(btnConnectionListener);
    		
    		new Thread(new Runnable()
            {@Override
    			
    			public void run(){
    		    	//calcul
            	Log.i("ON CREATE_New Thread","run before getdb");
    		    
    		    	data = getDatabaseList("192.168.1.9:8069");
    		    	//data = getDatabaseList("10.0.2.2:8069");
    		    Log.i("ON CREATE_New Thread","run after getdb");
    		    	handlerDB.sendEmptyMessage(0);
    		    Log.i("ON CREATE_New Thread","run after handler");
    		    }
    		    	
    			
            }).start();		
    		
    }
    
  



    private static void initEmbeddedDB() {
	
    	//créer thread pour initialiser la base de données
    	//afficher un progressDialog en attendant
    	Log.i("traitementDesDonnées initEmbeddedDB","début");

    	
    	progressDBinit.setMessage("Initialisation de la base en cours..");
    	progressDBinit.show();
    	progressDBinit.setCancelable(false);
    	new Thread(new Runnable() {
			@Override
    	   
			public void run() {
				//background thread process
				//operation d'initialisation de la base
				Log.i("initEmbeddedDB","run start");
				DatabaseManager.init(context);
				Log.i("initEmbeddedDB","databasemanager.init out");
				
				
				Log.d("initEmbeddedDB","initialize_db in");
	 			init=openerpconn.initialize_db(database,userid,password);  //suivre ici
	 			Log.i("initEmbeddedDB","initialize_db out");
	 			
	 			
	 			
				Message msg = handlerData.obtainMessage();
				msg.what= init;
				handlerDBinit.sendMessage(msg);
				Log.i("initEmbeddedDB","sendMessage out");
				progressDBinit.dismiss();
				Log.i("initEmbeddedDB","run out");
			}
		}).start();
		
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.connection_screen, menu);
        return true;
    }

    private View.OnClickListener btnConnectionListener = new View.OnClickListener() {

        @Override

        public void onClick(View v) {
        	Log.i("onClickListener","befor checkData");
        	if (checkData()==-1)
        	{
        		//dialogue
        		   new AlertDialog.Builder(context)
        		      .setMessage("Données de connexion incomplètes")
        		      .setTitle("Erreur")
        		      .setCancelable(true)
        		      .setNeutralButton(android.R.string.cancel,
        		         new DialogInterface.OnClickListener() {
        		         public void onClick(DialogInterface dialog, int whichButton){}
        		         })
        		      .show();
        	}else {
        		Log.i("onClickListener","after checkData ok");
        		traitementDesDonnees();
           	}
        	
        	
        }
    };
    
    
       private void traitementDesDonnees() {  
    	   	Log.i("traitementDesDonnées","début");

    	   	
        	progressDialog.setMessage("Connexion en cours..");
        	progressDialog.show();
        	progressDialog.setCancelable(false);
        	
        	new Thread(new Runnable() {
    			@Override
        	   
    			public void run() {
    				//background thread process
    				openerpconn = new OpenErpConnection();
    				userid=Integer.valueOf(OpenErpConnection.connect(database,username,password));
    				Message msg = handlerData.obtainMessage();
    				msg.what= userid;
    				handlerData.sendMessage(msg);
    				progressDialog.dismiss();
    			}
    		}).start();
    	}
        
        
        
        
        	
       protected int checkData() {
    	   
    	   if (_username.getText().toString().isEmpty() | _password.getText().toString().isEmpty()) {
    		   //données incomplètes
    		   _username.setText("admin");
        	   _password.setText("admin");
    	   }
    	//	return -1;   
    	
   // 	   }else{
  
    		username= _username.getText().toString();
    		password= _password.getText().toString();
           	database= _dbselect.getSelectedItem().toString();
           	return 0;
    //	   }
        		
            	
		
	}










   
    public List<String> getDatabaseList (String host) {
    	XMLRPCClient xmlrpcDB = new XMLRPCClient("http://"+host+"/xmlrpc/db");
    	try{
    		 //Retrieve databases
    	    List<Object> params = new ArrayList<Object>();
    	    Log.i("getDBlist","avant l'appel");
    	    Object result = xmlrpcDB.call("list", params);
    	    Log.i("getDBlist","après l'appel");
    	    Object[] a = (Object[]) result;

    	    List<String> res = new ArrayList<String>();
    	    for (int i = 0; i < a.length; i++) {
    	    if (a[i] instanceof String)
    	    {
    	      res.add((String)a[i]);
    	    }
    	}
    	    return res;
    	}catch (XMLRPCException e) {
    	    Log.w("XmlException Error while retrieving OpenERP Databases: ",e);
    	    return null;
    	 }
    	  catch (Exception e)
    	  {
    	    Log.w("Error while retrieving OpenERP Databases: ",e);
    	    return null;
    	  }
		
    			
    }
}
