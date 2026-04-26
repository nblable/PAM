# 📝 MyFirstKMPApp - Tugas 7 PAM

Aplikasi Pencatatan (Notes App) lintas platform yang dikembangkan menggunakan **Kotlin Multiplatform (KMP)**. Proyek ini merupakan pemenuhan Tugas 7 untuk mata kuliah Pengembangan Aplikasi Mobile (PAM), yang berfokus pada implementasi arsitektur **MVVM** dan penyimpanan data lokal persisten menggunakan **SQLDelight** dan **DataStore**.

---

## 🎯 Deskripsi Tugas & Pemenuhan Rubrik Penilaian

Proyek ini telah dikembangkan dengan memenuhi kriteria rubrik penilaian berikut:

-  **SQLDelight (Local Database):** Implementasi database SQLite lokal untuk fitur *Create, Read, Update, Delete* (CRUD) serta pencarian catatan (Search).
-  **DataStore (Preferences):** Menyimpan pengaturan pengguna secara permanen, meliputi preferensi Tema (Gelap/Terang) dan mode Filter/Sort (Terbaru/Terlama).
-  **Arsitektur MVVM:** Pemisahan tanggung jawab yang jelas antara *View* (UI/Compose), *ViewModel* (Logic & StateFlow), dan *Model* (Entity Database).
-  **Repository Pattern:** Menggunakan `NoteRepository` dan `SettingsRepository` sebagai *Single Source of Truth* untuk interaksi data.
-  **Offline-First:** Aplikasi berfungsi 100% tanpa memerlukan koneksi internet.

---

## ✨ Fitur Utama

1. **Manajemen Catatan (CRUD):** Tambah, Edit, Hapus, dan Lihat catatan dengan mudah.
2. **Pencarian Real-time:** Cari catatan berdasarkan judul atau isi teks.
3. **Filter & Sortir:** Urutkan catatan dari yang *Terbaru* atau *Terlama* (tersimpan otomatis).
4. **Tema Dinamis:** Beralih antara *Light Mode* (Tema Oranye/Terang) dan *Dark Mode* (Tema Gelap) dengan transisi animasi yang halus.

---

## 📸 Screenshots (Tangkapan Layar)

*Catatan: Ganti placeholder di bawah ini dengan link/path screenshot dari aplikasimu.*

| Tampilan Daftar Catatan (Light Mode) |                                                Tampilan Daftar Catatan (Dark Mode)                                                 |                                                       Fitur Search & Filter                                                        | Dialog Tambah/Edit Catatan |
| :---: |:----------------------------------------------------------------------------------------------------------------------------------:|:----------------------------------------------------------------------------------------------------------------------------------:| :---: |
| <img width="425" height="944" alt="Image" src="https://github.com/user-attachments/assets/9d3d66fe-c7a9-4a0c-8128-aea07252af8c" /> | <img width="426" height="944" alt="Image" src="https://github.com/user-attachments/assets/6d167323-46d8-4f86-bdff-70e6d3a1b9e0" /> | <img width="426" height="944" alt="Image" src="https://github.com/user-attachments/assets/eaefd4db-820b-4929-a62d-bc94cc619fea" /> | <img width="426" height="944" alt="Image" src="https://github.com/user-attachments/assets/d2ade789-13aa-4a7a-98bf-e3223f9e5732" /> |

---

## 🎥 Video Demonstrasi

Untuk melihat bagaimana aplikasi berjalan, interaksi UI, dan persistensi data (saat aplikasi ditutup lalu dibuka kembali), silakan tonton video demo melalui tautan di bawah ini:

👉 **https://drive.google.com/file/d/1-QDsZAybIUsVWhsmSm_0RgP12u-n7kyt/view?usp=sharing**

---

## 🛠️ Teknologi yang Digunakan

* **Kotlin Multiplatform (KMP)** - Berbagi logika bisnis dan UI.
* **Compose Multiplatform** - Framework UI deklaratif.
* **SQLDelight** - Pembuatan antarmuka database *type-safe* untuk SQLite.
* **Jetpack DataStore** - Penyimpanan konfigurasi *key-value* secara asinkron.
* **Coroutines & Flow** - Manajemen *asynchronous* dan *reactive programming*.
* **Material Design 2** - Komponen antarmuka pengguna (UI).

---