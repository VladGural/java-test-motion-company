package pro.gural.company.component_test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.net.URI;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class BaseComponentTest {

    protected static ObjectMapper mapper = new ObjectMapper().findAndRegisterModules()
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    @Autowired
    protected TestRestTemplate template;
    protected URI locationHeader;

    // rest calls

    public String get(String url) throws Exception {
        return callRest(url, HttpMethod.GET, null, HttpStatus.OK);
    }

    public String get(String url, HttpStatus status) throws Exception {
        return callRest(url, HttpMethod.GET, null, status);
    }

    public String get(String url, Object body) throws Exception {
        return callRest(url, HttpMethod.GET, body, HttpStatus.OK);
    }

    public String post(String url, HttpStatus status) throws Exception {
        return callRest(url, HttpMethod.POST, null, status);
    }

    public String post(String url, Object body) throws Exception {
        return callRest(url, HttpMethod.POST, body, HttpStatus.CREATED);
    }

    public String post(String url, Object body, HttpStatus status) throws Exception {
        return callRest(url, HttpMethod.POST, body, status);
    }

    public String put(String url, Object body) throws Exception {
        return callRest(url, HttpMethod.PUT, body, HttpStatus.OK);
    }

    public String patch(String url, String body) throws Exception {
        return callRest(url, HttpMethod.PATCH, body, HttpStatus.PARTIAL_CONTENT);
    }

    public String patch(String url, String body, HttpStatus status) throws Exception {
        return callRest(url, HttpMethod.PATCH, body, status);
    }

    public String delete(String url) throws Exception {
        return callRest(url, HttpMethod.DELETE, null, HttpStatus.NO_CONTENT);
    }

    public String delete(String url, HttpStatus status) throws Exception {
        return callRest(url, HttpMethod.DELETE, null, status);
    }

    public String callRest(String url, HttpMethod method, Object body, HttpStatus expectedStatus) throws Exception {
        var response = template.exchange(url, method, getHttpEntity(body), String.class);
        assertThat(response.getBody(), response.getStatusCode(), is(expectedStatus));
        this.locationHeader = response.getHeaders().getLocation();
        return response.getBody();
    }

    private HttpEntity<String> getHttpEntity(Object message) throws Exception {
        String msg = null;
        if (message instanceof String) {
            msg = (String) message;
        } else {
            msg = mapper.writeValueAsString(message);
        }
        return new HttpEntity<>(msg, getHeaders());
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
