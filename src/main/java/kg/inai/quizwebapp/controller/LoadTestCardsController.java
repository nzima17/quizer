package kg.inai.quizwebapp.controller;

import kg.inai.quizwebapp.Service.FlashCardService;
import kg.inai.quizwebapp.Service.QuizService;
import kg.inai.quizwebapp.repository.FlashCardRepository;
import kg.inai.quizwebapp.repository.QuizRepository;
import kg.inai.quizwebapp.repository.RoleRepository;
import kg.inai.quizwebapp.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

@Controller
public class LoadTestCardsController {

    FlashCardRepository flashCardRepository;
    QuizRepository quizRepository;

    FlashCardService flashCardService;
    QuizService quizService;

    UserRepository userRepository;

    RoleRepository roleRepository;

    public LoadTestCardsController(FlashCardRepository flashCardRepository, QuizRepository quizRepository, FlashCardService flashCardService, QuizService quizService, RoleRepository roleRepository, UserRepository userRepository) {
        this.flashCardRepository = flashCardRepository;
        this.quizRepository = quizRepository;
        this.flashCardService = flashCardService;
        this.quizService = quizService;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/loadexamples")
    public String loadExamples(Model model, RedirectAttributes redirectAttributes){

        String operation = "loaded";
        String entityType = "example";

        redirectAttributes.addFlashAttribute("operation",operation);
        redirectAttributes.addFlashAttribute("entitytype",entityType);

        makeDB("beansproutdb");
        quizService.createExampleModels();

        return "redirect:/successpage";

    }

    public void makeDB(String dbName) {

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/","root","password");
            Statement statement = connection.createStatement();

            String query = "CREATE DATABASE " + dbName;
            statement.executeUpdate(query);

            System.out.println("Database " + dbName + "created!");
            }
        catch (Exception e) {

            System.out.println(e);

        }

    }




}
