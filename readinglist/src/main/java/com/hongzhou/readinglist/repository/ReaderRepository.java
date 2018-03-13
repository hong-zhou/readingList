package com.hongzhou.readinglist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hongzhou.readinglist.domain.Reader;

public interface ReaderRepository extends JpaRepository<Reader, String> {

}
