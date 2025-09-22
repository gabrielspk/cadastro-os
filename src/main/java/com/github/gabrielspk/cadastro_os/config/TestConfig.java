package com.github.gabrielspk.cadastro_os.config;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.github.gabrielspk.cadastro_os.entities.Solicitacao;
import com.github.gabrielspk.cadastro_os.entities.Usuario;
import com.github.gabrielspk.cadastro_os.entities.enums.StatusSolicitacao;
import com.github.gabrielspk.cadastro_os.entities.enums.TipoUsuario;
import com.github.gabrielspk.cadastro_os.repositories.SolicitacaoRepository;
import com.github.gabrielspk.cadastro_os.repositories.UsuarioRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private SolicitacaoRepository solicitacaoRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		Usuario usuarioAdmin = new Usuario("Gabriel", "gabriel@email.com", "123", TipoUsuario.ADMIN);
		Usuario usuarioComum = new Usuario("Maria", "maria@email.com", "123", TipoUsuario.COMUM);
		
		usuarioRepository.saveAll(Arrays.asList(usuarioAdmin, usuarioComum));
		
		Solicitacao s1 = new Solicitacao(
				"11234",
				"fizeram besteira",
				LocalDateTime.now(),
				null,
				StatusSolicitacao.ABERTO,
				usuarioAdmin
		);
		
		solicitacaoRepository.saveAll(Arrays.asList(s1));
	}	
}
