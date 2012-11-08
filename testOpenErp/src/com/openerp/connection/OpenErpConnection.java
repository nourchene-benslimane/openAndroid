package com.openerp.connection;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.xmlrpc.android.XMLRPCClient;
import org.xmlrpc.android.XMLRPCException;

import android.database.SQLException;
import android.util.Log;

import com.openerp.db.DatabaseManager;
import com.openerp.model.Product;
import com.openerp.model.ProductTaxes;
import com.openerp.model.ProductUom;
import com.openerp.model.ResPartner;
import com.openerp.model.ResPartnerAddress;
import com.openerp.model.SaleOrder;
import com.openerp.model.SaleOrderLine;

public class OpenErpConnection {
	private static final String TAG = "OpenerpConnection class";
	public static final String HOST = "http://192.168.1.9:8069/xmlrpc";
	//public static final String HOST = "http://10.0.2.2:8069/xmlrpc";
	public static final String URL_COMMON = "/common";
	public static final String URL_OBJECT = "/object";
	public static final String URL_DB = "/db";
	public static XMLRPCClient rpcClientCom = new XMLRPCClient(HOST+URL_COMMON);
	public XMLRPCClient rpcClientObj = new XMLRPCClient(HOST+URL_OBJECT);
	private String createDateOp;
	private String writeDateOp;




	/*private String DB_NAME;
	private String USERNAME;
	private String PASSWORD;*/

	public static String _user="" ;
	public int uid = 1;

	public OpenErpConnection(){
		Log.i(TAG, "init");
	}
	public static int connect(String db, String user, String pass){
		try {
			_user = (String)rpcClientCom.call("login", db, user, pass).toString();

			Object uid1 = Integer.parseInt(_user);
			if (uid1 instanceof Integer)
			{
				return (Integer) uid1;
			}
			return -1;
		}catch (XMLRPCException e) {
			Log.e(TAG, "LOGIN FAILED-XMLRPCException: "+ e.toString());
			return -2;
		}catch (Exception e) {
			Log.e(TAG, "LOGIN FAILED-EXCEPTION: "+ e.toString());
			return -3;
		}
	}

	public List <Integer> idsList(String db, int user_id, String user_password, String model, Vector<String> fields, String function ){
		Object[] args = { db, user_id, user_password, model, function, fields };
		Object[] res =null;
		List <Integer> idList;
		idList= new ArrayList<Integer>();

		try{
			res = (Object[])rpcClientObj.callEx("execute", args);
		}catch (XMLRPCException e) {
			Log.i(TAG, "XMLRPCException: "+e);
			e.printStackTrace();
		}catch (Exception e) {
			Log.e(TAG, "Exception: "+ e.toString());
			e.printStackTrace();
		}
		for(int i = 0 ; i < res.length ; i++) {
			idList.add((Integer)res[i]);
		}

		return idList;
	}

	//new
	public List <Integer> idsListRel(String db, int user_id, String user_password,String model, Vector<String> fields, String function ){
		Log.i(TAG, "______________________ Begin idsList method ______________________");
		Object[] args = {db, user_id, user_password, model, function, fields };
		Object[] res =null;
		List <Integer> idList;
		idList= new ArrayList<Integer>();
		try{
			res = (Object[])rpcClientObj.callEx("execute_for_android_rel", args);
		}catch (XMLRPCException e) {
			Log.i(TAG, "XMLRPCException1: "+e);
			e.printStackTrace();
		}catch (Exception e) {
			Log.e(TAG, "Exception2: "+ e);
			e.printStackTrace();
		}
		for(int i = 0 ; i < res.length ; i++) {
			idList.add((Integer)res[i]);
		}
		Log.i(TAG, "______________________ End idsList method   ______________________");
		return idList;
	}


