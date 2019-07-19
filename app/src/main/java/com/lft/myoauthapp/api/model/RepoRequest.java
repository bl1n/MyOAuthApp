package com.lft.myoauthapp.api.model;

import com.google.gson.annotations.SerializedName;

public class RepoRequest {

    @SerializedName("name")
    private String mName;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("homepage")
    private String mHomepage;
    @SerializedName("private")
    private boolean mPrivateX;
    @SerializedName("has_issues")
    private boolean mHasIssues;
    @SerializedName("has_projects")
    private boolean mHasProjects;
    @SerializedName("has_wiki")
    private boolean mHasWiki;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getHomepage() {
        return mHomepage;
    }

    public void setHomepage(String homepage) {
        mHomepage = homepage;
    }

    public boolean isPrivateX() {
        return mPrivateX;
    }

    public void setPrivateX(boolean privateX) {
        mPrivateX = privateX;
    }

    public boolean isHasIssues() {
        return mHasIssues;
    }

    public void setHasIssues(boolean hasIssues) {
        mHasIssues = hasIssues;
    }

    public boolean isHasProjects() {
        return mHasProjects;
    }

    public void setHasProjects(boolean hasProjects) {
        mHasProjects = hasProjects;
    }

    public boolean isHasWiki() {
        return mHasWiki;
    }

    public void setHasWiki(boolean hasWiki) {
        mHasWiki = hasWiki;
    }
}
