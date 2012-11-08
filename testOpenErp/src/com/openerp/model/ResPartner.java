package com.openerp.model;

import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;


public class ResPartner {
	@DatabaseField(generatedId=true)
	private int id;
	@DatabaseField
	private String name;
	@DatabaseField
	private String website;
	@DatabaseField
	private String ref;
	@ForeignCollectionField
	private ForeignCollection<ResPartnerAddress> address;
	@DatabaseField
	private int idPostgres;
	@DatabaseField
	private String createDate;
	@DatabaseField
	private String writeDate;
	
	
	public ResPartner(){}
	
	public ResPartner (String name, String website,  String ref){
		this.name=name;
		this.website=website;
		this.ref=ref;
	}
	public ResPartner (String name, String website){
		this.name=name;
		this.website=website;
	}
	public ResPartner (String name, String website,  String ref,int idPostgres){
		this.name=name;
		this.website=website;
		this.ref=ref;
		this.idPostgres=idPostgres;
	}
	
	public ResPartner (String name, String website,  String ref,int idPostgres, String createDate, String writeDate){
		this.name=name;
		this.website=website;
		this.ref=ref;
		this.idPostgres=idPostgres;
		this.createDate=createDate;
		this.writeDate=writeDate;
	}
	
	public ResPartner (String name, String website,  String ref, String createDate, String writeDate){
		this.name=name;
		this.website=website;
		this.ref=ref;
		this.createDate=createDate;
		this.writeDate=writeDate;
	}
	/*  
	 *  Getters and setters
	*/
	public int getId(){
		return id;
	}
	
	public int getIdPostgres(){
		return idPostgres;
	}
	
	public String getName(){
		return name;
	}
	
	public String getWebsite(){
		return website;
	}
	
	public String getRef(){
		return ref;
	}
	
	
	public String getCreateDate(){
		return createDate;
	}
	
	public String getWriteDate(){
		return writeDate;
	}
	
	public void setId(int id){
		this.id=id;
	}
	
	public void setIdPostgres(int idPostgres){
		this.idPostgres=idPostgres;
	}

	public void setName(String name){
		this.name=name;
	}
	
	public void setWebsite(String website){
		this.website=website;
	}
	
	public void setRef(String ref){
		this.ref=ref;
	}
	
	public void setCreateDate(String createDate){
		this.createDate=createDate;
	}
	
	
	public void setWriteDate(String writeDate){
		this.writeDate=writeDate;
	}
	
	public void setAddress(ForeignCollection<ResPartnerAddress> address) {
		this.address = address;
	}

	public List<ResPartnerAddress> getAddress() {
		ArrayList<ResPartnerAddress> AddressList = new ArrayList<ResPartnerAddress>();
		for (ResPartnerAddress adr : address) {
			AddressList.add(adr);
		}
		return AddressList;
	}
	
	public String toString(){
		return "ID: " + id + ", Name: " + name + ", idPostgres: " + idPostgres;
	}
	
	
	
}
