package com.example.touristguide2.service;

import com.example.touristguide2.model.TouristAttraction;
import com.example.touristguide2.repository.TouristRepository;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class TouristServices {

    private TouristRepository repository;

    public TouristServices() {
        repository = new TouristRepository();
    }

    public List<TouristAttraction> getTouristAttractions() {
        return repository.getTouristAttractionList();
    }

    public TouristAttraction getTouristAttraction(String name) {
        return repository.getTouristAttraction(name);
    }

    public List<String> getCities(){
        return repository.getCities();
    }

    public List<String> getTags(){
        return repository.getTags();
    }

    public TouristAttraction addTouristAttraction(TouristAttraction touristAttraction) {
        TouristAttraction newTouristAttraction = repository.addTouristAttraction(touristAttraction);
        return newTouristAttraction;
    }

    public TouristAttraction updateTouristAttraction(TouristAttraction touristAttraction) {
        TouristAttraction updatedTouristAttraction = repository.updateTouristAttraction(touristAttraction);
        return updatedTouristAttraction;
    }

    public TouristAttraction deleteTouristAttraction(String name) {
        TouristAttraction deletedTouristAttraction = repository.deleteTouristAttraction(name);
        if (deletedTouristAttraction != null) {
            return deletedTouristAttraction;
        }
        return null;
    }


}
