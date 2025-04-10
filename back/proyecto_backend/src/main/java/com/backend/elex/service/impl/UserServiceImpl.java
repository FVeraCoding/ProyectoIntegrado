package com.backend.elex.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend.elex.converter.UserConverter;
import com.backend.elex.entity.UserEntity;
import com.backend.elex.repository.UserRepository;
import com.backend.elex.service.UserService;
import com.backend.elex.vo.UserVO;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

	private final UserRepository userRepository;
	private final UserConverter userConverter;
	private final PasswordEncoder passwordEncoder;

	public UserServiceImpl(UserRepository userRepository, UserConverter userConverter, PasswordEncoder passwordEncoder) {
	    this.userRepository = userRepository;
	    this.userConverter = userConverter;
	    this.passwordEncoder = passwordEncoder;
	}

	@Override
	public List<UserVO> listUsers() {
	    List<UserEntity> users = userRepository.findAll();
	    if (users.isEmpty()) {
	        return List.of(); 
	    }
	    List<UserVO> usersVO = users.stream().map(userConverter::toVO).collect(Collectors.toList());
	    return usersVO;
	}

	@Override
	public Optional<UserVO> getUserByDni(String dni) {
		return userRepository.findByDni(dni).map(userConverter::toVO);
	}

	@Override
	public Optional<UserVO> getUserById(int id) {
		return userRepository.findById(id).map(userConverter::toVO);
	}
	
	@Override
	public Optional<UserEntity> getUserByUsername(String username) {
	    return userRepository.findByUsername(username);
	}

	@Override
	public Optional<UserEntity> getUserByEmail(String email) {

		return userRepository.findByEmail(email);
	}

    @Override
    public UserVO registerUser(UserVO userVO, String rawPassword) {
        String encryptedPassword = passwordEncoder.encode(rawPassword);
        UserEntity userEntity = userConverter.toEntity(userVO, encryptedPassword);
        userEntity = userRepository.save(userEntity);
        return userConverter.toVO(userEntity);
    }

	@Override
	public void deleteUserById(int id) {
		userRepository.deleteById(id);
	}

	public void deleteUserByDni(String dni) {
		userRepository.findByDni(dni).ifPresent(userRepository::delete);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

		return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
				.password(user.getPassword()).roles("USER").build();
	}




}
