package kz.shaykemelov.projects.currency_exchange.services.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import kz.shaykemelov.projects.currency_exchange.exceptions.CurrencyExchangeUnavailable;
import kz.shaykemelov.projects.currency_exchange.models.ConvertedCurrency;
import kz.shaykemelov.projects.currency_exchange.models.CurrencyRate;
import kz.shaykemelov.projects.currency_exchange.outbound.database.CurrencyRatesRepository;
import kz.shaykemelov.projects.currency_exchange.services.CurrencyRatesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class DatabaseCurrencyRatesServiceImpl implements CurrencyRatesService
{
    private final CurrencyRatesRepository currencyRatesRepository;

    @Autowired
    public DatabaseCurrencyRatesServiceImpl(final CurrencyRatesRepository currencyRatesRepository)
    {
        this.currencyRatesRepository = currencyRatesRepository;
    }

    public List<CurrencyRate> listCurrencyRatesByDate(final String sourceCurrency, final LocalDate date)
    {
        return currencyRatesRepository.findCurrencyRates(sourceCurrency, date);
    }

    @Override
    public ConvertedCurrency convert(
            final String sourceCurrency,
            final String targetCurrency,
            final BigDecimal sourceCurrencyValue,
            final LocalDate date)
    {
        final var currencyRate = currencyRatesRepository.findCurrencyRate(sourceCurrency, date, targetCurrency)
                .orElseThrow(() -> new CurrencyExchangeUnavailable(sourceCurrency, targetCurrency, date));

        final var targetCurrencyValue = sourceCurrencyValue.multiply(currencyRate.getExchangeRate());

        return new ConvertedCurrency(
                sourceCurrency,
                targetCurrency,
                currencyRate.getExchangeRate(),
                sourceCurrencyValue,
                targetCurrencyValue
        );
    }
}
