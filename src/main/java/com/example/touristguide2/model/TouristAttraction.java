package com.example.touristguide2.model;


import java.util.List;

public class TouristAttraction {

    private String name;
    private String description;
    private String by;
    private List<String> tagList;


    public TouristAttraction() {

    }

    public TouristAttraction(String name, String description, String by, List<String> tagList) {
        this.name = name;
        this.description = description;
        this.by = by;
        this.tagList = tagList;

    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List getTagList() {
        return tagList;
    }

    public String getBy() {
        return by;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public void setTagList(List<String> tagList){
        this.tagList = tagList;
    }
}
