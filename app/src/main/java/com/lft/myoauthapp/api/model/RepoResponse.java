package com.lft.myoauthapp.api.model;

import com.google.gson.annotations.SerializedName;

public class RepoResponse {

    /**
     * id : 1296269
     * name : Hello-World
     * html_url : https://github.com/octocat/Hello-World
     */

    @SerializedName("id")
    private int mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("html_url")
    private String mHtmlUrl;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getHtmlUrl() {
        return mHtmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        mHtmlUrl = htmlUrl;
    }
}
