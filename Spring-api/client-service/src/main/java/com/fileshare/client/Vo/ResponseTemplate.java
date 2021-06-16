package com.fileshare.client.Vo;

import com.fileshare.client.model.Client;

import java.util.ArrayList;
import java.util.List;

public class ResponseTemplate {
    private Long id;
    private String file_tree;
    private String client_name;
    private List<Long> filepools;

    public ResponseTemplate(Client client, List<Long> filepools) {
        this.id = client.getId();
        this.file_tree = client.getFile_tree();
        this.client_name = client.getClient_name();
        this.filepools = filepools;
    }
    public ResponseTemplate(Client client) {
        this.id = client.getId();
        this.file_tree = client.getFile_tree();
        this.client_name = client.getClient_name();
        this.filepools = new ArrayList<Long>();
    }

    public ResponseTemplate() {
        this.filepools = new ArrayList<Long>();
    }

    public Client getClient() {
        return new Client(this.id,this.file_tree,this.client_name);
    }

    public void setClient(Client client) {
        this.id = client.getId();
        this.file_tree = client.getFile_tree();
        this.client_name = client.getClient_name();
    }

    public List<Long> getFilepools() {
        return filepools;
    }

    public void setFilepools(List<Long> filepools) {
        this.filepools = filepools;
    }
    public void addFilepool(Long id){
        this.filepools.add(id);
    }
}
