package com.eaglevision.Backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.eaglevision.Backend.dto.ChangePasswordDTO;
import com.eaglevision.Backend.dto.UpdateUserProfileDTO;
import com.eaglevision.Backend.service.UserService;

@RestController
@RequestMapping({"/api/secure/users","/api/bot/users"})
public class UserController {
	
	private UserService userService;
	
	
	@Autowired
	public UserController(UserService userService) {
		this.userService=userService;
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<Object> getUserById(@PathVariable Integer userId,@RequestHeader("Authorization")String authHeader){
		try {
			if(userId==null || authHeader==null) {
				return ResponseEntity.badRequest().body("Invalid input data.");
			}
			
			Object user = this.userService.getUserDTOByUserId(userId, authHeader);
			return ResponseEntity.ok(user);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.internalServerError().body("An Error occurred while fetching user: "+ex.getMessage());
		}
	}
	
	@PutMapping("/setProfilePicture")
	public ResponseEntity<Object> setProfilePicture(@RequestPart MultipartFile profilePicture,@RequestHeader("Authorization") String authHeader){
		try {
			if(profilePicture==null || authHeader==null) {
				return ResponseEntity.badRequest().body("Invalid input data.");
			}
			Object user = userService.setProfilePicture(profilePicture,authHeader);
			return ResponseEntity.ok("Changed Profile Picture successfully!");
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.internalServerError().body("Error setting profile picture: "+ex.getMessage());
		}
	}
	
	@PutMapping("/removeProfilePicture")
	public ResponseEntity<Object> removeProfilePicture(@RequestHeader("Authorization") String authHeader){
		try {
			if(authHeader==null) {
				return ResponseEntity.badRequest().body("Invalid input data.");
			}
			Object user = userService.removeProfilePicture(authHeader);
			return ResponseEntity.ok(user);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.internalServerError().body("Error removing profile picture: "+ex.getMessage());
		}
	}
	
	
	@PutMapping("/updateProfile")
	public ResponseEntity<Object> updateProfilePicture(@RequestBody UpdateUserProfileDTO updateUserProfileDTO,@RequestHeader("Authorization") String authHeader){
		try {
			if(updateUserProfileDTO==null || authHeader==null) {
				return ResponseEntity.badRequest().body("Invalid input data.");
			}
			userService.updateUser(updateUserProfileDTO,authHeader);
			return ResponseEntity.ok("Updated Profile Successfully!");
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.internalServerError().body("Error updating profile picture!");
		}
	}
	
	@PutMapping("/changePassword")
	public ResponseEntity<Object> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO,@RequestHeader("Authorization")String authHeader){
		try {
			if(changePasswordDTO==null || authHeader==null) {
				return ResponseEntity.badRequest().body("Invalid input data.");
			}
			userService.changePassword(changePasswordDTO,authHeader);
			return ResponseEntity.ok("Changed Password Successfully!");
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.internalServerError().body("Error changing password "+ex.getMessage());
		}
	}
	
	
	

}
