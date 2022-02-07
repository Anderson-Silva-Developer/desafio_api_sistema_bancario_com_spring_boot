package br.com.anderson_silva.Banking_system.controllers.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class EndpointUtilTest {

ObjectMapper objectMapper=new ObjectMapper();
    public void expected_success200_post(MockMvc mockMvc, String url, Object contentBody) throws Exception {

        mockMvc.perform(post(url)
                        .contentType("application/json")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .content(objectMapper.writeValueAsString(contentBody)))
                .andExpect(status().is(200));

    }

 public void expected_success201_post(MockMvc mockMvc, String url, Object contentBody) throws Exception {

        mockMvc.perform(post(url)
                        .contentType("application/json")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .content(objectMapper.writeValueAsString(contentBody)))
                .andExpect(status().is(201));

}
 public void expected_success200_get(MockMvc mockMvc,String url,Object contentBody) throws Exception {

        mockMvc.perform(get(url,1)
                        .contentType("application/json")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .content(objectMapper.writeValueAsString(contentBody)))
                .andExpect(status().is(200));

    }
    public ResultActions expected_success200_put(MockMvc mockMvc,String url,Object contentBody) throws Exception {

        ResultActions resultput = mockMvc.perform(put(url, 1)
                        .contentType("application/json")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .content(objectMapper.writeValueAsString(contentBody)))
                .andExpect(status().is(200));
        return resultput;

    }
    public String expected_failure400_get(MockMvc mockMvc,String url,Object contentBody) throws Exception {

        String error=mockMvc.perform(get(url,1)
                        .contentType("application/json")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .content(objectMapper.writeValueAsString(contentBody)))
                .andExpect(status().is(400))
                .andReturn().getResolvedException().getMessage();

        return error;

    }

public String expected_failure400_post(MockMvc mockMvc,String url,Object contentBody) throws Exception {

        String error=mockMvc.perform(post(url)
                        .contentType("application/json")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .content(objectMapper.writeValueAsString(contentBody)))
                .andExpect(status().is(400))
                .andReturn().getResolvedException().getMessage();

        return error;

}


}
