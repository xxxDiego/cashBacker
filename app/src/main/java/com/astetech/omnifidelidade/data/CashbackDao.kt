package com.astetech.omnifidelidade.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.astetech.omnifidelidade.models.Cashback
import kotlinx.coroutines.flow.Flow
//
//@Dao
//interface CashbackDao {
//
//    @Query("SELECT * FROM cashback ORDER BY data_validade desc")
//    fun getAll(): Flow<List<Cashback>>
//
////    @Insert(onConflict = OnConflictStrategy.IGNORE)
////    suspend fun insert(cashback: Cashback)
////
////    @Query("DELETE FROM cashback")
////    suspend fun deleteAll()
//}