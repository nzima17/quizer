package kg.inai.quizwebapp.controller;

import kg.inai.quizwebapp.Service.FlashCardService;
import kg.inai.quizwebapp.model.FlashCard;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

//Controls page that lists all flashcards
@Controller
public class FlashCardListController {

    private FlashCardService flashCardService;

    //Constructor that instantiates flashCardService (used later)
    public FlashCardListController(FlashCardService flashCardService){
        this.flashCardService = flashCardService;
    }

    //Makes page displaying list of flashcards
    @GetMapping("/flashcardlist")
    public String getFlashCardList(Model model){

        List<FlashCard> flashCardList = flashCardService.getAllFlashCards();

        model.addAttribute("flashCardList", flashCardList);

        return "all_flash_cards";
    }

}
