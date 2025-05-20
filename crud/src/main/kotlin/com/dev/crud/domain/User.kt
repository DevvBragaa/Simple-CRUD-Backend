package com.dev.crud.domain

import jakarta.persistence.*


@Entity
@Table(name = "user")
@SequenceGenerator(name = "seq_user", initialValue = 1, allocationSize = 1)
class User(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    var id: Long? = null,

    @Column(name = "name")
    var name: String,

    @Column(name = "phone")
    var phone: String,

    @Column(name = "email")
    var email: String,

    @Column(name = "password")
    var password: String,

    @OneToOne
    @Column(name = "address")
    var address: Address
) {


}