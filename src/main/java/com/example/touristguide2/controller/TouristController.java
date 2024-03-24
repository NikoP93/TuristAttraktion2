package com.example.touristguide2.controller;

import com.example.touristguide2.dto.TagDTO;
import com.example.touristguide2.dto.TouristAttractionDTO;
import com.example.touristguide2.model.TouristAttraction;
import com.example.touristguide2.service.TouristServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.HTML;
import java.util.List;


@Controller
@RequestMapping("/attractions")
public class TouristController {

    private TouristServices touristServices;


    public TouristController(TouristServices touristServices) {
        this.touristServices = touristServices;
    }


    @GetMapping("")
    public String getAttractions(Model model) {
        List<TouristAttractionDTO> touristAttractionList = touristServices.getTouristAttractions();
        model.addAttribute("touristAttractionlist", touristAttractionList);
        return "attractionlist";
    }

@GetMapping("/{name}/tags")
    public String getTags(@PathVariable String name, Model model){
    TouristAttractionDTO touristAttraction = touristServices.getTouristAttraction(name);
    List<TagDTO> taglist = touristServices.getTagDTO(name);
    model.addAttribute("tags", taglist);
    model.addAttribute("name",touristAttraction.getName());
    return "tags";
}

@GetMapping("/add")
    public String addAttraction(Model model){
    model.addAttribute("touristAttraction", new TouristAttractionDTO());
    List<String> cityList = touristServices.getCities();
    model.addAttribute("cityList",cityList);
    List<TagDTO> tagsList = touristServices.getTags();
    model.addAttribute("tagsList", tagsList);
    return "addTouristAttraction";
}

@PostMapping("/save")
    public String saveAttraction(@ModelAttribute TouristAttractionDTO touristAttraction){
    touristServices.addTouristAttraction(touristAttraction);
    return "redirect:/attractions";
}

@GetMapping("/{name}/edit")
    public String editAttraction(@PathVariable String name, Model model){
    TouristAttractionDTO editedAttraction = touristServices.getTouristAttraction(name);
    model.addAttribute("editedTouristAttraction", editedAttraction);
    List<String> cityList = touristServices.getCities();
    model.addAttribute("cityList",cityList);
    List<TagDTO> tagsList = touristServices.getTags();
    model.addAttribute("tagsList",tagsList);
    return "editTouristAttraction";
}

@PostMapping("/update")
    public String updateAttraction(@ModelAttribute TouristAttractionDTO touristAttraction){
    touristServices.editTouristAttraction(touristAttraction);
    return "redirect:/attractions";
}

@GetMapping("{name}/delete")
    public String deleteAttraction(@PathVariable String name){
    System.out.println(name);
    touristServices.deleteAttraction(name);
    return "redirect:/attractions";
}


}
