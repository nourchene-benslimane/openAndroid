package com.openerp.model;

import android.util.Log;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;



@DatabaseTable
public class ProductTaxes {

	@DatabaseField(generatedId=true)
	private int id;
	@DatabaseField(foreign=true,foreignAutoRefresh=true)
	private Product product;
	@DatabaseField
	private int taxId;
	@DatabaseField
	private double taxAmount;
	
	
	private static final String TAG = "ProductTaxes class";
	
	public ProductTaxes(){}
	
	public ProductTaxes(Product product, double taxAmount) {
		Log.i(TAG, "______________________ IN  ProductTaxes Constructor  ______________________");
		this.product = product;
		this.taxAmount = taxAmount;
	}
	
	public ProductTaxes(Product product, double taxAmount, int taxId) {
		Log.i(TAG, "______________________ IN  ProductTaxes Constructor  ______________________");
		this.product = product;
		this.taxAmount = taxAmount;
		this.taxId=taxId;
	}
	
	
	/*  
	 *  Getters and setters
	*/
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public double getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(double taxAmount) {
		this.taxAmount = taxAmount;
	}

	public int getTaxId() {
		return taxId;
	}

	public void setTaxId(int taxId) {
		this.taxId= taxId;
	}
	
	
	/*  
	 *  toString
	*/
	@Override
	public String toString() {
		return "ProducTaxes [id=" + id + ", product=" + product
				+ ", taxAmount=" + taxAmount + "]";
	}
	
	
}
