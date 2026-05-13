package com.example.myfirstkmpapp.data
data class Note(
    val id: Int,
    val title: String,
    val content: String,
    val isFavorite: Boolean = false
)

val dummyNotes = listOf(
    Note(1, "Stok Daging Taichan", "Cek ketersediaan daging ayam untuk jualan sate taichan minggu ini. Jangan lupa stok bumbu matcha.", false),
    Note(2, "Tugas KMP ITERA", "Selesaikan implementasi Navigasi Antar Layar dan State Management pakai Clean Architecture. Push ke branch week-5.", true),
    Note(3, "Ide OOTD Kampus", "Besok pake pashmina vischose warna pastel digabung kemeja putih, bismillah aman nggak bikin botak.", false)
)