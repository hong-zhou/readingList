package com.hongzhou.readinglist.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.hongzhou.readinglist.entities.Book;
import com.hongzhou.readinglist.entities.Reader;

public interface ReadingListRepository extends JpaRepository<Book, Long> {
	
	List<Book> findByReader(Reader reader);

}
