package com.github.gabrielspk.cadastro_os.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.gabrielspk.cadastro_os.dto.v1.SearchRequestDTO;
import com.github.gabrielspk.cadastro_os.dto.v1.SearchResultDTO;
import com.github.gabrielspk.cadastro_os.services.SearchService;

@RestController
@RequestMapping("/api/search/v1")
public class SearchResource {
	
	@Autowired
	private SearchService service;
	
	@PostMapping
    public ResponseEntity<SearchResultDTO> pesquisarDocumentos(@RequestBody SearchRequestDTO request) {
        SearchResultDTO searchResult = service.executeDocumentsSearch(
                request.getDiretorio(),
        		request.isValidarFpl(),
        		request.getDocumentos()
        );
		
        return ResponseEntity.ok(searchResult);
    }	
}

