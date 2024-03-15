//package com.example.touristguide2.controllerTest;
//
//import com.example.touristguide2.controller.TouristController;
//import com.example.touristguide2.model.TouristAttraction;
//import com.example.touristguide2.service.TouristServices;
//import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentMatchers;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.List;
//
//
//import static org.hamcrest.Matchers.containsString;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import static org.mockito.BDDMockito.given;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//
//@WebMvcTest(TouristController.class)
//public class ControllerTest {
//
//    private TouristAttraction touristAttraction = new TouristAttraction();
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private TouristServices touristServices;
//
//    @Test
//    void showAll()throws Exception{
//        mockMvc.perform(get("/attractions"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("attractionlist"));
//    }
//
//    @Test
//    void showTags() throws Exception{
//        TouristAttraction expectedAttraction = new TouristAttraction("Amalienborg", "Hjem for de kongelige", "København", List.of("Slot"));
//        given(touristServices.getTouristAttraction(ArgumentMatchers.any())).willReturn(expectedAttraction);
//        mockMvc.perform(get("/attractions/Amalienborg/tags"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("tags"))
//                .andExpect(content().string(containsString("Amalienborg Tags")));
//
//    }
//
//    @Test
//    void createTouristAttraction() throws Exception{
//        mockMvc.perform(get("/attractions/add"))
//                .andExpect(status().isOk())
//                .andExpect(model().attribute("touristAttraction", touristAttraction))
//                .andExpect(view().name("addTouristAttraction"));
//    }
//
//    @Test
//    void saveTouristAttraction() throws Exception{
//        mockMvc.perform(post("/attractions/save"))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(view().name("redirect:/attractions"));
//    }
//
//}
