package pl.com.bottega.exchangerate.domain.repositories;

import pl.com.bottega.exchangerate.domain.ExchangeRate;

import java.util.Optional;

public interface ExchangeRateRepository {

    void save(ExchangeRate exchangeRate);
    ExchangeRate get(String currency);
    Optional<ExchangeRate> getExchangeCurrency(String date, String currency);
}
