package kz.shaykemelov.projects.currency_exchange.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class CurrencyRateId
{
    private String sourceCurrency;

    private String targetCurrency;

    private BigDecimal exchangeRate;

    private LocalDate rateDate;

    public String getSourceCurrency()
    {
        return sourceCurrency;
    }

    public void setSourceCurrency(final String sourceCurrency)
    {
        this.sourceCurrency = sourceCurrency;
    }

    public String getTargetCurrency()
    {
        return targetCurrency;
    }

    public void setTargetCurrency(final String targetCurrency)
    {
        this.targetCurrency = targetCurrency;
    }

    public BigDecimal getExchangeRate()
    {
        return exchangeRate;
    }

    public void setExchangeRate(final BigDecimal exchangeRate)
    {
        this.exchangeRate = exchangeRate;
    }

    public LocalDate getRateDate()
    {
        return rateDate;
    }

    public void setRateDate(final LocalDate rateDate)
    {
        this.rateDate = rateDate;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }

        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        final CurrencyRateId that = (CurrencyRateId) o;
        return Objects.equals(sourceCurrency, that.sourceCurrency)
                && Objects.equals(targetCurrency, that.targetCurrency)
                && Objects.equals(exchangeRate, that.exchangeRate)
                && Objects.equals(rateDate, that.rateDate);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(sourceCurrency, targetCurrency, exchangeRate, rateDate);
    }
}
