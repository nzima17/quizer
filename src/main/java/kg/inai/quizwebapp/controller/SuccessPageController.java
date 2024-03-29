package kg.inai.quizwebapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

//Controller for the success page after performing an editing or deleting operation.
@Controller
public class SuccessPageController {

    //Receives String called "operation" which says whether item was edited or deleted so that
    //user can see appropriate confirmation.
    @GetMapping("/successpage")
    public String showSuccessPage(Model model, @RequestParam(name = "operation", required = false) String operation, @RequestParam(name = "entitytype", required = false) String entityType) {

        if(entityType == null)
        {
            entityType = "object";
        }

        if(operation == null)
        {
            operation = "changed";
        }

        return "success_page";

    }

    //Just a controller for the about page.
    @GetMapping("/about")
    public String getAbout(Model model){

        return "about";
    }

}
