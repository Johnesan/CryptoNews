
package com.princess.android.cryptonews.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class _links {

    @SerializedName("about")
    private List<About> mAbout;
    @SerializedName("author")
    private List<Author> mAuthor;
    @SerializedName("collection")
    private List<Collection> mCollection;
    @SerializedName("curies")
    private List<Cury> mCuries;
    @SerializedName("replies")
    private List<Reply> mReplies;
    @SerializedName("self")
    private List<Self> mSelf;
    @SerializedName("version-history")
    private List<VersionHistory> mVersionHistory;
    @SerializedName("wp:attachment")
    private List<WpAttachment> mWpAttachment;
    @SerializedName("wp:featuredmedia")
    private List<WpFeaturedmedium> mWpFeaturedmedia;
    @SerializedName("wp:term")
    private List<WpTerm> mWpTerm;

    public List<About> getAbout() {
        return mAbout;
    }

    public void setAbout(List<About> about) {
        mAbout = about;
    }

    public List<Author> getAuthor() {
        return mAuthor;
    }

    public void setAuthor(List<Author> author) {
        mAuthor = author;
    }

    public List<Collection> getCollection() {
        return mCollection;
    }

    public void setCollection(List<Collection> collection) {
        mCollection = collection;
    }

    public List<Cury> getCuries() {
        return mCuries;
    }

    public void setCuries(List<Cury> curies) {
        mCuries = curies;
    }

    public List<Reply> getReplies() {
        return mReplies;
    }

    public void setReplies(List<Reply> replies) {
        mReplies = replies;
    }

    public List<Self> getSelf() {
        return mSelf;
    }

    public void setSelf(List<Self> self) {
        mSelf = self;
    }

    public List<VersionHistory> getVersionHistory() {
        return mVersionHistory;
    }

    public void setVersionHistory(List<VersionHistory> versionHistory) {
        mVersionHistory = versionHistory;
    }

    public List<WpAttachment> getWpAttachment() {
        return mWpAttachment;
    }

    public void setWpAttachment(List<WpAttachment> wpAttachment) {
        mWpAttachment = wpAttachment;
    }

    public List<WpFeaturedmedium> getWpFeaturedmedia() {
        return mWpFeaturedmedia;
    }

    public void setWpFeaturedmedia(List<WpFeaturedmedium> wpFeaturedmedia) {
        mWpFeaturedmedia = wpFeaturedmedia;
    }

    public List<WpTerm> getWpTerm() {
        return mWpTerm;
    }

    public void setWpTerm(List<WpTerm> wpTerm) {
        mWpTerm = wpTerm;
    }

}
