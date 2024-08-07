package com.api.jwt_auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.jwt_auth.model.*;

public interface AuthRepository extends JpaRepository<AuthModel, Integer>{

}