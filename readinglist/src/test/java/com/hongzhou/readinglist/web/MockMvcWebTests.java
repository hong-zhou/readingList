package com.hongzhou.readinglist.web;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import org.hamcrest.Matchers;
import org.hamcrest.beans.SamePropertyValuesAs;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.hongzhou.readinglist.ReadinglistApplication;
import com.hongzhou.readinglist.domain.Book;
import com.hongzhou.readinglist.domain.Reader;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ReadinglistApplication.class)
/*
 * @RunWith(SpringJUnit4ClassRunner.class)
 * 
 * @ContextConfiguration(classes = ReadinglistApplication.class)
 * 
 * @WebAppConfiguration
 */
public class MockMvcWebTests {

	@Autowired
	private WebApplicationContext webContext;

	private MockMvc mockMvc;

	@Before
	public void setUpMockMvc() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webContext)./* apply(springSecurity()). */build();
	}

	@Test
	public void homePage() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/readingList")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("readingList"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("books"))
				.andExpect(MockMvcResultMatchers.model().attribute("books", Matchers.is(Matchers.empty())));
	}

	@Test
	public void postBook() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/readingList").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("title", "Book Title").param("author", "Book Author").param("isbn", "1234567890")
				.param("description", "Description")).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.header().string("Location", "/"));

		Book expectedBook = new Book();
		expectedBook.setId(1L);
		expectedBook.setTitle("Book Title");
		expectedBook.setAuthor("Book Author");
		//expectedBook.setReader("Hong");
		expectedBook.setIsbn("1234567890");
		expectedBook.setDescription("Description");

		mockMvc.perform(MockMvcRequestBuilders.get("/readingList")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("readingList"))
				.andExpect(MockMvcResultMatchers.model().attribute("books", Matchers.hasSize(1)))
				.andExpect(MockMvcResultMatchers.model().attribute("books", Matchers.contains(Matchers.samePropertyValuesAs(expectedBook))))
				.andExpect(MockMvcResultMatchers.model().attribute("amazonID", "hong"));
	}

}
