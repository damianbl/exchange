package pl.com.bottega.exchangerate.domain.commands;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CreateExchangeRateCommand implements Command {

    private String date;
    private String currency;
    private BigDecimal rate;

    public void validate(Validatable.ValidationErrors errors) {

        validateDate(errors,"date",date);
        validateCurrency(errors,"currency",currency);
        validateRate(errors,"rate",rate);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }


}