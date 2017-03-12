package com.upm.etsist.dao;

import java.util.List;

import com.upm.etsist.dto.SearchResponseDTO;

public interface SearchResponseDAO {
	void save(SearchResponseDTO response);
	void saveAll(List<SearchResponseDTO> responsesist);
	void update(SearchResponseDTO response);
	void delete(SearchResponseDTO response);
	void deleteAll();
	List<SearchResponseDTO> findSPA(String text);
	List<SearchResponseDTO> findENG(String text);
	List<SearchResponseDTO> findAll();
}
