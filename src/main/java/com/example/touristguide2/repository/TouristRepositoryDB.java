package com.example.touristguide2.repository;

import com.example.touristguide2.dto.TagDTO;
import com.example.touristguide2.dto.TouristAttractionDTO;
import com.example.touristguide2.model.TouristAttraction;
import com.example.touristguide2.util.ConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TouristRepositoryDB {

    @Value("${spring.datasource.url}")
    private String db_url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String pwd;


    public List<TouristAttractionDTO> getTouristAttractionList() {
        List<TouristAttractionDTO> touristAttractionList = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(db_url, username, pwd)) {
            String SQL = "SELECT attractions.name,attractions.description,city.name,tags.name FROM attractions JOIN city on attractions.cityID = city.cityID JOIN attractiontags on attractions.attractionID = attractiontags.attractionID JOIN tags on tags.tagID = attractiontags.tagID";
            PreparedStatement psts = con.prepareStatement(SQL);
            ResultSet rs = psts.executeQuery();

            String currentAttractionName = "";
            TouristAttractionDTO currentAttractionDTO = null;
            while (rs.next()) {
                String aName = rs.getString("attractions.name");
                String description = rs.getString("attractions.description");
                String city = rs.getString("city.name");
                TagDTO tagDTO = new TagDTO(rs.getString("tags.name"));
                if (aName.equals(currentAttractionName)) {
                    currentAttractionDTO.addTag(tagDTO);
                }
                else {
                    currentAttractionDTO = new TouristAttractionDTO(aName, description, city, new ArrayList<>(List.of(tagDTO)));
                    currentAttractionName = aName;
                    touristAttractionList.add(currentAttractionDTO);
                }

            }
        } catch (SQLException e) {
            System.out.println("Cannot connecto to database");
            e.printStackTrace();
        }
        return touristAttractionList;
    }


}


