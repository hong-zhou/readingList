package com.hongzhou.readinglist.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hongzhou.readinglist.config.AmazonProperties;
import com.hongzhou.readinglist.domain.Book;
import com.hongzhou.readinglist.repository.ReadingListRepository;

@Controller
@RequestMapping("/readingList")
public class ReadingListController {

  private static final String reader = "hong";
  
  	private AmazonProperties amazonProperties;
  	
	private ReadingListRepository readingListRepository;

	@Autowired
	public ReadingListController(ReadingListRepository readingListRepository, AmazonProperties amazonProperties) {
		this.readingListRepository = readingListRepository;
		this.amazonProperties = amazonProperties;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String readersBooks(Model model) {
		
		List<Book> readingList = readingListRepository.findByReader(reader);
		if (readingList != null) {
			model.addAttribute("books", readingList);
			model.addAttribute("reader", reader);
			model.addAttribute("amazonID", amazonProperties.getAssociateId());
		}
		return "readingList";
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String addToReadingList(Book book) {
		book.setReader(reader);
		readingListRepository.save(book);
		return "redirect:/readingList";
	}
	
}
