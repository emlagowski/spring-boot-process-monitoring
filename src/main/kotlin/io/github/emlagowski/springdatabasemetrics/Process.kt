package io.github.emlagowski.springdatabasemetrics

import javax.persistence.*

@Entity
data class Request(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int = 0,

        @Column
        val name: String,

        @Column
        @Enumerated(EnumType.STRING)
        val type: Type
)

enum class Type {
    REGISTERED,
    IN_PROGRESS,
    CLOSED;
}