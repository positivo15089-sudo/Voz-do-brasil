package br.com.vozdobrasil.studio.data.local

import android.content.Context
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "productions")
data class ProductionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val folderName: String = "Geral",
    val productionType: String, // "LOCUCAO" ou "SPOT"
    val scriptText: String,
    val speakerId: String,
    val speakerName: String,
    val audioFilePath: String,
    val durationSeconds: Int,
    val isFavorite: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "custom_voices")
data class CustomVoiceEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val originalPrompt: String,
    val gender: String,
    val ageGroup: String,
    val timbre: String,
    val pitch: Float,
    val speed: Float,
    val accent: String,
    val emotion: String,
    val personality: String,
    val intensity: Float,
    val interpretationStyle: String,
    val isFavorite: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
)

@Dao
interface StudioDao {
    @Query("SELECT * FROM productions ORDER BY createdAt DESC")
    fun observeAllProductions(): Flow<List<ProductionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduction(item: ProductionEntity): Long

    @Update
    suspend fun updateProduction(item: ProductionEntity)

    @Delete
    suspend fun deleteProduction(item: ProductionEntity)

    @Query("SELECT * FROM custom_voices ORDER BY createdAt DESC")
    fun observeCustomVoices(): Flow<List<CustomVoiceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomVoice(voice: CustomVoiceEntity): Long
}

@Database(entities = [ProductionEntity::class, CustomVoiceEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun studioDao(): StudioDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "voz_do_brasil_studio.db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
