package com.backend.elex.service;

import java.util.List;
import java.util.Optional;

import com.backend.elex.entity.UserEntity;
import com.backend.elex.vo.UserVO;

public interface UserService {

	List<UserVO> listUsers();
	Optional<UserVO> getUserByDni(String dni);
	Optional<UserVO> getUserById(int id);
	Optional<UserEntity> getUserByUsername(String username);
	Optional<UserEntity> getUserByEmail(String email);
	UserVO registerUser(UserVO userVO, String rawPassword);
	void deleteUserById(int id);
	void deleteUserByDni(String dni);

	
}
