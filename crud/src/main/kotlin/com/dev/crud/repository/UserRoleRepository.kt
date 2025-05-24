package com.dev.crud.repository

import com.dev.crud.domain.UserRole
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface UserRoleRepository : JpaRepository<UserRole, Long> {
}