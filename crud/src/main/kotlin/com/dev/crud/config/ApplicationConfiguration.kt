package com.dev.crud.config

import com.dev.crud.config.security.UserDetailService
import com.dev.crud.repository.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
class ApplicationConfiguration(
    private val repository: UserRepository,
    private val userDetailService: UserDetailService
) {

    @Bean
    fun bcryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authenticationManager(auth: AuthenticationConfiguration): AuthenticationManager {
        return auth.authenticationManager
    }

    @Bean
    fun authenticationProvider(): AuthenticationProvider {
        val authProvider: DaoAuthenticationProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(userDetailService)
        authProvider.setPasswordEncoder(bcryptPasswordEncoder())

        return authProvider
    }
}