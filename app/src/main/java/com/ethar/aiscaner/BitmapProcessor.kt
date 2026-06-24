package com.ethar.aiscaner

import android.content.ContentResolver
import android.graphics.*
import android.net.Uri
import java.io.InputStream

/**
 * [BitmapProcessor] handles memory-safe image manipulation and enhancement.
 * Refined by [Diagnostic-Profiler-Self-Healing-Agent] for zero-buffer stream decoding.
 */
object BitmapProcessor {

    /**
     * Decodes a bitmap from a Uri without loading the full byte array into memory.
     * Opens the stream twice to calculate bounds and then decode pixels.
     */
    fun decodeSampledBitmap(contentResolver: ContentResolver, uri: Uri, reqWidth: Int, reqHeight: Int): Bitmap? {
        // 1. First pass: Check dimensions
        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
        }
        contentResolver.openInputStream(uri)?.use { 
            BitmapFactory.decodeStream(it, null, options) 
        } ?: return null

        // 2. Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)

        // 3. Second pass: Decode actual bitmap
        options.inJustDecodeBounds = false
        options.inPreferredConfig = Bitmap.Config.ARGB_8888
        
        return contentResolver.openInputStream(uri)?.use {
            BitmapFactory.decodeStream(it, null, options)
        }
    }

    private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        val (height: Int, width: Int) = options.outHeight to options.outWidth
        var inSampleSize = 1
        if (height > reqHeight || width > reqWidth) {
            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }

    /**
     * Applies an adaptive contrast filter using a hardware-accelerated ColorMatrix.
     */
    fun applyAdaptiveContrast(src: Bitmap): Bitmap {
        val width = src.width
        val height = src.height
        val bmOut = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bmOut)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        
        // High-contrast matrix: Optimized for text clarity
        val contrast = 1.7f 
        val brightness = -20f
        val cm = ColorMatrix(floatArrayOf(
            contrast, 0f, 0f, 0f, brightness,
            0f, contrast, 0f, 0f, brightness,
            0f, 0f, contrast, 0f, brightness,
            0f, 0f, 0f, 1f, 0f
        ))
        
        paint.colorFilter = ColorMatrixColorFilter(cm)
        canvas.drawBitmap(src, 0f, 0f, paint)
        return bmOut
    }
}
