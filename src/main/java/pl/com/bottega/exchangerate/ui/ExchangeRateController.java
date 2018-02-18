package pl.com.bottega.exchangerate.ui;

import org.springframework.web.bind.annotation.*;
import pl.com.bottega.exchangerate.api.CalculationHandler;
import pl.com.bottega.exchangerate.api.CommandGateway;
import pl.com.bottega.exchangerate.domain.Calculation;
import pl.com.bottega.exchangerate.domain.commands.CalculationCommand;
import pl.com.bottega.exchangerate.domain.commands.CreateExchangeRateCommand;
import pl.com.bottega.exchangerate.domain.commands.InvalidCommandException;
import pl.com.bottega.exchangerate.domain.commands.Validatable;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
public class ExchangeRateController {

    private CommandGateway commandGateway;
    private CalculationHandler calculationHandler;

    public ExchangeRateController(CommandGateway commandGateway, CalculationHandler calculationHandler) {
        this.commandGateway = commandGateway;

        this.calculationHandler = calculationHandler;
    }

    @PutMapping("/exchange-rate")
    public void create(@RequestBody CreateExchangeRateCommand cmd) {

        commandGateway.execute(cmd);
    }

    @GetMapping("/calculation")
    public Calculation calculateAmount(@RequestParam Optional<String> date, @RequestParam Optional<String> from, @RequestParam Optional<String> to, @RequestParam Optional<BigDecimal> amount) {
        if (date.isPresent() && from.isPresent() && to.isPresent() && amount.isPresent()) {
            CalculationCommand cmd = new CalculationCommand();
            cmd.setFrom(from.get());
            cmd.setTo(to.get());
            cmd.setAmount(amount.get());
            cmd.setDate(date.get());
            return commandGateway.execute(cmd);
        } else {
            Validatable.ValidationErrors errors = new Validatable.ValidationErrors();
            if (!date.isPresent()) errors.add("date", "is required");
            if (!from.isPresent()) errors.add("from", "is required");
            if (!to.isPresent()) errors.add("to", "is required");
            if (!amount.isPresent()) errors.add("amount", "is required");

            throw new MissingRequestParametersException(errors);
        }

    }


}
