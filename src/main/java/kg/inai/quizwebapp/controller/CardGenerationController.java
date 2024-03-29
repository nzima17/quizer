package kg.inai.quizwebapp.controller;

import kg.inai.quizwebapp.model.FlashCard;
import kg.inai.quizwebapp.model.Quiz;
import kg.inai.quizwebapp.repository.FlashCardRepository;
import kg.inai.quizwebapp.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;


//Controller for the card generator page
@Controller
@RequestMapping("/")
public class CardGenerationController {


    @Autowired
    private FlashCardRepository flashCardRepository;

    @Autowired
    private QuizRepository quizRepository;

    private Quiz quizEntity;

    //Shows flash card generator page.
    //Takes the quiz attribute, so that we can establish what quiz the flashcard "belongs" to.
    //Stores the quiz in a class variable so that makeFlashCard() method can more easily access it.
    @GetMapping ("/generatecard")
    public String showFlashCardGenerator(Model model, @ModelAttribute("quiz") Quiz quiz) {

        quizEntity = quiz;

        return "flash_card_generator";

    }

    //Takes question, list of answers, and a "correct" answer as input.
    //Used to make both an independent flashcard and a question to construct a quiz.
    //Puts quiz as flash attribute so that next card/question "knows" what quiz it is being added to.
    @PostMapping("/generatecard")
    public String makeFlashCard(FlashCard flashcard, RedirectAttributes redirectAttributes)
    {

        flashCardRepository.save(flashcard);

        List<FlashCard> flashCardList;
        if(quizEntity.getFlashCards() == null) {
            flashCardList = new ArrayList<>();
        }
        else{
            flashCardList = quizEntity.getFlashCards();
        }
        flashCardList.add(flashcard);
        quizEntity.setFlashCards(flashCardList);
        quizRepository.save(quizEntity);

        redirectAttributes.addFlashAttribute("quiz", quizEntity);

        return "redirect:/generatecard";
    }


}
