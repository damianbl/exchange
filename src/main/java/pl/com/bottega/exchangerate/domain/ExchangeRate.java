package pl.com.bottega.exchangerate.domain;

import pl.com.bottega.exchangerate.domain.commands.CreateExchangeRateCommand;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class ExchangeRate {

    public ExchangeRate() {}

    @Id
    @GeneratedValue
    private Long id;

    private String date;
    private String currency;
    private BigDecimal rate;

    public ExchangeRate(CreateExchangeRateCommand cmd) {
        this.date = cmd.getDate();
        this.currency = cmd.getCurrency();
        this.rate = cmd.getRate();
    }

//    public Long getId() {
//        return id;
//    }

//    public void setId(Long id) {
//        this.id = id;
//    }

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
