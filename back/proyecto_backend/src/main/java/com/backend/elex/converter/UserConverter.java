package com.backend.elex.converter;

import org.springframework.stereotype.Component;

import com.backend.elex.entity.UserEntity;
import com.backend.elex.vo.UserVO;

@Component
public class UserConverter {

	public UserVO toVO(UserEntity user) {

		return new UserVO(
				user.getId(), 
				user.getName(), 
				user.getEmail(), 
				user.getDni(), 
				user.getUsername(),
				user.getFechaCreacion());
	}
	
	public UserEntity toEntity(UserVO user, String encryptedPassword) {
		return new UserEntity(
				user.getId(), 
				user.getName(), 
				user.getEmail(), 
				user.getDni(), 
				user.getUsername(), 
				encryptedPassword, 
				user.getFechaCreacion()	);
	}

}
