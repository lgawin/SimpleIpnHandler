package pl.lgawin.paypal.ipn.api;

import com.google.common.collect.ImmutableList;

import java.util.Collection;
import java.util.List;

import pl.lgawin.paypal.ipn.dto.HttpRequestDetails;

public class NotificationsResponse {

    private final int size;
    private final List<HttpRequestDetails> items;

    public NotificationsResponse(Collection<HttpRequestDetails> requests) {
        size = requests.size();
        items = ImmutableList.copyOf(requests);
    }

    public int getSize() {
        return size;
    }

    public List<HttpRequestDetails> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "NotificationsResponse{" +
                "size=" + size +
                ", items=" + items +
                '}';
    }
}
