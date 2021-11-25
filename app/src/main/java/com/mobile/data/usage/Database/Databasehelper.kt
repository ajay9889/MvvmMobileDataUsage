package com.mobile.data.usage.Database
import android.content.Context
import androidx.room.*
import com.mobile.data.usage.Domain.Model.MobileDataDomain

@Database(entities = [MobileDataDomain::class], version = 1, exportSchema = false)
abstract class Databasehelper: RoomDatabase() {

    abstract fun RoomDataAccessObejct(): RoomDataAccessObejct
    companion object{
         fun getDatabase(context: Context): Databasehelper {
            return Room.databaseBuilder(context,Databasehelper::class.java, "MobileDataUsageDB")
                .fallbackToDestructiveMigration().allowMainThreadQueries()
                .build()
        }
    }
}