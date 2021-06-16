package com.fileshare.client.Vo;

import java.util.ArrayList;
import java.util.List;

public class FilepoolList {

    private List<Filepool> filepools;

    public FilepoolList() {
        this.filepools = new ArrayList<>();
    }

    public FilepoolList(List<Filepool> filepools) {
        this.filepools = filepools;
    }

    public List<Filepool> getFilepools() {
        return filepools;
    }

    public void setFilepools(List<Filepool> filepools) {
        this.filepools = filepools;
    }
    public int size(){
        return this.filepools.size();
    }
    public Filepool get(int i){
        return this.filepools.get(i);
    }

}
