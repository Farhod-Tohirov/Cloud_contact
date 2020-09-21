package com.star.contactsrestapi2.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ContactData(
    @PrimaryKey
    var id: Int = 0,
    var phoneNumber: String?,
    var lastName: String?,
    var firstName: String?
)