package pl.com.bottega.exchangerate.api;

import org.springframework.stereotype.Component;
import pl.com.bottega.exchangerate.domain.ExchangeRate;
import pl.com.bottega.exchangerate.domain.commands.Command;
import pl.com.bottega.exchangerate.domain.commands.CreateExchangeRateCommand;
import pl.com.bottega.exchangerate.domain.repositories.ExchangeRateRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Component
public class CreateExchangeRateHandler implements Handler<CreateExchangeRateCommand, Void> {

    private ExchangeRateRepository exchangeRateRepository;

    public CreateExchangeRateHandler(ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }

    @Transactional
    public Void handle(CreateExchangeRateCommand cmd) {
        Optional<ExchangeRate> current = exchangeRateRepository.getExchangeCurrency(cmd.getDate(), cmd.getCurrency());
        if (current.isPresent()){
            current.get().setRate(cmd.getRate());
            exchangeRateRepository.save(current.get());
        } else {
            ExchangeRate exchangeRate = new ExchangeRate(cmd);
            exchangeRate.setRate(cmd.getRate());
            exchangeRateRepository.save(exchangeRate);
        }

        return null;
    }

    @Override
    public Class<? extends Command> getSupportedCommandClass() {
        return CreateExchangeRateCommand.class;
    }
}
