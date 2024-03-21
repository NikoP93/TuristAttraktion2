package com.example.touristguide2.repository;

import com.example.touristguide2.dto.TagDTO;
import com.example.touristguide2.dto.TouristAttractionDTO;
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
            String SQL = """
                    SELECT attractions.Name,attractions.Description,city.Name,tags.Name
                    FROM attractions 
                    JOIN city on attractions.CityID = city.CityID 
                    JOIN attractiontags on attractions.AttractionID = attractiontags.AttractionID 
                    JOIN tags on tags.TagID = attractiontags.TagID
                    """;
            PreparedStatement pstmt = con.prepareStatement(SQL);
            ResultSet rs = pstmt.executeQuery();

            String currentAttractionName = "";
            TouristAttractionDTO currentAttractionDTO = null;
            while (rs.next()) {
                String aName = rs.getString("attractions.name");
                String description = rs.getString("attractions.description");
                String city = rs.getString("city.name");
                TagDTO tagDTO = new TagDTO(rs.getString("tags.name"));
                if (aName.equals(currentAttractionName)) {
                    currentAttractionDTO.addTag(tagDTO);
                } else {
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


    public TouristAttractionDTO getTouristAttraction(String name) {
        TouristAttractionDTO touristAttraction = null;
        try (Connection con = DriverManager.getConnection(db_url, username, pwd)) {
            String SQL = """
                    SELECT attractions.Name, attractions.Description, city.Name, tags.Name
                    FROM attractions
                    JOIN city on attractions.CityID = city.CityID
                    JOIN attractiontags on attractions.AttractionID = attractiontags.AttractionID
                    JOIN tags on tags.TagID = attractiontags.TagID
                    WHERE attractions.Name = ?;
                    """;
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String aName = rs.getString("attractions.name");
                String description = rs.getString("attractions.description");
                String city = rs.getString("city.name");
                TagDTO tagDTO = new TagDTO(rs.getString("tags.name"));
                touristAttraction = new TouristAttractionDTO(aName, description, city, new ArrayList<>(List.of(tagDTO)));

            }
            return touristAttraction;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<TagDTO> getTagListDTO(String name) {
        List<TagDTO> tagList = new ArrayList<>();
        TagDTO tagDTO;
        try (Connection con = DriverManager.getConnection(db_url, username, pwd)) {
            String SQL = """
                    SELECT tags.Name
                    FROM attractiontags
                    join attractions on attractions.AttractionID = attractiontags.AttractionID
                    JOIN tags on tags.TagID = attractiontags.TagID
                    WHERE attractions.Name = ?;
                    """;
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String tName = rs.getString("tags.name");
                tagDTO = new TagDTO(tName);
                tagList.add(tagDTO);

            }
            return tagList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

//    public TouristAttractionDTO addTouristAttraction(TouristAttractionDTO attraction) {
//        try (Connection con = DriverManager.getConnection(db_url, username, pwd)) {
//            String SQL = """
//                    INSERT INTO attractions(name,cityID,description) VALUES(?,?,?);
//                    """;
//            PreparedStatement pstmt = con.prepareStatement(SQL);
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return attraction;
//    }

    public boolean deleteAttraction(String name) {
        return deleteAttractionTags(name) && deleteFromAttractionTable(name);

    }

    //Changes

    private boolean deleteAttractionTags(String name) {
        int rows = 0;
        try (Connection con = DriverManager.getConnection(db_url, username, pwd)) {
            String SQL = """
                    DELETE FROM attractiontags WHERE AttractionID = 
                    (SELECT AttractionID FROM attractions WHERE Name = ? )
                                        """;
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, name);
            rows = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rows >= 0;
    }

    private boolean deleteFromAttractionTable(String name) {
        int rows = 0;
        try (Connection con = DriverManager.getConnection(db_url, username, pwd)) {
            String SQL = """
                    DELETE FROM attractions WHERE Name = ?;
                    """;
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, name);
            rows = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rows == 1;
    }
}
