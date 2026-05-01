# 📝 MyFirstKMPApp - Nutrition AI Analysis

Aplikasi **Nutrition AI Analysis** berbasis **Kotlin Multiplatform (KMP)** yang mengintegrasikan layanan Google Gemini AI untuk menganalisis kandungan gizi makanan dengan fitur **Platform-Specific** dan desain **UI/UX Modern**.

---

## Arsitektur Aplikasi

Aplikasi ini menggunakan pola **MVVM (Model-View-ViewModel)** dengan **Repository Pattern** untuk memisahkan logika bisnis dan UI.
*   **commonMain**: Berisi logika utama, ViewModel, Repository, dan integrasi API (GeminiService).
*   **androidMain**: Implementasi native untuk engine HTTP (Ktor Android) dan konfigurasi spesifik Android.
*   **jvmMain/iosMain**: Dukungan multiplatform untuk konfigurasi HTTP dan pembacaan API Key.

---

## Detail Implementasi

### 1. Integrasi AI Gemini (Generative AI)
Aplikasi terhubung langsung ke **Google Gemini API** menggunakan model terbaru (`gemini-2.0-flash` dan `gemini-2.5-flash`).
*   **Prompt Engineering**: Implementasi instruksi sistem agar AI merespons dalam format JSON terstruktur untuk data nutrisi.
*   **Multi-Model Support**: Sistem otomatis melakukan *fallback* ke model alternatif jika model utama tidak tersedia.
*   **Nutrition Analysis**: Menganalisis makanan dan memberikan informasi kalori, protein, karbohidrat, lemak, dan tips kesehatan.

### 2. Ktor Multiplatform Networking
Menggunakan library **Ktor** untuk menangani permintaan HTTP secara asinkron di semua platform.
*   **Expect/Actual**: Abstraksi `httpClient` untuk menggunakan engine yang optimal di setiap platform (Android, CIO, Darwin).
*   **Serialization**: Menggunakan `kotlinx.serialization` untuk konversi data JSON API Gemini secara otomatis.

### 3. UI/UX Modern & Custom Theme
Tampilan aplikasi didesain menggunakan **Material 3** dengan sentuhan kustomisasi:
*   **Pink Theme**: Skema warna pink yang menarik dan profesional pada Header, Card Nutrisi, dan Action Button.
*   **Nutrition Card Design**: Tampilan kartu yang informatif untuk menampilkan hasil analisis nutrisi.
*   **Typing Indicator**: Memberikan umpan balik visual saat AI sedang menganalisis makanan.
*   **Auto-Scroll**: Daftar konten otomatis bergeser saat hasil analisis muncul.

### 4. Error Handling & Resilience
Penanganan kesalahan yang ramah pengguna untuk berbagai skenario:
*   **Rate Limit (429)**: Pesan instruksi khusus jika kuota API habis.
*   **Connection Error**: Deteksi kegagalan internet atau timeout.
*   **Model Not Found (404)**: Penanganan otomatis jika model AI tidak didukung di wilayah tertentu.

---

## Screenshots

| Fitur | Screenshot                                                                                                                          |
| :--- |:------------------------------------------------------------------------------------------------------------------------------------|
| **Nutrition Analysis Screen ** | <img width="720" height="1600" alt="Image" src="https://github.com/user-attachments/assets/011d5262-b9eb-414e-97cb-3b449cd90cbb" /> |
| **Error Handling ** | <img width="720" height="1600" alt="Image" src="https://github.com/user-attachments/assets/6a96f0ba-1953-41c6-9a4b-37061578b805" /> |

---
