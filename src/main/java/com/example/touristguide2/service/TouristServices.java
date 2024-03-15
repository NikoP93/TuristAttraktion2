package com.example.touristguide2.service;

import com.example.touristguide2.dto.TouristAttractionDTO;
import com.example.touristguide2.model.TouristAttraction;
import com.example.touristguide2.repository.TouristRepository;
import com.example.touristguide2.repository.TouristRepositoryDB;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class TouristServices {

    private TouristRepository repository;
    private TouristRepositoryDB dbRepository;


    public TouristServices(){

    }


    public TouristServices(TouristRepository repository) {
        this.repository = repository;
    }


    public TouristServices(TouristRepositoryDB dbRepository){
        this.dbRepository = dbRepository;
    }

    public List<TouristAttractionDTO> getTouristAttractions() {
        return dbRepository.getTouristAttractionList();
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
        return repository.addTouristAttraction(touristAttraction);

    }

    public TouristAttraction updateTouristAttraction(TouristAttraction touristAttraction) {
        return repository.updateTouristAttraction(touristAttraction);

    }

    public TouristAttraction deleteTouristAttraction(String name) {
        return repository.deleteTouristAttraction(name);

    }


}