	//maj
	@SuppressWarnings("rawtypes")
	public int initialize_db(String db, int user_id, String user_password){
		//TODO tester si une base de données existe deja, si oui, la charger, sinon en créer une
		Vector<String> noFields = new Vector<String>();
		rpcClientObj = new XMLRPCClient(HOST+URL_OBJECT);
		List <Integer> resPartnerids=idsList(db, user_id, user_password, "res.partner",noFields,"search");

		Vector<String> partnerFields = new Vector<String>();
		partnerFields.addElement("name");
		partnerFields.addElement("website");
		partnerFields.addElement("ref");
		partnerFields.addElement("id");
		partnerFields.addElement("create_date"); //new
		partnerFields.addElement("write_date");  //new

		Object[] arg0 = { db, user_id, user_password, "res.partner", "read",resPartnerids, partnerFields };
		Object res = null;
		try{
			res= rpcClientObj.callEx("execute_for_android", arg0); //new
			//res =  rpcClientObj.callEx("execute", arg0);
		} catch (XMLRPCException e) {
			Log.i(TAG, "XMLRPCException: "+e);
			e.printStackTrace();
			return -1;
		}
		Object[] resOpObj = (Object[]) res;
		try{
			for(int i = 0 ; i < resOpObj.length ; i++) {
				//… on crée un élément pour la liste…
				HashMap resOpObjHM= (HashMap) resOpObj[i];

				createDateOp = resOpObjHM.get("create_date").toString(); //new
				writeDateOp = resOpObjHM.get("write_date").toString();  //new

				if  (createDateOp.equals("false")==false)
					createDateOp=createDateOp.substring(0, 19);
				if  (writeDateOp.equals("false")==false)
					writeDateOp=writeDateOp.substring(0, 19);

				//Création d'un partenaire
				ResPartner resPartner = new ResPartner(resOpObjHM.get("name").toString(),resOpObjHM.get("website").toString(),resOpObjHM.get("ref").toString(),(Integer)resOpObjHM.get("id"),createDateOp,writeDateOp);
				DatabaseManager.getInstance().addResPartner(resPartner);

			}

		} catch (SQLException e) {
			Log.i(TAG, "SQLException:"+e);
			e.printStackTrace();
			return -2;
		}

		//	get the nbre of records from openERP: res_partner_address
		List <Integer> resPartnerAddressids=idsList(db, user_id, user_password,"res.partner.address",noFields,"search");
		//	 Data import from OpenERP 
		Vector<String> addressFields = new Vector<String>();
		addressFields.addElement("name");
		addressFields.addElement("type");
		addressFields.addElement("function");
		addressFields.addElement("street");
		addressFields.addElement("street2");
		addressFields.addElement("zip");
		addressFields.addElement("city");
		addressFields.addElement("email");
		addressFields.addElement("phone");
		addressFields.addElement("fax");
		addressFields.addElement("mobile");
		addressFields.addElement("partner_id");
		addressFields.addElement("id");
		addressFields.addElement("create_date");
		addressFields.addElement("write_date");
		Object[] arg1 = { db, user_id, user_password, "res.partner.address", "read",resPartnerAddressids, addressFields };
		Object resAddress = null;
		try{
			resAddress =  rpcClientObj.callEx("execute_for_android", arg1); //new
			//	resAddress =  rpcClientObj.callEx("execute", arg1);
		} catch (XMLRPCException e) {
			Log.i(TAG, "XMLRPCException: "+e);
			e.printStackTrace();
			return -1;
		}

		//  Insert into openandroidDB.sqlite
		Object[] resAddressOpObj = (Object[]) resAddress;
		try{
			for(int i = 0 ; i < resAddressOpObj.length ; i++) {
				//… on crée un élément pour la liste…
				HashMap resAddressOpObjHM= (HashMap) resAddressOpObj[i];
				
				createDateOp = resAddressOpObjHM.get("create_date").toString();
				writeDateOp = resAddressOpObjHM.get("write_date").toString();
				
				if  (createDateOp.equals("false")==false)
					createDateOp=createDateOp.substring(0, 19);
				

				if  (writeDateOp.equals("false")==false)
					writeDateOp=writeDateOp.substring(0, 19);
				
				
				ResPartnerAddress resPartnerAddress;
				if (resAddressOpObjHM.get("partner_id") instanceof Boolean) {
					resPartnerAddress = new ResPartnerAddress(resAddressOpObjHM.get("name").toString(),
							resAddressOpObjHM.get("type").toString(),
							resAddressOpObjHM.get("function").toString(),
							resAddressOpObjHM.get("street").toString(),
							resAddressOpObjHM.get("street2").toString(),
							resAddressOpObjHM.get("zip").toString(),
							resAddressOpObjHM.get("city").toString(),
							resAddressOpObjHM.get("email").toString(),
							resAddressOpObjHM.get("phone").toString(),
							resAddressOpObjHM.get("fax").toString(),
							resAddressOpObjHM.get("mobile").toString(),
							(Integer)resAddressOpObjHM.get("id"),
							createDateOp,
							writeDateOp);
				}
				else{
					
					int partner_id = (Integer) resAddressOpObjHM.get("partner_id");
					ResPartner resPartner= DatabaseManager.getInstance().getResPartnerWithIdPostgres(partner_id);

					resPartnerAddress = new ResPartnerAddress(resAddressOpObjHM.get("name").toString(),
							resAddressOpObjHM.get("type").toString(),
							resAddressOpObjHM.get("function").toString(),
							resAddressOpObjHM.get("street").toString(),
							resAddressOpObjHM.get("street2").toString(),
							resAddressOpObjHM.get("zip").toString(),
							resAddressOpObjHM.get("city").toString(),
							resAddressOpObjHM.get("email").toString(),
							resAddressOpObjHM.get("phone").toString(),
							resAddressOpObjHM.get("fax").toString(),
							resAddressOpObjHM.get("mobile").toString(),
							resPartner,
							(Integer)resAddressOpObjHM.get("id"),
							createDateOp,
							writeDateOp);
				}
				DatabaseManager.getInstance().addResPartnerAddress(resPartnerAddress);

			}
		} catch (SQLException e) {
			Log.i(TAG, "SQLException:"+e);
			e.printStackTrace();
			return -2;
		}

		

		//        ______________________ProductUom___________________

		List <Integer> productUomids=idsList(db, user_id, user_password,"product.uom",noFields,"search");


		//		 Data import from OpenERP 
		Vector<String> productUomFields = new Vector<String>();
		productUomFields.addElement("name");
		productUomFields.addElement("id");
		productUomFields.addElement("create_date");
		productUomFields.addElement("write_date");
		Object[] productUomArg = {db, user_id, user_password, "product.uom", "read", productUomids, productUomFields};
		Object productUomRes = null;
		try{
			//productUomRes =  rpcClientObj.callEx("execute", productUomArg);
			productUomRes =  rpcClientObj.callEx("execute_for_android", productUomArg);
		} catch (XMLRPCException e) {
			Log.i(TAG, "XMLRPCException: "+e);
			e.printStackTrace();
			return -1;
		}

		//      Insert into openerpmobile.db
		Object[] resProdUomOpObj = (Object[]) productUomRes;
		try{
			for(int i = 0 ; i < resProdUomOpObj.length ; i++) {
				HashMap resProdUomOpObjHM= (HashMap) resProdUomOpObj[i];
				createDateOp = resProdUomOpObjHM.get("create_date").toString();
				writeDateOp = resProdUomOpObjHM.get("write_date").toString();

				if  (createDateOp.equals("false")==false)
					createDateOp=createDateOp.substring(0, 19);
				if  (writeDateOp.equals("false")==false)
					writeDateOp=writeDateOp.substring(0, 19);


				//Création d'un ProduitUom
				ProductUom productUom = new ProductUom(resProdUomOpObjHM.get("name").toString(),(Integer)resProdUomOpObjHM.get("id"),createDateOp,writeDateOp);
				DatabaseManager.getInstance().addProductUom(productUom);//ici se fait l'ajout du productUom
			}
		} catch (SQLException e) {
			Log.i(TAG, "SQLException:"+e);
			e.printStackTrace();
			return -2;
		}
		
		
		//product
		
//		get the nbre of records from openERP: product
		Log.d(TAG, " ___________________Product_____________________");
		Log.d(TAG, " ________________________________________________");
		List <Integer> productids=idsList(db, user_id, user_password,"product.product",noFields,"search");
	    Log.d(TAG, "productids:"+productids);
//		 Data importation from OpenERP 
 		Vector<String> productFields = new Vector<String>();
 		productFields.addElement("name_template");
 		productFields.addElement("default_code");
// 		productFields.addElement("id");
 		productFields.addElement("product_tmpl_id");
 		productFields.addElement("id");
 		productFields.addElement("qty_available");
		Object[] productArg = { db, user_id, user_password, "product.product", "read", productids, productFields};
		Object productRes = null;
		
		try{
			productRes =  rpcClientObj.callEx("execute", productArg);
			} catch (XMLRPCException e) {
				Log.i(TAG, "XMLRPCException: "+e);
				e.printStackTrace();
				return -1;
			}
		
//      Insert into openerpmobile.db
			Object[] resProdOpObj = (Object[]) productRes;
		
			try{
				for(int i = 0 ; i < resProdOpObj.length ; i++) {
				    //… on crée un élément pour la liste…
				    HashMap resProdOpObjHM= (HashMap) resProdOpObj[i];
//                  for Template				    
				    Object[] template = (Object[])resProdOpObjHM.get("product_tmpl_id");
				    int temp_id=(Integer)template[0];
				    Vector<String> tempFields = new Vector<String>();
				    tempFields.addElement("list_price");
				    tempFields.addElement("categ_id");
				    tempFields.addElement("uom_id");
				    Object[] productTempArg = { db, user_id, user_password,  "product.template", "read", temp_id, tempFields };
					HashMap productTempRes = null;
					try{
						productTempRes = (HashMap) rpcClientObj.callEx("execute", productTempArg);
						} catch (XMLRPCException e) {
							Log.i(TAG, "XMLRPCException: "+e);
							e.printStackTrace();
							return -1;
						}
//					for category				    
				    Object[] categ = (Object[])productTempRes.get("categ_id");
				    int categ_id=(Integer)categ[0];
				    Vector<String> categFields = new Vector<String>();
				    categFields.addElement("name");
				    Object[] productCategArg = { db, user_id, user_password,  "product.category", "read", categ_id, categFields};
					HashMap productCategRes = null;
					try{
//							productTempRes =  rpcClientObj.callEx("execute", productArg);
						productCategRes = (HashMap) rpcClientObj.callEx("execute", productCategArg);
						} catch (XMLRPCException e) {
							Log.i(TAG, "XMLRPCException: "+e);
							e.printStackTrace();
							return -1;
						}
			        //Création d'un Produit
//					int product_uom_id =(Integer) productTempRes.get("uom_id");
					
					Object[] uom = (Object[])productTempRes.get("uom_id");
					int product_uom_id=(Integer)uom[0];
				    ProductUom productUom= DatabaseManager.getInstance().getProductUomWithIdPostgres((Integer)product_uom_id);
			        Product product = new Product(resProdOpObjHM.get("name_template").toString(),resProdOpObjHM.get("default_code").toString(),productCategRes.get("name").toString(),(Double)productTempRes.get("list_price"),(Double)resProdOpObjHM.get("qty_available"),(Integer)resProdOpObjHM.get("id"),productUom);
			        DatabaseManager.getInstance().addProduct(product);
				}
			} catch (SQLException e) {
				Log.i(TAG, "SQLException:"+e);
				e.printStackTrace();
				return -2;
			}
			
			Vector<String> date = new Vector<String>();
			date.addElement("id");
	 		date.addElement("create_date");
	 		date.addElement("write_date");
			Object[] productArgDate = {  db, user_id, user_password, "product.product", "read", productids, date};
			Object productResDate = null;
			try{
				productResDate =  rpcClientObj.callEx("execute_for_android", productArgDate);
				} catch (XMLRPCException e) {
					Log.i(TAG, "XMLRPCException: "+e);
					e.printStackTrace();
					return -1;
				}
			
//          Insert into openerpmobile.db
				Object[] resProdDateOpObj = (Object[]) productResDate;
			try{
				for(int i = 0 ; i < resProdDateOpObj.length ; i++) {
				    HashMap resProdDateOpObjHM= (HashMap) resProdDateOpObj[i];
				    createDateOp = resProdDateOpObjHM.get("create_date").toString();
				    writeDateOp = resProdDateOpObjHM.get("write_date").toString();
				    if  (createDateOp.equals("false")==false){
				    	createDateOp=createDateOp.substring(0, 19);
			    	}
				    if  (writeDateOp.equals("false")==false){
				    	writeDateOp=writeDateOp.substring(0, 19);
				    }
				    
				    Product prodUp=DatabaseManager.getInstance().getProductWithIdPostgres((Integer)resProdDateOpObjHM.get("id"));
				    if (prodUp!=  null){
				    	prodUp.setCreateDate(createDateOp);
				    	prodUp.setWriteDate(writeDateOp);
				    	DatabaseManager.getInstance().updateProduct(prodUp);
				    	
				    }
				}
			} catch (SQLException e) {
				Log.i(TAG, "SQLException:"+e);
				e.printStackTrace();
				return -2;
			}
			
			
			
			
		
		


		//	    ___________________SaleOrder____________________

		List <Integer> saleOrderids=idsList(db, user_id, user_password,"sale.order",noFields,"search");
		//		 Data importation from OpenERP 
		Vector<String> saleOrderFields = new Vector<String>();

		saleOrderFields.addElement("id");
		saleOrderFields.addElement("name");
		saleOrderFields.addElement("state");
		saleOrderFields.addElement("date_order");
		saleOrderFields.addElement("amount_tax");
		saleOrderFields.addElement("partner_id");
		saleOrderFields.addElement("create_date");
		saleOrderFields.addElement("write_date");
		Object[] saleOrderArgs = { db, user_id, user_password, "sale.order", "read",saleOrderids, saleOrderFields };
		Object resSaleOrder = null;
		try{
			resSaleOrder =  rpcClientObj.callEx("execute_for_android", saleOrderArgs);
		}catch (XMLRPCException e) {
			Log.i(TAG, "XMLRPCException: "+e);
			e.printStackTrace();
			return -1;
		}

		//      Insert into openandroidDB.sqlite
		Object[] resSaleOrderOpObj = (Object[]) resSaleOrder;
		try{
			for(int i = 0 ; i < resSaleOrderOpObj.length ; i++) {
				HashMap resSaleOrderOpObjHM= (HashMap) resSaleOrderOpObj[i];

				createDateOp = resSaleOrderOpObjHM.get("create_date").toString();
				writeDateOp = resSaleOrderOpObjHM.get("write_date").toString();

				if  (createDateOp.equals("false")==false)
					createDateOp=createDateOp.substring(0, 19);

				if  (writeDateOp.equals("false")==false)
					writeDateOp=writeDateOp.substring(0, 19);
				String dateOp = resSaleOrderOpObjHM.get("date_order").toString();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date dateOrder;

				try {
					dateOrder = dateFormat.parse(dateOp);
				} catch (Exception e) {
					e.printStackTrace();
					return -2;
				}
				ResPartner customer= DatabaseManager.getInstance().getResPartnerWithIdPostgres((Integer) resSaleOrderOpObjHM.get("partner_id"));
				SaleOrder saleOrder= new SaleOrder(resSaleOrderOpObjHM.get("name").toString(),
						resSaleOrderOpObjHM.get("state").toString(),
						dateOrder,
						(Double)resSaleOrderOpObjHM.get("amount_tax"),
						customer,
						(Integer)resSaleOrderOpObjHM.get("id"),
						createDateOp,
						writeDateOp
						);
				DatabaseManager.getInstance().addSaleOrder(saleOrder);

			}
		}catch (SQLException e) {
			Log.i(TAG, "SQLException:"+e);
			e.printStackTrace();
			return -2;
		}


		//	    ________________________SaleOrderLine___________________
		List <Integer> saleOrderLineids=idsList(db, user_id, user_password,"sale.order.line",noFields,"search");
		//		 Data import from OpenERP 
		Vector<String> saleOrderLineFields = new Vector<String>();
		saleOrderLineFields.addElement("id");
		saleOrderLineFields.addElement("name");
		saleOrderLineFields.addElement("order_id");
		saleOrderLineFields.addElement("product_id");
		saleOrderLineFields.addElement("product_uom");
		saleOrderLineFields.addElement("product_uom_qty");
		saleOrderLineFields.addElement("price_unit");
		saleOrderLineFields.addElement("discount");
		saleOrderLineFields.addElement("price_subtotal");

		Object[] saleOrderLineArgs = { db, user_id, user_password, "sale.order.line", "read",saleOrderLineids, saleOrderLineFields };
		Object resSaleOrderLine = null;
		try{
			resSaleOrderLine =  rpcClientObj.callEx("execute", saleOrderLineArgs);
		}catch (XMLRPCException e) {
			Log.i(TAG, "XMLRPCException: "+e);
			e.printStackTrace();
			return -1;
		}

		//Insert into openandroidDB.sqlite
		Object[] resSaleOrderLineOpObj = (Object[]) resSaleOrderLine;
		try{
			for(int i = 0 ; i < resSaleOrderLineOpObj.length ; i++) {
				HashMap resSaleOrderLineOpObjHM= (HashMap) resSaleOrderLineOpObj[i];

				Object[] opOrder = (Object[]) resSaleOrderLineOpObjHM.get("order_id");
				System.out.println("opOrder : " + opOrder);
				System.out.println("opOrder : " + opOrder[0].toString() + " , " + opOrder[1].toString());
				SaleOrder order = DatabaseManager.getInstance().getSaleOrderWithIdPostgres((Integer)opOrder[0]);

				Object[] opProduct = (Object[]) resSaleOrderLineOpObjHM.get("product_id");
				System.out.println("opProduct : " + opProduct[0].toString() + " , " + opProduct[1].toString());
				Product produit = DatabaseManager.getInstance().getProductWithIdPostgres((Integer)opProduct[0]);

				Object[] opProductUom = (Object[]) resSaleOrderLineOpObjHM.get("product_uom");
				System.out.println("opProductUom : " + opProductUom[0].toString() + " , " + opProductUom[1].toString());
				ProductUom productUom = DatabaseManager.getInstance().getProductUomWithIdPostgres((Integer)opProductUom[0]);

				SaleOrderLine saleOrderLine= new SaleOrderLine((Integer)resSaleOrderLineOpObjHM.get("id"),
						resSaleOrderLineOpObjHM.get("name").toString(),
						order,
						produit,
						productUom,
						(Double)resSaleOrderLineOpObjHM.get("product_uom_qty"),
						(Double)resSaleOrderLineOpObjHM.get("price_unit"),
						(Double)resSaleOrderLineOpObjHM.get("discount"),
						(Double)resSaleOrderLineOpObjHM.get("price_subtotal")
						);
				DatabaseManager.getInstance().addSaleOrderLine(saleOrderLine);
			}

		}catch (SQLException e) {
			Log.i(TAG, "SQLException:"+e);
			e.printStackTrace();
			return -2;
		}

		Vector<String> dateline = new Vector<String>();
		dateline.addElement("id");
		dateline.addElement("create_date");
		dateline.addElement("write_date");
		Object[] saleLineDate = { db, user_id,user_password, "sale.order.line", "read", saleOrderLineids, dateline};
		Object saleLineResDate = null;

		try{
			saleLineResDate =  rpcClientObj.callEx("execute_for_android", saleLineDate);
		}catch (XMLRPCException e) {
			Log.i(TAG, "XMLRPCException: "+e);
			e.printStackTrace();
			return -1;
		}
		Object[] resSaleLineDateOpObj = (Object[]) saleLineResDate;

		try{
			for(int i = 0 ; i < resSaleLineDateOpObj.length ; i++) {
				HashMap resSaleLineDateOpObjHM= (HashMap) resSaleLineDateOpObj[i];
				createDateOp = resSaleLineDateOpObjHM.get("create_date").toString();
				writeDateOp = resSaleLineDateOpObjHM.get("write_date").toString();
				if  (createDateOp.equals("false")==false)
					createDateOp=createDateOp.substring(0, 19);

				if  (writeDateOp.equals("false")==false)
					writeDateOp=writeDateOp.substring(0, 19);

				SaleOrderLine saleLineUp=DatabaseManager.getInstance().getSaleOrderLineWithIdPostgres((Integer)resSaleLineDateOpObjHM.get("id"));

				if (saleLineUp!=  null){
					saleLineUp.setCreateDate(createDateOp);
					saleLineUp.setWriteDate(writeDateOp);
					DatabaseManager.getInstance().updateSaleOrderLine(saleLineUp);
				}

			}
		}catch (SQLException e) {
			Log.i(TAG, "SQLException:"+e);
			e.printStackTrace();
			return -2;
		}


		//	    ___________________________ProducTaxes____________________
		List <Integer> producTemplateids=idsList(db, user_id, user_password,"product.template",noFields,"search");
		Vector<String> producTemplateFields = new Vector<String>();
		producTemplateFields.addElement("id");
		producTemplateFields.addElement("taxes_id");
		Object[] producTemplateArgs = { db, user_id, user_password, "product.template", "read",producTemplateids, producTemplateFields };
		Object resproducTemplate= null;

		try{
			resproducTemplate =  rpcClientObj.callEx("execute", producTemplateArgs);
		}catch (XMLRPCException e) {
			Log.i(TAG, "XMLRPCException: "+e);
			e.printStackTrace();
			return -1;
		}

		//      Insert into openandroidDB.sqlite
		Object[] resproducTemplateOpObj = (Object[]) resproducTemplate;
		try{

			for(int i = 0 ; i < resproducTemplateOpObj.length ; i++) {

				HashMap resproducTemplateOpObjHM= (HashMap) resproducTemplateOpObj[i];
				Product prod = DatabaseManager.getInstance().getProductWithIdPostgres((Integer)resproducTemplateOpObjHM.get("id"));
				//               for Tax amount				    
				Object[] opTax = (Object[])resproducTemplateOpObjHM.get("taxes_id");
				Vector<String> taxFields = new Vector<String>();
				taxFields.addElement("amount");
				taxFields.addElement("id");

				if (opTax != null && opTax.length==1){
					Object[] taxArg = { db, user_id,user_password, "account.tax", "read", (Integer)opTax[0], taxFields };
					HashMap taxRes = null;
					try{
						taxRes = (HashMap) rpcClientObj.callEx("execute", taxArg);
					}catch (XMLRPCException e) {
						Log.i(TAG, "XMLRPCException: "+e);
						e.printStackTrace();
						return -1;
					}
					ProductTaxes producTaxes= new ProductTaxes(prod,(Double)taxRes.get("amount"),(Integer)opTax[0]);
					DatabaseManager.getInstance().addProducTaxes(producTaxes);




				}else if (opTax != null && opTax.length>1){
					System.out.println("opTax : " + opTax[0].toString() + " , " + opTax[1].toString());
					List <Integer> taxProdIds= new ArrayList<Integer>();
					for(int j = 0 ; j < opTax.length ; j++) {
						taxProdIds.add((Integer)opTax[j]);
					}
					Object[] taxArg = {db, user_id, user_password, "account.tax", "read", taxProdIds, taxFields };
					Object taxRes = null;

					try{
						taxRes = rpcClientObj.callEx("execute", taxArg);
					}catch (XMLRPCException e) {
						Log.i(TAG, "XMLRPCException: "+e);
						e.printStackTrace();
						return -1;
					}
					Object[] taxResOpObj = (Object[]) taxRes;
					for(int k = 0 ; k < taxResOpObj.length ; k++) {
						HashMap taxResOpObjHM= (HashMap) taxResOpObj[k];

						ProductTaxes producTaxes= new ProductTaxes(prod,(Double)taxResOpObjHM.get("amount"),(Integer)taxResOpObjHM.get("id"));
						DatabaseManager.getInstance().addProducTaxes(producTaxes);
					}
				}


			}



		}catch (SQLException e) {
			Log.i(TAG, "SQLException:"+e);
			e.printStackTrace();
			return -2;
		}


		return 1;

	}




}
