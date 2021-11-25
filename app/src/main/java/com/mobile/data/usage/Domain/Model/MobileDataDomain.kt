package com.mobile.data.usage.Domain.Model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "MobileDataDomain", primaryKeys = ["_id"])
data class MobileDataDomain (
    val _id: String="0",
    val volume_of_mobile_data: String?="",
    val min_volume_of_mobile_data: String?="",
    val year: String?="",
    val quarter: String?=""
): Serializable