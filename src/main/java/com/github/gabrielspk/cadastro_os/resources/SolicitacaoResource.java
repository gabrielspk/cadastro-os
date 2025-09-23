package com.github.gabrielspk.cadastro_os.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.github.gabrielspk.cadastro_os.dto.SolicitacaoCreateDTO;
import com.github.gabrielspk.cadastro_os.dto.SolicitacaoDTO;
import com.github.gabrielspk.cadastro_os.dto.SolicitacaoUpdateDTO;
import com.github.gabrielspk.cadastro_os.entities.Solicitacao;
import com.github.gabrielspk.cadastro_os.services.SolicitacaoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/solicitacoes")
public class SolicitacaoResource {
	
	@Autowired
	private SolicitacaoService service;
	
	@GetMapping
	public ResponseEntity<List<SolicitacaoDTO>> findAll() {
		List<Solicitacao> solicitacaoList = service.findAll();
		List<SolicitacaoDTO> solicitacaoDtoList = solicitacaoList.stream().map(x -> new SolicitacaoDTO(x)).collect(Collectors.toList());
		return ResponseEntity.ok().body(solicitacaoDtoList);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<SolicitacaoDTO> findById(@PathVariable Long id) {
		Solicitacao usuario = service.findById(id);
		return ResponseEntity.ok().body(new SolicitacaoDTO(usuario));
	}
	
	@PostMapping
	public ResponseEntity<SolicitacaoDTO> insert(@Valid @RequestBody SolicitacaoCreateDTO  solicitacaoDto){
		Solicitacao solicitacao = service.fromCreateDTO(solicitacaoDto);
		solicitacao = service.Insert(solicitacao);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(solicitacao.getId())
				.toUri();
		return ResponseEntity.created(uri).body(new SolicitacaoDTO(solicitacao));
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

	    Solicitacao updated = service.patch(id, dto);
	    return ResponseEntity.ok(new SolicitacaoDTO(updated));
	}
}