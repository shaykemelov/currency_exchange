package kz.shaykemelov.projects.currency_exchange.inbound.api.v1.open.dto;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ErrorDto(
        @JsonProperty("timestamp")
        OffsetDateTime timestamp,
        @JsonProperty("status")
        int status,
        @JsonProperty("error")
        String error,
        @JsonProperty("path")
        String path
)
{
}
