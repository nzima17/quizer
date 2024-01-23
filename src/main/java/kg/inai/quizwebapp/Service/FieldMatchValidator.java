package kg.inai.quizwebapp.Service;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

//Fieldmatching validator
public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {

    private String fieldOne;
    private String fieldTwo;

    private String message;

    @Override
    public void initialize(final FieldMatch constraintAnnotation) {
        fieldOne = constraintAnnotation.first();
        fieldTwo = constraintAnnotation.second();
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        boolean valid = true;
        try
        {
            final Object first = new BeanWrapperImpl(value).getPropertyValue(fieldOne);
            final Object second = new BeanWrapperImpl(value).getPropertyValue(fieldTwo);

            valid =  first == null && second == null || first != null && first.equals(second);
        }
        catch (final Exception ignore)
        {
            //Nothing goes here
        }

        if (!valid){
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(fieldOne)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        return valid;
    }

}
