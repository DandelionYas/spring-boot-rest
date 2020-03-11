package com.in28minutes.springboot.springbootrestapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.in28minutes.springboot.controller.SurveyController;
import com.in28minutes.springboot.model.Question;
import com.in28minutes.springboot.service.SurveyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

/*
* Only launches URLs that are present in SurveyController
* It would not do component scan and would not create all beans
*/
@WebMvcTest(SurveyController.class)
class SurveyControllerUnitTest {
    @MockBean
    private SurveyService surveyService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void retrieveDetailsForQuestion() throws Exception {
        Question mockQuestion = new Question("Question1",
                "Largest Country in the World", "Russia", Arrays.asList(
                "India", "Russia", "United States", "China"));
        Mockito.when(
                surveyService.retrieveQuestion(Mockito.anyString(), Mockito.anyString())
        ).thenReturn(mockQuestion);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/surveys/Survey1/questions/Question1")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        String expected = "{id:Question1, description:\"Largest Country in the World\", correctAnswer:Russia}";
        JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), false);
    }

    @Test
    void createSurveyQuestion() throws Exception {
        Question mockQuestion = new Question("Question1",
                "Largest Country in the World", "Russia", Arrays.asList(
                "India", "Russia", "United States", "China"));
        String questionJson = objectMapper.writeValueAsString(mockQuestion);

        Mockito.when(
                surveyService.addQuestion(Mockito.anyString(), Mockito.any(Question.class))
        ).thenReturn(mockQuestion);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/surveys/Survey1/questions")
                .accept(MediaType.APPLICATION_JSON)
                .content(questionJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        Assertions.assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        Assertions.assertTrue(response.getHeader(HttpHeaders.LOCATION).contains("/surveys/Survey1/questions/Question1"));
    }
}