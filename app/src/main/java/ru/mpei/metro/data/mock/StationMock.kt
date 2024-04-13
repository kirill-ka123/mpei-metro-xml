package ru.mpei.metro.data.mock

import ru.mpei.metro.domain.model.Position
import ru.mpei.metro.data.model.RoadEntity
import ru.mpei.metro.domain.model.Station

enum class StationMock(override val value: Station) : Mock<Station> {
    BULVAR_ROKOSSOVSKOGO(
        Station(
            id = "BULVAR_ROKOSSOVSKOGO",
            name = "Бульвар Рокоссовского",
            position = Position(x = 1213.3f, y = 61.7f),
            branch = BranchMock.SOKOLNICHESKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "CHERKIZOVSKAYA",
                    1000,
                ),
            ),
        )
    ),
    CHERKIZOVSKAYA(
        Station(
            id = "CHERKIZOVSKAYA",
            name = "Черкизовская",
            position = Position(x = 1256.7f, y = 97.6f),
            branch = BranchMock.SOKOLNICHESKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "BULVAR_ROKOSSOVSKOGO",
                    1000,
                ),
                RoadEntity(
                    "PREOBRAZHENSKAYA_PLOSHCHAD",
                    1000,
                ),
            ),
        )
    ),
    PREOBRAZHENSKAYA_PLOSHCHAD(
        Station(
            id = "PREOBRAZHENSKAYA_PLOSHCHAD",
            name = "Преображенская площадь",
            position = Position(x = 1232.0f, y = 122.3f),
            branch = BranchMock.SOKOLNICHESKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "CHERKIZOVSKAYA",
                    1000,
                ),
                RoadEntity(
                    "SOKOLNIKI",
                    1000,
                ),
            ),
        )
    ),
    SOKOLNIKI(
        Station(
            id = "SOKOLNIKI",
            name = "Сокольники",
            position = Position(x = 1196.4f, y = 158.0f),
            branch = BranchMock.SOKOLNICHESKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "PREOBRAZHENSKAYA_PLOSHCHAD",
                    1000,
                ),
                RoadEntity(
                    "KRASNOSELSKAYA",
                    1000,
                ),
            ),
        )
    ),
    KRASNOSELSKAYA(
        Station(
            id = "KRASNOSELSKAYA",
            name = "Красносельская",
            position = Position(x = 1159.8f, y = 194.6f),
            branch = BranchMock.SOKOLNICHESKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "SOKOLNIKI",
                    1000,
                ),
                RoadEntity(
                    "KOMSOMOLSKAYA",
                    1000,
                ),
            ),
        )
    ),
    KOMSOMOLSKAYA(
        Station(
            id = "KOMSOMOLSKAYA",
            name = "Комсомольская",
            position = Position(x = 1120.0f, y = 234.3f),
            branch = BranchMock.SOKOLNICHESKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "KRASNOSELSKAYA",
                    1000,
                ),
                RoadEntity(
                    "VOROTA",
                    1000,
                ),
            ),
        )
    ),
    VOROTA(
        Station(
            id = "VOROTA",
            name = "Красные ворота",
            position = Position(x = 1068.2f, y = 286.2f),
            branch = BranchMock.SOKOLNICHESKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "KOMSOMOLSKAYA",
                    1000,
                ),
                RoadEntity(
                    "CHISTYE_PRUDY",
                    1000,
                ),
            ),
        )
    ),
    CHISTYE_PRUDY(
        Station(
            id = "CHISTYE_PRUDY",
            name = "Чистые пруды",
            position = Position(x = 1003.3f, y = 346.3f),
            branch = BranchMock.SOKOLNICHESKAYA_LINIYA.value,
            transaction = TransactionMock.SRETENSKY_BULVAR_TO_CHISTYE_PRUDY.value,
            roadConnections = listOf(
                RoadEntity(
                    "VOROTA",
                    1000,
                ),
                RoadEntity(
                    "LUBYANKA",
                    1000,
                ),
                RoadEntity(
                    "SRETENSKY_BULVAR",
                    1000,
                ),
            ),
        )
    ),
    LUBYANKA(
        Station(
            id = "LUBYANKA",
            name = "Лубянка",
            position = Position(x = 893.7f, y = 367.3f),
            branch = BranchMock.SOKOLNICHESKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "CHISTYE_PRUDY",
                    1000,
                ),
                RoadEntity(
                    "OKHOTNY_RYAD",
                    1000,
                ),
            ),
        )
    ),
    OKHOTNY_RYAD(
        Station(
            id = "OKHOTNY_RYAD",
            name = "Охотный Ряд",
            position = Position(x = 822.3f, y = 438.3f),
            branch = BranchMock.SOKOLNICHESKAYA_LINIYA.value,
            transaction = TransactionMock.OKHOTNY_RYAD_TO_TEATRALNAYA_TO_PLOSHAD_REVOLYUTSII.value,
            roadConnections = listOf(
                RoadEntity(
                    "LUBYANKA",
                    1000,
                ),
                RoadEntity(
                    "BIBLIOTEKA_IM_LENINA",
                    1000,
                ),
                RoadEntity(
                    "TEATRALNAYA",
                    1000,
                ),
                RoadEntity(
                    "PLOSHAD_REVOLYUTSII",
                    1000,
                ),
            ),
        )
    ),
    BIBLIOTEKA_IM_LENINA(
        Station(
            id = "BIBLIOTEKA_IM_LENINA",
            name = "Библиотека имени Ленина",
            position = Position(x = 774.7f, y = 486.3f),
            branch = BranchMock.SOKOLNICHESKAYA_LINIYA.value,
            transaction = TransactionMock.ARBATSKAYA_TO_BIBLIOTEKA_IM_LENINA.value,
            roadConnections = listOf(
                RoadEntity(
                    "OKHOTNY_RYAD",
                    1000,
                ),
                RoadEntity(
                    "KROPOTKINSKAYA",
                    1000,
                ),
                RoadEntity(
                    "ARBATSKAYA",
                    1000,
                ),
            ),
        )
    ),
    KROPOTKINSKAYA(
        Station(
            id = "KROPOTKINSKAYA",
            name = "Кропоткинская",
            position = Position(x = 700.8f, y = 560.2f),
            branch = BranchMock.SOKOLNICHESKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "BIBLIOTEKA_IM_LENINA",
                    1000,
                ),
                RoadEntity(
                    "PARK_KULTURY",
                    1000,
                ),
            ),
        )
    ),
    PARK_KULTURY(
        Station(
            id = "PARK_KULTURY",
            name = "Парк культуры",
            position = Position(x = 592.7f, y = 668.3f),
            branch = BranchMock.SOKOLNICHESKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "KROPOTKINSKAYA",
                    1000,
                ),
                RoadEntity(
                    "FRUNZENSKAYA",
                    1000,
                ),
            ),
        )
    ),
    FRUNZENSKAYA(
        Station(
            id = "FRUNZENSKAYA",
            name = "Фрунзенская",
            position = Position(x = 522.7f, y = 738.3f),
            branch = BranchMock.SOKOLNICHESKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "PARK_KULTURY",
                    1000,
                ),
                RoadEntity(
                    "SPORTIVNAYA",
                    1000,
                ),
            ),
        )
    ),
    SPORTIVNAYA(
        Station(
            id = "SPORTIVNAYA",
            name = "Спортивная",
            position = Position(x = 462.0f, y = 799.0f),
            branch = BranchMock.SOKOLNICHESKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "FRUNZENSKAYA",
                    1000,
                ),
                RoadEntity(
                    "GORY",
                    1000,
                ),
            ),
        )
    ),
    GORY(
        Station(
            id = "GORY",
            name = "Воробьёвы горы",
            position = Position(x = 387.3f, y = 873.7f),
            branch = BranchMock.SOKOLNICHESKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "SPORTIVNAYA",
                    1000,
                ),
                RoadEntity(
                    "UNIVERSITET",
                    1000,
                ),
            ),
        )
    ),
    UNIVERSITET(
        Station(
            id = "UNIVERSITET",
            name = "Университет",
            position = Position(x = 359.3f, y = 901.7f),
            branch = BranchMock.SOKOLNICHESKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "GORY",
                    1000,
                ),
                RoadEntity(
                    "PROSPEKT_VERNADSKOGO",
                    1000,
                ),
            ),
        )
    ),
    PROSPEKT_VERNADSKOGO(
        Station(
            id = "PROSPEKT_VERNADSKOGO",
            name = "Проспект Вернадского",
            position = Position(x = 326.7f, y = 936.7f),
            branch = BranchMock.SOKOLNICHESKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "BULVAR_ROKOSSOVSKOGO",
                    1000,
                ),
                RoadEntity(
                    "ZAPADNAYA",
                    1000,
                ),
            ),
        )
    ),
    ZAPADNAYA(
        Station(
            id = "ZAPADNAYA",
            name = "Юго-Западная",
            position = Position(x = 312.7f, y = 950.7f),
            branch = BranchMock.SOKOLNICHESKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "PROSPEKT_VERNADSKOGO",
                    1000,
                ),
                RoadEntity(
                    "TROPARYOVO",
                    1000,
                ),
            ),
        )
    ),
    TROPARYOVO(
        Station(
            id = "TROPARYOVO",
            name = "Тропарёво",
            position = Position(x = 289.3f, y = 974.0f),
            branch = BranchMock.SOKOLNICHESKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "ZAPADNAYA",
                    1000,
                ),
                RoadEntity(
                    "RUMYANTSEVO",
                    1000,
                ),
            ),
        )
    ),
    RUMYANTSEVO(
        Station(
            id = "RUMYANTSEVO",
            name = "Румянцево",
            position = Position(x = 266.0f, y = 998.7f),
            branch = BranchMock.SOKOLNICHESKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "TROPARYOVO",
                    1000,
                ),
                RoadEntity(
                    "SALARYEVO",
                    1000,
                ),
            ),
        )
    ),
    SALARYEVO(
        Station(
            id = "SALARYEVO",
            name = "Саларьево",
            position = Position(x = 242.7f, y = 1023.0f),
            branch = BranchMock.SOKOLNICHESKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "RUMYANTSEVO",
                    1000,
                ),
                RoadEntity(
                    "FILATOV_LUG",
                    1000,
                ),
            ),
        )
    ),
    FILATOV_LUG(
        Station(
            id = "FILATOV_LUG",
            name = "Филатов Луг",
            position = Position(x = 233.3f, y = 1060.3f),
            branch = BranchMock.SOKOLNICHESKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "SALARYEVO",
                    1000,
                ),
                RoadEntity(
                    "PROKSHINO",
                    1000,
                ),
            ),
        )
    ),
    PROKSHINO(
        Station(
            id = "PROKSHINO",
            name = "Прошкино",
            position = Position(x = 233.3f, y = 1097.7f),
            branch = BranchMock.SOKOLNICHESKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "FILATOV_LUG",
                    1000,
                ),
                RoadEntity(
                    "OLHOVAYA",
                    1000,
                ),
            ),
        )
    ),
    OLHOVAYA(
        Station(
            id = "OLHOVAYA",
            name = "Ольховая",
            position = Position(x = 233.3f, y = 1130.3f),
            branch = BranchMock.SOKOLNICHESKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "PROKSHINO",
                    1000,
                ),
                RoadEntity(
                    "KOMMUNARKA",
                    1000,
                ),
            ),
        )
    ),
    KOMMUNARKA(
        Station(
            id = "KOMMUNARKA",
            name = "Коммунарка",
            position = Position(x = 233.3f, y = 1163.0f),
            branch = BranchMock.SOKOLNICHESKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "OLHOVAYA",
                    1000,
                ),
            ),
        )
    ),
    KHOVRINO(
        Station(
            id = "KHOVRINO",
            name = "Ховрино",
            position = Position(x = 410.7f, y = -139.0f),
            branch = BranchMock.ZAMOSKVORECKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "BELOMORSKAYA",
                    1000,
                ),
            ),
        )
    ),
    BELOMORSKAYA(
        Station(
            id = "BELOMORSKAYA",
            name = "Беломорская",
            position = Position(x = 410.7f, y = -101.7f),
            branch = BranchMock.ZAMOSKVORECKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "KHOVRINO",
                    1000,
                ),
                RoadEntity(
                    "VOKZAL",
                    1000,
                ),
            ),
        )
    ),
    VOKZAL(
        Station(
            id = "VOKZAL",
            name = "Речной вокзал",
            position = Position(x = 410.7f, y = -64.3f),
            branch = BranchMock.ZAMOSKVORECKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "BELOMORSKAYA",
                    1000,
                ),
                RoadEntity(
                    "VODNY_STADION",
                    1000,
                ),
            ),
        )
    ),
    VODNY_STADION(
        Station(
            id = "VODNY_STADION",
            name = "Водный стадион",
            position = Position(x = 439.1f, y = -13.0f),
            branch = BranchMock.ZAMOSKVORECKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "VOKZAL",
                    1000,
                ),
                RoadEntity(
                    "VOYKOVSKAYA",
                    1000,
                ),
            ),
        )
    ),
    VOYKOVSKAYA(
        Station(
            id = "VOYKOVSKAYA",
            name = "Войковская",
            position = Position(x = 467.6f, y = 15.0f),
            branch = BranchMock.ZAMOSKVORECKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "VODNY_STADION",
                    1000,
                ),
                RoadEntity(
                    "SOKOL",
                    1000,
                ),
            ),
        )
    ),
    SOKOL(
        Station(
            id = "SOKOL",
            name = "Сокол",
            position = Position(x = 504.0f, y = 50.9f),
            branch = BranchMock.ZAMOSKVORECKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "VOYKOVSKAYA",
                    1000,
                ),
                RoadEntity(
                    "AEROPORT",
                    1000,
                ),
            ),
        )
    ),
    AEROPORT(
        Station(
            id = "AEROPORT",
            name = "Аэропорт",
            position = Position(x = 541.3f, y = 87.5f),
            branch = BranchMock.ZAMOSKVORECKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "BULVAR_ROKOSSOVSKOGO",
                    1000,
                ),
                RoadEntity(
                    "DINAMO",
                    1000,
                ),
            ),
        )
    ),
    DINAMO(
        Station(
            id = "DINAMO",
            name = "Динамо",
            position = Position(x = 575.4f, y = 120.9f),
            branch = BranchMock.ZAMOSKVORECKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "SOKOL",
                    1000,
                ),
                RoadEntity(
                    "BELORUSSKAYA",
                    1000,
                ),
            ),
        )
    ),
    BELORUSSKAYA(
        Station(
            id = "BELORUSSKAYA",
            name = "Белорусская",
            position = Position(x = 620.7f, y = 165.4f),
            branch = BranchMock.ZAMOSKVORECKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "DINAMO",
                    1000,
                ),
                RoadEntity(
                    "MAYAKOVSKAYA",
                    1000,
                ),
            ),
        )
    ),
    MAYAKOVSKAYA(
        Station(
            id = "MAYAKOVSKAYA",
            name = "Маяковская",
            position = Position(x = 695.3f, y = 257.7f),
            branch = BranchMock.ZAMOSKVORECKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "BELORUSSKAYA",
                    1000,
                ),
                RoadEntity(
                    "TVERSKAYA",
                    1000,
                ),
            ),
        )
    ),
    TVERSKAYA(
        Station(
            id = "TVERSKAYA",
            name = "Тверская",
            position = Position(x = 704.7f, y = 323.0f),
            branch = BranchMock.ZAMOSKVORECKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "MAYAKOVSKAYA",
                    1000,
                ),
                RoadEntity(
                    "TEATRALNAYA",
                    1000,
                ),
            ),
        )
    ),
    TEATRALNAYA(
        Station(
            id = "TEATRALNAYA",
            name = "Театральная",
            position = Position(x = 847.5f, y = 463.0f),
            branch = BranchMock.ZAMOSKVORECKAYA_LINIYA.value,
            transaction = TransactionMock.OKHOTNY_RYAD_TO_TEATRALNAYA_TO_PLOSHAD_REVOLYUTSII.value,
            roadConnections = listOf(
                RoadEntity(
                    "TVERSKAYA",
                    1000,
                ),
                RoadEntity(
                    "NOVOKUZNETSKAYA",
                    1000,
                ),
                RoadEntity(
                    "OKHOTNY_RYAD",
                    1000,
                ),
                RoadEntity(
                    "PLOSHAD_REVOLYUTSII",
                    1000,
                ),
            ),
        )
    ),
    NOVOKUZNETSKAYA(
        Station(
            id = "NOVOKUZNETSKAYA",
            name = "Новокузнецкая",
            position = Position(x = 947.8f, y = 593.6f),
            branch = BranchMock.ZAMOSKVORECKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "TEATRALNAYA",
                    1000,
                ),
                RoadEntity(
                    "PAVELETSKAYA",
                    1000,
                ),
            ),
        )
    ),
    PAVELETSKAYA(
        Station(
            id = "PAVELETSKAYA",
            name = "Павелецкая",
            position = Position(x = 1050.0f, y = 705.7f),
            branch = BranchMock.ZAMOSKVORECKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "NOVOKUZNETSKAYA",
                    1000,
                ),
                RoadEntity(
                    "AVTOZAVODSKAYA",
                    1000,
                ),
            ),
        )
    ),
    AVTOZAVODSKAYA(
        Station(
            id = "AVTOZAVODSKAYA",
            name = "Автозаводская",
            position = Position(x = 1157.3f, y = 855.0f),
            branch = BranchMock.ZAMOSKVORECKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "PAVELETSKAYA",
                    1000,
                ),
                RoadEntity(
                    "TEKHNOPARK",
                    1000,
                ),
            ),
        )
    ),
    TEKHNOPARK(
        Station(
            id = "TEKHNOPARK",
            name = "Технопарк",
            position = Position(x = 1157.3f, y = 920.3f),
            branch = BranchMock.ZAMOSKVORECKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "AVTOZAVODSKAYA",
                    1000,
                ),
                RoadEntity(
                    "KOLOMENSKAYA",
                    1000,
                ),
            ),
        )
    ),
    KOLOMENSKAYA(
        Station(
            id = "KOLOMENSKAYA",
            name = "Коломенская",
            position = Position(x = 1157.3f, y = 962.3f),
            branch = BranchMock.ZAMOSKVORECKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "TEKHNOPARK",
                    1000,
                ),
                RoadEntity(
                    "KASHIRSKAYA",
                    1000,
                ),
            ),
        )
    ),
    KASHIRSKAYA(
        Station(
            id = "KASHIRSKAYA",
            name = "Каширская",
            position = Position(x = 1157.3f, y = 1041.7f),
            branch = BranchMock.ZAMOSKVORECKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "KOLOMENSKAYA",
                    1000,
                ),
                RoadEntity(
                    "KANTEMIROVSKAYA",
                    1000,
                ),
            ),
        )
    ),
    KANTEMIROVSKAYA(
        Station(
            id = "KANTEMIROVSKAYA",
            name = "Кантемировская",
            position = Position(x = 1157.3f, y = 1079.0f),
            branch = BranchMock.ZAMOSKVORECKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "KASHIRSKAYA",
                    1000,
                ),
                RoadEntity(
                    "TSARITSYNO",
                    1000,
                ),
            ),
        )
    ),
    TSARITSYNO(
        Station(
            id = "TSARITSYNO",
            name = "Царицыно",
            position = Position(x = 1157.3f, y = 1107.0f),
            branch = BranchMock.ZAMOSKVORECKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "KANTEMIROVSKAYA",
                    1000,
                ),
                RoadEntity(
                    "OREKHOVO",
                    1000,
                ),
            ),
        )
    ),
    OREKHOVO(
        Station(
            id = "OREKHOVO",
            name = "Орехово",
            position = Position(x = 1177.9f, y = 1137.3f),
            branch = BranchMock.ZAMOSKVORECKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "TSARITSYNO",
                    1000,
                ),
                RoadEntity(
                    "DOMODEDOVSKAYA",
                    1000,
                ),
            ),
        )
    ),
    DOMODEDOVSKAYA(
        Station(
            id = "DOMODEDOVSKAYA",
            name = "Домодедовская",
            position = Position(x = 1196.5f, y = 1158.3f),
            branch = BranchMock.ZAMOSKVORECKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "OREKHOVO",
                    1000,
                ),
                RoadEntity(
                    "KRASNOGVARDEYSKAYA",
                    1000,
                ),
            ),
        )
    ),
    KRASNOGVARDEYSKAYA(
        Station(
            id = "KRASNOGVARDEYSKAYA",
            name = "Красногвардейская",
            position = Position(x = 1218.0f, y = 1181.7f),
            branch = BranchMock.ZAMOSKVORECKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "DOMODEDOVSKAYA",
                    1000,
                ),
                RoadEntity(
                    "ALMA_ATINSKAYA",
                    1000,
                ),
            ),
        )
    ),
    ALMA_ATINSKAYA(
        Station(
            id = "ALMA_ATINSKAYA",
            name = "Алма-Атинская",
            position = Position(x = 1283.3f, y = 1181.7f),
            branch = BranchMock.ZAMOSKVORECKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "KRASNOGVARDEYSKAYA",
                    1000,
                ),
            ),
        )
    ),
    SHOSSE(
        Station(
            id = "SHOSSE",
            name = "SHOSSE",
            position = Position(x = 98.0f, y = -31.7f),
            branch = BranchMock.ARBATSKO_POKROVSKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "MITINO",
                    1000,
                ),
            ),
        )
    ),
    MITINO(
        Station(
            id = "MITINO",
            name = "MITINO",
            position = Position(x = 98.0f, y = 5.7f),
            branch = BranchMock.ARBATSKO_POKROVSKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "SHOSSE",
                    1000,
                ),
                RoadEntity(
                    "VOLOKOLAMSKAYA",
                    1000,
                ),
            ),
        )
    ),
    VOLOKOLAMSKAYA(
        Station(
            id = "VOLOKOLAMSKAYA",
            name = "VOLOKOLAMSKAYA",
            position = Position(x = 98.0f, y = 43.0f),
            branch = BranchMock.ARBATSKO_POKROVSKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "MITINO",
                    1000,
                ),
                RoadEntity(
                    "MYAKININO",
                    1000,
                ),
            ),
        )
    ),
    MYAKININO(
        Station(
            id = "MYAKININO",
            name = "MYAKININO",
            position = Position(x = 98.0f, y = 89.7f),
            branch = BranchMock.ARBATSKO_POKROVSKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "VOLOKOLAMSKAYA",
                    1000,
                ),
                RoadEntity(
                    "STROGINO",
                    1000,
                ),
            ),
        )
    ),
    STROGINO(
        Station(
            id = "STROGINO",
            name = "STROGINO",
            position = Position(x = 98.0f, y = 127.0f),
            branch = BranchMock.ARBATSKO_POKROVSKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "MYAKININO",
                    1000,
                ),
                RoadEntity(
                    "KRYLATSKOYE",
                    1000,
                ),
            ),
        )
    ),
    KRYLATSKOYE(
        Station(
            id = "KRYLATSKOYE",
            name = "KRYLATSKOYE",
            position = Position(x = 98.0f, y = 164.3f),
            branch = BranchMock.ARBATSKO_POKROVSKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "STROGINO",
                    1000,
                ),
                RoadEntity(
                    "MOLODYOZHNAYA",
                    1000,
                ),
            ),
        )
    ),
    MOLODYOZHNAYA(
        Station(
            id = "MOLODYOZHNAYA",
            name = "MOLODYOZHNAYA",
            position = Position(x = 98.0f, y = 201.7f),
            branch = BranchMock.ARBATSKO_POKROVSKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "KRYLATSKOYE",
                    1000,
                ),
                RoadEntity(
                    "KUNTSEVSKAYA",
                    1000,
                ),
            ),
        )
    ),
    KUNTSEVSKAYA(
        Station(
            id = "KUNTSEVSKAYA",
            name = "KUNTSEVSKAYA",
            position = Position(x = 98.0f, y = 276.3f),
            branch = BranchMock.ARBATSKO_POKROVSKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "MOLODYOZHNAYA",
                    1000,
                ),
                RoadEntity(
                    "SLAVYANSKY_BULVAR",
                    1000,
                ),
            ),
        )
    ),
    SLAVYANSKY_BULVAR(
        Station(
            id = "SLAVYANSKY_BULVAR",
            name = "SLAVYANSKY_BULVAR",
            position = Position(x = 144.7f, y = 411.7f),
            branch = BranchMock.ARBATSKO_POKROVSKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "KUNTSEVSKAYA",
                    1000,
                ),
                RoadEntity(
                    "PARK_POBEDY",
                    1000,
                ),
            ),
        )
    ),
    PARK_POBEDY(
        Station(
            id = "PARK_POBEDY",
            name = "PARK_POBEDY",
            position = Position(x = 280.0f, y = 509.7f),
            branch = BranchMock.ARBATSKO_POKROVSKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "SLAVYANSKY_BULVAR",
                    1000,
                ),
                RoadEntity(
                    "KIEVSKAYA",
                    1000,
                ),
            ),
        )
    ),
    KIEVSKAYA(
        Station(
            id = "KIEVSKAYA",
            name = "KIEVSKAYA",
            position = Position(x = 504.0f, y = 509.7f),
            branch = BranchMock.ARBATSKO_POKROVSKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "PARK_POBEDY",
                    1000,
                ),
                RoadEntity(
                    "SMOLENSKAYA",
                    1000,
                ),
            ),
        )
    ),
    SMOLENSKAYA(
        Station(
            id = "SMOLENSKAYA",
            name = "SMOLENSKAYA",
            position = Position(x = 644.0f, y = 509.7f),
            branch = BranchMock.ARBATSKO_POKROVSKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "KIEVSKAYA",
                    1000,
                ),
                RoadEntity(
                    "ARBATSKAYA",
                    1000,
                ),
            ),
        )
    ),
    ARBATSKAYA(
        Station(
            id = "ARBATSKAYA",
            name = "ARBATSKAYA",
            position = Position(x = 728.0f, y = 486.3f),
            branch = BranchMock.ARBATSKO_POKROVSKAYA_LINIYA.value,
            transaction = TransactionMock.ARBATSKAYA_TO_BIBLIOTEKA_IM_LENINA.value,
            roadConnections = listOf(
                RoadEntity(
                    "SMOLENSKAYA",
                    1000,
                ),
                RoadEntity(
                    "PLOSHAD_REVOLYUTSII",
                    1000,
                ),
                RoadEntity(
                    "BIBLIOTEKA_IM_LENINA",
                    1000,
                ),
            ),
        )
    ),
    PLOSHAD_REVOLYUTSII(
        Station(
            id = "PLOSHAD_REVOLYUTSII",
            name = "PLOSHAD_REVOLYUTSII",
            position = Position(x = 871.7f, y = 486.3f),
            branch = BranchMock.ARBATSKO_POKROVSKAYA_LINIYA.value,
            transaction = TransactionMock.OKHOTNY_RYAD_TO_TEATRALNAYA_TO_PLOSHAD_REVOLYUTSII.value,
            roadConnections = listOf(
                RoadEntity(
                    "ARBATSKAYA",
                    1000,
                ),
                RoadEntity(
                    "KURSKAYA",
                    1000,
                ),
                RoadEntity(
                    "OKHOTNY_RYAD",
                    1000,
                ),
                RoadEntity(
                    "TEATRALNAYA",
                    1000,
                ),
            ),
        )
    ),
    KURSKAYA(
        Station(
            id = "KURSKAYA",
            name = "KURSKAYA",
            position = Position(x = 1130.6f, y = 461.7f),
            branch = BranchMock.ARBATSKO_POKROVSKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "PLOSHAD_REVOLYUTSII",
                    1000,
                ),
                RoadEntity(
                    "BAUMANSKAYA",
                    1000,
                ),
            ),
        )
    ),
    BAUMANSKAYA(
        Station(
            id = "BAUMANSKAYA",
            name = "BAUMANSKAYA",
            position = Position(x = 1185.3f, y = 407.0f),
            branch = BranchMock.ARBATSKO_POKROVSKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "KURSKAYA",
                    1000,
                ),
                RoadEntity(
                    "ELEKTROZAVODSKAYA",
                    1000,
                ),
            ),
        )
    ),
    ELEKTROZAVODSKAYA(
        Station(
            id = "ELEKTROZAVODSKAYA",
            name = "ELEKTROZAVODSKAYA",
            position = Position(x = 1213.3f, y = 379.0f),
            branch = BranchMock.ARBATSKO_POKROVSKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "BAUMANSKAYA",
                    1000,
                ),
                RoadEntity(
                    "SEMYONOVSKAYA",
                    1000,
                ),
            ),
        )
    ),
    SEMYONOVSKAYA(
        Station(
            id = "SEMYONOVSKAYA",
            name = "SEMYONOVSKAYA",
            position = Position(x = 1309.5f, y = 282.9f),
            branch = BranchMock.ARBATSKO_POKROVSKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "ELEKTROZAVODSKAYA",
                    1000,
                ),
                RoadEntity(
                    "PARTIZANSKAYA",
                    1000,
                ),
            ),
        )
    ),
    PARTIZANSKAYA(
        Station(
            id = "PARTIZANSKAYA",
            name = "PARTIZANSKAYA",
            position = Position(x = 1369.4f, y = 222.9f),
            branch = BranchMock.ARBATSKO_POKROVSKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "SEMYONOVSKAYA",
                    1000,
                ),
                RoadEntity(
                    "IZMAYLOVSKAYA",
                    1000,
                ),
            ),
        )
    ),
    IZMAYLOVSKAYA(
        Station(
            id = "IZMAYLOVSKAYA",
            name = "IZMAYLOVSKAYA",
            position = Position(x = 1406.1f, y = 186.3f),
            branch = BranchMock.ARBATSKO_POKROVSKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "PARTIZANSKAYA",
                    1000,
                ),
                RoadEntity(
                    "PERVOMAYSKAYA",
                    1000,
                ),
            ),
        )
    ),
    PERVOMAYSKAYA(
        Station(
            id = "PERVOMAYSKAYA",
            name = "PERVOMAYSKAYA",
            position = Position(x = 1442.7f, y = 149.6f),
            branch = BranchMock.ARBATSKO_POKROVSKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "IZMAYLOVSKAYA",
                    1000,
                ),
                RoadEntity(
                    "SHCHYOLKOVSKAYA",
                    1000,
                ),
            ),
        )
    ),
    SHCHYOLKOVSKAYA(
        Station(
            id = "SHCHYOLKOVSKAYA",
            name = "SHCHYOLKOVSKAYA",
            position = Position(x = 1479.3f, y = 113.0f),
            branch = BranchMock.ARBATSKO_POKROVSKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "PERVOMAYSKAYA",
                    1000,
                ),
            ),
        )
    ),
    FIZTEH(
        Station(
            id = "FIZTEH",
            name = "FIZTEH",
            position = Position(x = 728.0f, y = -279.0f),
            branch = BranchMock.LYUBLINSKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "LIANOZOVO",
                    1000,
                ),
            ),
        )
    ),
    LIANOZOVO(
        Station(
            id = "LIANOZOVO",
            name = "LIANOZOVO",
            position = Position(x = 728.0f, y = -246.3f),
            branch = BranchMock.LYUBLINSKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "FIZTEH",
                    1000,
                ),
                RoadEntity(
                    "YAHROMSKAYA",
                    1000,
                ),
            ),
        )
    ),
    YAHROMSKAYA(
        Station(
            id = "YAHROMSKAYA",
            name = "YAHROMSKAYA",
            position = Position(x = 728.0f, y = -213.7f),
            branch = BranchMock.LYUBLINSKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "LIANOZOVO",
                    1000,
                ),
                RoadEntity(
                    "SELIGERSKAYA",
                    1000,
                ),
            ),
        )
    ),
    SELIGERSKAYA(
        Station(
            id = "SELIGERSKAYA",
            name = "SELIGERSKAYA",
            position = Position(x = 728.0f, y = -181.0f),
            branch = BranchMock.LYUBLINSKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "YAHROMSKAYA",
                    1000,
                ),
                RoadEntity(
                    "LIKHOBORY",
                    1000,
                ),
            ),
        )
    ),
    LIKHOBORY(
        Station(
            id = "LIKHOBORY",
            name = "LIKHOBORY",
            position = Position(x = 728.0f, y = -148.3f),
            branch = BranchMock.LYUBLINSKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "SELIGERSKAYA",
                    1000,
                ),
                RoadEntity(
                    "OKRUZHNAYA",
                    1000,
                ),
            ),
        )
    ),
    OKRUZHNAYA(
        Station(
            id = "OKRUZHNAYA",
            name = "OKRUZHNAYA",
            position = Position(x = 728.0f, y = -113.7f),
            branch = BranchMock.LYUBLINSKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "LIKHOBORY",
                    1000,
                ),
                RoadEntity(
                    "PETR_RAZUMOVSKAYA",
                    1000,
                ),
            ),
        )
    ),
    PETR_RAZUMOVSKAYA(
        Station(
            id = "PETR_RAZUMOVSKAYA",
            name = "PETR_RAZUMOVSKAYA",
            position = Position(x = 739.6f, y = -85.2f),
            branch = BranchMock.LYUBLINSKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "OKRUZHNAYA",
                    1000,
                ),
                RoadEntity(
                    "FONVIZINSKAYA",
                    1000,
                ),
            ),
        )
    ),
    FONVIZINSKAYA(
        Station(
            id = "FONVIZINSKAYA",
            name = "FONVIZINSKAYA",
            position = Position(x = 777.0f, y = -46.6f),
            branch = BranchMock.LYUBLINSKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "PETR_RAZUMOVSKAYA",
                    1000,
                ),
                RoadEntity(
                    "BUTYRSKAYA",
                    1000,
                ),
            ),
        )
    ),
    BUTYRSKAYA(
        Station(
            id = "BUTYRSKAYA",
            name = "BUTYRSKAYA",
            position = Position(x = 818.1f, y = -3.7f),
            branch = BranchMock.LYUBLINSKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "FONVIZINSKAYA",
                    1000,
                ),
                RoadEntity(
                    "MARINA_ROSHCHA",
                    1000,
                ),
            ),
        )
    ),
    MARINA_ROSHCHA(
        Station(
            id = "MARINA_ROSHCHA",
            name = "MARINA_ROSHCHA",
            position = Position(x = 858.7f, y = 38.3f),
            branch = BranchMock.LYUBLINSKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "BUTYRSKAYA",
                    1000,
                ),
                RoadEntity(
                    "DOSTOYEVSKAYA",
                    1000,
                ),
            ),
        )
    ),
    DOSTOYEVSKAYA(
        Station(
            id = "DOSTOYEVSKAYA",
            name = "DOSTOYEVSKAYA",
            position = Position(x = 858.7f, y = 85.0f),
            branch = BranchMock.LYUBLINSKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "MARINA_ROSHCHA",
                    1000,
                ),
                RoadEntity(
                    "TRUBNAYA",
                    1000,
                ),
            ),
        )
    ),
    TRUBNAYA(
        Station(
            id = "TRUBNAYA",
            name = "TRUBNAYA",
            position = Position(x = 859.5f, y = 232.0f),
            branch = BranchMock.LYUBLINSKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "DOSTOYEVSKAYA",
                    1000,
                ),
                RoadEntity(
                    "SRETENSKY_BULVAR",
                    1000,
                ),
            ),
        )
    ),
    SRETENSKY_BULVAR(
        Station(
            id = "SRETENSKY_BULVAR",
            name = "SRETENSKY_BULVAR",
            position = Position(x = 984.7f, y = 368.2f),
            branch = BranchMock.LYUBLINSKAYA_LINIYA.value,
            transaction = TransactionMock.SRETENSKY_BULVAR_TO_CHISTYE_PRUDY.value,
            roadConnections = listOf(
                RoadEntity(
                    "TRUBNAYA",
                    1000,
                ),
                RoadEntity(
                    "CHKALOVSKAYA",
                    1000,
                ),
                RoadEntity(
                    "CHISTYE_PRUDY",
                    1000,
                ),
            ),
        )
    ),
    CHKALOVSKAYA(
        Station(
            id = "CHKALOVSKAYA",
            name = "CHKALOVSKAYA",
            position = Position(x = 1130.6f, y = 512.1f),
            branch = BranchMock.LYUBLINSKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "SRETENSKY_BULVAR",
                    1000,
                ),
                RoadEntity(
                    "RIMSKAYA",
                    1000,
                ),
            ),
        )
    ),
    RIMSKAYA(
        Station(
            id = "RIMSKAYA",
            name = "RIMSKAYA",
            position = Position(x = 1232.0f, y = 640.3f),
            branch = BranchMock.LYUBLINSKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "CHKALOVSKAYA",
                    1000,
                ),
                RoadEntity(
                    "KRESTYANSK_ZASTAVA",
                    1000,
                ),
            ),
        )
    ),
    KRESTYANSK_ZASTAVA(
        Station(
            id = "KRESTYANSK_ZASTAVA",
            name = "KRESTYANSK_ZASTAVA",
            position = Position(x = 1232.0f, y = 719.7f),
            branch = BranchMock.LYUBLINSKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "RIMSKAYA",
                    1000,
                ),
                RoadEntity(
                    "DUBROVKA",
                    1000,
                ),
            ),
        )
    ),
    DUBROVKA(
        Station(
            id = "DUBROVKA",
            name = "DUBROVKA",
            position = Position(x = 1232.0f, y = 771.0f),
            branch = BranchMock.LYUBLINSKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "KRESTYANSK_ZASTAVA",
                    1000,
                ),
                RoadEntity(
                    "KOZHUKHOVSKAYA",
                    1000,
                ),
            ),
        )
    ),
    KOZHUKHOVSKAYA(
        Station(
            id = "KOZHUKHOVSKAYA",
            name = "KOZHUKHOVSKAYA",
            position = Position(x = 1269.3f, y = 822.3f),
            branch = BranchMock.LYUBLINSKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "DUBROVKA",
                    1000,
                ),
                RoadEntity(
                    "PECHATNIKI",
                    1000,
                ),
            ),
        )
    ),
    PECHATNIKI(
        Station(
            id = "PECHATNIKI",
            name = "PECHATNIKI",
            position = Position(x = 1297.3f, y = 852.7f),
            branch = BranchMock.LYUBLINSKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "KOZHUKHOVSKAYA",
                    1000,
                ),
                RoadEntity(
                    "VOLZHSKAYA",
                    1000,
                ),
            ),
        )
    ),
    VOLZHSKAYA(
        Station(
            id = "VOLZHSKAYA",
            name = "VOLZHSKAYA",
            position = Position(x = 1344.0f, y = 925.0f),
            branch = BranchMock.LYUBLINSKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "PECHATNIKI",
                    1000,
                ),
                RoadEntity(
                    "LYUBLINO",
                    1000,
                ),
            ),
        )
    ),
    LYUBLINO(
        Station(
            id = "LYUBLINO",
            name = "LYUBLINO",
            position = Position(x = 1302.0f, y = 985.7f),
            branch = BranchMock.LYUBLINSKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "VOLZHSKAYA",
                    1000,
                ),
                RoadEntity(
                    "BRATISLAVSKAYA",
                    1000,
                ),
            ),
        )
    ),
    BRATISLAVSKAYA(
        Station(
            id = "BRATISLAVSKAYA",
            name = "BRATISLAVSKAYA",
            position = Position(x = 1264.7f, y = 1023.0f),
            branch = BranchMock.LYUBLINSKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "LYUBLINO",
                    1000,
                ),
                RoadEntity(
                    "MARINO",
                    1000,
                ),
            ),
        )
    ),
    MARINO(
        Station(
            id = "MARINO",
            name = "MARINO",
            position = Position(x = 1232.0f, y = 1074.3f),
            branch = BranchMock.LYUBLINSKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "BRATISLAVSKAYA",
                    1000,
                ),
                RoadEntity(
                    "BORISOVO",
                    1000,
                ),
            ),
        )
    ),
    BORISOVO(
        Station(
            id = "BORISOVO",
            name = "BORISOVO",
            position = Position(x = 1232.0f, y = 1111.7f),
            branch = BranchMock.LYUBLINSKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "MARINO",
                    1000,
                ),
                RoadEntity(
                    "SHIPILOVSKAYA",
                    1000,
                ),
            ),
        )
    ),
    SHIPILOVSKAYA(
        Station(
            id = "SHIPILOVSKAYA",
            name = "SHIPILOVSKAYA",
            position = Position(x = 1232.0f, y = 1149.0f),
            branch = BranchMock.LYUBLINSKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "BORISOVO",
                    1000,
                ),
                RoadEntity(
                    "ZYABLIKOVO",
                    1000,
                ),
            ),
        )
    ),
    ZYABLIKOVO(
        Station(
            id = "ZYABLIKOVO",
            name = "ZYABLIKOVO",
            position = Position(x = 1232.0f, y = 1195.7f),
            branch = BranchMock.LYUBLINSKAYA_LINIYA.value,
            transaction = null,
            roadConnections = listOf(
                RoadEntity(
                    "SHIPILOVSKAYA",
                    1000,
                ),
            ),
        )
    ),
}
