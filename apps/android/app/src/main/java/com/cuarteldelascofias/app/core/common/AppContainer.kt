package com.cuarteldelascofias.app.core.common

import android.content.Context
import androidx.room.Room
import com.cuarteldelascofias.app.data.local.CuartelDatabase
import com.cuarteldelascofias.app.data.repository.NurseRepository
import com.cuarteldelascofias.app.data.repository.PatientRepository
import com.cuarteldelascofias.app.data.repository.RoomNurseRepository
import com.cuarteldelascofias.app.data.repository.RoomPatientRepository
import com.cuarteldelascofias.app.data.repository.RoomShiftReportRepository
import com.cuarteldelascofias.app.data.repository.RoomShiftRepository
import com.cuarteldelascofias.app.data.repository.ShiftReportRepository
import com.cuarteldelascofias.app.data.repository.ShiftRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

object AppContainer {
    private var initialized = false
    private lateinit var database: CuartelDatabase
    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    lateinit var nurseRepository: NurseRepository
        private set

    lateinit var patientRepository: PatientRepository
        private set

    lateinit var shiftRepository: ShiftRepository
        private set

    lateinit var shiftReportRepository: ShiftReportRepository
        private set

    fun initialize(context: Context) {
        if (initialized) return

        database = Room.databaseBuilder(
            context.applicationContext,
            CuartelDatabase::class.java,
            "cuartel_de_las_cofias.db"
        )
            .fallbackToDestructiveMigration()
            .build()

        patientRepository = RoomPatientRepository(
            patientDao = database.patientDao(),
            scope = applicationScope
        )
        nurseRepository = RoomNurseRepository(
            nurseDao = database.nurseDao(),
            scope = applicationScope
        )
        shiftRepository = RoomShiftRepository(
            shiftDao = database.shiftDao(),
            patientDao = database.patientDao(),
            scope = applicationScope
        )
        shiftReportRepository = RoomShiftReportRepository(
            shiftReportDao = database.shiftReportDao(),
            scope = applicationScope
        )

        applicationScope.launch {
            CuartelDatabase.seedIfEmpty(database)
        }

        initialized = true
    }
}
