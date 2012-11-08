package com.openerp.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.util.Log;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class SaleOrder {
	@DatabaseField(generatedId=true)
	private int id;
	@DatabaseField
	private String name;
	@DatabaseField
	private String state;
	@DatabaseField
	private Date dateOrder;
	@DatabaseField
	private Double amounTax;
	@DatabaseField(foreign=true,foreignAutoRefresh=true)
	private ResPartner resPartner;
	@DatabaseField
	private int idPostgres;
	@DatabaseField
	private String createDate;
	@DatabaseField
	private String writeDate;
	@ForeignCollectionField
	private ForeignCollection<SaleOrderLine> lines;

	private static final String TAG = "SaleOrder class";
	
	public SaleOrder(){}
	
	public SaleOrder(String name, String state, Date dateOrder,
			Double amounTax, ResPartner resPartner, int idPostgres) {
		Log.i(TAG, "______________________ IN first ResPartner Constructor  ______________________");
		this.name = name;
		this.state = state;
		this.dateOrder = dateOrder;
		this.amounTax = amounTax;
		this.resPartner = resPartner;
		this.idPostgres = idPostgres;
	}
	
	public SaleOrder(String name, String state, Date dateOrder,
			Double amounTax, int idPostgres) {
		Log.i(TAG, "______________________ IN second ResPartner Constructor  ______________________");
		this.name = name;
		this.state = state;
		this.dateOrder = dateOrder;
		this.amounTax = amounTax;
		this.idPostgres = idPostgres;
	}
	
	public SaleOrder(String name, String state, Date dateOrder,
			Double amounTax, ResPartner resPartner, int idPostgres, String createDate, String writeDate) {
		Log.i(TAG, "______________________ IN first ResPartner Constructor  ______________________");
		this.name = name;
		this.state = state;
		this.dateOrder = dateOrder;
		this.amounTax = amounTax;
		this.resPartner = resPartner;
		this.idPostgres = idPostgres;
		this.createDate=createDate;
		this.writeDate=writeDate;
	}
	
	public SaleOrder(String name, String state, Date dateOrder,
			Double amounTax, int idPostgres, String createDate, String writeDate) {
		Log.i(TAG, "______________________ IN second ResPartner Constructor  ______________________");
		this.name = name;
		this.state = state;
		this.dateOrder = dateOrder;
		this.amounTax = amounTax;
		this.idPostgres = idPostgres;
		this.createDate=createDate;
		this.writeDate=writeDate;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getDateOrder() {
		return dateOrder;
	}

	public void setDateOrder(Date dateOrder) {
		this.dateOrder = dateOrder;
	}

	public Double getAmounTax() {
		return amounTax;
	}

	public void setAmounTax(Double amounTax) {
		this.amounTax = amounTax;
	}

	public ResPartner getResPartner() {
		return resPartner;
	}

	public void setResPartner(ResPartner resPartner) {
		this.resPartner = resPartner;
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
	
	public void setLines(ForeignCollection<SaleOrderLine> lines) {
		this.lines = lines;
	}

	public List<SaleOrderLine> getLines() {
		ArrayList<SaleOrderLine> lineList = new ArrayList<SaleOrderLine>();
		for (SaleOrderLine ln : lines) {
			lineList.add(ln);
		}
		return lineList;
	}
	/*  
	 *  toString
	*/
	@Override
	public String toString() {
		return "SaleOrder [id=" + id + ", name=" + name + ", state=" + state
				+ ", dateOrder=" + dateOrder + ", idPostgres=" + idPostgres
				+ "]";
	}
	
	
	
	
	
	
	
	
	
	
	
}
