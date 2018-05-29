package com.fangcm.modules.core;

import com.fangcm.ButterflyApplication;
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
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/core/login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", "13701011234")
                .param("password", "test1234")
                .accept(MediaType.APPLICATION_JSON)
        ).andReturn();

        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        System.out.println(content);
        Assert.assertTrue("错误，正确的返回值为200", status == 200);
    }

}
