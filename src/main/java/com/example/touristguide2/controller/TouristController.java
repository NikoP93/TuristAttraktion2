package com.example.touristguide2.controller;

import com.example.touristguide2.model.TouristAttraction;
import com.example.touristguide2.service.TouristServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        List<TouristAttraction> touristAttractionList = touristServices.getTouristAttractions();
        model.addAttribute("touristAttractionlist", touristAttractionList);
        return "attractionlist";
    }

@GetMapping("/{name}/tags")
    public String getTags(@PathVariable String name, Model model){
    TouristAttraction touristAttraction = touristServices.getTouristAttraction(name);
    model.addAttribute("tags", touristAttraction.getTagList());
    return "tags";
}

@GetMapping("/add")
    public String addAttraction(Model model){
    model.addAttribute("touristAttraction", new TouristAttraction());
    List<String> cityList = touristServices.getCities();
    model.addAttribute("cityList",cityList);
    List<String> tagsList = touristServices.getTags();
    model.addAttribute("tagsList", tagsList);
    return "addTouristAttraction";
}

@PostMapping("/save")
    public String saveAttraction(@ModelAttribute TouristAttraction touristAttraction){
    touristServices.addTouristAttraction(touristAttraction);
    return "redirect:/attractions";
}

@GetMapping("/{name}/edit")
    public String editAttraction(@PathVariable String name, Model model){
    TouristAttraction editedAttraction = touristServices.getTouristAttraction(name);
    model.addAttribute("editedTouristAttraction", editedAttraction);
    return "editTouristAttraction";
}

@PostMapping("/update")
    public String updateAttraction(@ModelAttribute TouristAttraction touristAttraction){
    touristServices.updateTouristAttraction(touristAttraction);
    return "redirect:/attractions";
}

@GetMapping("{name}/delete")
    public String deleteAttraction(@PathVariable String name){
    touristServices.deleteTouristAttraction(name);
    return "redirect:/attractions";
}


}
