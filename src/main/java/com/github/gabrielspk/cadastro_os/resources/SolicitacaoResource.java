package com.github.gabrielspk.cadastro_os.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.github.gabrielspk.cadastro_os.dto.v1.SolicitacaoCreateDTO;
import com.github.gabrielspk.cadastro_os.dto.v1.SolicitacaoDTO;
import com.github.gabrielspk.cadastro_os.dto.v1.SolicitacaoUpdateDTO;
import com.github.gabrielspk.cadastro_os.entities.enums.StatusSolicitacao;
import com.github.gabrielspk.cadastro_os.services.SolicitacaoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/solicitacoes/v1")
public class SolicitacaoResource {
	
	@Autowired
	private SolicitacaoService service;
	
	@GetMapping
	public ResponseEntity<Page<SolicitacaoDTO>> findAll(
	        @RequestParam(required = false) StatusSolicitacao status,
	        @RequestParam(required = false) Long usuarioId,
	        Pageable pageable) {

	    Page<SolicitacaoDTO> solicitacoes = service.findAll(status, usuarioId, pageable);

	    return ResponseEntity.ok().body(solicitacoes);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<SolicitacaoDTO> findById(@PathVariable Long id) {
		SolicitacaoDTO solicitacao = service.findById(id);
		return ResponseEntity.ok().body(solicitacao);
	}
	
	@PostMapping
	public ResponseEntity<SolicitacaoDTO> insert(@Valid @RequestBody SolicitacaoCreateDTO  solicitacaoDto){
		SolicitacaoDTO solicitacao = service.insert(solicitacaoDto);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(solicitacao.getId())
				.toUri();
		
		return ResponseEntity.created(uri).body(solicitacao);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<SolicitacaoDTO> partialUpdate(
	        @PathVariable Long id,
	        @RequestBody SolicitacaoUpdateDTO dto) {

	    SolicitacaoDTO updated = service.patch(id, dto);
	    return ResponseEntity.ok(updated);
	}
}