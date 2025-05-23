package com.dev.crud.domain

import com.dev.crud.enums.EnumRole
import jakarta.persistence.*

@Entity
@Table(name = "user_role")
class UserRole (
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id:Long?=null,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User,

    @Column(name = "role")
    var role: EnumRole



){
}