/**
 * 
 */
package com.jk.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Junaid.Khan
 *
 */

@RestController
@RequestMapping("/auth")
public class KeyCloakController {

	@GetMapping("/getAdminInfo")
	public ResponseEntity<String>  getAdminInfo(){
		return ResponseEntity.ok("Welcome to Admin Page");
	}
	
	@GetMapping("/getUserInfo")
	public ResponseEntity<String>  getUserInfo(){
		return ResponseEntity.ok("Welcome to User Page");
	}
}
