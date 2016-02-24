package pl.lgawin.paypal.ipn.data;

import java.util.Collection;
import java.util.Optional;

import pl.lgawin.paypal.ipn.dto.HttpRequestDetails;

public interface NotificationsRepository extends HttpRequestLogger {
    Collection<HttpRequestDetails> all();
    Collection<HttpRequestDetails> byPayKey(String payKey);
    Optional<HttpRequestDetails> latestByPayKey(String payKey);
}
