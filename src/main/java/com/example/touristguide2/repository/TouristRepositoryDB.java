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


    public List<TagDTO> getTags(){
        List<TagDTO> tags = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(db_url,username,pwd)){
            String SQl = "SELECT * FROM tags";
            PreparedStatement pstmt = con.prepareStatement(SQl);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                TagDTO tag = new TagDTO(rs.getString("Name"));
                tags.add(tag);

            }
        }

        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tags;
    }

    public List<String> getCities(){
        List<String> cityList = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(db_url,username,pwd)){
            String SQL = "SELECT * FROM city";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                cityList.add(rs.getString("Name"));
            }

        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cityList;
    }


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
                String aName = rs.getString("attractions.Name");
                String description = rs.getString("attractions.Description");
                String city = rs.getString("city.Name");
                TagDTO tagDTO = new TagDTO(rs.getString("tags.Name"));
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

    public TouristAttractionDTO addTouristAttraction(TouristAttractionDTO attraction) {
        try (Connection con = DriverManager.getConnection(db_url, username, pwd)) {

            //Get city ID from method down below
            int cityID = getCityId(attraction.getCity());

            //Insert new attraction
            String SQL = """
                    INSERT INTO attractions(name,cityID,description) VALUES(?,?,?);
                    """;
            PreparedStatement pstmtAttraction = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            pstmtAttraction.setString(1, attraction.getName());
            pstmtAttraction.setInt(2, cityID);
            pstmtAttraction.setString(3, attraction.getDescription());
            pstmtAttraction.executeUpdate();

            //Get the generated Attraction ID
            ResultSet generatedKeys = pstmtAttraction.getGeneratedKeys();
            int attractionID = -1;
            if (generatedKeys.next()) {
                attractionID = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Failed to get generated KEY");
            }

            //Associate the new attraction with the tags
            String insertTagSQL = "INSERT INTO attractiontags(AttractionID,TagID) VALUES(?,?)";
            PreparedStatement pstmtTag = con.prepareStatement(insertTagSQL);
            for (TagDTO tagDTO : attraction.getTaglistDTO()) {
                int tagID = getTagId(tagDTO.getTname());
                pstmtTag.setInt(1,attractionID);
                pstmtTag.setInt(2,tagID);
                pstmtTag.executeUpdate();
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return attraction;
    }


    public TouristAttractionDTO editTouristAttraction(TouristAttractionDTO attraction) {
        int rows = 0;
        try (Connection con = DriverManager.getConnection(db_url, username, pwd)) {
            String SQLAttraction = """
                    UPDATE attractions
                    SET Description = ?,CityID = ?
                    WHERE Name = ?
                    """;
            PreparedStatement psmtAttraction = con.prepareStatement(SQLAttraction);
            psmtAttraction.setString(1,attraction.getDescription());
            psmtAttraction.setInt(2,getCityId(attraction.getName()));
            psmtAttraction.setString(3,attraction.getName());
            rows = psmtAttraction.executeUpdate();

            String SQLAttractionTags = """
                    DELETE FROM attractiontags
                    WHERE attractionID = (SELECT attractionID FROM attractions WHERE name = ?)
                    """;
            PreparedStatement psmtAttractionTags = con.prepareStatement(SQLAttractionTags);
            psmtAttractionTags.setString(1,attraction.getName());
            psmtAttractionTags.executeUpdate();

            String insertTagSQL = "INSERT INTO attractiontags(AttractionID,TagID) VALUES(?,?)";
            PreparedStatement pstmtTag = con.prepareStatement(insertTagSQL);
            int attractionID = getAttractionID(attraction.getName());
            for (TagDTO tagDTO : attraction.getTaglistDTO()) {
                int tagID = getTagId(tagDTO.getTname());
                pstmtTag.setInt(1,attractionID);
                pstmtTag.setInt(2,tagID);
                pstmtTag.executeUpdate();
            }



        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
        return attraction;
    }

    public int getAttractionID(String name){
        try(Connection con = DriverManager.getConnection(db_url, username,pwd)){
            String attractionIDSQL = "SELECT AttractionID FROM attractions WHERE name = ?";
            PreparedStatement pstmt = con.prepareStatement(attractionIDSQL);
            pstmt.setString(1,name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
                return rs.getInt("AttractionID");
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return 0;
    }

    private int getCityId(String cityName) {
        try (Connection con = DriverManager.getConnection(db_url, username, pwd)) {
            String selectCitySQL = "SELECT cityID FROM city WHERE name = ?";
            PreparedStatement pstmt = con.prepareStatement(selectCitySQL);
            pstmt.setString(1, cityName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("CityID");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }


    private int getTagId(String tagName){
        try (Connection con = DriverManager.getConnection(db_url, username, pwd)) {
            String selectTagSQL = "SELECT TagID FROM tags WHERE name = ?";
            PreparedStatement pstmt = con.prepareStatement(selectTagSQL);
            pstmt.setString(1, tagName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("TagID");
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return 0;
    }


    public boolean deleteAttraction(String name) {
        return deleteAttractionTags(name) && deleteFromAttractionTable(name);

    }


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
