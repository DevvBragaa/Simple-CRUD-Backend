package com.dev.crud.service.user

import com.dev.crud.domain.User
import com.dev.crud.dto.UserDto
import com.dev.crud.exception.BadRequestException
import com.dev.crud.exception.InternalServerErrorException
import com.dev.crud.mapper.formMapper.UserFormMapper
import com.dev.crud.repository.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service


@Service
class UserServiceImpl(
    private val repository: UserRepository,
    private val mapper: UserFormMapper
) : UserService {

    private val bcrypt = BCryptPasswordEncoder()


    override fun addUser(userDto: UserDto): Long {
        validateDto(userDto)
        val user = mapper.map(userDto)
        criptografarSenha(user)
        val savedObject = repository.save(user)
        return savedObject.id ?: throw InternalServerErrorException("Error saving user")
    }

    override fun putuser(userDto: UserDto) {
        TODO("Not yet implemented")
    }

    override fun deleteUser(id: Long) {
        TODO("Not yet implemented")
    }



    private fun criptografarSenha(obj: User) {

        if (obj.passCode.isNullOrBlank()) {
            throw BadRequestException("The passowrd cannot be null")
        } else {
            obj.apply {
                passCode = bcrypt.encode(obj.passCode)
            }
        }
    }

    private fun validateDto(dto: UserDto) {
        val user = repository.findByEmail(dto.email)
        if (user != null) {
            throw BadRequestException("user already registered")
        }
    }

}
