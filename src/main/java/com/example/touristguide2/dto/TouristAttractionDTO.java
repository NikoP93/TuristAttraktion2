package com.example.touristguide2.dto;

import java.util.List;

public class TouristAttractionDTO {

    private String name;
    private String description;
    private String city;
    private List<TagDTO> taglistDTO;

    public TouristAttractionDTO(String name, String description, String city, List<TagDTO> taglistDTO){
        this.name = name;
        this.description = description;
        this.city = city;
        this.taglistDTO = taglistDTO;
    }

    public void addTag(TagDTO tagDTO){
        taglistDTO.add(tagDTO);
    }
}
