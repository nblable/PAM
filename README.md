# 📝 MyFirstKMPApp - Tugas Praktikum Minggu 9

Aplikasi **AI Chat Assistant** berbasis **Kotlin Multiplatform (KMP)** yang mengintegrasikan layanan Google Gemini AI dengan fitur **Platform-Specific** dan desain **UI/UX Modern**.

---

## 🏗️ Arsitektur Aplikasi

Aplikasi ini menggunakan pola **MVVM (Model-View-ViewModel)** dengan **Repository Pattern** untuk memisahkan logika bisnis dan UI.
*   **commonMain**: Berisi logika utama, ViewModel, Repository, dan integrasi API (GeminiService).
*   **androidMain**: Implementasi native untuk engine HTTP (Ktor Android) dan konfigurasi spesifik Android.
*   **jvmMain/iosMain**: Dukungan multiplatform untuk konfigurasi HTTP dan pembacaan API Key.

---

## 🛠️ Detail Implementasi (Rubrik Penilaian Tugas 9)

### 1. Integrasi AI Gemini (Generative AI)
Aplikasi terhubung langsung ke **Google Gemini API** menggunakan model terbaru (`gemini-1.5-flash` dan `gemini-2.0-flash-exp`).
*   **Prompt Engineering**: Implementasi instruksi sistem agar AI merespons dalam Bahasa Indonesia yang sopan.
*   **Multi-Model Support**: Sistem otomatis melakukan *fallback* ke model alternatif jika model utama tidak tersedia.
*   **Conversation History**: Mendukung konteks percakapan sehingga AI memahami pesan sebelumnya.

### 2. Ktor Multiplatform Networking
Menggunakan library **Ktor** untuk menangani permintaan HTTP secara asinkron di semua platform.
*   **Expect/Actual**: Abstraksi `httpClient` untuk menggunakan engine yang optimal di setiap platform (Android, CIO, Darwin).
*   **Serialization**: Menggunakan `kotlinx.serialization` untuk konversi data JSON API Gemini secara otomatis.

### 3. UI/UX Modern & Custom Theme
Tampilan aplikasi didesain menggunakan **Material 3** dengan sentuhan kustomisasi:
*   **Orange Theme**: Skema warna oranye yang cerah dan profesional pada Header, Bubble Chat, dan Action Button.
*   **Bubble Chat Design**: Perbedaan visual yang kontras antara pesan Pengguna dan AI (Shadow, Corner Radius, Alignment).
*   **Typing Indicator**: Memberikan umpan balik visual saat AI sedang memproses jawaban.
*   **Auto-Scroll**: Daftar chat otomatis bergeser ke bawah saat pesan baru masuk.

### 4. Error Handling & Resilience
Penanganan kesalahan yang ramah pengguna untuk berbagai skenario:
*   **Rate Limit (429)**: Pesan instruksi khusus jika kuota API habis.
*   **Connection Error**: Deteksi kegagalan internet atau timeout.
*   **Model Not Found (404)**: Penanganan otomatis jika model AI tidak didukung di wilayah tertentu.

---

## 📸 Dokumentasi (Screenshots)

| Fitur | Screenshot                                                                                                                          |
| :--- |:------------------------------------------------------------------------------------------------------------------------------------|
| **Main Chat Screen ** | <img width="720" height="1600" alt="Image" src="https://github.com/user-attachments/assets/7bfa1eee-bf39-41dc-8cb0-94e61efc4df7" /> |
| **Error Handling ** | <img width="720" height="1600" alt="Image" src="https://github.com/user-attachments/assets/86b0179a-c78a-4535-bb4f-78753f59c3a6" /> |

---
