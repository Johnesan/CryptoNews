package com.princess.android.cryptonews.model.ccn;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class WpFeaturedmediaItem{

	@SerializedName("date")
	private String date;

	@SerializedName("_links")
	private Links links;

	@SerializedName("author")
	private int author;

	@SerializedName("link")
	private String link;

	@SerializedName("caption")
	private Caption caption;

	@SerializedName("type")
	private String type;

	@SerializedName("title")
	private Title title;

	@SerializedName("media_details")
	private MediaDetails mediaDetails;

	@SerializedName("source_url")
	private String sourceUrl;

	@SerializedName("alt_text")
	private String altText;

	@SerializedName("media_type")
	private String mediaType;

	@SerializedName("mime_type")
	private String mimeType;

	@SerializedName("id")
	private int id;

	@SerializedName("slug")
	private String slug;

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	public void setLinks(Links links){
		this.links = links;
	}

	public Links getLinks(){
		return links;
	}

	public void setAuthor(int author){
		this.author = author;
	}

	public int getAuthor(){
		return author;
	}

	public void setLink(String link){
		this.link = link;
	}

	public String getLink(){
		return link;
	}

	public void setCaption(Caption caption){
		this.caption = caption;
	}

	public Caption getCaption(){
		return caption;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setTitle(Title title){
		this.title = title;
	}

	public Title getTitle(){
		return title;
	}

	public void setMediaDetails(MediaDetails mediaDetails){
		this.mediaDetails = mediaDetails;
	}

	public MediaDetails getMediaDetails(){
		return mediaDetails;
	}

	public void setSourceUrl(String sourceUrl){
		this.sourceUrl = sourceUrl;
	}

	public String getSourceUrl(){
		return sourceUrl;
	}

	public void setAltText(String altText){
		this.altText = altText;
	}

	public String getAltText(){
		return altText;
	}

	public void setMediaType(String mediaType){
		this.mediaType = mediaType;
	}

	public String getMediaType(){
		return mediaType;
	}

	public void setMimeType(String mimeType){
		this.mimeType = mimeType;
	}

	public String getMimeType(){
		return mimeType;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
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
			"WpFeaturedmediaItem{" + 
			"date = '" + date + '\'' + 
			",_links = '" + links + '\'' + 
			",author = '" + author + '\'' + 
			",link = '" + link + '\'' + 
			",caption = '" + caption + '\'' + 
			",type = '" + type + '\'' + 
			",title = '" + title + '\'' + 
			",media_details = '" + mediaDetails + '\'' + 
			",source_url = '" + sourceUrl + '\'' + 
			",alt_text = '" + altText + '\'' + 
			",media_type = '" + mediaType + '\'' + 
			",mime_type = '" + mimeType + '\'' + 
			",id = '" + id + '\'' + 
			",slug = '" + slug + '\'' + 
			"}";
		}
}