package com.hongzhou.readinglist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hongzhou.readinglist.entities.Reader;

public interface ReaderRepository extends JpaRepository<Reader, String> {

}
