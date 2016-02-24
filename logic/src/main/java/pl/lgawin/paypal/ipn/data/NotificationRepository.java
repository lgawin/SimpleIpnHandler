package pl.lgawin.paypal.ipn.data;

import java.util.Collection;

import pl.lgawin.paypal.ipn.dto.HttpRequestDetails;

public interface NotificationRepository extends HttpRequestLogger {
    Collection<HttpRequestDetails> all();
}
