package kz.shaykemelov.projects.currency_exchange.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import kz.shaykemelov.projects.currency_exchange.models.ConvertedCurrency;
import kz.shaykemelov.projects.currency_exchange.models.CurrencyRate;

public interface CurrencyRatesService
{
    List<CurrencyRate> listCurrencyRatesByDate(final String sourceCurrency, final LocalDate date);

    ConvertedCurrency convert(final String sourceCurrency, final String targetCurrency, final BigDecimal sourceCurrencyValue, final LocalDate date);
}
