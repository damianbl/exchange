package pl.com.bottega.exchangerate.domain;

import pl.com.bottega.exchangerate.domain.commands.CalculationCommand;
import pl.com.bottega.exchangerate.domain.commands.InvalidCommandException;
import pl.com.bottega.exchangerate.domain.commands.Validatable;
import pl.com.bottega.exchangerate.domain.repositories.ExchangeRateRepository;
import pl.com.bottega.exchangerate.infrastructure.JPAExchangeRateRepository;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;


public class Calculation {

    public final String mainCurrency = "PLN";


    String from;
    String to;
    BigDecimal amount;
    BigDecimal calculatedAmount;
    String date;

    CalculationCommand cmd;

    public Calculation(CalculationCommand cmd, ExchangeRateRepository exchangeRateRepository) {
        this.cmd = cmd;
        this.from = cmd.getFrom();
        this.to = cmd.getTo();
        this.amount = cmd.getAmount();
        this.date = cmd.getDate();
        this.exchangeRateRepository = exchangeRateRepository;
    }


    private ExchangeRateRepository exchangeRateRepository;

    public Calculation calculate(String from, String to, BigDecimal amount, String date) {

        if (mainCurrency.equals(cmd.getFrom())) {
            Optional<ExchangeRate> exchangeRate = exchangeRateRepository.getExchangeCurrency(date, to);
            if (exchangeRate.isPresent()) {
                calculatedAmount = amount.divide(exchangeRate.get().getRate(), 2, RoundingMode.HALF_DOWN);
            } else {
                throw new NoRateException();
            }
        }

        if (mainCurrency.equals(cmd.getTo())) {
            Optional<ExchangeRate> exchangeRate = exchangeRateRepository.getExchangeCurrency(cmd.getDate(), cmd.getFrom());

            if (exchangeRate.isPresent()) {
                calculatedAmount = amount.multiply(exchangeRate.get().getRate());
            } else {
                throw new NoRateException();
            }

        }
        if ((!mainCurrency.equals(cmd.getTo())) && (!mainCurrency.equals(cmd.getFrom()))) {
            Optional<ExchangeRate> exchangeRateTo = exchangeRateRepository.getExchangeCurrency(cmd.getDate(), cmd.getTo());
            Optional<ExchangeRate> exchangeRateFrom = exchangeRateRepository.getExchangeCurrency(cmd.getDate(), cmd.getFrom());
            if (exchangeRateFrom.isPresent() && exchangeRateTo.isPresent()) {
                calculatedAmount = (amount.multiply(exchangeRateFrom.get().getRate())).divide(exchangeRateTo.get().getRate());

            } else {
                throw new NoRateException();
            }
        }
        if (cmd.getTo().equals(cmd.getFrom())) {
            throw new InvalidCommandException("mus be");

        }
        return this;


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

    public BigDecimal getCalculatedAmount() {
        return calculatedAmount;
    }

    public void setCalculatedAmount(BigDecimal calculatedAmount) {
        this.calculatedAmount = calculatedAmount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
