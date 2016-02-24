package pl.lgawin.paypal.ipn.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import pl.lgawin.paypal.ipn.dto.HttpRequestDetails;

public class InMemoryNotificationsRepository implements NotificationRepository {
    private final Logger logger = LoggerFactory.getLogger(NotificationRepository.class);
    private final LinkedList<HttpRequestDetails> list = new LinkedList<>();

    @Override
    public Collection<HttpRequestDetails> all() {
        return Collections.unmodifiableList(list);
    }

    @Override
    public void log(HttpRequestDetails details) {
        logger.info(String.valueOf(details));
        list.addFirst(details);
    }
}
