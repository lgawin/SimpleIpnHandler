package pl.lgawin.paypal.ipn.api;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.Strings;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import pl.lgawin.paypal.ipn.data.NotificationsRepository;
import pl.lgawin.paypal.ipn.dto.HttpRequestDetails;

@RestController
public class MainController {

    private final NotificationsRepository notificationsRepository;

    @Inject
    public MainController(NotificationsRepository notificationsRepository) {
        this.notificationsRepository = checkNotNull(notificationsRepository);
    }

    @RequestMapping("/")
    String home() {
        return "Hello, this is Simple Ipn Handler.";
    }

    @RequestMapping(path = "/ipn", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    void handleNotification(@RequestBody String body, @RequestHeader HttpHeaders headers) {
        notificationsRepository.log(new HttpRequestDetails(DateTime.now(DateTimeZone.getDefault()), headers, body));
    }

    @RequestMapping(path = "/notifications", method = RequestMethod.GET)
    NotificationsResponse notifications(@RequestParam(required = false, name = "pay_key") String payKey) {
        return new NotificationsResponse(Strings.isNullOrEmpty(payKey)
                ? notificationsRepository.all()
                : notificationsRepository.byPayKey(payKey));
    }

    @RequestMapping(path = "/notifications/latest", method = RequestMethod.GET)
    HttpRequestDetails latestNotification(@RequestParam(required = false, name = "pay_key") String payKey) {
        return (Strings.isNullOrEmpty(payKey)
                ? notificationsRepository.all().stream().findFirst()
                : notificationsRepository.latestByPayKey(payKey))
                .orElse(null);
    }

    @RequestMapping(path = "/notifications/byPayKey/{payKey}/latest", method = RequestMethod.GET)
    HttpRequestDetails latestNotificationsByPayKey(@PathVariable String payKey) {
        return notificationsRepository.latestByPayKey(payKey).orElse(null);
    }

    @RequestMapping(path = "/notifications/byPayKey/{payKey}", method = RequestMethod.GET)
    NotificationsResponse notificationsByPayKey(@PathVariable String payKey) {
        return new NotificationsResponse(notificationsRepository.byPayKey(payKey));
    }
}
