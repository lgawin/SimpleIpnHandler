package pl.lgawin.paypal.ipn.data;

import org.apache.commons.codec.Charsets;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import pl.lgawin.paypal.ipn.dto.HttpRequestDetails;
import pl.lgawin.paypal.ipn.utils.SchwartzianTransformItem;

@Component
public class InMemoryNotificationsRepository implements NotificationsRepository, InitializingBean {
    private final static Logger logger = LoggerFactory.getLogger(NotificationsRepository.class);
    private static final String PAY_KEY_PARAM = "pay_key";

    private final LinkedList<HttpRequestDetails> list = new LinkedList<>();

    @Override
    public Collection<HttpRequestDetails> all() {
        return Collections.unmodifiableList(list);
    }

    @Override
    public Collection<HttpRequestDetails> byPayKey(String payKey) {
        return notificationsByPayKayAsStream(payKey).collect(Collectors.toList());
    }

    @Override
    public Optional<HttpRequestDetails> latestByPayKey(String payKey) {
        return notificationsByPayKayAsStream(payKey).findFirst();
    }

    @Override
    public void log(HttpRequestDetails details) {
        logger.info(String.valueOf(details));
        list.addFirst(details);
    }

    private Stream<HttpRequestDetails> notificationsByPayKayAsStream(String payKey) {
        return all().stream()
                .map(entry ->
                        new SchwartzianTransformItem<>(entry,
                                request -> URLEncodedUtils.parse(request.getBody(), Charsets.UTF_8).stream()
                                        .map(pair -> new SchwartzianTransformItem<>(pair, NameValuePair::getName))
                                        .filter(name -> PAY_KEY_PARAM.equals(name.mapped()))
                                        .findAny()
                                        .map(SchwartzianTransformItem::original)
                                        .map(NameValuePair::getValue)))
                .filter(value -> value.mapped().isPresent())
                .filter(value -> payKey.equalsIgnoreCase(value.mapped().get()))
                .map(SchwartzianTransformItem::original);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
    }
}
