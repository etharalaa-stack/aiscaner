package com.ethar.aiscaner

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.ethar.aiscaner.databinding.ActivityMainBinding
import com.google.mlkit.vision.documentscanner.GmsDocumentScanningResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * [MainActivity] orchestrates the integrated swarm logic for "aiscaner".
 * FINAL PRODUCTION SEAL: Optimized for memory-safe image processing (Native Stream Decoding)
 * and high-accuracy Arabic OCR with RTL-supported document export.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var scannerManager: ScannerManager
    private val ocrEngine = OCREngine()
    private val documentExporter by lazy { DocumentExporter(this) }

    private val scannerLauncher = registerForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data ?: return@registerForActivityResult
            val response = GmsDocumentScanningResult.fromActivityResultIntent(data)
            response?.pages?.firstOrNull()?.imageUri?.let { uri ->
                processScannedDocument(uri)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        scannerManager = ScannerManager(this)

        binding.btnScan.setOnClickListener {
            scannerManager.startScan(scannerLauncher)
        }
    }

    /**
     * Pipeline flow: Uri -> Optimized Bitmap -> Contrast Enhancement -> Arabic OCR -> PDF/Docx Export.
     */
    private fun processScannedDocument(uri: Uri) {
        lifecycleScope.launch {
            binding.progressBar.visibility = View.VISIBLE
            binding.tvStatus.text = "Initializing AI Pipeline..."

            try {
                // 1. Memory-Safe Bitmap Optimization (Using Native Stream Decoding)
                val rawBitmap = withContext(Dispatchers.IO) {
                    BitmapProcessor.decodeSampledBitmap(contentResolver, uri, 1080, 1920)
                } ?: throw Exception("Buffer Error: Could not decode image stream.")

                // Update UI preview
                binding.imgPreview.setImageBitmap(rawBitmap)

                // 2. Hardware-Accelerated Contrast Enhancement
                binding.tvStatus.text = "Enhancing Contrast..."
                val enhancedBitmap = withContext(Dispatchers.Default) {
                    BitmapProcessor.applyAdaptiveContrast(rawBitmap)
                }

                // 3. Arabic OCR Extraction
                binding.tvStatus.text = "Extracting Arabic Text..."
                ocrEngine.extractArabicText(enhancedBitmap, { extractedText ->
                    if (extractedText.isBlank()) {
                        handleError(Exception("No legible text detected."))
                        return@extractArabicText
                    }

                    lifecycleScope.launch {
                        // 4. Multi-Format RTL Export
                        binding.tvStatus.text = "Generating RTL Documents..."
                        val timestamp = System.currentTimeMillis()
                        
                        val pdfFile = withContext(Dispatchers.IO) {
                            documentExporter.exportToPdf(extractedText, "Scan_$timestamp")
                        }
                        val docxFile = withContext(Dispatchers.IO) {
                            documentExporter.exportToDocx(extractedText, "Scan_$timestamp")
                        }

                        binding.tvStatus.text = "Success! Files saved."
                        binding.progressBar.visibility = View.GONE
                        
                        Toast.makeText(this@MainActivity, 
                            "Exported:\n${pdfFile.name}\n${docxFile.name}", 
                            Toast.LENGTH_LONG).show()
                    }
                }, { exception ->
                    handleError(exception)
                })

            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    private fun handleError(e: Exception) {
        lifecycleScope.launch(Dispatchers.Main) {
            binding.progressBar.visibility = View.GONE
            binding.tvStatus.text = "System Error: ${e.message}"
            Toast.makeText(this@MainActivity, "Process Failed: ${e.message}", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }
}
