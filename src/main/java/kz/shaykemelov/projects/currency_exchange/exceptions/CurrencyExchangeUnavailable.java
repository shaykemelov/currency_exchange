package kz.shaykemelov.projects.currency_exchange.exceptions;

import java.time.LocalDate;

public class CurrencyExchangeUnavailable extends RuntimeException
{
    public CurrencyExchangeUnavailable(final String sourceCurrency, final String targetCurrency, final LocalDate date)
    {
        super("Conversation from %s to %s at %s is not available".formatted(sourceCurrency, targetCurrency, date));
    }
}
