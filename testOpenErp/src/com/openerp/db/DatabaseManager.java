package com.openerp.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.openerp.model.Product;
import com.openerp.model.ProductTaxes;
import com.openerp.model.ProductUom;
import com.openerp.model.ResPartner;
import com.openerp.model.ResPartnerAddress;
import com.openerp.model.SaleOrder;
import com.openerp.model.SaleOrderLine;


public class DatabaseManager {
	static private DatabaseManager instance;
	private static DatabaseHelper helper;

	private DatabaseManager(Context ctx) {
		helper = new DatabaseHelper(ctx);
	}

	private static DatabaseHelper getHelper() {
		return helper;
	}
	
	static public void init(Context ctx) {
		if (null==instance) {
			instance = new DatabaseManager(ctx);
		}
	}
	
	static public DatabaseManager getInstance() {
		return instance;
	}
	
	
//resPartner related functions

	public List<ResPartner> getAllResPartner() {
		List<ResPartner> resPartners = null;
		try {
			resPartners = getHelper().getResPartnerDao().queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resPartners;
	}
	
	public void addResPartner(ResPartner resPartner) {
		try {
			getHelper().getResPartnerDao().create(resPartner);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateResPartner(ResPartner resPartner) {
		try {
			int x=getHelper().getResPartnerDao().update(resPartner);
			Log.i("updateResPartner", "x: "+x);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ResPartner getResPartnerWithId(int resPartnerId) {
		ResPartner resPartner = null;
		try {
			resPartner = getHelper().getResPartnerDao().queryForId(resPartnerId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resPartner;
	}
	
	public ResPartner getResPartnerWithIdPostgres(int resPartnerIdPostgres)
	{
		ResPartner resPartner = null;
		try {
			QueryBuilder<ResPartner, Integer> qb = getHelper().getResPartnerDao().queryBuilder();
			qb.where().eq("idPostgres", resPartnerIdPostgres);
			PreparedQuery<ResPartner> pq = qb.prepare();
			resPartner =getHelper().getResPartnerDao().queryForFirst(pq);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return  resPartner;
	}
	
	public void refreshResPartner(ResPartner resPartner) {
		try {
			getHelper().getResPartnerDao().refresh(resPartner);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteResPartner(ResPartner resPartner) {
		try {
			getHelper().getResPartnerDao().delete(resPartner);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Integer> getAllResPartnerIdPostgres() {
		List<Integer> ids= new ArrayList<Integer>();
		List<ResPartner> resPartners = null;
		try {
			resPartners = getHelper().getResPartnerDao().queryForAll();
			for (ResPartner resPart:resPartners){
				ids.add(resPart.getIdPostgres());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ids;
	}
	
	public List<Object> getResPartnerDataForSynchro() {
		List<ResPartner> resPartners = null;
		List<Object> ls= new ArrayList<Object>();
		List<Object> list= new ArrayList<Object>();

		try {
			Boolean False=false;
			resPartners = getHelper().getResPartnerDao().queryForAll();
			Iterator<ResPartner> iter= resPartners.iterator();
			while (iter.hasNext()) {
				ResPartner res=iter.next();
				Log.i("getResPartnerDataForSynchro", "res.getCreateDate().toString()"+res.getCreateDate().toString());
				Log.i("getResPartnerDataForSynchro", "res.getCreateDate()"+res.getCreateDate());

				ls.add(res.getIdPostgres());
				if (res.getWriteDate()==null){
					ls.add(False);
				}else{
					ls.add(res.getWriteDate());
				}
				list.add(ls);
				ls= new ArrayList<Object>();
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		Log.i("getResPartnerDataForSynchro", "list"+list);
		return list;
	}

	public List<Object> getResPartnerIdsForSynchro() {
		List<ResPartner> resPArtner = null;
		List<Object> ls= new ArrayList<Object>();
		List<Object> list= new ArrayList<Object>();
		
		try {
			resPArtner= getHelper().getResPartnerDao().queryForAll();
			Iterator<ResPartner> iter= resPArtner.iterator();
			while (iter.hasNext()) {
				
				ResPartner partner=iter.next();
				ls.add(partner.getIdPostgres());
				list.add(ls);
				ls= new ArrayList<Object>();
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
//resPartnerAddress related functions
	public void addResPartnerAddress(ResPartnerAddress resPartnerAddress) {
		try {
			getHelper().getResPartnerAddressDao().create(resPartnerAddress);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ResPartnerAddress getResPartnerAddressWithId(int resPartnerAddressId) {
		ResPartnerAddress resPartnerAddress = null;
		try {
			resPartnerAddress = getHelper().getResPartnerAddressDao().queryForId(resPartnerAddressId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resPartnerAddress;
	}
	
	public void updateResPartnerAddress(ResPartnerAddress adr) {
		try {
			getHelper().getResPartnerAddressDao().update(adr);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteResPartnerAddress(ResPartnerAddress resPartnerAddress) {
		try {
			getHelper().getResPartnerAddressDao().delete(resPartnerAddress);
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

	public ResPartnerAddress getResPartnerAddressWithIdPostgres(int resPartnerAddressIdPostgres){
		ResPartnerAddress resPartnerAddress = null;
		try {
			QueryBuilder<ResPartnerAddress, Integer> qb = getHelper().getResPartnerAddressDao().queryBuilder();
			qb.where().eq("idPostgres", resPartnerAddressIdPostgres);
			PreparedQuery<ResPartnerAddress> pq = qb.prepare();
			resPartnerAddress =getHelper().getResPartnerAddressDao().queryForFirst(pq);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return  resPartnerAddress;
	}
	
	public List<Object> getResPartnerAdrDataForSynchro() {
		List<ResPartnerAddress> resPartnersAddress = null;
		List<Object> ls= new ArrayList<Object>();
		List<Object> list= new ArrayList<Object>();
		
		try {
			Boolean False=false;
			resPartnersAddress = getHelper().getResPartnerAddressDao().queryForAll();
			Iterator<ResPartnerAddress> iter= resPartnersAddress.iterator();
				while (iter.hasNext()) {
				
					ResPartnerAddress resAdr=iter.next();
					Log.i("getResPartnerAdrDataForSynchro", "res.getCreateDate().toString()"+resAdr.getCreateDate().toString());
					Log.i("getResPartnerAdrDataForSynchro", "res.getCreateDate()"+resAdr.getCreateDate());
					
					ls.add(resAdr.getIdPostgres());
					if (resAdr.getWriteDate()==null){
						ls.add(False);
					}else{
						ls.add(resAdr.getWriteDate());
					}
					list.add(ls);
					ls= new ArrayList<Object>();
				}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
		
	}
	
	
	
	
	

// Product related functions
	public static List<Product> getAllProduct() {
		List<Product> product = null;
		try {
			product = getHelper().getProductDao().queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return product;
	}
	
	public void addProduct(Product product) {
		try {
			getHelper().getProductDao().create(product);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Product getProductWithId(int productId) {
		Product product = null;
		try {
			product = getHelper().getProductDao().queryForId(productId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return product;
	}
	
	public void refreshProduct(Product product) {
		try {
			getHelper().getProductDao().refresh(product);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteProduct(Product product) {
		try {
			getHelper().getProductDao().delete(product);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Product getProductWithIdPostgres(int productIdPostgres)
	{
		Product product = null;
		try {
			QueryBuilder<Product, Integer> qb = getHelper().getProductDao().queryBuilder();
			qb.where().eq("idPostgres", productIdPostgres);
			PreparedQuery<Product> pq = qb.prepare();
			product =getHelper().getProductDao().queryForFirst(pq);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return  product;
	}
	
	//new
	public List<Object> getProductDataForSynchro() {
		List<Product> product = null;
		List<Object> ls= new ArrayList<Object>();
		List<Object> list= new ArrayList<Object>();
		
		try {
			Boolean False=false;
			product = getHelper().getProductDao().queryForAll();
			Iterator<Product> iter= product.iterator();
			while (iter.hasNext()) {
				Product prod=iter.next();
				ls.add(prod.getIdPostgres());
				if (prod.getWriteDate()==null){
					ls.add(False);
				}else{
					ls.add(prod.getWriteDate());
				}
				list.add(ls);
				ls= new ArrayList<Object>();
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
		
		
	}
	
	public void updateProduct(Product product) {
		try {
			getHelper().getProductDao().update(product);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Object> getProductIdsForSynchro() {
		List<Product> product = null;
		List<Object> ls= new ArrayList<Object>();
		List<Object> list= new ArrayList<Object>();
		try {
			product= getHelper().getProductDao().queryForAll();
			Iterator<Product> iter= product.iterator();
			while (iter.hasNext()) {
				Product prod=iter.next();
				ls.add(prod.getIdPostgres());
				list.add(ls);
				ls= new ArrayList<Object>();
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	
// ProductUom related functions
	public List<ProductUom> getAllProductUom() {
		List<ProductUom> productUom = null;
		try {
			productUom = getHelper().getProductUomDao().queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return productUom;
	}
	
	public void addProductUom(ProductUom productUom) {
		try {
			getHelper().getProductUomDao().create(productUom);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ProductUom getProductUomWithId(int productUomId) {
		ProductUom productUom = null;
		try {
			productUom = getHelper().getProductUomDao().queryForId(productUomId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return productUom;
	}
	
	public ProductUom getProductUomWithIdPostgres(int productUomIdPostgres)
	{
		ProductUom productUom = null;
		try {
			QueryBuilder<ProductUom, Integer> qb = getHelper().getProductUomDao().queryBuilder();
			qb.where().eq("idPostgres", productUomIdPostgres);
			PreparedQuery<ProductUom> pq = qb.prepare();
			productUom =getHelper().getProductUomDao().queryForFirst(pq);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return  productUom;
	}
	
	//new
	public void updateProductUom(ProductUom productUom) {
		try {
			getHelper().getProductUomDao().update(productUom);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Object> getProductUomDataForSynchro() {
		List<ProductUom> productUom = null;
		List<Object> ls= new ArrayList<Object>();
		List<Object> list= new ArrayList<Object>();
		
		try {
			Boolean False=false;
			productUom = getHelper().getProductUomDao().queryForAll();
			Iterator<ProductUom> iter= productUom.iterator();
			while (iter.hasNext()) {
				ProductUom res=iter.next();
				ls.add(res.getIdPostgres());
				if (res.getWriteDate()==null){
					ls.add(False);
				}else{
					ls.add(res.getWriteDate());
				}
				list.add(ls);
				ls= new ArrayList<Object>();
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<Object> getProductUomIdsForSynchro() {
		List<ProductUom> productUom = null;
		List<Object> ls= new ArrayList<Object>();
		List<Object> list= new ArrayList<Object>();
		
		try {
			productUom= getHelper().getProductUomDao().queryForAll();
			Iterator<ProductUom> iter= productUom.iterator();
			while (iter.hasNext()) {
				
				ProductUom prodUom=iter.next();
				ls.add(prodUom.getIdPostgres());
				list.add(ls);
				ls= new ArrayList<Object>();
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public void deleteProductUom(ProductUom productUom) {
		try {
			getHelper().getProductUomDao().delete(productUom);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
//	SaleOrder related functions
	public void addSaleOrder(SaleOrder saleOrder) {
		try {
			getHelper().getSaleOrderDao().create(saleOrder);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public SaleOrder getSaleOrderWithId(int saleOrderId) {
		SaleOrder saleOrder = null;
		try {
			saleOrder = getHelper().getSaleOrderDao().queryForId(saleOrderId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return saleOrder;
	}
	
	public void updateSaleOrder(SaleOrder saleOrder) {
		try {
			getHelper().getSaleOrderDao().update(saleOrder);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteSaleOrder(SaleOrder saleOrder) {
		try {
			getHelper().getSaleOrderDao().delete(saleOrder);
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	public SaleOrder getSaleOrderWithIdPostgres(int saleOrderIdPostgres)
	{
		SaleOrder saleOrder = null;
		try {
			QueryBuilder<SaleOrder, Integer> qb = getHelper().getSaleOrderDao().queryBuilder();
			qb.where().eq("idPostgres", saleOrderIdPostgres);
			PreparedQuery<SaleOrder> pq = qb.prepare();
			saleOrder =getHelper().getSaleOrderDao().queryForFirst(pq);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return  saleOrder;
	}
	
	
	
	  public  List<SaleOrder> getSaleOrdersWithPartnerId(int partnerId) {
		  Log.i("DBM", "______________________ Begin getSaleOrdersWithPartnerId method ______________________");
		  List<SaleOrder> saleOrders = null; 
		  try {
		  QueryBuilder<SaleOrder, Integer> qb = getHelper().getSaleOrderDao().queryBuilder();
		  qb.where().eq("resPartner_id", partnerId); 
		  PreparedQuery<SaleOrder> pq = qb.prepare(); 
		  saleOrders =getHelper().getSaleOrderDao().query(pq);
		  } catch (SQLException e) {
		  e.printStackTrace(); } 
		  Log.i("DBM", "______________________ End getSaleOrdersWithPartnerId method   ______________________");
		  Log.i("DBM", "nb_element= "+saleOrders.size());
		  return  saleOrders; } 
	
	  
	public Boolean hasSaleOrders(int partnerId)
	{
		List <SaleOrder> liste = getSaleOrdersWithPartnerId(partnerId);
		if (liste.isEmpty())
		{
			Log.d("hasSaleOrders", "pas de bons de commande pr ce partner");
			return false;
	
		}
		else {
			Log.d("hasSaleOrders", "au moins un bon de commande pr ce partner");
			return true;
			}
	}
	
	
	
	public List<SaleOrder> getAllSaleOrder() {
		List<SaleOrder> saleOrder = null;
		try {
			saleOrder = getHelper().getSaleOrderDao().queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return saleOrder;
	}
	
	public List<Object> getSaleOrderDataForSynchro() {
		List<SaleOrder> saleOrder = null;
		List<Object> ls= new ArrayList<Object>();
		List<Object> list= new ArrayList<Object>();
		try {
			Boolean False=false;
			saleOrder = getHelper().getSaleOrderDao().queryForAll();
			Iterator<SaleOrder> iter= saleOrder.iterator();
			while (iter.hasNext()) {
				
				SaleOrder prod=iter.next();
				
				ls.add(prod.getIdPostgres());
				if (prod.getWriteDate()==null){
					ls.add(False);
				}else{
					ls.add(prod.getWriteDate());
				}
				list.add(ls);
				ls= new ArrayList<Object>();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<Object> getSaleOrderIdsForSynchro() {
		List<SaleOrder> saleOrder = null;
		List<Object> ls= new ArrayList<Object>();
		List<Object> list= new ArrayList<Object>();
		
		try {
			//Boolean False=false;
			saleOrder = getHelper().getSaleOrderDao().queryForAll();
			Iterator<SaleOrder> iter= saleOrder.iterator();
			while (iter.hasNext()) {
				
				SaleOrder prod=iter.next();
				ls.add(prod.getIdPostgres());
				list.add(ls);
				ls= new ArrayList<Object>();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
//	SaleOrderLine related functions	
	
	public void addSaleOrderLine(SaleOrderLine saleOrderLine) {
		try {
			getHelper().getSaleOrderLineDao().create(saleOrderLine);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public SaleOrderLine getSaleOrderLineWithId(int saleOrderLineId) {
		SaleOrderLine saleOrderLine = null;
		try {
			saleOrderLine = getHelper().getSaleOrderLineDao().queryForId(saleOrderLineId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return saleOrderLine;
	}
	
	public void updateSaleOrderLine(SaleOrderLine saleOrderLine) {
		try {
			getHelper().getSaleOrderLineDao().update(saleOrderLine);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteSaleOrderLine(SaleOrderLine saleOrderLine) {
		try {
			getHelper().getSaleOrderLineDao().delete(saleOrderLine);
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	public SaleOrderLine getSaleOrderLineWithIdPostgres(int saleOrderLineIdPostgres)
	{
		SaleOrderLine saleOrderLine = null;
		try {
			QueryBuilder<SaleOrderLine, Integer> qb = getHelper().getSaleOrderLineDao().queryBuilder();
			qb.where().eq("idPostgres", saleOrderLineIdPostgres);
			PreparedQuery<SaleOrderLine> pq = qb.prepare();
			saleOrderLine =getHelper().getSaleOrderLineDao().queryForFirst(pq);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return  saleOrderLine;
	}
	
	public List<Object> getSaleOrderLineDataForSynchro() {
		List<SaleOrderLine> saleOrderLine = null;
		List<Object> ls= new ArrayList<Object>();
		List<Object> list= new ArrayList<Object>();
		
		try {
			Boolean False=false;
			saleOrderLine = getHelper().getSaleOrderLineDao().queryForAll();
			Iterator<SaleOrderLine> iter= saleOrderLine.iterator();
			
			while (iter.hasNext()) {
				SaleOrderLine prod=iter.next();
				ls.add(prod.getIdPostgres());
				if (prod.getWriteDate()==null){
					ls.add(False);
				}else{
					ls.add(prod.getWriteDate());
				}
				list.add(ls);
				ls= new ArrayList<Object>();
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<Object> getSaleOrderLineIdsForSynchro() {
		List<SaleOrderLine> saleOrderLine = null;
		List<Object> ls= new ArrayList<Object>();
		List<Object> list= new ArrayList<Object>();
		try {
			saleOrderLine = getHelper().getSaleOrderLineDao().queryForAll();
			Iterator<SaleOrderLine> iter= saleOrderLine.iterator();
			while (iter.hasNext()) {
				
				SaleOrderLine line=iter.next();
				ls.add(line.getIdPostgres());
				list.add(ls);
				ls= new ArrayList<Object>();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}


//	ProductTaxes related functions
	public void addProducTaxes(ProductTaxes producTaxes) {
		try {
			getHelper().getProducTaxesDao().create(producTaxes);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ProductTaxes getProducTaxesWithId(int producTaxesId) {
		ProductTaxes producTaxes = null;
		try {
			producTaxes = getHelper().getProducTaxesDao().queryForId(producTaxesId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return producTaxes;
	}
	
	public void updateProducTaxes(ProductTaxes producTaxes) {
		try {
			getHelper().getProducTaxesDao().update(producTaxes);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteProducTaxes(ProductTaxes producTaxes) {
		try {
			getHelper().getProducTaxesDao().delete(producTaxes);
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	public ProductTaxes getProducTaxesWithCouple(Product product,int taxId)
	{
		ProductTaxes producTaxes = null;
		try {
			QueryBuilder<ProductTaxes, Integer> qb = getHelper().getProducTaxesDao().queryBuilder();
			qb.where().eq("product_id", product.getId());
			qb.where().eq("taxId", taxId);
			PreparedQuery<ProductTaxes> pq = qb.prepare();
			producTaxes =getHelper().getProducTaxesDao().queryForFirst(pq);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return  producTaxes;
	}
	
	public static ProductTaxes getProductTaxesWithProductId(int id)
	{
		ProductTaxes producTaxes = null;
		try {
			QueryBuilder<ProductTaxes, Integer> qb = getHelper().getProducTaxesDao().queryBuilder();
			qb.where().eq("product_id", id);
			PreparedQuery<ProductTaxes> pq = qb.prepare();
			producTaxes =getHelper().getProducTaxesDao().queryForFirst(pq);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return  producTaxes;

	}
	public Boolean hasTaxe(int productId){
		ProductTaxes taxe =getProductTaxesWithProductId(productId);
		if (taxe == null)
		{
			Log.d("hasTaxe","pas de taxe sur ce produit");
			return false;
		}
		else{
			Log.d("hasTaxe","il existe une taxe sur ce produit");
			return true;
		}
		
	}
	
	
	public List<Object> getProducTaxesDataForSynchro() {
		List<ProductTaxes> producTaxes = null;
		List<Object> ls= new ArrayList<Object>();
		List<Object> list= new ArrayList<Object>();
		try {
			producTaxes = getHelper().getProducTaxesDao().queryForAll();
			Iterator<ProductTaxes> iter= producTaxes.iterator();
			while (iter.hasNext()) {
				ProductTaxes prodTax=iter.next();
				ls.add(prodTax.getProduct().getIdPostgres());
				ls.add(prodTax.getTaxId());
				list.add(ls);
				ls= new ArrayList<Object>();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	






}
