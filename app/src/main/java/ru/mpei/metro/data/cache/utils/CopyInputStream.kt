package ru.mpei.metro.data.cache.utils

import java.io.InputStream
import java.io.OutputStream

/**
 * All the data read from the returned [InputStream] will be copied to [outputStream].
 * [outputStream] will be flushed and closed automatically when returned [InputStream] is closed.
 */
internal fun InputStream.copyingTo(outputStream: OutputStream): InputStream =
    CopyInputStream(
        source = this,
        destination = outputStream
    )

private class CopyInputStream(
    private val source: InputStream,
    private val destination: OutputStream,
) : InputStream() {

    override fun read() = source.read().also { read ->
        if (read == -1) {
            destination.flush()
            destination.close()
        } else {
            destination.write(read)
        }
    }

    override fun close() {
        source.close()
        destination.close()
    }
}
