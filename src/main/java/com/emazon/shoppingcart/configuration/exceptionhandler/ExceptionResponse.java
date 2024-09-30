package com.emazon.shoppingcart.configuration.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ExceptionResponse {

    private String message;
    private String status;
    private LocalDateTime localDateTime;

    @JsonCreator
    public ExceptionResponse(
            @JsonProperty("message") String message,
            @JsonProperty("status") String status,
            @JsonProperty("localDateTime") LocalDateTime localDateTime) {
        this.message = message;
        this.status = status;
        this.localDateTime = localDateTime;
    }

}