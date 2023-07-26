package com.piti.java.school.onlinevideotraining.config.security.jwt;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TokenVerifyFilter extends OncePerRequestFilter{	
	private String secretKey = "mykey123456789abcdmykey123456789abcdmykey123456789abcd";
	
	//@Value("${application.security.jwt.secretKey}")
	//public String secretKey;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String authorizationHeader = request.getHeader("Authorization");
		if(Objects.isNull(authorizationHeader) || 
				!authorizationHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		String token = authorizationHeader.replace("Bearer ", "");
		try {
			
			
			Jws<Claims> claimsJws = Jwts.parserBuilder()
				.setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
				.build()
				.parseClaimsJws(token);
			
			Claims body = claimsJws.getBody();
			
			String username = body.getSubject();
			//String username = (String) body.get("sub");
			var authorities = (List<Map<String, String>>)body.get("authorities");
			Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream()
				.map(m -> new SimpleGrantedAuthority(m.get("authority")))
				.collect(Collectors.toSet());
			
			//GrantedAuthority
			
			Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, simpleGrantedAuthorities);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			filterChain.doFilter(request, response);
		}catch(JwtException e) {
			log.error(e.getMessage());
			throw new IllegalStateException("Invalid token %s".formatted(token));
		}
		
	}

}
