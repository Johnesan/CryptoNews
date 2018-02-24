package com.princess.android.cryptonews.model.ccn;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class WpTermItemItem{

	@SerializedName("_links")
	private Links links;

	@SerializedName("link")
	private String link;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("taxonomy")
	private String taxonomy;

	@SerializedName("slug")
	private String slug;

	public void setLinks(Links links){
		this.links = links;
	}

	public Links getLinks(){
		return links;
	}

	public void setLink(String link){
		this.link = link;
	}

	public String getLink(){
		return link;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setTaxonomy(String taxonomy){
		this.taxonomy = taxonomy;
	}

	public String getTaxonomy(){
		return taxonomy;
	}

	public void setSlug(String slug){
		this.slug = slug;
	}

	public String getSlug(){
		return slug;
	}

	@Override
 	public String toString(){
		return 
			"WpTermItemItem{" + 
			"_links = '" + links + '\'' + 
			",link = '" + link + '\'' + 
			",name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			",taxonomy = '" + taxonomy + '\'' + 
			",slug = '" + slug + '\'' + 
			"}";
		}
}