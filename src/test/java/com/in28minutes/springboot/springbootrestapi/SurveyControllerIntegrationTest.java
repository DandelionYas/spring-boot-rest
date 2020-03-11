package com.in28minutes.springboot.springbootrestapi;

import com.in28minutes.springboot.model.Question;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SurveyControllerIntegrationTest {

    @LocalServerPort
    private Integer port;
    private static final HttpHeaders httpHeaders = new HttpHeaders();
    private final TestRestTemplate testRestTemplate = new TestRestTemplate();

    @BeforeAll
    private static void initialize() {
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    }

    @Test
    void testRetrieveSurveyQuestion() throws Exception {
        HttpEntity entitiy = new HttpEntity<String>(null, httpHeaders);
        ResponseEntity<String> response = testRestTemplate.exchange(
                createUrl("/surveys/Survey1/questions/Question1"), HttpMethod.GET, entitiy, String.class);
        assertTrue("Does not contain expected response", response.getBody().contains("\"id\":\"Question1\""));
        String expected = "{id:Question1, description:\"Largest Country in the World\", correctAnswer:Russia}";
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    void retrieveAllSurveyQuestions() throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();

        HttpEntity entitiy = new HttpEntity<String>(null, httpHeaders);
        ResponseEntity<List<Question>> response = testRestTemplate.exchange(createUrl("/surveys/Survey1/questions"), HttpMethod.GET, entitiy,
                new ParameterizedTypeReference<List<Question>>() {});
        Question question = new Question("Question1",
                "Largest Country in the World", "Russia", Arrays.asList(
                "India", "Russia", "United States", "China"));
        assertTrue("Does not contain expected response", response.getBody().contains(question));
    }

    @Test
    void createSurveyQuestion() throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        Question question = new Question("DOESN'T MATTER", "Smallest Number",
                "1", Arrays.asList("1", "2", "3", "4"));
        HttpEntity entitiy = new HttpEntity<>(question, httpHeaders);
        ResponseEntity<String> response = testRestTemplate.exchange(
                createUrl("/surveys/Survey1/questions"),
                HttpMethod.POST, entitiy, String.class);
        String actualLocation = response.getHeaders().get(HttpHeaders.LOCATION).get(0);
        assertTrue("Does not contain expected response", actualLocation.contains("/surveys/Survey1/questions"));
    }

    private String createUrl(String url) {
        return String.format("http://localhost:%s" + url, port);
    }
}
