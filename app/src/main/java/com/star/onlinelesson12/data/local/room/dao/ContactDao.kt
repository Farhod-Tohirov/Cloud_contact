package com.star.contactsrestapi2.data.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.star.contactsrestapi2.data.local.room.entity.ContactData

@Dao
interface ContactDao : BaseDao<ContactData>{
    @Query("SELECT * FROM ContactData")
    fun getAllContacts(): List<ContactData>

    @Query("DELETE FROM ContactData")
    fun clearDB()
}