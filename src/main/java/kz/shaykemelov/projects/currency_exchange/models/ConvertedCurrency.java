package kz.shaykemelov.projects.currency_exchange.models;

import java.math.BigDecimal;

public record ConvertedCurrency(
        String sourceCurrency,
        String targetCurrency,
        BigDecimal exchangeRate,
        BigDecimal sourceCurrencyValue,
        BigDecimal targetCurrencyValue)
{
}
