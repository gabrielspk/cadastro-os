package com.github.gabrielspk.cadastro_os.security;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.github.gabrielspk.cadastro_os.dto.security.TokenDTO;
import com.github.gabrielspk.cadastro_os.exceptions.InvalidJwtAuthenticationException;
import com.github.gabrielspk.cadastro_os.exceptions.JwtTokenExpiredException;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class JwtTokenProvider {

	@Value("${security.jwt.token.secret-key:secret}")
	private String secretKey;

	@Value("${security.jwt.token.expire-lenght:900000}")
	private long accessTokenValidityMs; // 15 min

	@Value("${security.jwt.token.refresh-expire-ms:86400000}") // 1 dia
	private long refreshTokenValidityMs;

	@Autowired
	private UserDetailsService userDetailsService;

	Algorithm algorithm = null;

	@PostConstruct
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
		algorithm = Algorithm.HMAC256(secretKey.getBytes());
	}

	public TokenDTO createAccessToken(String username, List<String> roles) {
		Date now = new Date();
		Date validity = new Date(now.getTime() + accessTokenValidityMs);
		String accessToken = getAccessToken(username, roles, now, validity);
		String refreshToken = getRefreshToken(username, roles, now);

		return new TokenDTO(username, true, now, validity, accessToken, refreshToken);
	}

	public TokenDTO refreshToken(String refreshToken) {
	    String token = tokenHasBearerPrefix(refreshToken) ? removeBearerPrefix(refreshToken) : refreshToken;
	    DecodedJWT decodedJWT = decodeToken(token);
	    return createAccessToken(decodedJWT.getSubject(), decodedJWT.getClaim("roles").asList(String.class));
	}

	public Authentication getAuthentication(String token) {
		DecodedJWT decodedJWT = decodeToken(token);
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(decodedJWT.getSubject());
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	public boolean validateToken(String token) {
		DecodedJWT decodedJWT = decodeToken(token);
		Date expiration = decodedJWT.getExpiresAt();

		if (expiration.before(new Date())) {
			throw new JwtTokenExpiredException("Token expirado.");
		}
		return true;
	}

	public String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (tokenHasBearerPrefix(bearerToken))
			return removeBearerPrefix(bearerToken);
		return null;
	}

	private String getRefreshToken(String username, List<String> roles, Date now) {
		Date refreshTokenValidity = new Date(now.getTime() + refreshTokenValidityMs);
		;

		return JWT.create().withClaim("roles", roles).withIssuedAt(now).withExpiresAt(refreshTokenValidity)
				.withSubject(username).sign(algorithm);
	}

	private String getAccessToken(String username, List<String> roles, Date now, Date validity) {
		String issueUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();

		return JWT.create().withClaim("roles", roles).withIssuedAt(now).withExpiresAt(validity).withSubject(username)
				.withIssuer(issueUrl).sign(algorithm);
	}

	private DecodedJWT decodeToken(String token) {
		try {
			JWTVerifier verifier = JWT.require(algorithm).build();
			return verifier.verify(token);
		} catch (TokenExpiredException ex) {
			throw new JwtTokenExpiredException("Token expirado. Faça login novamente.");
		} catch (JWTVerificationException ex) {
			throw new InvalidJwtAuthenticationException("Token inválido ou mal formado.");
		}
	}

	private boolean tokenHasBearerPrefix(String refreshToken) {
		return StringUtils.isNotBlank(refreshToken) && refreshToken.startsWith("Bearer ");
	}

	private String removeBearerPrefix(String bearerToken) {
		return bearerToken.substring("Bearer ".length());
	}
}