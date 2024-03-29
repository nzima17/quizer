package kg.inai.quizwebapp.controller;

import kg.inai.quizwebapp.Service.FlashCardService;
import kg.inai.quizwebapp.model.FlashCard;
import kg.inai.quizwebapp.repository.FlashCardRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//The controller class for the flashcard editing and deleting functions.
@Controller
public class FlashCardEditorController {

    private FlashCardRepository flashCardRepository;
    private FlashCardService flashCardService;

    private Integer cardID;

    public FlashCardEditorController(FlashCardRepository flashCardRepository, FlashCardService flashCardService)
    {
        this.flashCardRepository = flashCardRepository;
        this.flashCardService = flashCardService;
    }

    //Shows the card editing page
    //Asks for the card ID as a parameter so that we know what card we're editing
    @GetMapping("/editflashcard")
    public String showFlashCardEditor(Model model, @RequestParam("cardid") Integer cardID) {

        this.cardID = cardID;

        FlashCard flashCard = flashCardRepository.findById(cardID).orElse(null);

        model.addAttribute("flashcard", flashCard);

        return "edit_flash_card";

    }

    //Grabs the editing input from the page
    @PostMapping("/editflashcard")
    public String editFlashCard(Model model, FlashCard flashCard, @RequestParam(name = "deletecard", required = false) boolean deleteCard, RedirectAttributes redirectAttributes) {

        //These will be passed to the success page to make its message more specific about what type of entity
        //and what operation was performed on it.
        String operationString = "edited";
        String entityString = "card";

        //If user checked box to delete card, call the deletion service from the FlashCard service class.
        if(deleteCard)
        {
            try{
                flashCardService.deleteFlashCardById(cardID);
                //Change operation type to "deleted" so that it can show on the success page.
                operationString = "deleted";
            }
            catch (Exception e)
            {
                System.out.println("Error deleting entity!");
                System.out.println(e);
                return "redirect:/error";
            }
        }
        else {
            //Set the ID of the card created by user input to that of the card that wants to be updated.
            flashCard.setCardID(cardID);

            //Basically making a new card with the same ID but different info, thus editing the card.
            flashCardRepository.save(flashCard);
        }

        redirectAttributes.addFlashAttribute("operation",operationString);
        redirectAttributes.addFlashAttribute("entitytype",entityString);
        return "redirect:/successpage";

    }


}
