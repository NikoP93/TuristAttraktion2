//package com.example.touristguide2.repositoryTest;
//
//import com.example.touristguide2.model.TouristAttraction;
//import com.example.touristguide2.repository.TouristRepository;
//import org.junit.jupiter.api.Test;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class RepositoryTest {
//
//    TouristRepository repository = new TouristRepository();
//
//    @Test
//    void showAll(){
//        int expectedSize = 4;
//        int actualSize = repository.getTouristAttractionList().size();
//        assertEquals(expectedSize,actualSize);
//    }
//
//    @Test
//    void attractionOnAttractionList(){
//        TouristAttraction tivoli = repository.getTouristAttraction("Tivoli");
//        assertNotNull(tivoli);
//    }
//
//    @Test
//    void attractionNotOnAttractionList(){
//        TouristAttraction legoland = repository.getTouristAttraction("Legoland");
//        assertNull(legoland);
//    }
//
//    @Test
//    void addAttraction(){
//        int startSize = repository.getTouristAttractionList().size();
//        TouristAttraction legoLand = new TouristAttraction("Legoland","Meget lego","Billund", List.of("børnevenlig","sjov"));
//        repository.addTouristAttraction(legoLand);
//        int actualSize = startSize + 1;
//        int expectedSize = 5;
//        assertEquals(actualSize,expectedSize);
//    }
//
//    @Test
//    void deleteAttraction(){
//        repository.deleteTouristAttraction("tivoli");
//        int expectedSize = 3;
//        int actualSize = repository.getTouristAttractionList().size();
//        assertEquals(expectedSize,actualSize);
//
//    }
//}
