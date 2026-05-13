# Laporan Praktikum PAM - Testing & Dependency Injection (KMP)

**Informasi Mahasiswa:**
- **Nama:** Nabila Ramadhani Mujahidin
- **NIM:** 123140062
- **Mata Kuliah:** Pengembangan Aplikasi Mobile (PAM)

---

## 📝 Ringkasan Proyek
Proyek ini merupakan pengembangan lanjutan dari aplikasi **Notes App** berbasis Kotlin Multiplatform (KMP). Fokus utama pada tugas kali ini adalah pengintegrasian **Dependency Injection** menggunakan library **Koin** serta penerapan pengujian otomatis (*Automated Testing*) yang komprehensif untuk menjamin kualitas kode.

## 🛠️ Arsitektur & Teknologi
- **Kotlin Multiplatform (KMP)**: Berbagi logika bisnis di Android dan JVM.
- **Koin DI**: Digunakan untuk memisahkan inisialisasi modul data (`dataModule`) dan modul ViewModel (`viewModelModule`).
- **SQLDelight**: Sebagai mesin database persisten.
- **Testing Stack**: 
  - **MockK**: Untuk mocking dependensi pada ViewModel.
  - **Turbine**: Untuk pengujian reaktif pada Kotlin Flows.
  - **Compose Test**: Untuk pengujian antarmuka (UI).

## 🧪 Detail Unit Testing (AAA Pattern)

### 1. Data Layer: NoteRepository
Menguji fungsionalitas CRUD database untuk memastikan data tersimpan dan terbaca dengan benar.
- [x] `insertNote_savesToDatabaseSuccessfully`
- [x] `getAllNotes_returnsCorrectListOfNotes`
- [x] `getNoteById_returnsCorrectNoteWhenExists`
- [x] `deleteNote_removesNoteFromDatabase`
- [x] `updateNote_modifiesExistingNoteData`

### 2. ViewModel Layer: NotesViewModel
Menggunakan **MockK** untuk mensimulasikan interaksi antara UI dan Repository.
- [x] `loadNotes_updatesUiStateSuccessfully`
- [x] `addNote_callsRepositoryInsertAndRefreshesState`
- [x] `deleteNote_callsRepositoryDeleteAndRefreshesState`
- [x] `updateNote_callsRepositoryUpdate`

### 3. Reactive Flow: Turbine Tests
Memastikan emisi data pada Flow berjalan sesuai urutan yang diharapkan.
- [x] `notesStateFlow_emitsLoadingThenSuccessState`
- [x] `notesStateFlow_emitsUpdatedListAfterInsertion`

### 4. UI Layer: Compose Tests
Memastikan elemen antarmuka muncul dan berfungsi dengan baik.
- [x] `notesScreen_displaysEmptyStateWhenNoNotes`
- [x] `notesScreen_displaysNoteListCorrectly`
- [x] `addNoteButton_isClickableAndTriggersAction`

## 📊 Status Pencapaian Kriteria
- [x] **DI Koin (20%)**: Modul data & viewModel terpisah dengan rapi.
- [x] **Repository Tests (20%)**: 5 test cases lulus verifikasi.
- [x] **ViewModel Tests (20%)**: Mocking berhasil dengan MockK.
- [x] **Turbine Flow Tests (15%)**: Aliran data teruji dengan baik.
- [x] **UI Testing (15%)**: Interaksi UI diverifikasi.
- [x] **Kualitas Kode (10%)**: Implementasi bersih mengikuti standar AAA.
- [x] **Bonus (+10%)**: Coverage testing mencapai lebih dari 80%.

---

## 📸 Laporan Coverage
<img width="1780" height="563" alt="Image" src="https://github.com/user-attachments/assets/bf8d8480-960f-426b-a7b9-93c4472416b5" />

## 🎥 Video Demo Aplikasi
https://drive.google.com/file/d/1psCZ4U3zqCKpj_F71G2QdJfbnk9Bhiy3/view?usp=sharing