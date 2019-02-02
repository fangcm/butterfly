package com.fangcm.modules.core;

import com.fangcm.ButterflyApplication;
import com.fangcm.modules.core.vo.LoginForm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


/**
 * Created by FangCM on 2018/5/29.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ButterflyApplication.class)
@WebAppConfiguration
public class UserControllerTest {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;

    @Before
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    /* 向/readingList发起一个GET请求 */
    @Test
    public void login() throws Exception {
        LoginForm loginForm = new LoginForm();
        loginForm.setMobile("13701011234");
        loginForm.setPassword("test1234");

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(loginForm);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/core/user/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestJson)
                .accept(MediaType.APPLICATION_JSON)
        ).andReturn();

        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        System.out.println(content);
        Assert.assertTrue("错误，正确的返回值为200", status == 200);
    }

    @Test
    public void findByCondition() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/core/user/findByCondition")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .header("Authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1NDkxODA4MTksInVzZXJuYW1lIjoiMTM3MDEwMTEyMzQifQ.Xr8zip7jsIv1Ik716fi8I05t_4Cr5uptvoO3SCrQvpo")
                .accept(MediaType.APPLICATION_JSON)
        ).andReturn();

        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        System.out.println(content);
        Assert.assertTrue("错误，正确的返回值为200", status == 200);
    }


}
