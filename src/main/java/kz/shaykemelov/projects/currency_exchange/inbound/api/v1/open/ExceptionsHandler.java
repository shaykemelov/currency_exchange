package kz.shaykemelov.projects.currency_exchange.inbound.api.v1.open;

import java.time.OffsetDateTime;

import kz.shaykemelov.projects.currency_exchange.exceptions.CurrencyExchangeUnavailable;
import kz.shaykemelov.projects.currency_exchange.inbound.api.v1.open.dto.ErrorDto;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler
{
    @Nullable
    @ExceptionHandler(CurrencyExchangeUnavailable.class)
    public ResponseEntity<?> handleCurrencyExchangeUnavailable(final CurrencyExchangeUnavailable e, final WebRequest request)
    {
        final var errorDto = new ErrorDto(
                OffsetDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage(),
                ((ServletWebRequest) request).getRequest().getRequestURI()
        );

        return handleExceptionInternal(e, errorDto, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
