package kz.shaykemelov.projects.currency_exchange.services.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import kz.shaykemelov.projects.currency_exchange.common.AbstractTestCase;
import kz.shaykemelov.projects.currency_exchange.models.ConvertedCurrency;
import kz.shaykemelov.projects.currency_exchange.models.CurrencyRate;
import kz.shaykemelov.projects.currency_exchange.outbound.database.CurrencyRatesRepository;

import org.junit.jupiter.api.Test;

class DatabaseCurrencyRatesServiceImplTest extends AbstractTestCase
{
    private final CurrencyRatesRepository currencyRatesRepository = mock(CurrencyRatesRepository.class);

    private final DatabaseCurrencyRatesServiceImpl databaseCurrencyRatesService = new DatabaseCurrencyRatesServiceImpl(currencyRatesRepository);

    @Test
    void testListCurrencyRatesByDate()
    {
        // prepare
        final var now = LocalDate.now();

        final var currencyRate = new CurrencyRate();
        currencyRate.setSourceCurrency("KZT");
        currencyRate.setTargetCurrency("USD");
        currencyRate.setRateDate(now);
        currencyRate.setExchangeRate(new BigDecimal("0.001300"));

        when(currencyRatesRepository.findCurrencyRates("KZT", now))
                .thenReturn(List.of(currencyRate));

        // do
        final var actualRates = databaseCurrencyRatesService.listCurrencyRatesByDate("KZT", now);

        // assert & verify
        assertThat(actualRates)
                .hasSize(1)
                .first()
                .isSameAs(currencyRate);
    }

    @Test
    void testConvert()
    {
        // prepare
        final var now = LocalDate.now();

        final var currencyRate = new CurrencyRate();
        currencyRate.setSourceCurrency("USD");
        currencyRate.setTargetCurrency("KZT");
        currencyRate.setRateDate(now);
        currencyRate.setExchangeRate(new BigDecimal("455.55"));

        final var expectedConvertedCurrency = new ConvertedCurrency(
                "USD",
                "KZT",
                new BigDecimal("455.55"),
                new BigDecimal("155"),
                new BigDecimal("70610.25")
        );

        when(currencyRatesRepository.findCurrencyRate("USD", now, "KZT"))
                .thenReturn(Optional.of(currencyRate));

        // do
        final var convertedCurrency = databaseCurrencyRatesService.convert("USD", "KZT", new BigDecimal("155"), now);

        // assert & verify
        assertThat(convertedCurrency)
                .usingRecursiveComparison()
                .withStrictTypeChecking()
                .isEqualTo(expectedConvertedCurrency);
    }
}