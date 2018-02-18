package pl.com.bottega.exchangerate.api;

import org.springframework.stereotype.Component;
import pl.com.bottega.exchangerate.domain.Calculation;
import pl.com.bottega.exchangerate.domain.commands.CalculationCommand;
import pl.com.bottega.exchangerate.domain.commands.Command;
import pl.com.bottega.exchangerate.domain.repositories.ExchangeRateRepository;

import javax.transaction.Transactional;

@Component
public class CalculationHandler implements Handler<CalculationCommand,Calculation> {

    private ExchangeRateRepository exchangeRateRepository;

    public CalculationHandler(ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }

    @Transactional
    public Calculation handle(CalculationCommand cmd) {
        Calculation calculation = new Calculation(cmd,exchangeRateRepository);
        Calculation c = calculation.calculate(cmd.getFrom(),cmd.getTo(),cmd.getAmount(),cmd.getDate());
        return c;
    }

    @Override
    public Class<? extends Command> getSupportedCommandClass() {
        return CalculationCommand.class;
    }
}
