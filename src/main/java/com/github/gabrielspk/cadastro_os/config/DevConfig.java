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

/**
 * Configuração para ambiente de desenvolvimento.
 * Carrega dados iniciais apenas quando o profile 'dev' está ativo.
 * 
 * ⚠️ Esta classe NÃO deve ser usada em produção!
 */
@Slf4j
@Configuration
@Profile("dev")
public class DevConfig implements CommandLineRunner {
	
	Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SolicitacaoRepository solicitacaoRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${dev.user.name:Dev Admin}")
    private String devUserName;

    @Value("${dev.user.email:admin@dev.local}")
    private String devUserEmail;

    @Value("${dev.user.password:Dev@123456}")
    private String devUserPassword;

    @Value("${dev.seed.enabled:true}")
    private boolean seedEnabled;

    @Override
    public void run(String... args) throws Exception {
        if (!seedEnabled) {
        	logger.info("ℹ️  Seed de dados desabilitado (dev.seed.enabled=false)");
            return;
        }

        logger.warn("🚧 EXECUTANDO CARGA DE DADOS DE DESENVOLVIMENTO");
        logger.warn("🚧 Esta configuração é apenas para ambiente DEV!");

        if (usuarioRepository.count() > 0) {
        	logger.info("ℹ️  Dados já existem no banco. Pulando seed.");
            return;
        }

        Permission adminPermission = new Permission();
        adminPermission.setDescription("ROLE_ADMIN");

        Permission userPermission = new Permission();
        userPermission.setDescription("ROLE_USER");

        permissionRepository.saveAll(Arrays.asList(adminPermission, userPermission));
        logger.info("✓ Permissões criadas");

        // Criar usuário admin de desenvolvimento
        Usuario adminUser = new Usuario(
            devUserName,
            devUserEmail,
            passwordEncoder.encode(devUserPassword)
        );
        adminUser.setAccountNonExpired(true);
        adminUser.setAccountNonLocked(true);
        adminUser.setCredentialsNonExpired(true);
        adminUser.setEnabled(true);
        adminUser.setPermissions(Arrays.asList(adminPermission));

        // Criar usuário comum de desenvolvimento
        Usuario normalUser = new Usuario(
            "User Dev",
            "user@dev.local",
            passwordEncoder.encode(devUserPassword)
        );
        normalUser.setAccountNonExpired(true);
        normalUser.setAccountNonLocked(true);
        normalUser.setCredentialsNonExpired(true);
        normalUser.setEnabled(true);
        normalUser.setPermissions(Arrays.asList(userPermission));

        usuarioRepository.saveAll(Arrays.asList(adminUser, normalUser));
        logger.info("✓ Usuários de desenvolvimento criados");

        // Criar solicitações variadas
        Solicitacao s1 = new Solicitacao(
            "DEV-001",
            "Implementar autenticação JWT",
            LocalDateTime.now().minusDays(5),
            LocalDateTime.now().minusDays(2),
            StatusSolicitacao.CONCLUIDA,
            adminUser
        );

        Solicitacao s2 = new Solicitacao(
            "DEV-002",
            "Criar endpoints de CRUD",
            LocalDateTime.now().minusDays(3),
            null,
            StatusSolicitacao.EM_ANDAMENTO,
            adminUser
        );

        Solicitacao s3 = new Solicitacao(
            "DEV-003",
            "Configurar profiles do Spring",
            LocalDateTime.now().minusHours(12),
            null,
            StatusSolicitacao.ABERTA,
            normalUser
        );

        Solicitacao s4 = new Solicitacao(
            "DEV-004",
            "Adicionar validações nos DTOs",
            LocalDateTime.now().minusDays(1),
            null,
            StatusSolicitacao.ABERTA,
            normalUser
        );

        Solicitacao s5 = new Solicitacao(
            "DEV-005",
            "Implementar testes unitários",
            LocalDateTime.now().minusWeeks(1),
            LocalDateTime.now().minusDays(4),
            StatusSolicitacao.CONCLUIDA,
            adminUser
        );

        Solicitacao s6 = new Solicitacao(
            "DEV-006",
            "Melhorar tratamento de exceções",
            LocalDateTime.now().minusDays(2),
            null,
            StatusSolicitacao.EM_ANDAMENTO,
            adminUser
        );

        solicitacaoRepository.saveAll(Arrays.asList(s1, s2, s3, s4, s5, s6));
        logger.info("✓ {} solicitações de desenvolvimento criadas", 6);
        
        logger.warn("🚧 DADOS DE DESENVOLVIMENTO CARREGADOS");
        logger.info("🔗 H2 Console: http://localhost:8080/h2-console");
    }
}