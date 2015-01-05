package com.esiea.test;

import org.json.JSONException;
import org.json.JSONObject;

public class Biere{
	private String category;
	private String category_id;
	private String created_at;
	private String description;
	private String id;
	private JSONObject image;
	private String name;
	private String note;
	private String updated_at;
	private JSONObject country;
	private String buveur;
	private String note_moyenne;
	private String number_of_notes;
	
	
	public Biere(){

	}
	
	@Override
	public String toString() {
		return ("Catégorie: " + this.category + " & Nom: " + this.name + " & Buveur: " + this.buveur);
	}
	
	
	public String getURLBigImage(){
		JSONObject jsontmp = new JSONObject();
		String urlImage = "";
		try {
			jsontmp = this.image.getJSONObject("image");
			urlImage = jsontmp.getString("url");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ("http://binouze.fabrigli.fr" + urlImage);
		
	}
	
	public String getURLSmallImage(){
		JSONObject jsontmp;
		String urlImage = new String();
		try {
			jsontmp = this.image.getJSONObject("image");
			jsontmp = jsontmp.getJSONObject("thumb");
			urlImage = jsontmp.getString("url");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ("http://binouze.fabrigli.fr" + urlImage);
		
	}
	
	public String getCountryName(){
		try {
			return this.country.getString("name");
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//*****************************************************************
	//GETTERS & SETTERS
	public String getBuveur() {
		return buveur;
	}
	public String getCategory() {
		return category;
	}
	public String getCategory_id() {
		return category_id;
	}
	public JSONObject getCountry() {
		return country;
	}
	public String getCreated_at() {
		return created_at;
	}
	public String getDescription() {
		return description;
	}
	public String getId() {
		return id;
	}
	public JSONObject getImage() {
		return image;
	}
	public String getName() {
		return name;
	}
	public String getNote() {
		return note;
	}
	public String getNote_moyenne() {
		return note_moyenne;
	}
	public String getNumber_of_notes() {
		return number_of_notes;
	}
	public String getUpdated_at() {
		return updated_at;
	}
	public void setBuveur(String buveur) {
		this.buveur = buveur;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}
	public void setCountry(JSONObject country) {
		this.country = country;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setImage(JSONObject image) {
		this.image = image;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public void setNote_moyenne(String note_moyenne) {
		this.note_moyenne = note_moyenne;
	}
	public void setNumber_of_notes(String number_of_notes) {
		this.number_of_notes = number_of_notes;
	}
	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}
	
}

