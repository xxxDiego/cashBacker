package com.astetech.omnifidelidade.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.astetech.omnifidelidade.R
import com.astetech.omnifidelidade.models.Cashback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


//@Database(entities = [Cashback::class], version = 1)
//    abstract class AppDatabase : RoomDatabase() {
//        abstract fun cashbackDao(): CashbackDao
//
//
//    companion object {
//        @Volatile
//        private var INSTANCE: AppDatabase? = null
//
//        fun getDatabase(
//            context: Context,
//            scope: CoroutineScope
//        ): AppDatabase {
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context,
//                    AppDatabase::class.java,
//                    "omnifidelidade-db")
//                    .addCallback(CashbackDatabaseCallback(scope))
//                    .build()
//                INSTANCE = instance
//
//                instance
//            }
//        }
//
//        private class CashbackDatabaseCallback(
//            private val scope: CoroutineScope
//        ) : RoomDatabase.Callback() {
//            /**
//             * Override the onCreate method to populate the database.
//             */
//            override fun onCreate(db: SupportSQLiteDatabase) {
//                super.onCreate(db)
//                // If you want to keep the data through app restarts,
//                // comment out the following line.
//                INSTANCE?.let { database ->
//                    scope.launch(Dispatchers.IO) {
//                        populateDatabase(database.cashbackDao())
//                    }
//                }
//            }
//        }
//
//        suspend fun populateDatabase(cashbackDao: CashbackDao) {
//            // Start the app with a clean database every time.
//            // Not needed if you only populate on creation.
//
//            //cashbackDao.deleteAll()
//
////            var cashback = Cashback(1, R.drawable.nb,"New Balance", "R$: 90,00", "27/06/2022", "13/05/2022")
////            cashbackDao.insert(cashback)
////            cashback = Cashback(2, R.drawable.nb,"New Balance", "R$: 11,00", "12/07/2022", "28/05/2022")
////            cashbackDao.insert(cashback)
//        }
//    }
//}