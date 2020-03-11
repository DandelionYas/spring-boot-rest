package com.in28minutes.springboot.springbootrestapi;

import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.Collections;

import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringBootRestApiApplicationTests {

    @LocalServerPort
    private Integer port;

    @Test
    void testRetrieveSurveyQuestion() throws Exception {
        String url = String.format("http://localhost:%s/surveys/Survey1/questions/Question1", port);
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity entitiy = new HttpEntity<String>(null, httpHeaders);
        ResponseEntity<String> response = testRestTemplate.exchange(url, HttpMethod.GET, entitiy, String.class);
        assertTrue("Does not contain expected response", response.getBody().contains("\"id\":\"Question1\""));
        String expected = "{id:Question1, description:\"Largest Country in the World\", correctAnswer:Russia}";
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

}
