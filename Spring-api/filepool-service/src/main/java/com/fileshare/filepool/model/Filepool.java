package com.fileshare.filepool.model;

import javax.persistence.*;

@Entity
@Table(name = "fs_filepool")
public class Filepool {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "owner_id",nullable = false)
    private Long owner_id;
    private String path;

    public Filepool(){}
    public Filepool(Long owner_id, String path){
        this.owner_id = owner_id;
        this.path = path;
    }
    public Long getId() {
        return id;
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
