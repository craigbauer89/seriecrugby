package com.epicode.progettofinaleepicode.auth.config;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.epicode.progettofinaleepicode.auth.service.UserDetailsServiceImpl;

import lombok.extern.slf4j.Slf4j;


//@Component
@Slf4j
public class AuthTokenFilter extends OncePerRequestFilter {
	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
//		if (request.getRequestURI().contains("/auth/login")) {
//			System.out.println("YESSS");
//			
//			try {
//				String username = request.getRemoteUser();
//					UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//					log.debug("[doFilterInternal] token valido");
//					userDetails.getAuthorities()
//					.stream()
//					.forEach(g->log.debug("[doFilterInternal] "+g.getAuthority()) );
//					
//					log.debug("[doFilterInternal] autorities found   " + userDetails.getAuthorities().size()+"");
//					
//					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//							userDetails, null, userDetails.getAuthorities());
//					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//					SecurityContextHolder.getContext().setAuthentication(authentication);
//				
//			} catch (Exception e) {
//				log.error("Cannot set user authentication: {}", e);
//			}
//			
//		}
//		
//		else {
//			System.out.println("NOOO");
		
		try {
			String jwt = parseJwt(request);
			System.out.println(jwt);
			log.debug("[doFilterInternal] jwt "+jwt);
			if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
				String username = jwtUtils.getUserNameFromJwtToken(jwt);

				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				log.debug("[doFilterInternal] token valido");
				userDetails.getAuthorities()
				.stream()
				.forEach(g->log.debug("[doFilterInternal] "+g.getAuthority()) );
				
				log.debug("[doFilterInternal] autorities found   " + userDetails.getAuthorities().size()+"");
				
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (Exception e) {
			log.error("Cannot set user authentication: {}", e);
		}

		filterChain.doFilter(request, response);
		}
//	}


	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");

		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.substring(7, headerAuth.length());
		}

		return null;
	}
}

