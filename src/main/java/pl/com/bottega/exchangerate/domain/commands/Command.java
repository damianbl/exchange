package pl.com.bottega.exchangerate.domain.commands;


import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Command {

    default void validate(Validatable.ValidationErrors validationErrors) {

    }

    default void validateDate(Validatable.ValidationErrors errors,String field, String value) {
        if (value == null || value.length() == 0)
            errors.add(field, "is required");
        try {
            if (value != null && (value.length() == 0))
                errors.add(field, "is required");

        }catch (NullPointerException ex) {
            errors.add(field,"is required");
        }
    }

    default void validateRate(Validatable.ValidationErrors errors, String field, BigDecimal rate) {
        try {
            if(rate.toPlainString().isEmpty())
                errors.add(field,"not empty");


            if(rate.compareTo(BigDecimal.ZERO) == -1 )
                errors.add(field,"must be > than 0.0");


        }catch (NullPointerException ex) {
            errors.add(field,"is required");
        }
    }
    default void validateRateTo(Validatable.ValidationErrors errors,String field, String to,String from) {
        if (to == null || to.length() == 0) {
            errors.add(field, "can't be empty");
        }
        try {
            if(to.equals(from) )
                errors.add(field,"must be different than from");


        }catch (NullPointerException ex) {
            errors.add(field,"is required");
        }
    }
    default void validateRateFrom(Validatable.ValidationErrors errors,String field, String to,String from) {
        try {
            if(to.equals(from) )
                errors.add(field,"must be different than to");


        }catch (NullPointerException ex) {
            errors.add(field,"is required");
        }
    }



    default void validatePresence(Validatable.ValidationErrors errors, String field, String value) {
        if (value == null) {
            errors.add(field, "can't be blank");
        }

    }
    default void validateCurrency(Validatable.ValidationErrors errors,String field, String value) {

            if (value == null || value.length() == 0) {
                errors.add(field, "can't be empty");
            }
            try {
            String regex = "[A-Z]+[A-Z]+[A-Z]";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(value);
            if (value !=null && !matcher.matches())  {
                errors.add(field, "has invalid format");
            }
        }catch (NullPointerException ex) {
            errors.add(field,"is required");
        }


    }
}
