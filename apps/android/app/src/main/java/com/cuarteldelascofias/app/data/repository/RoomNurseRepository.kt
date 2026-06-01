package com.cuarteldelascofias.app.data.repository

import com.cuarteldelascofias.app.data.local.NurseDao
import com.cuarteldelascofias.app.data.local.toEntity
import com.cuarteldelascofias.app.data.local.toModel
import com.cuarteldelascofias.app.data.model.Nurse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RoomNurseRepository(
    nurseDao: NurseDao,
    private val scope: CoroutineScope
) : NurseRepository {
    private val nursesState = MutableStateFlow<List<Nurse>>(emptyList())

    override val nurses: StateFlow<List<Nurse>> = nursesState.asStateFlow()

    private val dao = nurseDao

    init {
        scope.launch {
            dao.observeNurses().collect { entities ->
                nursesState.value = entities.map { it.toModel() }
            }
        }
    }

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

        scope.launch {
            dao.insert(nurse.toEntity())
        }
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
