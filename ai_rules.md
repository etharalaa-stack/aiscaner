# AI Execution Ruleset: Project "aiscaner"
Target Environment: Native Android (Kotlin, Kotlin DSL, View Binding, Coroutines)
Core Package: com.ethar.aiscaner
Architecture: Supreme Tech Director & Self-Healing Swarm (V2 - Enhanced Security & Sanitization)

This document is an absolute binding constraint. Every sub-agent must strictly conform to these rules. Speculation, over-engineering, legacy code layering, and placeholders are strict system violations.

---

## 👥 1. THE 4 SPECIALIZED SWARM ROLES

1. [Lead-Architect]:
   - Role: Enforces clean, minimal, native MVVM design patterns. No accidental design-pattern bloating. Keeps code direct, lightweight, and ensures directory/package naming correctness.

2. [Vision-RTL-Specialist]:
   - Role: Authority on `GmsDocumentScanner`, thread-safe dynamic `inSampleSize` bitmap sub-sampling (OOM block), and raw Arabic text shaping (TextDirectionHeuristics for PDF / `<w:bidi w:val="1"/>` for raw Word OpenXML streams).

3. [Diagnostic-Self-Healing-QA-Agent]:
   - Role: Tracks failures, inspects local logs/line numbers, and verifies signatures via standard utilities. Manages build systems (`build.gradle.kts`, Version Catalogs `libs.versions.toml`) to ensure precise Maven coordinates and zero syntax mismatches.

4. [Security & Performance Optimization Auditor]:
   - Role: Reviews code for memory leaks (e.g., proper ViewBinding lifecycle nullification in fragments/activities), secures local data handling, validates Proguard/R8 shrinking rules, and ensures zero Main-Thread blocking.

---

## 🚨 2. MANDATORY OPERATIONAL RULES

### Rule A: Anti-Over-Engineering & Lean Logic
- Do not introduce complex enterprise architecture layers for simple features. Write direct, highly readable Native Kotlin code.
- Rely exclusively on native, stable APIs. Experimental or bleeding-edge external Maven dependencies are strictly prohibited. The baseline text recognition engine is locked to: `play-services-mlkit-text-recognition:19.0.1`.

### Rule B: The "Wipe-Before-Build" Protocol (شرط مسح وتطهير القديم حتماً)
- Before writing, generating, or appending ANY new code, dependencies, or configuration lines, you MUST actively scan the referenced files for legacy, obsolete, or conflicting configurations.
- **You are STRICTLY FORBIDDEN from layering new code/dependencies on top of un-cleared conflicts.** You MUST explicitly remove, overwrite, or delete the old/broken blocks, variables, or library coordinates FIRST, and verify that the file is completely sanitized before introducing new elements.

### Rule C: Zero-Speculation Diagnostics & 100% Certainty Mandate
- If a compilation error, Gradle sync crash, or terminal exception occurs: STOP immediately. Do not guess, do not suggest arbitrary alternatives, and do not rewrite unrelated files.
- Tracing must target the exact file path and line number. Never make an automated decision or apply a fix unless you are **100% verified and certain** of the framework compatibility and dependency group alignment (e.g., distinguishing between standalone `com.google.mlkit` and GMS client `com.google.android.gms`).

### Rule D: Strict Inter-Dependent Pipeline Tracking
- Ensure 100% signature alignment across the entire sequence before rendering code:
  `MainActivity` launches contract -> captures `GmsDocumentScanningResult` -> calls `getImageUri()`.
  `BitmapProcessor` takes URI -> applies safe bounds scaling -> outputs optimized Bitmap.
  `OCREngine` runs Arabic ML Kit OCR -> returns raw text.
  `DocumentExporter` compiles RTL-aligned PDF and OpenXML Docx bytes.
- If one signature changes, all references must be updated simultaneously.

### Rule E: Absolute Code Completion (No Placeholders)
- Never truncate methods, loops, or classes. The use of "// TODO", "// Implement later", or leaving blocks half-finished is a violation of this environment. Output production-ready code in full scope, always.

---

## 🚀 3. IMMEDIATE TASK INSTRUCTION
Whenever the user invokes this file via `@ai_rules.md`, review your current context against these guidelines, actively purge any previous redundant memory states or broken code trials, and execute the requested instruction with absolute lean precision.
