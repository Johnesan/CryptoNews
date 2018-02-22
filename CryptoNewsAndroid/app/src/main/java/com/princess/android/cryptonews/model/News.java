
package com.princess.android.cryptonews.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class News {

    @SerializedName("author")
    private Long mAuthor;
    @SerializedName("categories")
    private List<Long> mCategories;
    @SerializedName("comment_status")
    private String mCommentStatus;
    @SerializedName("content")
    private Content mContent;
    @SerializedName("date")
    private String mDate;
    @SerializedName("date_gmt")
    private String mDateGmt;
    @SerializedName("excerpt")
    private Excerpt mExcerpt;
    @SerializedName("featured_media")
    private Long mFeaturedMedia;
    @SerializedName("format")
    private String mFormat;
    @SerializedName("guid")
    private Guid mGuid;
    @SerializedName("id")
    private Long mId;
    @SerializedName("link")
    private String mLink;
    @SerializedName("meta")
    private List<Object> mMeta;
    @SerializedName("modified")
    private String mModified;
    @SerializedName("modified_gmt")
    private String mModifiedGmt;
    @SerializedName("ping_status")
    private String mPingStatus;
    @SerializedName("slug")
    private String mSlug;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("sticky")
    private Boolean mSticky;
    @SerializedName("tags")
    private List<Long> mTags;
    @SerializedName("template")
    private String mTemplate;
    @SerializedName("title")
    private Title mTitle;
    @SerializedName("type")
    private String mType;
    @SerializedName("_links")
    private com.princess.android.cryptonews.model._links m_links;

    public Long getAuthor() {
        return mAuthor;
    }

    public void setAuthor(Long author) {
        mAuthor = author;
    }

    public List<Long> getCategories() {
        return mCategories;
    }

    public void setCategories(List<Long> categories) {
        mCategories = categories;
    }

    public String getCommentStatus() {
        return mCommentStatus;
    }

    public void setCommentStatus(String commentStatus) {
        mCommentStatus = commentStatus;
    }

    public Content getContent() {
        return mContent;
    }

    public void setContent(Content content) {
        mContent = content;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getDateGmt() {
        return mDateGmt;
    }

    public void setDateGmt(String dateGmt) {
        mDateGmt = dateGmt;
    }

    public Excerpt getExcerpt() {
        return mExcerpt;
    }

    public void setExcerpt(Excerpt excerpt) {
        mExcerpt = excerpt;
    }

    public Long getFeaturedMedia() {
        return mFeaturedMedia;
    }

    public void setFeaturedMedia(Long featuredMedia) {
        mFeaturedMedia = featuredMedia;
    }

    public String getFormat() {
        return mFormat;
    }

    public void setFormat(String format) {
        mFormat = format;
    }

    public Guid getGuid() {
        return mGuid;
    }

    public void setGuid(Guid guid) {
        mGuid = guid;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getLink() {
        return mLink;
    }

    public void setLink(String link) {
        mLink = link;
    }

    public List<Object> getMeta() {
        return mMeta;
    }

    public void setMeta(List<Object> meta) {
        mMeta = meta;
    }

    public String getModified() {
        return mModified;
    }

    public void setModified(String modified) {
        mModified = modified;
    }

    public String getModifiedGmt() {
        return mModifiedGmt;
    }

    public void setModifiedGmt(String modifiedGmt) {
        mModifiedGmt = modifiedGmt;
    }

    public String getPingStatus() {
        return mPingStatus;
    }

    public void setPingStatus(String pingStatus) {
        mPingStatus = pingStatus;
    }

    public String getSlug() {
        return mSlug;
    }

    public void setSlug(String slug) {
        mSlug = slug;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public Boolean getSticky() {
        return mSticky;
    }

    public void setSticky(Boolean sticky) {
        mSticky = sticky;
    }

    public List<Long> getTags() {
        return mTags;
    }

    public void setTags(List<Long> tags) {
        mTags = tags;
    }

    public String getTemplate() {
        return mTemplate;
    }

    public void setTemplate(String template) {
        mTemplate = template;
    }

    public Title getTitle() {
        return mTitle;
    }

    public void setTitle(Title title) {
        mTitle = title;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public com.princess.android.cryptonews.model._links get_links() {
        return m_links;
    }

    public void set_links(com.princess.android.cryptonews.model._links _links) {
        m_links = _links;
    }

}
