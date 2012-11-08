package com.openerp.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable
public class Product {
	
	@DatabaseField(generatedId=true)
	private int id;
	@DatabaseField
	private String name;
	@DatabaseField
	private String code;
	@DatabaseField
	private String categ_name;
	@DatabaseField
	private double list_price;
	@DatabaseField
	private double qty_available;
	@DatabaseField
	private int idPostgres;
	@DatabaseField
	private String createDate;
	@DatabaseField
	private String writeDate;
	@DatabaseField(foreign=true,foreignAutoRefresh=true)
	private ProductUom productUom;

	private static final String TAG = "Product class";
	
	public Product(){}
	
	public Product(String name, String code, String categ_name,
			double list_price, double qty_available, int idPostgres,ProductUom productUom) {
		this.name = name;
		this.code = code;
		this.categ_name = categ_name;
		this.list_price = list_price;
		this.qty_available = qty_available;
		this.idPostgres = idPostgres;
		this.productUom=productUom;
	}
	
	
	public Product(String name, String code, String categ_name,
			double list_price, double qty_available, int idPostgres, String createDate, String writeDate) {
		this.name = name;
		this.code = code;
		this.categ_name = categ_name;
		this.list_price = list_price;
		this.qty_available = qty_available;
		this.idPostgres = idPostgres;
		this.createDate=createDate;
		this.writeDate=writeDate;
	}
	
	public Product(String name, String code, String categ_name,
			double list_price, int idPostgres, String createDate, String writeDate,ProductUom productUom) {
		this.name = name;
		this.code = code;
		this.categ_name = categ_name;
		this.list_price = list_price;
		this.idPostgres = idPostgres;
		this.createDate=createDate;
		this.writeDate=writeDate;
		this.productUom=productUom;
	}

	
	
	/*  
	 *  Getters and setters
	*/
	
	
	public void setProductUom(ProductUom productUom) {
		this.productUom = productUom;
	}
	
	public ProductUom getProductUom() {
		return productUom;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCateg_name() {
		return categ_name;
	}

	public void setCateg_name(String categ_name) {
		this.categ_name = categ_name;
	}

	public double getList_price() {
		return list_price;
	}

	public void setList_price(double list_price) {
		this.list_price = list_price;
	}

	public double getQty_available() {
		return qty_available;
	}

	public void setQty_available(double qty_available) {
		this.qty_available = qty_available;
	}

	public int getIdPostgres() {
		return idPostgres;
	}

	public void setIdPostgres(int idPostgres) {
		this.idPostgres = idPostgres;
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

	
	public static String getTag() {
		return TAG;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", idPostgres="
				+ idPostgres + "]";
	}
	
	
}
