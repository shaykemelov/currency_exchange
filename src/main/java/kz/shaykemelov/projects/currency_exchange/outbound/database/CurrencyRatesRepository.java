package kz.shaykemelov.projects.currency_exchange.outbound.database;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import kz.shaykemelov.projects.currency_exchange.models.CurrencyRate;
import kz.shaykemelov.projects.currency_exchange.models.CurrencyRateId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CurrencyRatesRepository extends JpaRepository<CurrencyRate, CurrencyRateId>
{
    @Query("select cr from CurrencyRate cr where cr.sourceCurrency = :source_currency and cr.rateDate = :date")
    List<CurrencyRate> findCurrencyRates(
            @Param("source_currency")
            final String sourceCurrency,
            @Param("date")
            final LocalDate date);

    @Query("""
            select
                cr
            from
                CurrencyRate cr
            where
                cr.sourceCurrency = :source_currency
                and cr.rateDate = :date
                and cr.targetCurrency = :target_currency
            """)
    Optional<CurrencyRate> findCurrencyRate(
            @Param("source_currency")
            final String sourceCurrency,
            @Param("date")
            final LocalDate date,
            @Param("target_currency")
            final String targetCurrency);
}
