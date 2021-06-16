package com.fileshare.client.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user_client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "file_tree",nullable = false)
    private String file_tree;
    @Column(name="client_name",nullable = false)
    private String client_name;

    public Client(Long id, String file_tree, String client_name) {
        this.id = id;
        this.file_tree = file_tree;
        this.client_name = client_name;
    }
    public Client(){}

    public Long getId() {
        return id;
    }

    public String getFile_tree() {
        return file_tree;
    }

    public void setFile_tree(String file_tree) {
        this.file_tree = file_tree;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }
}
