package com.openerp.model;

import android.util.Log;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class ResPartnerAddress {
	@DatabaseField(generatedId=true)
	private int id;
	@DatabaseField
	private int idPostgres;
	@DatabaseField
	private String name;
	@DatabaseField
	private String  type;
	@DatabaseField
	private String function;
	@DatabaseField
	private String street;
	@DatabaseField
	private String street2;
	@DatabaseField
	private String zip;
	@DatabaseField
	private String city;
	@DatabaseField
	private String email;
	@DatabaseField
	private String phone;
	@DatabaseField
	private String fax;
	@DatabaseField
	private String mobile;
	@DatabaseField
	private String createDate;
	@DatabaseField
	private String writeDate;
	
	@DatabaseField(foreign=true,foreignAutoRefresh=true)
	private ResPartner resPartner;

	private static final String TAG = "ResPartnerAddress class";
	
	public ResPartnerAddress(){}
	
	public ResPartnerAddress (String name, String type,  String function,
			String street, String street2, String zip, String city, String email,
			String phone, String fax, String mobile, ResPartner resPartner,int idPostgres){
		this.name=name;
		this.type= type;
		this.function=function;
		this.street=street;
		this.street2=street2;
		this.zip=zip;
		this.city=city;
		this.email=email;
		this.phone=phone;
		this.fax=fax;
		this.mobile=mobile;
		this.resPartner=resPartner;
		this.idPostgres=idPostgres;
	}
	
	public ResPartnerAddress (String name,  ResPartner resPartner,int idPostgres){
		this.name=name;
		this.resPartner=resPartner;
		this.idPostgres=idPostgres;
	}
	
	public ResPartnerAddress (String name,  int idPostgres){
		this.name=name;
		this.idPostgres=idPostgres;
	}
	
	public ResPartnerAddress (String name, String type,  String function,
			String street, String street2, String zip, String city, String email,
			String phone, String fax, String mobile,int idPostgres){
		Log.i(TAG, "______________________ IN res_partner Constructor ______________________");
		this.name=name;
		this.type= type;
		this.function=function;
		this.street=street;
		this.street2=street2;
		this.zip=zip;
		this.city=city;
		this.email=email;
		this.phone=phone;
		this.fax=fax;
		this.mobile=mobile;
		this.idPostgres=idPostgres;
	}
	
	public ResPartnerAddress (String name, String type,  String function,
			String street, String street2, String zip, String city, String email,
			String phone, String fax, String mobile, ResPartner resPartner,int idPostgres, String createDate, String writeDate){
		Log.i(TAG, "______________________ IN res_partner Constructor ______________________");
		this.name=name;
		this.type= type;
		this.function=function;
		this.street=street;
		this.street2=street2;
		this.zip=zip;
		this.city=city;
		this.email=email;
		this.phone=phone;
		this.fax=fax;
		this.mobile=mobile;
		this.resPartner=resPartner;
		this.idPostgres=idPostgres;
		this.createDate=createDate;
		this.writeDate=writeDate;
	}
	public ResPartnerAddress (String name, String type,  String function,
			String street, String street2, String zip, String city, String email,
			String phone, String fax, String mobile,int idPostgres, String createDate, String writeDate){
		Log.i(TAG, "______________________ IN res_partner Constructor ______________________");
		this.name=name;
		this.type= type;
		this.function=function;
		this.street=street;
		this.street2=street2;
		this.zip=zip;
		this.city=city;
		this.email=email;
		this.phone=phone;
		this.fax=fax;
		this.mobile=mobile;
		this.idPostgres=idPostgres;
		this.createDate=createDate;
		this.writeDate=writeDate;
	}
	
	
	/*  
	 *  Getters and setters
	*/
	
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id=id;
	}
	
	public int getIdPostgres(){
		return idPostgres;
	}
	
	public void setIdPostgres(int idPostgres){
		this.idPostgres=idPostgres;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getStreet2() {
		return street2;
	}

	public void setStreet2(String street2) {
		this.street2 = street2;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setResPartner(ResPartner resPartner) {
		this.resPartner = resPartner;
	}
	
	public ResPartner getAddress(){
		return resPartner;
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
		return "ResPartnerAddress [id=" + id + ", idPostgres=" + idPostgres
				+ ", name=" + name + ", resPartner=" + resPartner + "]";
	}
	
	
	
	
}
