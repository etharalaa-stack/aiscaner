package com.ethar.aiscaner

import android.graphics.Bitmap
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

/**
 * [OCREngine] manages the text extraction lifecycle.
 * Optimized for GMS-based thin client.
 */
class OCREngine {
    
    // Using Latin options as a base for GMS text recognition.
    // Note: GMS Text Recognition automatically handles multiple languages if the models are available on device.
    private val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    /**
     * Extracts text from the provided [bitmap].
     * @param bitmap The enhanced bitmap from [BitmapProcessor].
     * @param onSuccess Callback for successfully extracted text string.
     * @param onFailure Callback for error handling.
     */
    fun extractArabicText(
        bitmap: Bitmap,
        onSuccess: (String) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val image = InputImage.fromBitmap(bitmap, 0)
        recognizer.process(image)
            .addOnSuccessListener { visionText ->
                // visionText.text returns blocks in logical reading order (RTL aware).
                onSuccess(visionText.text)
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }
}
