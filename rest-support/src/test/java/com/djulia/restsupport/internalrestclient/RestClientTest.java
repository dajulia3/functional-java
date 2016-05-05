package com.djulia.restsupport.internalrestclient;

import com.djulia.result.Result;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.Fail.fail;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

public class RestClientTest {

    private final RestTemplate restTemplate = new RestTemplate();
    private RestClient restClient = new RestClient(restTemplate);
    private final MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);

    private static class Car{
        private final String makeAndModel;

        @JsonCreator
        private Car(@JsonProperty("makeAndModel") String makeAndModel) {
            this.makeAndModel = makeAndModel;
        }

        public String getMakeAndModel() {
            return makeAndModel;
        }
    }

    @Test
    public void getForResource_success(){
        mockServer.expect(requestTo("http://example.com/cars/1"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("{ \"makeAndModel\" : \"BMW 325xi\"}", MediaType.APPLICATION_JSON));

        Result<Car, RestClientError> r = restClient.getSomeResource("http://example.com/cars/1", Car.class);

        assertThat(r).isNotNull();
        r.apply(s -> {
            assertThat(s.getMakeAndModel()).isEqualTo("BMW 325xi");
        }, f -> {
        });
    }

    @Test
    public void getForResource_clientFailure(){
        mockServer.expect(requestTo("http://example.com/cars/1"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.NOT_FOUND).body("oops."));

        Result<Car, RestClientError> r = restClient.getSomeResource("http://example.com/cars/1", Car.class);

        assertThat(r).isNotNull();
        r.apply(s -> {
        }, f -> {
            assertThat(f).isEqualTo(RestClientError.httpClientError(new HttpClientError(404, "oops.")));
        });
        fail("now change this to deserialize into common error format. Or perhaps create an internal http client" +
                "that delegates to this more general http client for internal AND external requests.");
    }

}