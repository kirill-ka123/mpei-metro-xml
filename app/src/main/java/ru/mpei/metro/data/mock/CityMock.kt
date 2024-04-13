package ru.mpei.metro.data.mock

import ru.mpei.metro.domain.model.City

enum class CityMock(override val value: City): Mock<City> {
    MOSCOW(
        City(
            id = "Moscow",
            name = "Москва",
            stations = StationMock.entries.map { it.value },
            branches = BranchMock.entries.map { it.value },
            transitions = TransactionMock.entries.map { it.value },
        )
    ),
    SAINT_PETERSBURG(
        City(
            id = "Saint-Petersburg",
            name = "Санкт-Петербург",
            stations = StationMock.entries.map { it.value },
            branches = BranchMock.entries.map { it.value },
            transitions = TransactionMock.entries.map { it.value },
        )
    ),
}
