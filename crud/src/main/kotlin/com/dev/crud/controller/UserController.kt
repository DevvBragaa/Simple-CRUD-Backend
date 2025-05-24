package com.dev.crud.controller

import com.dev.crud.dto.user.UserDTO
import com.dev.crud.dto.user.ViewUserDTO
import com.dev.crud.service.user.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/user")
class UserController(
    private val service: UserService
) {

    @PostMapping("/add-first-user")
    @ResponseStatus(HttpStatus.CREATED)
    fun createUser(
        @RequestBody userDto: UserDTO
    ): ViewUserDTO {
        return try {
            service.addUser(userDto = userDto)
        } catch (e: Exception) {
            throw e
        }
    }

    @PostMapping("/create-common-user")
    @ResponseStatus(HttpStatus.CREATED)
    fun createCommonUser(
        @RequestBody userDto: UserDTO
    ): ViewUserDTO{
        return try {
            service.addCommonUser(userDto)
        }catch (e:Exception){
            throw e
        }
    }

    @PutMapping("/edit-user/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun editUser(
        @RequestBody userDTo: UserDTO,
        @PathVariable id: Long
    ): ViewUserDTO {
        return try {
            service.editUser(userDTo, idUser = id)
        } catch (e: Exception) {
            throw e
        }
    }

    @GetMapping("/get/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getUser(
        @PathVariable("id") id: Long
    ): ViewUserDTO {
        return try {
            service.getUser(id = id)
        } catch (e: Exception) {
            throw e
        }
    }

    @GetMapping("/list-user")
    @ResponseStatus(HttpStatus.OK)
    fun listUser(): List<UserDTO> {
        return try {
            service.listUsers()
        } catch (e: Exception) {
            throw e
        }
    }

    @DeleteMapping("/delete-user/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteUser(
        @PathVariable id: Long
    ) {
        return try {
            service.deleteUser(id = id)
        } catch (e: Exception) {
            throw e
        }
    }
}