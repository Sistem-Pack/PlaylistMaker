package com.practicum.playlistmaker.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.playlistmaker.db.data.TrackDao

@Database(
    version = 1,
    entities = [TrackEntity::class, PlayListsTrackEntity::class, PlayListEntity::class, TrackPlayListEntity::class]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun trackDao(): TrackDao
    abstract fun playListsTrackDao(): PlayListsDao

    companion object {
        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE track_table ADD COLUMN timestamp INTEGER DEFAULT 0 NOT NULL")
            }
        }
    }


}