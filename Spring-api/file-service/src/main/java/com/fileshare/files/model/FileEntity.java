package com.fileshare.files.model;

import javax.persistence.*;

@Entity
@Table(name="fs_file")
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String file_path;
    @Column(nullable = false)
    private String display_name;
    @Column(nullable = false)
    private int year;
    @Column(nullable = false)
    private Long filepool;

    public FileEntity(Long id, String file_path, String display_name, int year, Long filepool) {
        this.id = id;
        this.file_path = file_path;
        this.display_name = display_name;
        this.year = year;
        this.filepool = filepool;
    }
    public FileEntity(){}
    public FileEntity(String display_name, int year, Long filepool){
        this.display_name = display_name;
        this.year = year;
        this.filepool = filepool;
    }
    public Boolean notNull(){
        return (this.display_name!= null && this.year> 0 && this.filepool!= null);
    }
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Long getId() {
        return id;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public Long getFilepool() {
        return filepool;
    }

    public void setFilepool(Long filepool) {
        this.filepool = filepool;
    }
}
