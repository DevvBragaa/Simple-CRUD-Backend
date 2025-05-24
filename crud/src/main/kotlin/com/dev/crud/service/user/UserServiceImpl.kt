package com.dev.crud.service.user

import com.dev.crud.domain.User
import com.dev.crud.domain.UserRole
import com.dev.crud.dto.user.UserDTO
import com.dev.crud.dto.user.ViewUserDTO
import com.dev.crud.enums.EnumRole
import com.dev.crud.exception.BadRequestException
import com.dev.crud.exception.NotFoundException
import com.dev.crud.mapper.formMapper.UserFormMapper
import com.dev.crud.mapper.viewMapper.UserViewMapper
import com.dev.crud.repository.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime


@Service
class UserServiceImpl(
    private val repository: UserRepository,
    private val mapper: UserFormMapper,
    private val viewMapper: UserViewMapper
) : UserService {

    private val bcrypt = BCryptPasswordEncoder()

    @Transactional
    override fun addUser(userDto: UserDTO): ViewUserDTO {
        validateDto(userDto)
        val user = mapper.map(userDto)
        passwordEncryption(user)
        val savedObject = repository.save(user)
        val roleAdmin = UserRole(
            user = savedObject,
            role = EnumRole.ADMIN
        )
        savedObject.apply { roles = mutableListOf(roleAdmin) }
        repository.save(savedObject)
        return viewMapper.mapViewUser(savedObject)
    }

    override fun addCommonUser(userDto: UserDTO): ViewUserDTO {
        validateDto(userDto)
        val user = mapper.map(userDto)
        passwordEncryption(user)
        val savedObject = repository.save(user)
        savedObject.apply {
            if (!userDto.roles.isNullOrEmpty()) {
                userDto.roles!!.forEach {
                    it.user = savedObject
                }
            }
        }
        repository.save(savedObject)
        return viewMapper.mapViewUser(savedObject)
    }

    override fun editUser(dto: UserDTO, idUser: Long): ViewUserDTO {
        val user = repository.findById(idUser).orElseThrow { throw NotFoundException("User not found") }
        prepareObjectForUpdate(dto, user)
        val savedObject = repository.save(user)
        return viewMapper.mapViewUser(savedObject)
    }

    private fun prepareObjectForUpdate(dto: UserDTO, user: User) {
        user.apply {
            if (passwordIsNotTheSame(dto.password, user.password)) {
                user.passCode = bcrypt.encode(dto.password)
            }
            name = dto.name
            phone = dto.phone
            address = dto.address
            email = dto.email
            updatedAt = LocalDateTime.now()
        }
    }


    @Transactional
    override fun deleteUser(id: Long) {
        repository.deleteById(id)
    }

    override fun getUser(id: Long): ViewUserDTO {
        val user = repository.findById(id).orElseThrow { throw NotFoundException("User not found") }
        return viewMapper.mapViewUser(user)
    }


    @Transactional(readOnly = true)
    override fun listUsers(): List<UserDTO> {
        val listUser = repository.findAll()
        return viewMapper.mapList(listUser)
    }

    private fun passwordIsNotTheSame(dtoPassword: String, entityPassword: String): Boolean {
        return !bcrypt.matches(dtoPassword, entityPassword)
    }


    private fun passwordEncryption(obj: User) {

        if (obj.passCode.isBlank()) {
            throw BadRequestException("The passowrd cannot be null")
        } else {
            obj.apply {
                passCode = bcrypt.encode(obj.passCode)
            }
        }
    }

    private fun validateDto(dto: UserDTO) {
        val user = repository.findByEmail(dto.email)
        if (user != null) {
            throw BadRequestException("user already registered")
        }
    }
}
