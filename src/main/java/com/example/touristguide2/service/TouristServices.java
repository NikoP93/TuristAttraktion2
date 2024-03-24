package com.example.touristguide2.service;

import com.example.touristguide2.dto.TagDTO;
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


//    public TouristServices(TouristRepository repository) {
//        this.repository = repository;
//    }

    public TouristServices(TouristRepositoryDB dbRepository){
        this.dbRepository = dbRepository;
    }

    public List<TagDTO> getTagDTO(String name){
        return dbRepository.getTagListDTO(name);
    }

    public List<TagDTO> getTags(){
        return dbRepository.getTags();
    }

    public List<String> getCities(){
        return dbRepository.getCities();
    }


    public List<TouristAttractionDTO> getTouristAttractions() {
        return dbRepository.getTouristAttractionList();
    }

    public TouristAttractionDTO getTouristAttraction(String name) {
        return dbRepository.getTouristAttraction(name);
    }


    public boolean deleteAttraction(String name){
       return dbRepository.deleteAttraction(name);
    }

    public TouristAttractionDTO addTouristAttraction(TouristAttractionDTO touristAttraction) {
        return dbRepository.addTouristAttraction(touristAttraction);

    }
    public TouristAttractionDTO editTouristAttraction(TouristAttractionDTO touristAttraction){
        return dbRepository.editTouristAttraction(touristAttraction);
    }


//    public TouristAttraction getTouristAttraction1(String name){
//        return repository.getTouristAttraction(name);
//    }
//
//    public List<String> getCities(){
//        return repository.getCities();
//    }

//    public List<String> getTags(){
//        return repository.getTags();
//    }
//
//
//    public TouristAttraction updateTouristAttraction(TouristAttraction touristAttraction) {
//        return repository.updateTouristAttraction(touristAttraction);
//
//    }
//
//    public TouristAttraction deleteTouristAttraction(String name) {
//        return repository.deleteTouristAttraction(name);
//
//    }


}
