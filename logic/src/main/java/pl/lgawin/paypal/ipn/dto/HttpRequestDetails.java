package pl.lgawin.paypal.ipn.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.joda.time.DateTime;
import org.springframework.http.HttpHeaders;

public class HttpRequestDetails {
    private final DateTime dateTime;
    private final HttpHeaders headers;
    private final String body;

    @JsonCreator
    public HttpRequestDetails(@JsonProperty("dateTime") DateTime dateTime, @JsonProperty("headers") HttpHeaders headers,
                              @JsonProperty("body") String body) {
        this.dateTime = dateTime;
        this.headers = headers;
        this.body = body;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "HttpRequestDetails{" +
                "dateTime=" + dateTime +
                ", headers=" + headers +
                ", body='" + body + '\'' +
                '}';
    }
}
