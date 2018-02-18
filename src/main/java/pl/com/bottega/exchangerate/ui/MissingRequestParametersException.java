package pl.com.bottega.exchangerate.ui;

import pl.com.bottega.exchangerate.domain.commands.Validatable;

public class MissingRequestParametersException extends RuntimeException {
    private Validatable.ValidationErrors errors;

    public MissingRequestParametersException(Validatable.ValidationErrors errors) {
        this.errors = errors;
    }

    public Validatable.ValidationErrors getErrors() {
        return errors;
    }

}
