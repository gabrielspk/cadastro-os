package com.github.gabrielspk.cadastro_os.config;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.github.gabrielspk.cadastro_os.entities.Permission;
import com.github.gabrielspk.cadastro_os.entities.Solicitacao;
import com.github.gabrielspk.cadastro_os.entities.Usuario;
import com.github.gabrielspk.cadastro_os.entities.enums.StatusSolicitacao;
import com.github.gabrielspk.cadastro_os.repositories.PermissionRepository;
import com.github.gabrielspk.cadastro_os.repositories.SolicitacaoRepository;
import com.github.gabrielspk.cadastro_os.repositories.UsuarioRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private SolicitacaoRepository solicitacaoRepository;
	
	@Autowired
	private PermissionRepository permissionRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public void run(String... args) throws Exception {
		
	    Permission adminPermission = new Permission();
	    adminPermission.setDescription("ROLE_ADMIN");
	    
	    Permission userPermission = new Permission();
	    userPermission.setDescription("ROLE_USER");
	    
	    permissionRepository.saveAll(Arrays.asList(adminPermission, userPermission));
		
	    Usuario usuarioAdmin = new Usuario(
	            "Gabriel",
	            "gabriel@email.com",
	            passwordEncoder.encode("123")
	    );
	    usuarioAdmin.setAccountNonExpired(true);
	    usuarioAdmin.setAccountNonLocked(true);
	    usuarioAdmin.setCredentialsNonExpired(true);
	    usuarioAdmin.setEnabled(true);
	    usuarioAdmin.setPermissions(Arrays.asList(adminPermission));
		
		usuarioRepository.saveAll(Arrays.asList(usuarioAdmin));
		
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
		        usuarioAdmin
		);

		Solicitacao s3 = new Solicitacao(
		        "11236",
		        "Bug na geração de relatórios",
		        LocalDateTime.now().minusHours(10),
		        null,
		        StatusSolicitacao.ABERTO,
		        usuarioAdmin
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
		        usuarioAdmin
		);

		solicitacaoRepository.saveAll(Arrays.asList(s1, s2, s3, s4, s5));
	}	
}
