package kz.shaykemelov.projects.currency_exchange.inbound.api.v1.open;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import kz.shaykemelov.projects.currency_exchange.inbound.api.v1.open.dto.ConvertCurrencyDto;
import kz.shaykemelov.projects.currency_exchange.inbound.api.v1.open.dto.CurrencyRateListItemDto;
import kz.shaykemelov.projects.currency_exchange.models.CurrencyRate;
import kz.shaykemelov.projects.currency_exchange.services.CurrencyRatesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/open/currency")
public class CurrencyController
{
    private final CurrencyRatesService currencyRatesService;

    @Autowired
    public CurrencyController(final CurrencyRatesService currencyRatesService)
    {
        this.currencyRatesService = currencyRatesService;
    }

    @RequestMapping
    public List<CurrencyRateListItemDto> getAll(
            @RequestParam("source_currency")
            final String sourceCurrency,
            @Nullable
            @RequestParam(value = "date", required = false)
            final LocalDate inputDate)
    {
        final LocalDate date = inputDate == null ? LocalDate.now() : inputDate;

        return currencyRatesService.listCurrencyRatesByDate(sourceCurrency, date)
                .stream()
                .map(this::map)
                .toList();
    }

    private CurrencyRateListItemDto map(final CurrencyRate currencyRate)
    {
        return new CurrencyRateListItemDto(
                currencyRate.getSourceCurrency(),
                currencyRate.getTargetCurrency(),
                currencyRate.getExchangeRate()
        );
    }

    @RequestMapping("/convert")
    public ConvertCurrencyDto convert(
            @RequestParam("source_currency")
            final String sourceCurrency,
            @RequestParam("target_currency")
            final String targetCurrency,
            @RequestParam("source_currency_value")
            final BigDecimal sourceCurrencyValue,
            @Nullable
            @RequestParam(value = "date", required = false)
            final LocalDate inputDate)
    {
        final var date = inputDate == null ? LocalDate.now() : inputDate;

        final var convertedCurrency = currencyRatesService.convert(
                sourceCurrency,
                targetCurrency,
                sourceCurrencyValue,
                date
        );

        return new ConvertCurrencyDto(
                convertedCurrency.sourceCurrency(),
                convertedCurrency.targetCurrency(),
                convertedCurrency.exchangeRate(),
                convertedCurrency.sourceCurrencyValue(),
                convertedCurrency.targetCurrencyValue()
        );
    }
}
