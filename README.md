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
