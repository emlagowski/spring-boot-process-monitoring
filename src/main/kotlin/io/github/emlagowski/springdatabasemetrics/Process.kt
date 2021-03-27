package io.github.emlagowski.springdatabasemetrics

import javax.persistence.*

@Entity
data class Process(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int = 0,

        @Column
        val name: String,

        @Column
        @Enumerated(EnumType.STRING)
        var state: State
) {
    fun moveToRandomNextStep(): State {
        if (state.isTerminal()) {
            throw IllegalStateException("Cannot move further from terminal state $state.")
        }
        val nextStep = state.nextSteps().random()
        this.state = nextStep
        return this.state
    }
}

enum class State {
    REGISTERED {
        override fun nextSteps(): List<State> {
            return listOf(IN_PROGRESS)
        }
    },
    IN_PROGRESS {
        override fun nextSteps(): List<State> {
            return listOf(AUTO, MANUAL)
        }
    },
    AUTO {
        override fun nextSteps(): List<State> {
            return listOf(CLOSED)
        }
    },
    MANUAL {
        override fun nextSteps(): List<State> {
            return listOf(CLOSED)
        }
    },
    CLOSED {
        override fun nextSteps(): List<State> {
            return listOf()
        }
    };

    abstract fun nextSteps(): List<State>

    fun isTerminal(): Boolean = nextSteps().isEmpty()

}