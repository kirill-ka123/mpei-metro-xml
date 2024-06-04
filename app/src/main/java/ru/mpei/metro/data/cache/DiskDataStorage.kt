package ru.mpei.metro.data.cache

import android.util.Log
import ru.mpei.metro.presentation.di.scopes.ApplicationScope
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream

private const val TAG = "[DiskDataStorage]"

internal class DiskDataStorage(private val file: File) {

    fun save(byteArray: ByteArray) {
        // on some devices opening FileOutputStream does not clear the file
        file.delete()
        file.parentFile?.mkdirs()
        file.createNewFile()
        file.writeBytes(byteArray)
    }

    fun load(): InputStream? =
        if (!file.exists()) {
            null
        } else if (file.isDirectory) {
            Log.e(TAG, "Expected $file to be a file, got directory instead.")
            null
        } else {
            BufferedInputStream(FileInputStream(file))
        }

    fun clear() {
        if (!file.delete()) {
            throw IOException("Deleting ${file.path} failed!")
        }
    }
}
