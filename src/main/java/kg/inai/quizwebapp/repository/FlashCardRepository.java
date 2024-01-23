package kg.inai.quizwebapp.repository;

import kg.inai.quizwebapp.model.FlashCard;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//Repository for flash card entity.
@Repository
public interface FlashCardRepository extends CrudRepository<FlashCard, Integer> {

    FlashCard findByCardID(Integer cardID);

}
