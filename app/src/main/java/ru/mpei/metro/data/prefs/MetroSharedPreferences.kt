package ru.mpei.metro.data.prefs

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import javax.inject.Inject

private const val METRO_PREFERENCES_NAME = "metro_preferences"

class MetroSharedPreferences @Inject constructor(
    private val applicationContext: Context,
) {
    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    private var sharedPreferences: SharedPreferences? = null

    fun get() = sharedPreferences ?: EncryptedSharedPreferences.create(
        METRO_PREFERENCES_NAME,
        masterKeyAlias,
        applicationContext,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    ).also { sharedPreferences = it }
}
