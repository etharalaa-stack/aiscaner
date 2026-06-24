package com.ethar.aiscaner

import android.content.Context
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.text.*
import java.io.File
import java.io.FileOutputStream
import java.nio.charset.StandardCharsets
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

/**
 * [DocumentExporter] handles multi-format export with absolute Arabic RTL compliance.
 * Engineered by [Arabic-RTL-Localization-Agent] and [Lead-Architect-Agent].
 */
class DocumentExporter(private val context: Context) {

    /**
     * Exports raw text to a PDF file with RTL layout support.
     */
    fun exportToPdf(text: String, fileName: String): File {
        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create() // A4 dimensions
        val page = pdfDocument.startPage(pageInfo)
        val canvas = page.canvas

        val textPaint = TextPaint().apply {
            textSize = 14f
            isAntiAlias = true
            isSubpixelText = true
            isLinearText = true
        }

        val margin = 50f
        val width = pageInfo.pageWidth - 2 * margin

        // Use StaticLayout to handle multiline RTL text shaping
        val staticLayout = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StaticLayout.Builder.obtain(text, 0, text.length, textPaint, width.toInt())
                .setAlignment(Layout.Alignment.ALIGN_NORMAL)
                .setTextDirection(TextDirectionHeuristics.RTL)
                .setLineSpacing(0f, 1.2f)
                .build()
        } else {
            @Suppress("DEPRECATION")
            StaticLayout(text, textPaint, width.toInt(), Layout.Alignment.ALIGN_NORMAL, 1.2f, 0f, false)
        }

        canvas.save()
        canvas.translate(margin, margin)
        staticLayout.draw(canvas)
        canvas.restore()

        pdfDocument.finishPage(page)
        val file = File(context.getExternalFilesDir(null), "$fileName.pdf")
        pdfDocument.writeTo(FileOutputStream(file))
        pdfDocument.close()
        return file
    }

    /**
     * Constructs a .docx package manually to inject bidirectional formatting tags.
     */
    fun exportToDocx(text: String, fileName: String): File {
        val file = File(context.getExternalFilesDir(null), "$fileName.docx")
        val escapedText = text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")

        ZipOutputStream(FileOutputStream(file)).use { zipOut ->
            // 1. [Content_Types].xml
            addZipEntry(zipOut, "[Content_Types].xml", """<?xml version="1.0" encoding="UTF-8" standalone="yes"?><Types xmlns="http://schemas.openxmlformats.org/package/2006/content-types"><Default Extension="rels" ContentType="application/vnd.openxmlformats-package.relationships+xml"/><Default Extension="xml" ContentType="application/xml"/><Override PartName="/word/document.xml" ContentType="application/vnd.openxmlformats-officedocument.wordprocessingml.document.main+xml"/></Types>""")
            
            // 2. _rels/.rels
            addZipEntry(zipOut, "_rels/.rels", """<?xml version="1.0" encoding="UTF-8" standalone="yes"?><Relationships xmlns="http://schemas.openxmlformats.org/package/2006/relationships"><Relationship Id="rId1" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument" Target="word/document.xml"/></Relationships>""")

            // 3. word/document.xml - Crucial RTL implementation
            addZipEntry(zipOut, "word/document.xml", """<?xml version="1.0" encoding="UTF-8" standalone="yes"?><w:document xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main"><w:body><w:p><w:pPr><w:bidi w:val="1"/><w:jc w:val="right"/></w:pPr><w:r><w:rPr><w:bidi w:val="1"/><w:rtl w:val="1"/></w:rPr><w:t>$escapedText</w:t></w:r></w:p></w:body></w:document>""")
        }
        return file
    }

    private fun addZipEntry(zipOut: ZipOutputStream, name: String, content: String) {
        zipOut.putNextEntry(ZipEntry(name))
        zipOut.write(content.toByteArray(StandardCharsets.UTF_8))
        zipOut.closeEntry()
    }
}
