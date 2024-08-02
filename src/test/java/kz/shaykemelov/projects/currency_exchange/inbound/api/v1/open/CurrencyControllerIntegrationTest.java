package kz.shaykemelov.projects.currency_exchange.inbound.api.v1.open;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import kz.shaykemelov.projects.currency_exchange.common.AbstractTestCase;
import kz.shaykemelov.projects.currency_exchange.models.ConvertedCurrency;
import kz.shaykemelov.projects.currency_exchange.models.CurrencyRate;
import kz.shaykemelov.projects.currency_exchange.outbound.database.CurrencyRatesRepository;
import kz.shaykemelov.projects.currency_exchange.services.impl.DatabaseCurrencyRatesServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import org.junit.jupiter.api.Test;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CurrencyControllerIntegrationTest extends AbstractTestCase
{
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @SpyBean
    private DatabaseCurrencyRatesServiceImpl currencyRatesService;

    @MockBean
    private CurrencyRatesRepository currencyRatesRepository;

    @Test
    void testGetAll()
    {
        // prepare
        final var now = LocalDate.now();

        final var currencyRate = new CurrencyRate();
        currencyRate.setSourceCurrency("USD");
        currencyRate.setTargetCurrency("KZT");
        currencyRate.setRateDate(now);
        currencyRate.setExchangeRate(new BigDecimal("455.55"));

        when(currencyRatesService.listCurrencyRatesByDate("USD", now))
                .thenReturn(List.of(currencyRate));

        // do
        final var result = restTemplate.getForObject("http://localhost:" + port + "/api/v1/open/currency?source_currency=USD", String.class);

        // assert & verify
        assertThat(result).isEqualTo("[{\"sourceCurrency\":\"USD\",\"targetCurrency\":\"KZT\",\"exchangeRate\":455.55}]");
    }

    @Test
    void testConvert()
    {
        // prepare
        final var now = LocalDate.now();

        final var convertedCurrency = new ConvertedCurrency(
                "USD",
                "KZT",
                new BigDecimal("455.55"),
                new BigDecimal("123"),
                new BigDecimal("56032.65")
        );

        doReturn(convertedCurrency).when(currencyRatesService).convert("USD", "KZT", new BigDecimal("455.55"), now);

        // do
        final var result = restTemplate.getForObject("http://localhost:" + port + "/api/v1/open/currency/convert?source_currency=USD&target_currency=KZT&source_currency_value=455.55", String.class);

        // assert & verify
        assertThat(result).isEqualTo("{\"sourceCurrency\":\"USD\",\"targetCurrency\":\"KZT\",\"exchangeRate\":455.55,\"sourceCurrencyValue\":123,\"targetCurrencyValue\":56032.65}");
    }

    @Test
    void testConvertFailed()
    {
        // prepare
        final var now = LocalDate.now();

        when(currencyRatesRepository.findCurrencyRate(anyString(), any(), anyString()))
                .thenReturn(Optional.empty());

        // do
        final var result = restTemplate.getForObject("http://localhost:" + port + "/api/v1/open/currency/convert?source_currency=USD&target_currency=KZT&source_currency_value=455.55", String.class);

        // assert & verify
        assertThat(result).endsWith("\",\"status\":400,\"error\":\"Conversation from USD to KZT at 2024-08-02 is not available\",\"path\":\"/api/v1/open/currency/convert\"}");
    }
}