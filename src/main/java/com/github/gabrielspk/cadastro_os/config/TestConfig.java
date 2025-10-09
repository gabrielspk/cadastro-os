
package com.github.gabrielspk.cadastro_os.config;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.github.gabrielspk.cadastro_os.services.AuthService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@Profile("test") // Apenas no profile test
public class TestConfig implements CommandLineRunner {

	Logger logger = LoggerFactory.getLogger(AuthService.class);
	
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SolicitacaoRepository solicitacaoRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Valores via variáveis de ambiente com defaults para teste
    @Value("${test.user.name:Test User}")
    private String testUserName;

    @Value("${test.user.email:test@example.com}")
    private String testUserEmail;

    @Value("${test.user.password:testPassword123!}")
    private String testUserPassword;

    @Override
    public void run(String... args) throws Exception {
    	logger.warn("⚠️  EXECUTANDO CARGA DE DADOS DE TESTE - Profile: test");
    	logger.warn("⚠️  Esta configuração NÃO deve ser usada em produção!");

        // Criar permissões
        Permission adminPermission = new Permission();
        adminPermission.setDescription("ROLE_ADMIN");

        Permission userPermission = new Permission();
        userPermission.setDescription("ROLE_USER");

        permissionRepository.saveAll(Arrays.asList(adminPermission, userPermission));
        logger.info("✓ Permissões criadas");

        // Criar usuário admin de teste
        Usuario usuarioAdmin = new Usuario(
            testUserName,
            testUserEmail,
            passwordEncoder.encode(testUserPassword)
        );
        usuarioAdmin.setAccountNonExpired(true);
        usuarioAdmin.setAccountNonLocked(true);
        usuarioAdmin.setCredentialsNonExpired(true);
        usuarioAdmin.setEnabled(true);
        usuarioAdmin.setPermissions(Arrays.asList(adminPermission));

        usuarioRepository.save(usuarioAdmin);
        logger.info("✓ Usuário admin de teste criado: {}", testUserEmail);

        // Criar solicitações de teste
        Solicitacao s1 = new Solicitacao(
            "TST-001",
            "Erro no sistema de login",
            LocalDateTime.now().minusDays(2),
            null,
            StatusSolicitacao.ABERTA,
            usuarioAdmin
        );

        Solicitacao s2 = new Solicitacao(
            "TST-002",
            "Pagamento não processado",
            LocalDateTime.now().minusDays(5),
            LocalDateTime.now().minusDays(2),
            StatusSolicitacao.CONCLUIDA,
            usuarioAdmin
        );

        Solicitacao s3 = new Solicitacao(
            "TST-003",
            "Bug na geração de relatórios",
            LocalDateTime.now().minusHours(10),
            null,
            StatusSolicitacao.ABERTA,
            usuarioAdmin
        );

        Solicitacao s4 = new Solicitacao(
            "TST-004",
            "Solicitação de nova funcionalidade",
            LocalDateTime.now().minusWeeks(1),
            null,
            StatusSolicitacao.EM_ANDAMENTO,
            usuarioAdmin
        );

        Solicitacao s5 = new Solicitacao(
            "TST-005",
            "Erro de validação nos dados",
            LocalDateTime.now().minusDays(3),
            LocalDateTime.now().minusDays(1),
            StatusSolicitacao.CONCLUIDA,
            usuarioAdmin
        );

        solicitacaoRepository.saveAll(Arrays.asList(s1, s2, s3, s4, s5));
        logger.info("✓ {} solicitações de teste criadas", 5);
        
        logger.warn("⚠️  DADOS DE TESTE CARREGADOS COM SUCESSO");
        logger.info("📧 Email de teste: {}", testUserEmail);
        logger.info("🔑 Use a senha configurada em test.user.password para login");
    }
}