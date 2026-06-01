package com.cuarteldelascofias.app.data.local

import androidx.room.TypeConverter
import com.cuarteldelascofias.app.data.model.ShiftStatus

class ShiftStatusConverters {
    @TypeConverter
    fun fromStatus(status: ShiftStatus): String = status.name

    @TypeConverter
    fun toStatus(value: String): ShiftStatus = ShiftStatus.valueOf(value)
}
