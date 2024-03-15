package com.example.touristguide2.dto;

public class TagDTO {

    private String tname;


    public TagDTO(String tname){
        this.tname = tname;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }
}
