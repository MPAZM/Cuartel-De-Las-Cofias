package com.cuarteldelascofias.app.core.common

data class CatalogOption(
    val id: String,
    val label: String
)

object AppCatalogs {
    val patientLocationTypes = listOf(
        CatalogOption("hospital_privado", "Hospital privado"),
        CatalogOption("hospital_publico", "Hospital publico"),
        CatalogOption("casa_particular", "Casa particular"),
        CatalogOption("asilo", "Asilo"),
        CatalogOption("centro_rehabilitacion", "Centro de rehabilitacion"),
        CatalogOption("otro", "Otro")
    )

    val nurseAvailability = listOf(
        CatalogOption("disponible_hoy", "Disponible hoy"),
        CatalogOption("disponible_noche", "Disponible solo noche"),
        CatalogOption("disponible_fin_semana", "Disponible fines de semana"),
        CatalogOption("por_confirmar", "Por confirmar"),
        CatalogOption("no_disponible", "No disponible")
    )

    val nurseTrustLevels = listOf(
        CatalogOption("alta", "Alta"),
        CatalogOption("media", "Media"),
        CatalogOption("nueva", "Nueva"),
        CatalogOption("por_validar", "Por validar")
    )

    val shiftServiceTypes = listOf(
        CatalogOption("guardia_nocturna", "Guardia nocturna"),
        CatalogOption("guardia_diurna", "Guardia diurna"),
        CatalogOption("guardia_24h", "Guardia 24 horas"),
        CatalogOption("acompanamiento_hospitalario", "Acompanamiento hospitalario"),
        CatalogOption("cuidado_domiciliario", "Cuidado domiciliario")
    )
}
