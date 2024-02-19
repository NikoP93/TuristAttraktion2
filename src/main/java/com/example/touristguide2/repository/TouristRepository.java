package com.example.touristguide2.repository;

import com.example.touristguide2.model.TouristAttraction;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TouristRepository {

    private List<TouristAttraction> touristAttractionList =
            new ArrayList<TouristAttraction>(List.of(
                    new TouristAttraction("Tivoli", "Forlystelsespark i hjertet af København", "København", List.of("Børnevenlig", "Forlystelsespark")),
                    new TouristAttraction("Amalienborg", "Hjem for de kongelige", "København", List.of("Slot")),
                    new TouristAttraction("Den lille havfrue", "Stedet alle japanere skal tage billeder", "København", List.of("Kunst", "Statue")),
                    new TouristAttraction("Aalborg Tårnet", "Nordens Paris", "Aalborg", List.of("Tårn", "Kultur"))
            ));

    public List<TouristAttraction> getTouristAttractionList() {
        return touristAttractionList;
    }

    public TouristAttraction getTouristAttraction(String name) {
        for (TouristAttraction attraction : touristAttractionList) {
            if (attraction.getName().toLowerCase().contains(name.toLowerCase())) {
                return attraction;
            }
        }
        return null;
    }

    public List<String> getCities(){
        List<String> cityList = new ArrayList<>(List.of(
                "København",
                "Aalborg",
                "Odense",
                "Aarhus",
                "Esbjerg",
                "Vejle"
        ));

        return cityList;
    }

    public List<String> getTags(){
        List<String> tagList = new ArrayList<>(List.of(
                "Underholdning",
                "Koncert",
                "Kunst",
                "Gratis"

        ));

        return tagList;
    }

    public TouristAttraction addTouristAttraction(TouristAttraction touristAttraction) {
        touristAttractionList.add(touristAttraction);
        return touristAttraction;
    }

    public TouristAttraction updateTouristAttraction(TouristAttraction touristAttraction) {
        for (TouristAttraction ta : touristAttractionList) {
            if (ta.getName().equalsIgnoreCase(touristAttraction.getName())) {
                ta.setDescription(touristAttraction.getDescription());
                return ta;
            }
        }
        return null;
    }

    public TouristAttraction deleteTouristAttraction(String name) {
        for (TouristAttraction ta : touristAttractionList) {
            if (ta.getName().equalsIgnoreCase(name)) {
                touristAttractionList.remove(ta);
                return ta;
            }
        }
        return null;
    }

}
