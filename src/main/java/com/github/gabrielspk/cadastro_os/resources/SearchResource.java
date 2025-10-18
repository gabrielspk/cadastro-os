package com.github.gabrielspk.cadastro_os.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.gabrielspk.cadastro_os.dto.v1.ExportRequestDTO;
import com.github.gabrielspk.cadastro_os.dto.v1.SearchRequestDTO;
import com.github.gabrielspk.cadastro_os.dto.v1.SearchResultDTO;
import com.github.gabrielspk.cadastro_os.services.SearchService;

@RestController
@RequestMapping("/api/search/v1")
public class SearchResource {
	
	@Autowired
	private SearchService service;

	@PostMapping("/exportar")
	public ResponseEntity<byte[]> exportarRelatorio(@RequestBody ExportRequestDTO dados) {
		byte[] relatorio = service.exportarRelatorio(dados);
		
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + dados.getNomeArquivo())
                .contentType(MediaType.parseMediaType(
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                ))
                .body(relatorio); 
	}
	
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
