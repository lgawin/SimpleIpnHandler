package pl.lgawin.paypal.ipn.dto;

import org.joda.time.DateTime;
import org.springframework.http.HttpHeaders;

public class HttpRequestDetails {
    private final DateTime dateTime;
    private final HttpHeaders headers;
    private final String body;

    public HttpRequestDetails(DateTime dateTime, HttpHeaders headers, String body) {
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
