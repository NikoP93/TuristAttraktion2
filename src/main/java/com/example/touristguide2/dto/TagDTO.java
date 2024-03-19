package com.example.touristguide2.dto;

import java.util.Objects;

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


    @Override
    public int hashCode() {
        return Objects.hash(tname);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof TagDTO tagDTO)) return false;
        if (tname == null && tagDTO.getTname() == null) return true;
        return tname.equals(tagDTO.getTname());
    }

    @Override
    public String toString() {
        return tname;
    }
}
