package pl.com.bottega.exchangerate.ui;

import org.springframework.web.bind.annotation.*;
import pl.com.bottega.exchangerate.api.CalculationHandler;
import pl.com.bottega.exchangerate.api.CommandGateway;
import pl.com.bottega.exchangerate.domain.Calculation;
import pl.com.bottega.exchangerate.domain.commands.CalculationCommand;
import pl.com.bottega.exchangerate.domain.commands.CreateExchangeRateCommand;

import java.math.BigDecimal;

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
    public Calculation calculateAmount(@RequestParam String date,@RequestParam String from,@RequestParam String to,@RequestParam BigDecimal amount) {
        CalculationCommand cmd = new CalculationCommand();
        cmd.setFrom(from);
        cmd.setTo(to);
        cmd.setAmount(amount);
        cmd.setDate(date);
        return commandGateway.execute(cmd);

    }


}
