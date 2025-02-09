package com.eaglevision.Backend.service;

import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.eaglevision.Backend.dto.ChangePasswordDTO;
import com.eaglevision.Backend.dto.UpdateUserProfileDTO;
import com.eaglevision.Backend.dto.UserDTO;
import com.eaglevision.Backend.model.Buyer;
import com.eaglevision.Backend.model.Subscription;
import com.eaglevision.Backend.model.User;
import com.eaglevision.Backend.model.Vendor;
import com.eaglevision.Backend.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {

    private UserRepository userRepository;
    
    private UtilityService utilityService;
    
    private BCryptPasswordEncoder passwordEncoder;
    

    @Autowired
    public UserService(UserRepository userRepository,UtilityService utilityService,BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.utilityService = utilityService;
        this.passwordEncoder = passwordEncoder;
    }

    public User findUserByUserName(String userName) {
        User user = userRepository.findByUserName(userName).get();
        encodeImage(user);
        return user;
    }
    
    public User getUserByUserId(Integer userId) {
    	User user = userRepository.findById(userId).get();
    	encodeImage(user);
    	return user;
    }
    
    public User getUserByUserId(Integer userId,String authHeader) throws Exception {
    	String roles = this.utilityService.getRoles(authHeader);
    	Integer currentUserId = this.utilityService.getUserId(authHeader);
    	User user = userRepository.findById(userId).get();
    	
    	if(user instanceof Vendor) {
    		if(roles.contains("VENDOR") || userId!=currentUserId) {
    			throw new Exception("Vendors not allowed to view Buyer Profiles.");
    		}
    	}
    	
    	encodeImage(user);
    	return user;
    }
    
    public UserDTO getUserDTOByUserId(Integer userId,String authHeader) throws Exception {
    	User user = this.getUserByUserId(userId);
    
    	return new UserDTO(user);
    }

    public Boolean userExistsWithUserName(String userName) {
        return userRepository.existsByUserName(userName);
    }

    public Boolean userExistsWithPhoneNumber(String phoneNumber) {
        return userRepository.existsByPhoneNumber(phoneNumber);
    }

    public Integer getUserIdByUserName(String name) {
        return userRepository.findByUserName(name).get().getUserId();
    }

	public User setProfilePicture(MultipartFile profilePicture, String authHeader) throws Exception {
		Integer userId = this.utilityService.getUserId(authHeader);
		
		User user = this.userRepository.findById(userId).get();
		user.setProfilePictureName(profilePicture.getName());
		user.setProfilePictureType(profilePicture.getContentType());
		user.setProfilePictureData(profilePicture.getBytes());
		user = encodeImage(user);
		
		return this.userRepository.save(user);
		
	}

	public User removeProfilePicture(String authHeader) {
		Integer userId = this.utilityService.getUserId(authHeader);
		
		User user = this.userRepository.findById(userId).get();
		user.setProfilePictureName(null);
		user.setProfilePictureType(null);
		user.setProfilePictureData(null);
		
		return this.userRepository.save(user);
	}
	
	public User encodeImage(User user) {
		if(user.getProfilePictureData()!=null)user.setProfilePictureDataB64(Base64.getEncoder().encodeToString(user.getProfilePictureData()));
		return user;
	}
	
	public List<User> encodeImage(List<User> users){
		for(User user:users) {
			encodeImage(user);
		}
		return users;
	}
	
	public Page<User> encodeImage(Page<User> users){
		for(User user:users) {
			encodeImage(user);
		}
		return users;
	}
	
	public User save(User user) {
		return this.userRepository.save(user);
	}

	public List<Subscription> findActiveSubscriptions(Integer userId) {
		return this.userRepository.findActiveSubscriptions(userId);
	}

	public void updateUser(UpdateUserProfileDTO updateUserProfileDTO, String authHeader) {
		Integer userId = this.utilityService.getUserId(authHeader);
		
		User user = this.getUserByUserId(userId);
		
		user.setUserName(updateUserProfileDTO.getUserName());
		user.setPhoneNumber(updateUserProfileDTO.getPhoneNumber());
		
		this.userRepository.save(user);
		return;
	}

	public void changePassword(ChangePasswordDTO changePasswordDTO, String authHeader) throws Exception {
		
		
		Integer userId = this.utilityService.getUserId(authHeader);
		
		User user = this.userRepository.findById(userId).get();
		
		if(!passwordEncoder.matches(changePasswordDTO.getOldPassword(),user.getPassword())){
			throw new Exception("Incorrect password!");
		}
		
		if(!changePasswordDTO.getNewPassword().equals(changePasswordDTO.getConfirmNewPassword())) {
			throw new Exception("The new password and the confirm new password fields should match!");
		}
		
		user.setPassword(this.passwordEncoder.encode(changePasswordDTO.getNewPassword()));
		
		this.userRepository.save(user);
		return;
	}


}
