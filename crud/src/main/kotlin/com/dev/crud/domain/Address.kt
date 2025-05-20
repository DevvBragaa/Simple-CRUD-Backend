package com.dev.crud.domain

import jakarta.persistence.*


@Entity
@Table(name = "address")
@SequenceGenerator(name = "seq_address", initialValue = 1, allocationSize = 1)
class Address(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    var id: Long? = null,

    @Column(name = "cep")
    var cep: String,

    @Column(name = "city")
    var city: String,

    @Column(name = "state")
    var state: String,

    @Column(name = "bairro")
    var bairro: String,

    @OneToOne
    var user: User
) {

}
