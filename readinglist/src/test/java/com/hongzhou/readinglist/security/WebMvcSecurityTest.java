package com.hongzhou.readinglist.security;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.hongzhou.readinglist.ReadinglistApplication;
import com.hongzhou.readinglist.entities.Reader;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ReadinglistApplication.class)
public class WebMvcSecurityTest {

	@Autowired
	WebApplicationContext webContext;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webContext).apply(SecurityMockMvcConfigurers.springSecurity())
				.build();
	}

/*	@Test
	public void homePage_unauthenticateduser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/")).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.header().string("Location", "http://localhost/login"));

	}*/

	@Test
	@WithUserDetails("Hong")
	//@WithMockUser(username = "Hong", password = "password", roles = "READER")
	public void homePage_authenticatedUser() throws Exception {

		Reader expectedReader = new Reader();
		expectedReader.setUsername("Hong");
		expectedReader.setPassword("password");
		expectedReader.setFullname("Hong Zhou");

		mockMvc.perform(MockMvcRequestBuilders.get("/"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("readingList"))
			.andExpect(MockMvcResultMatchers.model().attribute("reader",
						Matchers.samePropertyValuesAs(expectedReader)))
			.andExpect(MockMvcResultMatchers.model().attribute("books", Matchers.hasSize(0)));
	}

}
