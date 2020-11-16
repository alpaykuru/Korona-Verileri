package com.example.demo.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RestServiceWebContextTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    MockMvc mockMvc;

    private MockMvc mvc;

    @Before
    public void setup() throws Exception {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testAdd() throws Exception {
        String text = "21.03.2020 tarihinde Muş için korona virüs ile ilgili yeni bir açıklama yapıldı. Korona virüs salgınında yapılan testlerde 1 yeni vaka tespit edildi.taburcu sayısı ise 1 oldu.  1 kişin de vefat ettiği öğrenildi.";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/coronaData/add").
                queryParam("text",text);
        MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
    }

    @Test
    public void testMissingDate() throws Exception {
        String text = "tarihinde Muş için korona virüs ile ilgili yeni bir açıklama yapıldı. Korona virüs salgınında yapılan testlerde 1 yeni vaka tespit edildi.taburcu sayısı ise 1 oldu.  1 kişin de vefat ettiği öğrenildi.";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/coronaData/add").
                queryParam("text",text);
        MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
    }

    @Test
    public void testMissingCity() throws Exception {
        String text = "21.03.2020 tarihinde için korona virüs ile ilgili yeni bir açıklama yapıldı. Korona virüs salgınında yapılan testlerde 1 yeni vaka tespit edildi.taburcu sayısı ise 1 oldu.  1 kişin de vefat ettiği öğrenildi.";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/coronaData/add").
                queryParam("text",text);
        MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
    }

    @Test
    public void getData() throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("selectedIl", "Muş");
        requestParams.add("kumulatif", "false");
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/coronaData/il").
                queryParams(requestParams);
        MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
    }

    @Test
    public void getDataKumulatif() throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("selectedIl", "Muş");
        requestParams.add("kumulatif", "true");
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/coronaData/il").
                queryParams(requestParams);
        MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
    }
}