package com.openerp.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.openerp.model.Product;
import com.openerp.model.ProductTaxes;
import com.openerp.model.ProductUom;
import com.openerp.model.ResPartner;
import com.openerp.model.ResPartnerAddress;
import com.openerp.model.SaleOrder;
import com.openerp.model.SaleOrderLine;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper{
	private static final String DATABASE_NAME = "openandroid001DB.sqlite";
	private static final int DATABASE_VERSION = 1;
	private static final String TAG = "DatabaseHelper class";

	private Dao<ResPartner, Integer> resPartnerDao = null;
	private Dao<ResPartnerAddress, Integer> resPartnerAddressDao = null;
	private Dao<Product, Integer> productDao = null;
	private Dao<ProductUom, Integer> productUomDao = null;
	private Dao<SaleOrder, Integer> saleOrder = null;    //new
	private Dao<SaleOrderLine, Integer> saleOrderLine = null;   //new
	private Dao<ProductTaxes, Integer> productTaxes = null;    //new
	
	
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		super.onCreate(getWritableDatabase()); //new
	}
	
	public void onCreate(SQLiteDatabase database,ConnectionSource connectionSource) {
		try {
			
			//essayer drop
			Log.i("DATABASE"," ** drop table ** ");
			TableUtils.dropTable(connectionSource, ResPartner.class, true); 
			TableUtils.dropTable(connectionSource, ResPartnerAddress.class, true);
			TableUtils.dropTable(connectionSource, Product.class, true); 
			TableUtils.dropTable(connectionSource, ProductUom.class, true);
			//new
			TableUtils.dropTable(connectionSource, SaleOrder.class, true); 
			TableUtils.dropTable(connectionSource, SaleOrderLine.class, true);
			TableUtils.dropTable(connectionSource, ProductTaxes.class, true);
			
			Log.i("DATABASE"," ** create table ** ");
			TableUtils.createTable(connectionSource, ResPartner.class);
			TableUtils.createTable(connectionSource, ResPartnerAddress.class);
			TableUtils.createTable(connectionSource, Product.class);
			TableUtils.createTable(connectionSource, ProductUom.class);
			//new
			TableUtils.createTable(connectionSource, SaleOrder.class);
			TableUtils.createTable(connectionSource, SaleOrderLine.class);
			TableUtils.createTable(connectionSource, ProductTaxes.class);
			
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
			throw new RuntimeException(e);
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void onUpgrade(SQLiteDatabase db,ConnectionSource connectionSource, int oldVersion, int newVersion) {
		try {
			TableUtils.dropTable(connectionSource, ResPartner.class, true);  
			TableUtils.dropTable(connectionSource, ResPartnerAddress.class, true);  
			TableUtils.dropTable(connectionSource, Product.class, true); 
			TableUtils.dropTable(connectionSource, ProductUom.class, true); 
			//new
			TableUtils.dropTable(connectionSource, SaleOrder.class, true); 
			TableUtils.dropTable(connectionSource, SaleOrderLine.class, true);
			TableUtils.dropTable(connectionSource, ProductTaxes.class, true);
			
			
			TableUtils.createTable(connectionSource, ResPartner.class);
			TableUtils.createTable(connectionSource, ResPartnerAddress.class);
			TableUtils.createTable(connectionSource, Product.class);
			TableUtils.createTable(connectionSource, ProductUom.class);
			//new
			TableUtils.createTable(connectionSource, SaleOrder.class);
			TableUtils.createTable(connectionSource, SaleOrderLine.class);
			TableUtils.createTable(connectionSource, ProductTaxes.class);
			
	        onCreate(db);  
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "exception during onUpgrade", e);
			throw new RuntimeException(e);
		}catch (java.sql.SQLException e) {
			e.printStackTrace();
		}
	}
		
	public Dao<ResPartner, Integer> getResPartnerDao() {
		if (null == resPartnerDao) {
			try {
				Log.i(TAG, "getResPartnerDao case null");
				resPartnerDao = getDao(ResPartner.class);
				Log.i(TAG, "getResPartnerDao getDao ok");
			}catch (java.sql.SQLException e) {
				e.printStackTrace();
			}
		}
		return resPartnerDao;
	}
	
	public Dao<ResPartnerAddress, Integer> getResPartnerAddressDao() {
		if (null == resPartnerAddressDao) {
			try {
				resPartnerAddressDao = getDao(ResPartnerAddress.class);
			}catch (java.sql.SQLException e) {
				e.printStackTrace();
			}
		}
		return resPartnerAddressDao;
	}
	
	public Dao<Product, Integer> getProductDao() {
		if (null == productDao) {
			try {
				productDao = getDao(Product.class);
			}catch (java.sql.SQLException e) {
				e.printStackTrace();
			}
		}
		return productDao;
	}
	
	public Dao<ProductUom, Integer> getProductUomDao() {
		if (null == productUomDao) {
			try {
				productUomDao = getDao(ProductUom.class);
			}catch (java.sql.SQLException e) {
				e.printStackTrace();
			}
		}
		return productUomDao;
	}
	
	//new
	public Dao<SaleOrder, Integer> getSaleOrderDao() {
		if (null == saleOrder) {
			try {
				saleOrder = getDao(SaleOrder.class);
			}catch (java.sql.SQLException e) {
				e.printStackTrace();
			}
		}
		return saleOrder;
	}
	
	public Dao<SaleOrderLine, Integer> getSaleOrderLineDao() {
		if (null == saleOrderLine) {
			try {
				saleOrderLine = getDao(SaleOrderLine.class);
			}catch (java.sql.SQLException e) {
				e.printStackTrace();
			}
		}
		return saleOrderLine;
	}
	
	public Dao<ProductTaxes, Integer> getProducTaxesDao() {
		if (null == productTaxes) {
			try {
				productTaxes = getDao(ProductTaxes.class);
			}catch (java.sql.SQLException e) {
				e.printStackTrace();
			}
		}
		return productTaxes;
	}
	
	
	
}
