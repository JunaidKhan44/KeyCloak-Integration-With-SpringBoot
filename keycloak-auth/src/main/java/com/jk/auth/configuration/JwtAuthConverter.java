/**
 * 
 */
package com.jk.auth.configuration;

import org.springframework.stereotype.Component;

import com.jk.auth.KeycloakAuthApplication;
import com.nimbusds.jwt.JWTClaimNames;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimAccessor;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.DelegatingJwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

/**
 * @author Junaid.Khan
 *
 */

@Component
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {

	
	private JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
	private KeyCloakConfiguration properties;
	
	
	
	public JwtAuthConverter(KeyCloakConfiguration properties) {
		this.properties = properties;
	}

	@Override
	public AbstractAuthenticationToken convert(Jwt source) {
		
		 Collection<GrantedAuthority> collect = Stream.concat(authoritiesConverter.convert(source).stream(),
				extractRole(source).stream()).collect(Collectors.toSet());
	
		return new JwtAuthenticationToken(source,collect);
	}


	private Collection<? extends GrantedAuthority> extractRole(Jwt jwt) {
		Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
	
		Map<String, Object> resource = (Map<String, Object>) resourceAccess.get(properties.getResourceId());
		Collection<String> resourceRoles = (Collection<String>) resource.get("roles");
		
	
		if( resourceAccess == null
				|| resource == null
				|| resourceRoles == null){
			return Set.of();
		}
	
	  return resourceRoles.stream()
			 .map(r -> new SimpleGrantedAuthority("ROLE_"+ r))
			 .collect(Collectors.toSet());
	
	}
	
	private String getPrincipalName(Jwt jwt) {
		String name = JwtClaimNames.SUB;
		
		if(properties.getPrincipalAttribute() != null) {
			name = properties.getPrincipalAttribute();
		}
		
		return jwt.getClaimAsString(name);
	}
	
	
	
}
