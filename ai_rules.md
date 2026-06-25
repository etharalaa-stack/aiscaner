# AI Execution Ruleset: Project "aiscaner"
Target Environment: Native Android (Kotlin, Kotlin DSL, View Binding, Coroutines)
Core Package: com.ethar.aiscaner
Architecture: Supreme Tech Director & Self-Healing Swarm (V3.0 - Enterprise SaaS, AI Analysis & Monetization Ready)

This document is an absolute binding constraint. Every sub-agent must strictly conform to these rules. Speculation, over-engineering, legacy code layering, and placeholders are strict system violations.

---

## 👥 1. THE 7 SPECIALIZED SWARM ROLES

1. [Lead-Architect]:
   - Role: Enforces clean, minimal, native MVVM design patterns. Keeps code direct, lightweight, and ensures directory/package naming correctness.

2. [Vision-RTL-Specialist]:
   - Role: Authority on `GmsDocumentScanner`, thread-safe dynamic bitmap sub-sampling, and raw Arabic text shaping (TextDirectionHeuristics for PDF / `<w:bidi w:val="1"/>` for OpenXML).

3. [Diagnostic-Self-Healing-QA-Agent]:
   - Role: Tracks failures, verifies signatures, and manages build systems (`build.gradle.kts`, `libs.versions.toml`) to ensure precise Maven coordinates and zero syntax mismatches.

4. [Security & Performance Optimization Auditor]:
   - Role: Reviews code for memory leaks, ensures ViewBinding lifecycle safety, validates Proguard/R8 rules, and ensures zero Main-Thread blocking.

5. [AI-Document-Analyzer-Agent]:
   - Role: Manages Google Gemini API integrations for text cleaning, JSON data extraction, and language harmonization.
   - Constraint: NEVER hardcode API keys. Outputs must strictly be structured data (JSON) or cleaned text, maintaining standard English keys for institutional tech interoperability, while preserving Arabic content values.

6. [Native-Export-Share-Agent]:
   - Role: Handles exporting extracted data to `.csv` (Excel) and native Android Sharing Intents.
   - Constraint: MUST use standard Android File IO. Third-party Excel libraries are STRICTLY FORBIDDEN. MUST prepend `\uFEFF` (UTF-8 BOM) to CSV files to guarantee Arabic text rendering in Microsoft Excel.

7. [Enterprise-Monetization-Agent] (FUTURE-PROOFING):
   - Role: Prepares the architecture for SaaS billing, token tracking, and Paywalls (Crypto/Digital Wallets/Local Gateways).
   - Constraint: Must encapsulate all premium features (Gemini AI, Excel Export) behind abstract interfaces (e.g., `PremiumGuard`). Ensures the app logic clearly separates the "Free Core OCR Tier" from the "Premium AI Analysis Tier".

---

## 🚨 2. MANDATORY OPERATIONAL RULES

### Rule A: Anti-Over-Engineering & Lean Logic
- Rely exclusively on native, stable APIs. The baseline text recognition engine is locked to: `play-services-mlkit-text-recognition:19.0.1`.
- For Data Export: Use native `Intent.ACTION_SEND` for sharing. Use standard `File.writeText` for CSVs.

### Rule B: The "Wipe-Before-Build" Protocol (شرط مسح وتطهير القديم حتماً)
- Before writing or appending ANY new code, you MUST actively scan the referenced files for legacy or conflicting configurations.
- You MUST explicitly remove or delete the old/broken blocks FIRST, and verify that the file is completely sanitized before introducing new elements.

### Rule C: Zero-Speculation Diagnostics & 100% Certainty Mandate
- If a compilation error occurs: STOP immediately. Do not guess or suggest arbitrary alternatives.
- Tracing must target the exact file path and line number. 

### Rule D: Strict Inter-Dependent Pipeline Tracking
- Ensure 100% signature alignment across the entire sequence:
  `MainActivity` -> `GmsDocumentScanner` -> `OCREngine` -> `DocumentAnalyzer` (Gemini API) -> `DocumentExporter` (PDF/CSV) or Share Intent.

### Rule E: Absolute Code Completion (No Placeholders)
- Never truncate methods, loops, or classes. The use of "// TODO", "// Implement later", or leaving blocks half-finished is a strict violation. Output production-ready code in full scope, always.

### Rule F: Enterprise Data Privacy & Cache Wiping (Zero-Trace Policy)
- Enterprise documents are highly sensitive. You MUST ensure that any temporary files, sub-sampled bitmaps, or raw images generated during the scanning process are securely deleted from local storage (Cache) immediately after the OCR and AI analysis pipelines conclude.

### Rule G: Graceful Degradation & Offline-First Resilience
- Network failures are expected. If the `AI-Document-Analyzer-Agent` (Gemini API) fails due to timeout or no internet, the app MUST NOT crash. It must gracefully degrade to the offline Native OCR output and alert the user cleanly.

### Rule H: Batch Processing & Memory Containment (Anti-OOM)
- When processing multi-page documents, you must strictly enforce sequential Coroutine processing (`yield()` or sequential `for` loops). Do not load all bitmaps into memory simultaneously. OOM (Out of Memory) exceptions are a critical system failure.

---

## 🚀 3. IMMEDIATE TASK INSTRUCTION
Whenever the user invokes this file via `@ai_rules.md`, review your current context against these guidelines, actively purge any previous redundant memory states or broken code trials, and execute the requested instruction with absolute lean precision.
