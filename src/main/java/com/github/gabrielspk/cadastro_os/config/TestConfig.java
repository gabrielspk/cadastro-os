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
		        "Erro no sistema de login",
		        LocalDateTime.now().minusDays(2),
		        null,
		        StatusSolicitacao.ABERTO,
		        usuarioAdmin
		);

		Solicitacao s2 = new Solicitacao(
		        "11235",
		        "Pagamento não processado",
		        LocalDateTime.now().minusDays(5),
		        LocalDateTime.now().minusDays(2),
		        StatusSolicitacao.CONCLUIDA,
		        usuarioComum
		);

		Solicitacao s3 = new Solicitacao(
		        "11236",
		        "Bug na geração de relatórios",
		        LocalDateTime.now().minusHours(10),
		        null,
		        StatusSolicitacao.ABERTO,
		        usuarioComum
		);

		Solicitacao s4 = new Solicitacao(
		        "11237",
		        "Solicitação de nova funcionalidade",
		        LocalDateTime.now().minusWeeks(1),
		        null,
		        StatusSolicitacao.EM_ANDAMENTO,
		        usuarioAdmin
		);

		Solicitacao s5 = new Solicitacao(
		        "11238",
		        "Erro de validação nos dados",
		        LocalDateTime.now().minusDays(3),
		        LocalDateTime.now().minusDays(1),
		        StatusSolicitacao.CONCLUIDA,
		        usuarioComum
		);

		solicitacaoRepository.saveAll(Arrays.asList(s1, s2, s3, s4, s5));
	}	
}
