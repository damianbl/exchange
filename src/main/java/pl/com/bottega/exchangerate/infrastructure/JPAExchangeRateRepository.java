package pl.com.bottega.exchangerate.infrastructure;

import org.springframework.stereotype.Component;
import pl.com.bottega.exchangerate.domain.ExchangeRate;
import pl.com.bottega.exchangerate.domain.NoRateException;
import pl.com.bottega.exchangerate.domain.commands.CalculationCommand;
import pl.com.bottega.exchangerate.domain.commands.CreateExchangeRateCommand;
import pl.com.bottega.exchangerate.domain.commands.Validatable;
import pl.com.bottega.exchangerate.domain.repositories.ExchangeRateRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.Optional;

@Component
public class JPAExchangeRateRepository implements ExchangeRateRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public JPAExchangeRateRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(ExchangeRate exchangeRate) {
        entityManager.persist(exchangeRate);
    }

    @Override
    public ExchangeRate get(String currency) {
        ExchangeRate exchangeRate = entityManager.find(ExchangeRate.class,currency);
        if(exchangeRate == null) {
            throw  new NoSuchEntityException();
        }
        return exchangeRate;
    }

    public Optional<ExchangeRate> getExchangeCurrency(String date, String currency) {
        try {
            ExchangeRate exchangeRate = (ExchangeRate) entityManager.createQuery("FROM ExchangeRate e WHERE e.currency = :currency AND e.date = :date")
                    .setParameter("currency", currency).setParameter("date",date).getSingleResult();
            return Optional.of(exchangeRate);
        } catch (NoResultException ex) {
            return Optional.empty();
        }

    }
}
