package pl.com.bottega.exchangerate.api;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import pl.com.bottega.exchangerate.domain.commands.Command;
import pl.com.bottega.exchangerate.domain.commands.InvalidCommandException;
import pl.com.bottega.exchangerate.domain.commands.Validatable;
import pl.com.bottega.exchangerate.infrastructure.ValidationAspect;

import java.util.Map;
import java.util.Optional;

    @Component
    public class CommandGateway {

        private ApplicationContext applicationContext;

        public CommandGateway(ApplicationContext applicationContext) {
            this.applicationContext = applicationContext;
        }

        public <T> T execute(Command command) {
            validate(command);
            Handler handler = handlerFor(command);
            return (T) handler.handle(command);
        }

        private void validate(Command command) {
            Validatable.ValidationErrors validationErrors = new Validatable.ValidationErrors();
            command.validate(validationErrors);
            if(validationErrors.any())
                throw new InvalidCommandException(validationErrors);
        }

        private Handler handlerFor(Command command) {
            Map<String, Handler> handlers = applicationContext.getBeansOfType(Handler.class);
            Optional<Handler> handlerOptional = handlers.values().stream().
                    filter((h) -> h.canHandle(command)).findFirst();
            return handlerOptional.orElseThrow(() ->
                    new IllegalArgumentException("No handler found for " + command.getClass()));
        }

    }

