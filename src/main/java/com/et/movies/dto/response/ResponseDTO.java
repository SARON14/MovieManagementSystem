package com.et.movies.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Collection;
import java.util.Collections;

/**
 *
 * @author sharon
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
public class ResponseDTO<E> {

    private String statusCode;
    private String statusMessage;
    private Collection<E> data;
    private Collection<E> summary;
    private Collection<String> errors;
    private String timestamp;
    private String status;
    private String error;
    private String trace;
    private String message;
    private String path;

    public ResponseDTO(String statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.data = Collections.emptyList();
        this.errors = Collections.emptyList();
    }

    public ResponseDTO(String statusCode, String statusMessage, Collection<E> data) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.data = data;
        this.errors = Collections.emptyList();
    }

    public ResponseDTO(String statusCode, String statusMessage, Collection<E> data, Collection<E> summary) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.data = data;
        this.summary = summary;
        this.errors = Collections.emptyList();
    }
}
