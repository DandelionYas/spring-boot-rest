package com.in28minutes.springboot.springbootrestapi;

import com.in28minutes.springboot.model.Question;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SurveyControllerIntegrationTest {

    private static final HttpHeaders httpHeaders = new HttpHeaders();
    private final TestRestTemplate testRestTemplate = new TestRestTemplate();
    @LocalServerPort
    private Integer port;

    @BeforeAll
    private static void initialize() {
        httpHeaders.add("Authorization", createHttpAuthenticationHeaderValue("user1", "secret1"));
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    }

    private static String createHttpAuthenticationHeaderValue(String userId, String password) {
        String auth = userId + ":" + password;
        return "Basic " + Base64.encode(auth.getBytes(Charset.forName("UTF-8")));
    }

    @Test
    void testRetrieveSurveyQuestion() throws Exception {
        HttpEntity entitiy = new HttpEntity<String>(null, httpHeaders);
        ResponseEntity<String> response = testRestTemplate.exchange(
                createUrl("/surveys/Survey1/questions/Question1"), HttpMethod.GET, entitiy, String.class);
        assertTrue(response.getBody().contains("\"id\":\"Question1\""));
        String expected = "{id:Question1, description:\"Largest Country in the World\", correctAnswer:Russia}";
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    void retrieveAllSurveyQuestions() throws Exception {
        HttpEntity entitiy = new HttpEntity<String>(null, httpHeaders);
        ResponseEntity<List<Question>> response = testRestTemplate.exchange(createUrl("/surveys/Survey1/questions"), HttpMethod.GET, entitiy,
                new ParameterizedTypeReference<List<Question>>() {
                });
        Question question = new Question("Question1",
                "Largest Country in the World", "Russia", Arrays.asList(
                "India", "Russia", "United States", "China"));
        assertTrue(response.getBody().contains(question));
    }

    @Test
    void createSurveyQuestion() throws Exception {
        Question question = new Question("DOESN'T MATTER", "Smallest Number",
                "1", Arrays.asList("1", "2", "3", "4"));
        HttpEntity entitiy = new HttpEntity<>(question, httpHeaders);
        ResponseEntity<String> response = testRestTemplate.exchange(
                createUrl("/surveys/Survey1/questions"),
                HttpMethod.POST, entitiy, String.class);
        String actualLocation = response.getHeaders().get(HttpHeaders.LOCATION).get(0);
        assertTrue(actualLocation.contains("/surveys/Survey1/questions"));
    }

    private String createUrl(String url) {
        return String.format("http://localhost:%s" + url, port);
    }
}
