package pl.com.bottega.exchangerate.domain.commands;

import java.math.BigDecimal;

public class CalculationCommand implements Command {

    String from;
    String to;
    BigDecimal amount;
    String date;

    public void validate(Validatable.ValidationErrors errors) {


        validateDate(errors,"date",date);
        validateRate(errors,"amount",amount);
        validateCurrency(errors,"from",from);
        validateCurrency(errors,"to",to);
        validateRateTo(errors,"to",to,from);
        validateRateFrom(errors,"from",to,from);
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

     public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
