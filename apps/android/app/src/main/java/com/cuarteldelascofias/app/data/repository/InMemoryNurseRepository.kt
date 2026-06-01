package com.cuarteldelascofias.app.data.repository

import com.cuarteldelascofias.app.data.model.Nurse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object InMemoryNurseRepository : NurseRepository {
    private val nursesState = MutableStateFlow(
        listOf(
            Nurse(
                id = "nurse-andrea-perez",
                fullName = "Lic. Andrea Perez",
                phone = "55 3456 7890",
                specialtyNotes = "Cuidados postoperatorios y acompanamiento hospitalario.",
                baseZone = "Monterrey Sur",
                availabilityLabel = "Disponible hoy por la noche",
                active = true,
                trustLevel = "Alta",
                notes = "Suele tomar guardias nocturnas y entregar reportes puntuales."
            ),
            Nurse(
                id = "nurse-karla-sanchez",
                fullName = "Lic. Karla Sanchez",
                phone = "55 4567 8901",
                specialtyNotes = "Pacientes geriatricos y seguimiento durante hospitalizacion.",
                baseZone = "San Pedro",
                availabilityLabel = "Cubriendo guardia",
                active = true,
                trustLevel = "Alta",
                notes = "Muy buena comunicacion con familiares y personal del hospital."
            ),
            Nurse(
                id = "nurse-diana-moreno",
                fullName = "Lic. Diana Moreno",
                phone = "55 5678 9012",
                specialtyNotes = "Acompanamiento hospitalario y observacion nocturna.",
                baseZone = "Zona Centro",
                availabilityLabel = "Descanso",
                active = false,
                trustLevel = "Media",
                notes = "Disponible solo con programacion previa durante fines de semana."
            )
        )
    )

    override val nurses: StateFlow<List<Nurse>> = nursesState.asStateFlow()

    override fun getNurseById(nurseId: String): Nurse? {
        return nursesState.value.firstOrNull { it.id == nurseId }
    }

    override fun addNurse(draft: NurseDraft): Nurse {
        val nurse = Nurse(
            id = buildNurseId(draft.fullName),
            fullName = draft.fullName.trim(),
            phone = draft.phone.trim(),
            specialtyNotes = draft.specialtyNotes.trim(),
            baseZone = draft.baseZone.trim(),
            availabilityLabel = draft.availabilityLabel.trim(),
            active = draft.active,
            trustLevel = draft.trustLevel.trim().ifBlank { "Media" },
            notes = draft.notes.trim().ifBlank { "Sin notas internas por ahora." }
        )

        nursesState.value = listOf(nurse) + nursesState.value
        return nurse
    }

    private fun buildNurseId(fullName: String): String {
        val baseSlug = fullName
            .trim()
            .lowercase()
            .replace(Regex("[^a-z0-9]+"), "-")
            .trim('-')
            .ifBlank { "nurse" }

        val existingMatches = nursesState.value.count { it.id.startsWith(baseSlug) }
        return "$baseSlug-${existingMatches + 1}"
    }
}
