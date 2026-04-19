# Tugas 3 - Pengembangan Aplikasi Mobile
## Aplikasi My Profile
- **Nama:** Nabila Ramadhani Mujahidin
- **NIM:** 123140062
- **Kelas:** Pengembangan Aplikasi Mobile RB

## Tampilan Aplikasi (Screenshot)
<img width="446" height="986" alt="image" src="https://github.com/user-attachments/assets/6a348102-21aa-4ab0-9a4f-eca08ab41e6e" />

## Fitur Aplikasi

Aplikasi menampilkan informasi profil pengguna dengan komponen UI modern dari Compose Multiplatform, meliputi:

* Foto profil berbentuk circular dengan border kustom (warna pink)
* Nama, Pekerjaan (Role), dan Status koneksi
* Tombol interaktif "Open Bio / Close Bio" yang menampilkan/menyembunyikan deskripsi secara mulus menggunakan animasi
* Tombol "Message" bergaya outlined
* Informasi detail pengguna (dibungkus dalam Card):
  * Email
  * Instagram
  * Education (Pendidikan)

## Komponen UI yang Digunakan

Aplikasi ini menggunakan beberapa komponen utama dari Compose Material 3:

* Scaffold & TopAppBar (Kerangka dasar dan header atas)
* Column, Row, Box (Untuk layouting)
* Card (Sebagai wadah informasi dengan efek elevasi/bayangan)
* Text (Untuk tipografi)
* Button & OutlinedButton (Tombol aksi)
* Image & Icon (Menampilkan foto profil dan ikon material)
* Divider & Spacer (Untuk garis pemisah dan jarak antar elemen)
* AnimatedVisibility (Untuk animasi transisi memunculkan/menyembunyikan Bio)

## Reusable Composable Functions

Project ini menggunakan beberapa Composable Function yang reusable (dapat digunakan kembali), yaitu:

* `ProfileScreen()`
  Menjadi kerangka utama atau screen pembungkus halaman profil.

* `ProfileHeader()`
  Menampilkan bagian atas profil yang berisi foto profil, nama, role, dan status singkat.

* `InfoRowItem()`
  Menampilkan setiap baris item informasi (seperti Email, Instagram, Education) yang menerima parameter berupa ikon, label, dan nilainya.

---

# Tugas 4 - Pengembangan Aplikasi Mobile
## Pembaruan: Arsitektur MVVM & Fitur Interaktif Tambahan

Pada Tugas 4, aplikasi dikembangkan lebih lanjut dengan memisahkan logika bisnis dari tampilan UI menggunakan arsitektur **MVVM (Model-View-ViewModel)** dan penambahan fitur dinamis yang lebih interaktif.

## Fitur Baru Aplikasi

* **Pengelolaan State dengan MVVM:** Data profil tidak lagi di-hardcode di UI, melainkan disimpan dalam Data Class (`ProfileUiState`) dan dikelola secara reaktif oleh `ProfileViewModel`.
* **Fitur Edit Profil:** Pengguna dapat menekan tombol "Edit Profile" untuk memunculkan form pengisian nama dan bio. Perubahan teks akan langsung tersimpan secara *real-time* menggunakan prinsip *State Hoisting*.
* **Dark Mode & Light Mode (Tema Gelap/Terang):** Terdapat sebuah *Switch* (ikon 🌙/☀️) di TopAppBar. Ketika ditekan, tema aplikasi beserta warnanya akan berubah dari terang ke gelap (dan sebaliknya) dengan animasi transisi warna latar yang halus.

## Komponen & Teknologi Baru yang Digunakan

* **ViewModel & StateFlow:** Menggunakan `ViewModel` dari *Lifecycle* dan `StateFlow` (`MutableStateFlow`, `asStateFlow`, `.update`) dari *Kotlin Coroutines* untuk manajemen data reaktif.
* **Switch:** Komponen Compose untuk tombol *toggle* pada fitur Dark Mode.
* **OutlinedTextField:** Komponen form input teks untuk mengetik/mengubah nama dan bio.
* **animateColorAsState:** Komponen animasi yang digunakan untuk membuat perubahan warna background transisi terang-ke-gelap menjadi lebih *smooth* (menggunakan durasi *tween 500ms*).
* **MaterialTheme (Color Schemes):** Pengaturan palet warna kustom untuk mode terang (`LightColorScheme`) dan mode gelap (`DarkColorScheme`).

## Reusable Composable Functions (Tambahan)

Aplikasi kini sepenuhnya menerapkan **State Hoisting** (menarik *state* ke atas) sehingga seluruh fungsi UI menjadi *Stateless*. Terdapat penambahan fungsi reusable baru:

