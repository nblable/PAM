# Tugas 8: Pengembangan Aplikasi Mobile (KMP)

## Deskripsi Proyek
Proyek ini adalah aplikasi catatan (Notes App) berbasis **Kotlin Multiplatform (KMP)** yang dikembangkan untuk memenuhi kriteria Tugas Praktikum Minggu 8. Fokus utama tugas ini adalah implementasi arsitektur **Dependency Injection (DI)**, penggunaan fitur spesifik platform melalui mekanisme **expect/actual**, serta peningkatan UI/UX menggunakan **Material 3**.

---

## Fitur Utama (Kriteria Tugas 8)

### 1. Dependency Injection (Koin)
Seluruh komponen aplikasi dikelola menggunakan **Koin DI** untuk memastikan kode yang *decoupled* dan mudah diuji.
- **Injeksi Database & Driver**: Dikelola secara spesifik per platform.
- **Injeksi ViewModel**: Menggunakan cakupan `factory` untuk efisiensi memori.
- **Injeksi Repository & Settings**: Dikelola sebagai `single` instance.

### 2. Platform-Specific Features (Expect/Actual)
Berhasil mengimplementasikan akses ke API Native Android untuk fitur-fitur berikut:
- **Device Info**: Menampilkan model perangkat dan versi sistem operasi.
- **Network Monitor**: Pemantauan status koneksi internet secara *real-time*.
- **Battery Info**: Menampilkan persentase baterai dan status pengisian daya (Charging).

### 3. UI/UX & Interaktivitas
- **Material 3 Design**: Menggunakan skema warna Pink yang modern.
- **Navigation Drawer**: Menu navigasi samping untuk akses cepat ke berbagai halaman.
- **Search & Sort**: Pencarian teks pada catatan dan pengurutan (Terbaru/Terlama) langsung melalui query SQLDelight.
- **Offline Indicator**: Munculnya banner peringatan otomatis saat koneksi internet terputus.
- **Tema Dinamis**: Dukungan penuh untuk Mode Terang (Light) dan Mode Gelap (Dark).

---

## Arsitektur Aplikasi
Aplikasi ini menggunakan pola arsitektur **MVVM (Model-View-ViewModel)** yang terintegrasi dengan Koin:
- **Model**: SQLDelight untuk penyimpanan lokal.
- **View**: Jetpack Compose Multiplatform.
- **ViewModel**: Mengelola state UI menggunakan `StateFlow` dan menangani logika bisnis.
- **Repository**: Mengabstraksi sumber data untuk ViewModel.

---

## Dokumentasi Visual

### Screenshots Aplikasi
| Daftar Catatan |                                                         Halaman Settings                                                          | Indikator Offline |
| :---: |:---------------------------------------------------------------------------------------------------------------------------------:| :---: |
| <img width="434" height="945" alt="Image" src="https://github.com/user-attachments/assets/c2aa4d08-d678-4515-ac08-0eb139ded48f" /> |<img width="434" height="945" alt="Image" src="https://github.com/user-attachments/assets/3272ad7d-a702-4dc4-a29c-3475ff49085a" /> | <img width="434" height="945" alt="Image" src="https://github.com/user-attachments/assets/ed09ac0c-3d52-4805-aaac-f285e15c4390" /> |

---

## Video Demo Aplikasi
Anda dapat melihat demo fitur-fitur Tugas 8 (durasi ±45 detik) melalui tautan di bawah ini:

🔗 **https://drive.google.com/file/d/1f6DS1fhP4RA2IBLDU2lgknUen5rpgxgM/view?usp=sharing**

**Cakupan Video:**
1. Bukti inisialisasi Koin di kode program.
2. Demonstrasi fungsionalitas CRUD, Search, dan Sort.
3. Simulasi mematikan koneksi internet (Banner Offline muncul).
4. Penjelasan informasi perangkat dan baterai di halaman Settings.

---
*Proyek ini dikembangkan menggunakan Kotlin Multiplatform dan Jetpack Compose.*