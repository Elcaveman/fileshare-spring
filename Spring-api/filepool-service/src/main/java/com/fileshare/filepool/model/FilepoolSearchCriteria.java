package com.fileshare.filepool.model;

public class FilepoolSearchCriteria {
    private String owner_id;
    private String path;

    public String getOwnerId() {
        return owner_id;
    }

    public void setOwnerId(String owner_id) {
        this.owner_id = owner_id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
