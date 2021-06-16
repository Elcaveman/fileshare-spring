package com.fileshare.files.vo;

public class FileVO{
    private String display_name;
    private int year;
    private Long filepool;

    public FileVO(String display_name, int year, Long filepool) {
        this.display_name = display_name;
        this.year = year;
        this.filepool = filepool;
    }
    public FileVO(){}
    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Long getFilepool() {
        return filepool;
    }

    public void setFilepool(Long filepool) {
        this.filepool = filepool;
    }
}
