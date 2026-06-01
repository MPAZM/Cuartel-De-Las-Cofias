package com.cuarteldelascofias.app.data.local

object SeedData {
    val patients = listOf(
        PatientEntity(
            id = "patient-maria-lopez",
            fullName = "Maria del Carmen Lopez",
            preferredName = "Maria del Carmen Lopez",
            age = 68,
            careContext = "Recuperacion postquirurgica con acompanamiento nocturno.",
            serviceLocationType = "Hospital Angeles",
            serviceAddress = "Habitacion 402",
            emergencyContactName = "Laura Lopez",
            emergencyContactPhone = "55 1234 5678",
            active = true,
            notes = "Paciente sensible al ruido. La familia pide actualizar solo al cierre del turno.",
            activeShiftCount = 2
        ),
        PatientEntity(
            id = "patient-jose-rivera",
            fullName = "Jose Manuel Rivera",
            preferredName = "Jose Rivera",
            age = 74,
            careContext = "Acompanamiento hospitalario y seguimiento de indicaciones medicas.",
            serviceLocationType = "Hospital General",
            serviceAddress = "Piso 3 - Cama 18",
            emergencyContactName = "Monica Rivera",
            emergencyContactPhone = "55 9876 5432",
            active = true,
            notes = "El familiar solicita confirmar toma de signos vitales al menos una vez por turno.",
            activeShiftCount = 1
        ),
        PatientEntity(
            id = "patient-ana-ortega",
            fullName = "Ana Luisa Ortega",
            preferredName = "Ana Ortega",
            age = 59,
            careContext = "Cuidado nocturno en casa y apoyo durante recuperacion.",
            serviceLocationType = "Casa particular",
            serviceAddress = "Col. Del Valle",
            emergencyContactName = "Carlos Ortega",
            emergencyContactPhone = "55 2222 1111",
            active = false,
            notes = "Caso pausado. La familia podria reactivar guardias el proximo fin de semana.",
            activeShiftCount = 0
        )
    )

    val nurses = listOf(
        NurseEntity(
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
        NurseEntity(
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
        NurseEntity(
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

    val shifts = listOf(
        ShiftEntity(
            id = "shift-maria-night-1",
            patientId = "patient-maria-lopez",
            nurseName = "Lic. Andrea Perez",
            serviceType = "Guardia nocturna",
            locationSummary = "Hospital Angeles - Habitacion 402",
            startLabel = "22 mayo - 8:00 p. m.",
            endLabel = "23 mayo - 8:00 a. m.",
            scheduleSummary = "Hoy - 8:00 p. m. a 8:00 a. m.",
            rateAmount = "$1,200 MXN",
            commissionAmount = "$200 MXN",
            nurseNetAmount = "$1,000 MXN",
            status = com.cuarteldelascofias.app.data.model.ShiftStatus.IN_PROGRESS,
            adminNotes = "Confirmar reporte final con la familia al cierre del turno."
        ),
        ShiftEntity(
            id = "shift-jose-day-1",
            patientId = "patient-jose-rivera",
            nurseName = "Lic. Karla Sanchez",
            serviceType = "Guardia diurna",
            locationSummary = "Hospital General - Piso 3 - Cama 18",
            startLabel = "22 mayo - 8:00 a. m.",
            endLabel = "22 mayo - 8:00 p. m.",
            scheduleSummary = "Hoy - 8:00 a. m. a 8:00 p. m.",
            rateAmount = "$1,000 MXN",
            commissionAmount = "$150 MXN",
            nurseNetAmount = "$850 MXN",
            status = com.cuarteldelascofias.app.data.model.ShiftStatus.COMPLETED,
            adminNotes = "La guardia termino sin incidencias mayores."
        ),
        ShiftEntity(
            id = "shift-ana-night-1",
            patientId = "patient-ana-ortega",
            nurseName = null,
            serviceType = "Guardia nocturna",
            locationSummary = "Casa particular - Col. Del Valle",
            startLabel = "23 mayo - 8:00 p. m.",
            endLabel = "24 mayo - 8:00 a. m.",
            scheduleSummary = "Manana - 8:00 p. m. a 8:00 a. m.",
            rateAmount = "$900 MXN",
            commissionAmount = "$150 MXN",
            nurseNetAmount = "$750 MXN",
            status = com.cuarteldelascofias.app.data.model.ShiftStatus.PENDING,
            adminNotes = "Falta asignar enfermera y confirmar acceso al domicilio."
        )
    )

    val reports = listOf(
        ShiftReportEntity(
            id = "report-shift-maria-night-1",
            shiftId = "shift-maria-night-1",
            generalStatus = "La paciente se mantuvo orientada y estable durante toda la guardia nocturna.",
            medicationNotes = "Se apoyo en la administracion de medicamentos indicados por enfermeria del hospital y se dio seguimiento a horarios.",
            vitalSignsNotes = "Sin cambios relevantes reportados durante el turno. Se registro toma de signos antes de medianoche.",
            relevantEvents = "La paciente refirio molestia leve al cambio de posicion, sin complicaciones posteriores.",
            hospitalStaffComments = "El personal de piso indico continuar vigilancia nocturna y avisar si aumentaba el dolor.",
            familyComments = "La familia solicito avisar solo en caso de alguna incidencia relevante.",
            handoffSummary = "Se entrega turno con paciente estable, comoda y con medicamentos cubiertos conforme a indicacion.",
            createdAtLabel = "23 mayo - 7:50 a. m."
        ),
        ShiftReportEntity(
            id = "report-shift-jose-day-1",
            shiftId = "shift-jose-day-1",
            generalStatus = "El paciente se observo tranquilo y colaborador durante la guardia diurna.",
            medicationNotes = "Se acompanaron horarios de medicamentos y alimentacion conforme a indicaciones medicas.",
            vitalSignsNotes = "Se reportan signos estables durante el turno, sin alteraciones comunicadas por el personal.",
            relevantEvents = "No se presentaron incidencias mayores.",
            hospitalStaffComments = "El medico de guardia indico mantener acompanamiento y observacion.",
            familyComments = "La familia fue informada al cierre del turno sin novedades importantes.",
            handoffSummary = "Turno concluido sin incidencias. Se deja al paciente estable y acompanado.",
            createdAtLabel = "22 mayo - 7:40 p. m."
        )
    )
}
