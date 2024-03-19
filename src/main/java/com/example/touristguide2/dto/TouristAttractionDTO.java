package com.example.touristguide2.dto;

import com.example.touristguide2.model.TouristAttraction;

import java.util.List;

public class TouristAttractionDTO {

    private String name;
    private String description;
    private String city;
    private List<TagDTO> taglistDTO;


    public TouristAttractionDTO(){

    }

    public TouristAttractionDTO(String name, String description, String city, List<TagDTO> taglistDTO){
        this.name = name;
        this.description = description;
        this.city = city;
        this.taglistDTO = taglistDTO;
    }

    public void addTag(TagDTO tagDTO){
        taglistDTO.add(tagDTO);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<TagDTO> getTaglistDTO() {
        return taglistDTO;
    }

    public void setTaglistDTO(List<TagDTO> taglistDTO) {
        this.taglistDTO = taglistDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof TouristAttractionDTO touristAttractionDTO)) return false;
        if (name == null && touristAttractionDTO.getName() == null) return true;
        return name.equals(touristAttractionDTO.getName());
    }
}
