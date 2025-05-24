package com.dev.crud.config

import com.dev.crud.config.security.JsonUsernameAuthenticationFilter
import com.dev.crud.config.security.JwtAuthorizationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


@Configuration
class SecurityConfig(
    private val jwtAuthorizationFilter: JwtAuthorizationFilter,
    private val authenticationProvider: AuthenticationProvider,
    private val jsonFilter: JsonUsernameAuthenticationFilter
) {

    private val PUBLIC_POST_MATCHERS = arrayOf("/login","/user/add-first-user")

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val config = CorsConfiguration().apply {
            allowedOrigins = listOf("*")
            allowedMethods = listOf("GET","POST","PUT","DELETE","OPTIONS")
            allowedHeaders = listOf("*")
        }
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", config)
        return source
    }


    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .csrf { it.disable() }
            .cors { it.disable() }
            .authorizeHttpRequests { authorize ->
                authorize.requestMatchers(HttpMethod.POST,* PUBLIC_POST_MATCHERS).permitAll()
                authorize.requestMatchers(HttpMethod.GET).hasAuthority("admin")
                authorize.requestMatchers(HttpMethod.POST).hasAuthority("admin")
                authorize.requestMatchers(HttpMethod.PUT).hasAuthority("admin")
                authorize.requestMatchers(HttpMethod.DELETE).hasAuthority("admin")
                authorize.anyRequest().authenticated()
            }
            .sessionManagement { session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authenticationProvider(authenticationProvider)
            .addFilterAt(jsonFilter, UsernamePasswordAuthenticationFilter::class.java)
            .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()
    }
}