package pl.lgawin.paypal.ipn.api;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.Strings;

import org.apache.commons.codec.Charsets;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.inject.Inject;
import pl.lgawin.paypal.ipn.data.NotificationRepository;
import pl.lgawin.paypal.ipn.dto.HttpRequestDetails;
import pl.lgawin.paypal.ipn.utils.SchwartzianTransformItem;

@RestController
public class MainController {

    private static final String PAY_KEY_PARAM = "pay_key";

    private final NotificationRepository notificationRepository;

    @Inject
    public MainController(NotificationRepository notificationRepository) {
        this.notificationRepository = checkNotNull(notificationRepository);
    }

    @RequestMapping("/")
    String home() {
        return "Hello, this is Simple Ipn Handler.";
    }

    @RequestMapping(path = "/ipn", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    void handleNotification(@RequestBody String body, @RequestHeader HttpHeaders headers) {
        notificationRepository.log(new HttpRequestDetails(DateTime.now(DateTimeZone.getDefault()), headers, body));
    }

    @RequestMapping(path = "/notifications", method = RequestMethod.GET)
    NotificationsResponse notificationsByPayKey(@RequestParam(required = false, name = PAY_KEY_PARAM) String payKey) {
        Collection<HttpRequestDetails> notifications = notificationRepository.all();
        if (!Strings.isNullOrEmpty(payKey)) notifications = notifications.stream()
                .map(entry -> new SchwartzianTransformItem<>(entry,
                        request -> URLEncodedUtils.parse(request.getBody(), Charsets.UTF_8).stream()
                                .map(pair -> new SchwartzianTransformItem<>(pair, NameValuePair::getName))
                                .filter(name -> PAY_KEY_PARAM.equals(name.mapped()))
                                .findAny()
                                .map(SchwartzianTransformItem::original)
                                .map(NameValuePair::getValue)))
                .filter(value -> value.mapped().isPresent())
                .filter(value -> payKey.equalsIgnoreCase(value.mapped().get()))
                .map(SchwartzianTransformItem::original)
                .collect(Collectors.toList());
        return new NotificationsResponse(notifications);
    }

}
