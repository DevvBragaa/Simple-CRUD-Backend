package com.dev.crud.config.security

import com.dev.crud.exception.NotFoundException
import com.dev.crud.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailService(private val repository: UserRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val usuario = repository.findByEmail(username) ?: throw NotFoundException("User not found")
        return usuario
    }
}