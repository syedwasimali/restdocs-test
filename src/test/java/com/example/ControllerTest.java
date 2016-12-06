package com.example;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by sali on 12/5/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@DirtiesContext
public class ControllerTest
{
    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");

    private MockMvc mockMvc;

    @InjectMocks
    Controller controller;

    @Before
    public void setup()
    {
        MockitoAnnotations.initMocks(this);

        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).
                apply(documentationConfiguration(this.restDocumentation)).
                build();
    }

    @Test
    public void prettyPrintOnlyTest() throws Exception
    {
        /**
         * There is no way to leverage other Jackson attributes like
         *   INCLUDE_NON_NULLS
         *
         * The ObjectMapper is baked within.
         */

        String uri = "/info";

        MvcResult result = mockMvc.
                perform(MockMvcRequestBuilders.get(uri).
                        accept(MediaType.APPLICATION_JSON)).
        andExpect(status().isOk()).
        andDo(document("test-1-pretty-print",
                        preprocessResponse(prettyPrint()))).
        andReturn();
    }

    @Test
    public void ignoredNotWorkingTest() throws Exception
    {
        /**
         * "If you do not want to document a field, you can mark it as ignored". However,
         *   it doesn't appear to be working as prescribed.
         *
         */

        String uri = "/info";

        MvcResult result = mockMvc.
                perform(MockMvcRequestBuilders.get(uri).
                        accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).
                andDo(document("test-1-ignored",
                        responseFields(
                                fieldWithPath("name").description("Full Name"),
//                fieldWithPath("address").description("Address"),
                                fieldWithPath("address").ignored(),
                                fieldWithPath("company").description("Company")))).
                andReturn();
    }
}