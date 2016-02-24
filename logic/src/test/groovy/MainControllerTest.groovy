import org.joda.time.DateTime
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import pl.lgawin.paypal.ipn.api.MainController
import pl.lgawin.paypal.ipn.data.NotificationsRepository
import pl.lgawin.paypal.ipn.dto.HttpRequestDetails
import spock.lang.Ignore
import spock.lang.Specification

@Ignore
class MainControllerTest extends Specification {

    NotificationsRepository repository
    MockMvc mockMvc

    def setup() {
        repository = Mock(NotificationsRepository)
        mockMvc = MockMvcBuilders.standaloneSetup(new MainController(repository)).build()
    }

    def "a"() {
//        given:
//        mockMvc.perform(post("/ipn")
//                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//                .param("key", "value"))

        when:
        def result = mockMvc.perform(get("/notifications"))

        then:
//        repository.all() >> Collections.emptyList()
        repository.all() >> Collections.singleton(new HttpRequestDetails(DateTime.now(), null, "key=value"))
//        1 * repository.all() >> null
//        1 * repository.all() >> [] as Collection

        result.andExpect(status().isOk())
        result.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        result.andExpect(jsonPath('$.size').value(1))
        result.andExpect(jsonPath('$.items').value("key=value"))
        result.andExpect(jsonPath('$.items[0].body').value("key=value"))
        result.andExpect(jsonPath('$.items[0].dateTime').isString())
    }

    def "b"() {
        given:
        def underTest = new UnderTest(repository);

        when:
        def result = underTest.do()

        then:
        repository.all() >> Collections.singletonList(new HttpRequestDetails(DateTime.now(), null, "key=value"))

        result == [] as Collection
    }

    static class UnderTest {

        private final NotificationsRepository repo

        UnderTest(NotificationsRepository repo) {
            this.repo = repo
        }

        Collection<HttpRequestDetails> "do"() {
            repo.all()
        }
    }
}
