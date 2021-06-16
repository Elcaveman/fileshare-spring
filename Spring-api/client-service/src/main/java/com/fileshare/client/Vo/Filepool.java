package com.fileshare.client.Vo;


public class Filepool {
    private Long id;
    private Long owner_id;
    private String path;

    public Filepool(Long id, Long owner_id, String path) {
        this.id = id;
        this.owner_id = owner_id;
        this.path = path;
    }
    public Filepool(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(Long owner_id) {
        this.owner_id = owner_id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
