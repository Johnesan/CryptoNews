package com.princess.android.cryptonews.model.ccn;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class AuthorItem{

	@SerializedName("_links")
	private Links links;

	@SerializedName("name")
	private String name;

	@SerializedName("link")
	private String link;

	@SerializedName("description")
	private String description;

	@SerializedName("id")
	private int id;

	@SerializedName("url")
	private String url;

	@SerializedName("slug")
	private String slug;

	public void setLinks(Links links){
		this.links = links;
	}

	public Links getLinks(){
		return links;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setLink(String link){
		this.link = link;
	}

	public String getLink(){
		return link;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
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
			"AuthorItem{" + 
			"_links = '" + links + '\'' + 
			",name = '" + name + '\'' + 
			",link = '" + link + '\'' + 
			",description = '" + description + '\'' + 
			",id = '" + id + '\'' + 
			",url = '" + url + '\'' + 
			",slug = '" + slug + '\'' + 
			"}";
		}
}