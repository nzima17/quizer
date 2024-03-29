package kg.inai.quizwebapp.Service;

import kg.inai.quizwebapp.model.FlashCard;
import kg.inai.quizwebapp.model.Quiz;
import kg.inai.quizwebapp.repository.FlashCardRepository;
import kg.inai.quizwebapp.repository.QuizRepository;
import org.springframework.stereotype.Service;

import java.util.List;

//Flash card service class.
@Service
public class FlashCardService {

    private final FlashCardRepository flashCardRepository;

    //Including quiz repository because flash card deletion method requires altering relationship with quiz entity.
    private final QuizRepository quizRepository;

    public FlashCardService(FlashCardRepository flashCardRepository, QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
        this.flashCardRepository = flashCardRepository;
    }

    //Returns all flash cards that exist in flash card table, regardless of associated quiz.
    public List<FlashCard> getAllFlashCards(){

        return (List<FlashCard>) flashCardRepository.findAll();
    }

    //Takes flash card ID as parameter and deletes flash card with this ID.
    public void deleteFlashCardById(Integer cardID) {

        //Retrieve flashcard.
        FlashCard flashcard = flashCardRepository.findById(cardID).orElseThrow();

        //Remove relationship in Quiz entity first. (Otherwise it will not work.)
        for (Quiz quiz : flashcard.getQuizCollect())
        {
            quiz.getFlashCards().remove(flashcard);
        }

        //Remove relationship to any related Quiz entities on FlashCard side.
        flashcard.getQuizCollect().clear();

        //Save changes.
        flashCardRepository.save(flashcard);

        //Delete flash card.
        flashCardRepository.delete(flashcard);

    }


    public FlashCard createExampleCard(String question, List<String> answerList, Integer answer){

        boolean success = false;

        FlashCard flashCard = new FlashCard();

        flashCard.setQuestion(question);

        flashCard.setAnswerList(answerList);

        flashCard.setAnswerList(answerList);
        flashCard.setAnswer(answer);

        flashCardRepository.save(flashCard);

        success = true;

        return flashCard;

    }


}
