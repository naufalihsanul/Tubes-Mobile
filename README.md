# PharmaTic 🏥

[![Kotlin Version](https://img.shields.io/badge/Kotlin-1.9.0-purple.svg?style=flat-square&logo=kotlin)](https://kotlinlang.org)
[![Android SDK Target](https://img.shields.io/badge/Android%20SDK-Target%2036-green.svg?style=flat-square&logo=android)](https://developer.android.com)
[![Room Database](https://img.shields.io/badge/Database-Room%20SQLite-blue.svg?style=flat-square&logo=sqlite)](https://developer.android.com/training/data-storage/room)
[![Firebase Firestore](https://img.shields.io/badge/Cloud%20Sync-Firebase%20Firestore-orange.svg?style=flat-square&logo=firebase)](https://firebase.google.com)
[![Payment Gateway](https://img.shields.io/badge/Payment-Midtrans%20Sandbox-teal.svg?style=flat-square)](https://midtrans.com)
[![Lifecycle](https://img.shields.io/badge/Architecture-MVVM-red.svg?style=flat-square)](https://developer.android.com/topic/libraries/architecture)

---

PharmaTic adalah aplikasi kasir dan manajemen apotek berbasis Android. Aplikasi ini dibuat untuk mempermudah operasional apotek dalam mengelola stok obat, data suplier, akun kasir, dan transaksi penjualan harian agar lebih rapi.

Aplikasi ini dikembangkan untuk memenuhi tugas besar mata kuliah **Tubes Mobile - Semester 6**.

---

## ✨ Fitur Utama

Aplikasi ini punya dua hak akses (role) utama:

### 1. Admin
* **Kelola Obat**: Tambah, edit, cari, dan hapus data obat (nama, stok, harga, jenis, deskripsi, expired date, barcode).
* **Kelola Kasir**: Daftarkan akun kasir baru. Password otomatis dienkripsi pakai SHA-256 agar aman.
* **Kelola Suplier**: Mengelola data kontak suplier yang memasok obat.
* **Laporan Penjualan**: Melihat total pendapatan dan riwayat transaksi harian, serta bisa langsung dibagikan (via WhatsApp/Email/dll).
* **Peringatan Stok**: Muncul notifikasi otomatis di dashboard kalau ada obat yang stoknya sisa 10 atau kurang.

### 2. Kasir
* **Cek Stok & Cari Obat**: Mencari obat dengan cepat dan melihat sisa stok yang tersedia secara real-time.
* **Keranjang Belanja**: Memasukkan obat ke keranjang dan menghitung total harga otomatis.
* **Pilihan Pembayaran**: Bisa bayar tunai (hitung kembalian otomatis) atau pakai QRIS (via Midtrans Sandbox).
* **Cetak Struk**: Menghasilkan struk transaksi belanja digital setelah pembayaran selesai dikonfirmasi.
* **Riwayat Kasir**: Melihat daftar riwayat transaksi yang pernah diproses oleh kasir tersebut.

---

## 🛠️ Stack Teknologi

* **Bahasa**: Kotlin
* **Arsitektur**: MVVM (Model-View-ViewModel) + Repository Pattern
* **Database Lokal**: Room Database (SQLite)
* **Cloud Backup**: Firebase Firestore (sinkronisasi transaksi real-time)
* **Pembayaran**: Midtrans Snap API (Sandbox)
* **Koneksi API**: Retrofit & OkHttp
* **Pemuatan Gambar**: Glide (dengan caching agar hemat kuota)

---

## 🚀 Cara Pemasangan & Menjalankan Projek

1. **Setup Midtrans Key**: Buka file `local.properties` di folder utama projek, lalu tambahkan baris berikut:
   ```properties
   MIDTRANS_API_KEY=TWlkLXNlcnZlci15UUdocWR6MF85OUhuU1BlTVIyM1hGbVU6
   ```
2. **Setup Firebase**: Masukkan file `google-services.json` milik Anda ke folder `/app` proyek ini.
3. **Buka di Android Studio**: Tunggu proses gradle sync selesai.
4. **Jalankan**: Hubungkan HP Android asli atau aktifkan emulator, lalu klik tombol **Run (Shift + F10)**.

---

## 🔑 Akun Login Default

Aplikasi sudah memiliki data awal bawaan (*database seeding*) untuk uji coba login:

* **Admin**: Username `admin` | Password `admin`
* **Kasir**: Username `kasir` | Password `1234` (bisa juga pakai username `budi_kasir` atau `siti22` dengan password yang sama `1234`)

---
*Semoga bermanfaat!* 🏥💚