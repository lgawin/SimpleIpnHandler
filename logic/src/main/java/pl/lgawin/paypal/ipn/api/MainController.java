package pl.lgawin.paypal.ipn.api;

import static com.google.common.base.Preconditions.checkNotNull;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import pl.lgawin.paypal.ipn.data.NotificationRepository;
import pl.lgawin.paypal.ipn.dto.HttpRequestDetails;

@RestController
public class MainController {

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
    NotificationsResponse notifications(){
        return new NotificationsResponse(notificationRepository.all());
    }

}
