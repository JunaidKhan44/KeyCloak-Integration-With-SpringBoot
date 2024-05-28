/**
 * 
 */
package com.jk.auth.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * @author Junaid.Khan
 *
 */

@Data
@Configuration
@ConfigurationProperties(prefix = "jwt.auth.converter")
public class KeyCloakConfiguration {

	private String resourceId;
	private String principalAttribute;
	
}
