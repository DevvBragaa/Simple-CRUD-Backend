package com.dev.crud.service

import com.dev.crud.domain.User
import com.dev.crud.dto.UserDto
import com.dev.crud.exception.BadRequestException
import com.dev.crud.exception.InternalServerErrorException
import com.dev.crud.mapper.formMapper.UserFormMapper
import com.dev.crud.repository.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service


@Service
class UserService (
    private val repository: UserRepository,
    private val mapper: UserFormMapper
) {

    private val bcrypt = BCryptPasswordEncoder()

    fun createUser(dto: UserDto): Long {
        validationDto(dto)
        val user = mapper.map(dto)
        criptografarSenha(user)
        val savedObject = repository.save(user)
        return savedObject.id ?: throw InternalServerErrorException("Error saving user")
    }

    private fun criptografarSenha(obj: User) {
        if(obj.password.isNullOrBlank()){
            throw BadRequestException("The passowrd cannot be null")
        }else{
            obj.apply {
                password = bcrypt.encode(obj.password)
            }
        }
    }

    private fun validationDto(dto: UserDto) {
        val user = repository.findByEmail(dto.email)
        if(user != null){
            throw BadRequestException("user already registered")
        }
    }
}
