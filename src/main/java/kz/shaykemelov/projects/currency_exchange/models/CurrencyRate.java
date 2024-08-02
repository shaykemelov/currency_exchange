package kz.shaykemelov.projects.currency_exchange.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@IdClass(CurrencyRateId.class)
@Table(name = "tbl_currency_rates")
public class CurrencyRate
{
    @Id
    @Column(name = "source_currency", nullable = false, length = 3)
    private String sourceCurrency;

    @Id
    @Column(name = "target_currency", nullable = false, length = 3)
    private String targetCurrency;

    @Id
    @Column(name = "exchange_rate", nullable = false)
    private BigDecimal exchangeRate;

    @Id
    @Column(name = "rate_date", nullable = false)
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

        final CurrencyRate that = (CurrencyRate) o;

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
