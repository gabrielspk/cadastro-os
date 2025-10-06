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
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.github.gabrielspk.cadastro_os.dto.security.TokenDTO;
import com.github.gabrielspk.cadastro_os.exceptions.InvalidJwtAuthenticationException;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class JwtTokenProvider {

	@Value("${security.jwt.token.secret-key:secret}")
	private String secretKey = "secret";

	@Value("${security.jwt.token.expire-lenght:3600000}")
	private long validityInMilliseconds = 3600000; // 1h

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
		Date validity = new Date(now.getTime() + validityInMilliseconds);
		String accessToken = getAccessToken(username, roles, now, validity);
		String refreshToken = getRefreshToken(username, roles, now);

		return new TokenDTO(username, true, now, validity, accessToken, refreshToken);
	}
	
	public TokenDTO refreshToken(String refreshToken) {
		var token = "";
		if(tokenHasBearerPrefix(refreshToken)) {
			token = sliceBearerToken(refreshToken);
		}
		
		DecodedJWT decodedJWT = decodedToken(token);
		
		String username = decodedJWT.getSubject();
		List<String> roles = decodedJWT.getClaim("roles").asList(String.class);
		return createAccessToken(username, roles);
	}

	private String getRefreshToken(String username, List<String> roles, Date now) {
		Date refreshTokenValidity = new Date(now.getTime() + (validityInMilliseconds * 3));;
		
		return JWT.create()
				.withClaim("roles", roles)
				.withIssuedAt(now)
				.withExpiresAt(refreshTokenValidity)
				.withSubject(username)
				.sign(algorithm);
	}

	private String getAccessToken(String username, List<String> roles, Date now, Date validity) {
		String issueUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
		
		return JWT.create().
				withClaim("roles", roles).
				withIssuedAt(now).
				withExpiresAt(validity).
				withSubject(username)
				.withIssuer(issueUrl).
				sign(algorithm);
	}
	
	public Authentication getAuthentication(String token) {
		DecodedJWT decodedJWT = decodedToken(token);
		UserDetails userDetails = this.userDetailsService
				.loadUserByUsername(decodedJWT.getSubject());
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	private DecodedJWT decodedToken(String token) {
		JWTVerifier verifier = JWT.require(algorithm).build();
		DecodedJWT decodedJWT = verifier.verify(token);
		return decodedJWT;
	}
	
    public String getUsernameFromToken(String token) {
        var cleanToken = token;
        if (tokenHasBearerPrefix(token)) {
            cleanToken = sliceBearerToken(token);
        }

        DecodedJWT decoded = decodedToken(cleanToken);
        return decoded.getSubject();
    }
    
    public boolean validateTokenOwnership(String token, String username) {
        try {
            var cleanToken = token;
            if (tokenHasBearerPrefix(token)) {
                cleanToken = sliceBearerToken(token);
            }

            String tokenUsername = getUsernameFromToken(cleanToken);
            return tokenUsername.equals(username);
        } catch (JWTVerificationException e) {
            return false;
        }
    }
	
	public String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if(tokenHasBearerPrefix(bearerToken)) return sliceBearerToken(bearerToken);
		return null;
	}
	
	private boolean tokenHasBearerPrefix(String refreshToken) {
		return StringUtils.isNotBlank(refreshToken) && refreshToken.startsWith("Bearer ");
	}
	
	private String sliceBearerToken(String bearerToken) {
		return bearerToken.substring("Bearer ".length());
	}
	
	public boolean validateToken(String token) {
		DecodedJWT decodedJWT = decodedToken(token);
		try {
			if(decodedJWT.getExpiresAt().before(new Date())) {
				return false;
			}
			return true;
		} catch (Exception e) {
			throw new InvalidJwtAuthenticationException("Expired or invalid JWT Token");
		}
	}
}