* **`StatelessEditField()`**
  Merupakan form input teks (`OutlinedTextField`) yang dapat digunakan kembali. Menerima parameter berupa `label`, nilai teks saat ini (`value`), dan aksi pembaruan saat teks diketik (`onValueChange`). Fungsi ini digunakan untuk input Nama dan Bio secara efisien.

## Tampilan Aplikasi (Screenshot)
<img width="422" height="935" alt="Image" src="https://github.com/user-attachments/assets/f7668de3-7f5e-4121-bf20-5072b36f0be8" />
<img width="423" height="938" alt="Image" src="https://github.com/user-attachments/assets/958b6450-6902-4d63-aebe-e747ae837655" />
<img width="418" height="938" alt="Image" src="https://github.com/user-attachments/assets/6af92be8-d47f-4ad3-aa72-39e07b785cdb" />
<img width="431" height="940" alt="Image" src="https://github.com/user-attachments/assets/41e04028-7b75-4428-bcce-640f22ff53cb" />

---

# Tugas 5 - Pengembangan Aplikasi Mobile
## Pembaruan: Navigasi Multi-Layar, Clean Architecture & State Management

Pada Tugas 5, aplikasi dirombak secara menyeluruh dari *single-file monolithic* menjadi struktur proyek *feature-based* yang menerapkan prinsip **Clean Architecture**. Aplikasi ini kini menggabungkan fitur **Personal Profile** (dari tugas sebelumnya) dengan fitur **Notes/Catatan Pribadi** yang terintegrasi penuh menggunakan navigasi antar layar (*Multi-Screen Navigation*) dan *State Management* yang reaktif.

## Fitur Baru Aplikasi

* **Struktur Proyek Modular:** Pemisahan kode ke dalam folder `data`, `navigation`, `screens`, `viewmodel`, dan `ui` untuk menerapkan prinsip *Separation of Concerns* agar kode lebih terstruktur dan mudah di-*maintain*.
* **Bottom Navigation Menu:** Implementasi 3 tab navigasi utama di bagian bawah layar:
  1. **Notes:** Menampilkan daftar seluruh catatan pribadi.
  2. **Favorites:** Menampilkan daftar catatan yang telah ditandai sebagai favorit.
  3. **Profile:** Menampilkan halaman profil utama pengguna.
* **Side Menu (Navigation Drawer):** Panel menu samping interaktif yang dapat diakses melalui ikon *burger menu* pada TopAppBar.
* **Passing Arguments (Navigasi List ke Detail):** Pengguna dapat menekan sebuah catatan pada *List* untuk masuk ke halaman `NoteDetailScreen`. Navigasi ini mengirimkan parameter `noteId` (sebagai argumen) secara dinamis untuk merender isi detail catatan yang ditekan.
* **Sistem Favorite Reaktif (MVVM):** Pengguna dapat menandai catatan sebagai favorit dari layar detail maupun list. Berkat penggunaan `NotesViewModel` sebagai *Single Source of Truth* (`StateFlow`), penambahan atau penghapusan status favorit akan langsung diperbarui secara *real-time* (reaktif) di semua layar yang ada.
* **Dynamic OS Status Bar (KMP Expect/Actual):** Fitur *advanced* menggunakan konsep *platform-specific* Kotlin Multiplatform (`expect`/`actual`) untuk memanipulasi warna *Status Bar* OS (ikon baterai, jam, sinyal). Warna status bar akan otomatis beradaptasi menjadi kontras setiap kali *Dark Mode* atau *Light Mode* diaktifkan dari dalam aplikasi.

## Komponen & Teknologi Utama

* **Navigation Compose:** Menggunakan `NavHost`, `composable`, dan `rememberNavController` untuk mendeklarasikan *route* (rute) layar dan mengontrol perpindahan tumpukan (*BackStack*).
* **navArgument & NavType:** Komponen khusus untuk mengirim dan mengurai (*parsing*) data (seperti ID bertipe Integer) antar rute layar.
* **NavigationBar, NavigationBarItem & ModalNavigationDrawer:** Komponen navigasi *Material Design 3* untuk struktur antarmuka aplikasi.
* **Multiple ViewModels (`StateFlow`):** Injeksi `ProfileViewModel` dan `NotesViewModel` di level atas aplikasi (`App.kt`), yang kemudian didistribusikan ke layar-layar yang membutuhkan untuk menjaga konsistensi state.
* **Platform-Specific Code:** Memodifikasi `WindowCompat.getInsetsController` khusus pada modul `androidMain` agar tampilan bar perangkat sinkron dengan status tema yang diatur di dalam `Compose Multiplatform`.

