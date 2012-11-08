package com.openerp.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable
public class ProductUom {
	
	@DatabaseField(generatedId=true)
	private int id;
	@DatabaseField
	private String name;
	@DatabaseField
	private int idPostgres;
	@DatabaseField
	private String createDate;
	@DatabaseField
	private String writeDate;
	
	
	public ProductUom(){}
	
	public ProductUom(String name,  int idPostgres) {
		this.name = name;
		this.idPostgres = idPostgres;
	}
	
	public ProductUom(String name,  int idPostgres, String createDate, String writeDate) {
		this.name = name;
		this.idPostgres = idPostgres;
		this.createDate=createDate;
		this.writeDate=writeDate;
	}
	public ProductUom(String name, String createDate, String writeDate) {
		this.name = name;
		this.createDate=createDate;
		this.writeDate=writeDate;
	}
	
	/*  
	 *  Getters and setters
	*/
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getIdPostgres() {
		return idPostgres;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
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

	@Override
	public String toString() {
		return "ProductUom [id=" + id + ", name=" + name + ", idPostgres="
				+ idPostgres + "]";
	}

}
