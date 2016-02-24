package pl.lgawin.paypal.ipn.data;

import pl.lgawin.paypal.ipn.dto.HttpRequestDetails;

public interface HttpRequestLogger {
    void log(HttpRequestDetails details);
}