## Tampilan Aplikasi (Screenshot)
*<img width="445" height="956" alt="Image" src="https://github.com/user-attachments/assets/3f7a5404-0fa7-4f82-a982-430bdb72513a" />*
*<img width="445" height="956" alt="Image" src="https://github.com/user-attachments/assets/d95a7e3a-000e-4eb3-b09e-d64ca8467792" />*
*<img width="445" height="956" alt="Image" src="https://github.com/user-attachments/assets/a06e3b58-28a6-4dfe-9d14-cf6ca43b1e41" />*

# Tugas 6 - Pengembangan Aplikasi Mobile
## Pembaruan: Networking (REST API), Ktor Client & Asynchronous Data Fetching

Pada Tugas 6, aplikasi ditingkatkan kemampuannya untuk berinteraksi dengan dunia luar melalui **Networking**. Aplikasi tidak lagi hanya mengelola data lokal, melainkan mengambil data berita internasional secara *real-time* dari internet menggunakan **REST API**. Fitur ini diintegrasikan ke dalam ekosistem aplikasi yang sudah ada dengan tetap mempertahankan **Tema Pink** dan arsitektur **MVVM**.

## Fitur Baru Aplikasi

* **Integrasi REST API:** Mengambil data berita terbaru seputar teknologi luar angkasa secara dinamis dari *Spaceflight News API*.
* **Asynchronous Networking dengan Ktor:** Implementasi HTTP Client menggunakan library **Ktor** yang berjalan secara non-blocking di background thread menggunakan *Kotlin Coroutines*.
* **Automated JSON Parsing:** Mengonversi data mentah JSON dari internet menjadi objek Kotlin secara otomatis menggunakan library **Kotlinx Serialization**.
* **Handling Network UI States:** * **Loading State:** Menampilkan *Circular Progress Indicator* saat data sedang diunduh.
  * **Success State:** Menampilkan daftar berita dalam bentuk kartu (Card) yang rapi dengan informasi judul dan ringkasan.
  * **Error State:** Menampilkan pesan kesalahan dan tombol "Coba Lagi" jika koneksi internet terputus atau API bermasalah.
* **Fitur Bookmark Berita:** Pengguna dapat menandai berita tertentu sebagai favorit (Bookmark). Status ini dikelola secara reaktif menggunakan `StateFlow` di dalam ViewModel.
* **Navigasi Detail Berita:** Mendukung perpindahan layar dari daftar berita ke informasi detail berita secara lengkap.

## Komponen & Teknologi Baru yang Digunakan

* **Ktor Client (Core, ContentNegotiation, Logging):** Library utama untuk menangani request HTTP (GET) dan logging aktivitas jaringan.
* **Kotlinx Serialization:** Digunakan untuk melakukan *encoding* dan *decoding* format JSON dengan tipe data yang aman (*type-safe*).
* **Repository Pattern (Abstraction Layer):** Memisahkan logika pengambilan data (Network) dari logika bisnis (ViewModel) untuk mempermudah pengujian dan pemeliharaan kode.
* **LazyColumn & Refresh Action:** Komponen untuk menampilkan daftar berita dalam jumlah banyak secara efisien serta fitur muat ulang manual menggunakan icon *Refresh* pada TopAppBar.
* **Result Pattern:** Penggunaan kelas `Result<T>` di level Repository untuk menangkap *exception* (seperti `ConnectException`) agar aplikasi tidak *crash* saat jaringan bermasalah.

## Struktur Data API (JSON Structure)

Aplikasi mengonsumsi endpoint `https://api.spaceflightnewsapi.net/v4/articles/` dengan struktur data:
* `title`: Judul berita internasional.
* `summary`: Ringkasan konten berita.
* `image_url`: URL gambar pendukung berita.
* `url`: Link sumber berita asli sebagai ID unik untuk fitur bookmark.

## Tampilan Aplikasi (Screenshot)

| Daftar Berita | Detail Berita                                                                                                                      | State Loading |
|---|------------------------------------------------------------------------------------------------------------------------------------|---|
| <img width="366" height="799" alt="Image" src="https://github.com/user-attachments/assets/d1775477-c138-43bf-86dc-3c5c488065c9" /> | <img width="363" height="805" alt="Image" src="https://github.com/user-attachments/assets/64061b62-3909-42b8-a321-dec93b7d1025" /> | <img width="373" height="812" alt="Image" src="https://github.com/user-attachments/assets/05b24304-fec0-4ad1-a013-c17a00477757" /> |

## Video Demonstrasi Aplikasi

Berikut adalah rekaman demonstrasi fitur Networking, Navigasi, dan Bookmark pada Tugas 6:
* **Link Video:** https://drive.google.com/file/d/1mqbnuXEWlgip2lIC9A7QZppZ72n452Qk/view?usp=drive_link

---