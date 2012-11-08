package com.openerp.model;

import android.util.Log;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class SaleOrderLine {
	@DatabaseField(generatedId=true)
	private int id;
	@DatabaseField
	private int idPostgres;
	@DatabaseField
	private String name;	
	@DatabaseField(foreign=true,foreignAutoRefresh=true)
	private SaleOrder saleOrder;
	@DatabaseField(foreign=true,foreignAutoRefresh=true)
	private Product product;
	@DatabaseField(foreign=true,foreignAutoRefresh=true)
	private ProductUom productUom;
	@DatabaseField
	private Double productUomQty;	
	@DatabaseField
	private Double priceUnit;	
	@DatabaseField
	private Double discount;	
	@DatabaseField
	private Double priceSubTotal;
	
	@DatabaseField
	private String createDate;
	@DatabaseField
	private String writeDate;
	
	private static final String TAG = "SaleOrderLine class";
	public SaleOrderLine(){}
	
	public SaleOrderLine(int idPostgres, String name, SaleOrder saleOrder,
			Product product, ProductUom productUom, Double productUomQty,
			Double priceUnit, Double discount, Double priceSubTotal) {
		Log.i(TAG, "______________________ IN SaleOrderLine Constructor ______________________");
		this.idPostgres = idPostgres;
		this.name = name;
		this.saleOrder = saleOrder;
		this.product = product;
		this.productUom = productUom;
		this.productUomQty = productUomQty;
		this.priceUnit = priceUnit;
		this.discount = discount;
		this.priceSubTotal = priceSubTotal;
	}
	
	public SaleOrderLine(int idPostgres, String name, SaleOrder saleOrder,
			Product product, ProductUom productUom, Double productUomQty,
			Double priceUnit, Double discount, Double priceSubTotal, String createDate, String writeDate) {
		Log.i(TAG, "______________________ IN SaleOrderLine Constructor ______________________");
		this.idPostgres = idPostgres;
		this.name = name;
		this.saleOrder = saleOrder;
		this.product = product;
		this.productUom = productUom;
		this.productUomQty = productUomQty;
		this.priceUnit = priceUnit;
		this.discount = discount;
		this.priceSubTotal = priceSubTotal;
		this.createDate=createDate;
		this.writeDate=writeDate;
	}
	
//	public SaleOrderLine(int idPostgres, String name, SaleOrder saleOrder,
//			Product product, ProductUom productUom, Double productUomQty,
//			Double priceUnit, Double discount,  String createDate, String writeDate) {
//		Log.i(TAG, "______________________ IN SaleOrderLine Constructor ______________________");
//		this.idPostgres = idPostgres;
//		this.name = name;
//		this.saleOrder = saleOrder;
//		this.product = product;
//		this.productUom = productUom;
//		this.productUomQty = productUomQty;
//		this.priceUnit = priceUnit;
//		this.discount = discount;
//		this.createDate=createDate;
//		this.writeDate=writeDate;
//	}
	
	/*  
	 *  Getters and setters
	*/
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdPostgres() {
		return idPostgres;
	}

	public void setIdPostgres(int idPostgres) {
		this.idPostgres = idPostgres;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SaleOrder getSaleOrder() {
		return saleOrder;
	}

	public void setSaleOrder(SaleOrder saleOrder) {
		this.saleOrder = saleOrder;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public ProductUom getProductUom() {
		return productUom;
	}

	public void setProductUom(ProductUom productUom) {
		this.productUom = productUom;
	}

	public Double getProductUomQty() {
		return productUomQty;
	}

	public void setProductUomQty(Double productUomQty) {
		this.productUomQty = productUomQty;
	}

	public Double getPriceUnit() {
		return priceUnit;
	}

	public void setPriceUnit(Double priceUnit) {
		this.priceUnit = priceUnit;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Double getPriceSubTotal() {
		return priceSubTotal;
	}

	public void setPriceSubTotal(Double priceSubTotal) {
		this.priceSubTotal = priceSubTotal;
	}

	public static String getTag() {
		return TAG;
	}
	public void setCreateDate(String createDate){
		this.createDate=createDate;
	}
	
	
	public void setWriteDate(String writeDate){
		this.writeDate=writeDate;
	}
	public String getCreateDate(){
		return createDate;
	}
	
	public String getWriteDate(){
		return writeDate;
	}
	
	
	/*  
	 *  toString
	*/
	
//	@Override
//	public String toString() {
//		return "SaleOrderLine [id=" + id + ", idPostgres=" + idPostgres
//				+ ", name=" + name + ", saleOrderId=" + saleOrder.getId() +"productId= "+ product.getId()+", ProductUom= "
//				+productUom.getId()+", ProductUomQty=  "+productUomQty+", priceU= "+priceUnit+", discount= "+discount
//				+", priceSubT= "+priceSubTotal+", createDate= "+createDate+"]";
//	}
	
	
	
	@Override
	public String toString() {
		return "SaleOrderLine [id=" + id + ", idPostgres=" + idPostgres
				+ ", name=" + name + ", saleOrder=" + saleOrder + "]";
	}
	
	
	
}
