package ru.mpei.metro.domain.model

data class City(
    val id: String,
    val name: String,
    val stations: List<Station>,
    val branches: List<Branch>,
    val transitions: List<Transition>,
)
