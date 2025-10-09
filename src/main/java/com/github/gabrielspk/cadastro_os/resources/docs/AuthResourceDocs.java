package com.github.gabrielspk.cadastro_os.resources.docs;

import org.springframework.http.ResponseEntity;

import com.github.gabrielspk.cadastro_os.dto.security.AccountCredentialsDTO;
import com.github.gabrielspk.cadastro_os.dto.v1.UsuarioCreateDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

public interface AuthResourceDocs {
	
    @Operation(
            summary = "Autentica um usuário e retorna um token",
            description = "Valida as credenciais de um usuário e gera um token de acesso para autenticação",
            tags = {"Authentication"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
	ResponseEntity<?> signin(AccountCredentialsDTO credentials);
    
    @Operation(
            summary = "Renova um token para um usuário autenticado e retorna o token",
            description = "Gera um novo token de acesso usando o token de renovação e o usuário autenticado",
            tags = {"Authentication"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
	ResponseEntity<?> refreshToken(String refreshToken);

    @Operation(
            summary = "Cria um novo usuário",
            description = "Registers a new user in the system with the provided credentials. Registra um novo usuário no sistema com as credenciais fornecidas",
            tags = {"User Management"},
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Conflict", responseCode = "409", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
	ResponseEntity<?> createUser(UsuarioCreateDTO dto);

}