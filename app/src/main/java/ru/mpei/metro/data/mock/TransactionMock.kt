package ru.mpei.metro.data.mock

import ru.mpei.metro.domain.model.Transition

enum class TransactionMock(override val value: Transition): Mock<Transition> {
    OKHOTNY_RYAD_TO_TEATRALNAYA_TO_PLOSHAD_REVOLYUTSII(
        Transition(
            id = "OKHOTNY_RYAD_TO_TEATRALNAYA_TO_PLOSHAD_REVOLYUTSII",
            time = 0,
            stationsIds = listOf(
                "OKHOTNY_RYAD",
                "TEATRALNAYA",
                "PLOSHAD_REVOLYUTSII",
            ),
        )
    ),
    SRETENSKY_BULVAR_TO_CHISTYE_PRUDY(
        Transition(
            id = "SRETENSKY_BULVAR_TO_CHISTYE_PRUDY",
            time = 0,
            stationsIds = listOf(
                "SRETENSKY_BULVAR",
                "CHISTYE_PRUDY",
            ),
        )
    ),
    ARBATSKAYA_TO_BIBLIOTEKA_IM_LENINA(
        Transition(
            id = "ARBATSKAYA_TO_BIBLIOTEKA_IM_LENINA",
            time = 0,
            stationsIds = listOf(
                "ARBATSKAYA",
                "BIBLIOTEKA_IM_LENINA",
            ),
        )
    ),
}
