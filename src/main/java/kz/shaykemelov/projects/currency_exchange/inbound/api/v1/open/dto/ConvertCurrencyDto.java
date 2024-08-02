package kz.shaykemelov.projects.currency_exchange.inbound.api.v1.open.dto;

import java.math.BigDecimal;

public record ConvertCurrencyDto(String sourceCurrency,
                                 String targetCurrency,
                                 BigDecimal exchangeRate,
                                 BigDecimal sourceCurrencyValue,
                                 BigDecimal targetCurrencyValue)
{
}
