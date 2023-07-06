package com.example.dealerapp.application

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dealerapp.dao.DealerDao
import com.example.dealerapp.model.Dealer

@Database(entities = [Dealer::class], version = 1, exportSchema = false)
abstract class DealerDatabase: RoomDatabase(){
 abstract fun DealerDao(): DealerDao

 companion object{
  private var INSTANCE: DealerDatabase? = null

  fun getDatabase(context: Context): DealerDatabase {
   return  INSTANCE ?: synchronized(this){
    val instance = Room.databaseBuilder(
     context.applicationContext,
     DealerDatabase::class.java,
     "dealer_database"
    )
     .allowMainThreadQueries()
     .build()

    INSTANCE= instance
    instance
   }
  }
 }
}