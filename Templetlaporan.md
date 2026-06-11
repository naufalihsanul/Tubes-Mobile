## LAPORAN TUGAS BESAR

## PEMROGRAMAN BERGERAK

## SEMESTER GENAP 2026

## PHARMATIC: APLIKASI MANAJEMEN APOTEK DAN KASIR

Disusun oleh :
Naufal Ihsanul Islam F1D02310084
Didy Ardianto F1D

Dosen pengampu :
Pahrul Irfan S.Kom., M.Kom.

## PROGRAM STUDI TEKNIK INFORMATIKA

## FAKULTAS TEKNIK

## UNIVERSITAS MATARAM

## 2026

## DAFTAR ISI

- DAFTAR ISI
- DAFTAR GAMBAR
- BAB I PENDAHULUAN
   - 1.1 Latar Belakang
   - 1.2 Deskripsi Aplikasi
   - 1.3 Batasan Masalah
   - 1.4 Tujuan
- BAB II ANALISA DAN DESAIN
   - 2.1 Use Case Diagram
   - 2.2 Entity Relationship Diagram (ERD)
- BAB III IMPLEMENTASI
   - 3.1 Halaman Splash Screen dan Awal Aplikasi
   - 3.2 Halaman Login
   - 3.3 Halaman Dashboard Kasir
   - 3.4 Halaman Daftar Obat Kasir
   - 3.5 Halaman Transaksi Kasir
   - 3.6 Halaman Keranjang dan Pembayaran
   - 3.7 Halaman Struk dan Riwayat Transaksi
   - 3.8 Halaman Dashboard Admin
   - 3.9 Halaman Kelola Obat
   - 3.10 Halaman Kelola Kasir
   - 3.11 Halaman Kelola Suplier
   - 3.12 Halaman Laporan
- BAB IV PENUTUP
   - 4.1 Kesimpulan
   - 4.2 Saran
- DAFTAR PUSTAKA

## DAFTAR GAMBAR

- Gambar 2.1 Use Case Diagram
- Gambar 2.2 Entity Relationship Diagram (ERD)
- Gambar 3.1 Tampilan Splash Screen
- Gambar 3.2 Tampilan Halaman Login
- Gambar 3.3 Tampilan Dashboard Kasir
- Gambar 3.4 Tampilan Daftar Obat Kasir
- Gambar 3.5 Tampilan Transaksi Kasir
- Gambar 3.6 Tampilan Keranjang dan Pembayaran QRIS
- Gambar 3.7 Tampilan Struk dan Riwayat Transaksi
- Gambar 3.8 Tampilan Dashboard Admin
- Gambar 3.9 Tampilan Kelola Obat
- Gambar 3.10 Tampilan Kelola Kasir
- Gambar 3.11 Tampilan Kelola Suplier
- Gambar 3.12 Tampilan Laporan Transaksi

## BAB I PENDAHULUAN

### 1.1 Latar Belakang

Manajemen stok obat dan proses transaksi di apotek sering kali masih dilakukan secara manual atau menggunakan sistem yang kurang praktis. Hal ini dapat menyebabkan ketidakakuratan data stok, keterlambatan pelayanan, dan kesulitan dalam melacak riwayat penjualan. Perkembangan teknologi seluler saat ini menawarkan solusi yang sangat efektif untuk mengatasi masalah tersebut. Dengan menggunakan perangkat Android, pencatatan barang dan proses pembayaran dapat dilakukan dengan jauh lebih cepat dan akurat. Oleh karena itu, aplikasi PharmaTic dikembangkan sebagai solusi digital yang dirancang khusus untuk mempermudah pengelolaan apotek, mulai dari pemantauan ketersediaan obat hingga sistem kasir yang terintegrasi.

### 1.2 Deskripsi Aplikasi

PharmaTic adalah aplikasi berbasis Android yang dirancang untuk membantu operasional apotek sehari-hari. Aplikasi ini membedakan hak akses antara pengelola apotek dan petugas kasir agar setiap pekerjaan lebih terarah. Fitur utama dalam aplikasi ini meliputi:

1. Sistem Login, memisahkan akses untuk admin dan kasir.
2. Dashboard Admin, memberikan ringkasan total obat dan riwayat penjualan.
3. Dashboard Kasir, berfokus pada fitur transaksi cepat dan pemantauan stok.
4. Manajemen Data, memungkinkan penambahan, pengubahan, dan penghapusan data obat, suplier, dan akun kasir.
5. Transaksi Kasir, sistem keranjang belanja yang dilengkapi dengan fitur scan barcode untuk mempercepat pencarian barang.
6. Pembayaran Digital, integrasi dengan payment gateway untuk menerima pembayaran QRIS secara otomatis.
7. Notifikasi Stok, peringatan cerdas yang akan muncul apabila jumlah suatu obat sudah menipis.

### 1.3 Batasan Masalah

Aplikasi PharmaTic memiliki beberapa batasan fokus pengembangan:

- Platform, aplikasi ini dikembangkan khusus untuk sistem operasi Android.
- Pembayaran, fitur QRIS berjalan pada mode sandbox untuk keperluan simulasi dan pengujian transaksi.
- Data, penyimpanan data utama berjalan secara offline di dalam perangkat, namun riwayat transaksi disinkronkan ke layanan cloud sebagai cadangan.

### 1.4 Tujuan

Tujuan utama pembuatan aplikasi ini adalah untuk mempercepat dan mempermudah alur kerja di apotek. PharmaTic berupaya menekan tingkat kesalahan pencatatan barang dan mempercepat proses antrean pembelian. Dengan antarmuka yang bersih dan fitur otomasi seperti pemindai barcode serta pembayaran digital, petugas apotek dapat bekerja dengan lebih terorganisir dan efisien.

## BAB II ANALISA DAN DESAIN

### 2.1 Use Case Diagram

[Tempat Gambar]

Gambar 2.1 Use Case Diagram

Berdasarkan gambar di atas, terdapat dua aktor utama yang menggunakan aplikasi, yaitu Admin dan Kasir. Admin memiliki hak akses penuh untuk mengelola master data seperti daftar obat, daftar suplier, dan data akun kasir. Selain itu, Admin dapat memantau laporan keseluruhan. Di sisi lain, Kasir memiliki akses yang difokuskan pada kegiatan operasional penjualan. Kasir dapat melihat daftar obat, melayani transaksi pembeli, memindai barcode barang, memasukkan barang ke keranjang, dan mencetak struk pembayaran. Kedua aktor tersebut menerima notifikasi peringatan jika terdapat stok obat yang akan habis.

### 2.2 Entity Relationship Diagram (ERD)

[Tempat Gambar]

Gambar 2.2 Entity Relationship Diagram (ERD)

Berdasarkan gambar di atas, sistem basis data terdiri dari beberapa tabel utama yang saling berelasi, yaitu entitas Obat, Suplier, Kasir (Pengguna), Transaksi, dan Detail Transaksi. Skema database ini diimplementasikan menggunakan Room Database untuk penyimpanan lokal, sementara data Riwayat Transaksi secara sinkron direplikasi ke Firebase Cloud Firestore untuk kebutuhan backup dan pelaporan online.

## BAB III IMPLEMENTASI

Pada bab ini, akan diuraikan hasil antarmuka pengguna beserta penjelasan mengenai implementasi kode dan file-file penyusunnya. Pembahasan dimulai dari halaman yang diakses oleh Kasir, dilanjutkan dengan halaman untuk Admin.

### 3.1 Halaman Splash Screen dan Awal Aplikasi

[Tempat Gambar]

Gambar 3.1 Tampilan Splash Screen

Halaman ini adalah antarmuka pertama yang muncul sesaat ketika aplikasi dibuka, menampilkan logo aplikasi selama beberapa detik. Tampilannya dikelola oleh `"activity_splash.xml"` dan `"SplashActivity.kt"`. Secara sistem, berjalannya aplikasi ini dimulai dengan inisialisasi *"base application"* pada `"PharmaTicApp.kt"` yang juga mengatur akses instansiasi database lokal `"PharmaTicDatabase.kt"`. Setelah proses muat (loading) di Splash selesai, sistem akan diarahkan melalui kelas `"MainActivity.kt"` yang bertugas sebagai pengatur rute utama (tanpa memuat `"activity_main.xml"` sebagai *view*). Kelas ini akan langsung mengecek `"SessionManager.kt"` untuk menentukan apakah pengguna harus diarahkan ke halaman Login, Dashboard Kasir, atau Dashboard Admin secara otomatis berdasarkan status sesi login terakhir.

### 3.2 Halaman Login

[Tempat Gambar]

Gambar 3.2 Tampilan Halaman Login

Halaman login berfungsi sebagai gerbang utama aplikasi bagi pengguna baru atau yang belum memiliki sesi aktif. Tampilannya dirancang secara bersih dengan form input untuk username dan password guna membedakan hak akses. Secara teknis, antarmuka halaman ini dibangun di dalam file `"activity_login.xml"`. Untuk logika sistemnya (autentikasi dan pengecekan akses admin atau kasir) ditangani oleh kelas `"LoginActivity.kt"`. Proses masuk ini juga terhubung dengan penyimpanan data sesi di lokal menggunakan `SessionManager` berbasis `SharedPreferences`.

### 3.3 Halaman Dashboard Kasir

[Tempat Gambar]

Gambar 3.3 Tampilan Dashboard Kasir

Setelah kasir berhasil login, mereka akan diarahkan ke halaman ini. Tampilannya memuat menu-menu pintasan berbentuk kartu yang responsif untuk memudahkan navigasi ke fitur kasir seperti transaksi atau riwayat. Desain visual halaman ini berada pada `"activity_dashboard.xml"`, sedangkan untuk mengatur fungsi klik tombol dan pemindahan halaman dikelola melalui kelas `"DashboardActivity.kt"`.

### 3.4 Halaman Daftar Obat Kasir

[Tempat Gambar]

Gambar 3.4 Tampilan Daftar Obat Kasir

Berbeda dengan fitur kelola obat untuk admin, halaman ini digunakan khusus oleh kasir untuk melihat inventaris dan mempermudah pencarian. Tampilannya memuat komponen RecyclerView yang didefinisikan pada `"activity_obat.xml"` dan susunan tiap itemnya menggunakan `"item_obat.xml"`. Daftar obat dari database ini dimuat dan dikelola pencariannya oleh `"ObatActivity.kt"`, serta diterjemahkan ke dalam list visual oleh kelas `"ObatAdapter.kt"`.

### 3.5 Halaman Transaksi Kasir

[Tempat Gambar]

Gambar 3.5 Tampilan Transaksi Kasir

Halaman ini merupakan tempat utama kasir melayani pembelian barang. Di sini terdapat fitur pemindai (scan) barcode yang memanfaatkan kamera secara langsung untuk mempercepat pencarian barang fisik. Tampilan halamannya disusun menggunakan `"activity_transaksi.xml"`, dengan daftar pencarian sementara yang di-*render* oleh `"item_transaksi.xml"` dan `"TransaksiAdapter.kt"`. Adapun logika program untuk menjalankan scanner, mengambil data obat, dan mengirimnya ke keranjang diatur melalui `"TransaksiActivity.kt"` bersama antarmuka datanya melalui `"TransaksiViewModel.kt"`.

### 3.6 Halaman Keranjang dan Pembayaran

[Tempat Gambar]

Gambar 3.6 Tampilan Keranjang dan Pembayaran QRIS

Setelah barang dipindai, data akan masuk ke halaman keranjang belanja yang menampung daftar barang pembelian. Antarmukanya menggunakan `"activity_keranjang.xml"` yang dipasangkan dengan `"item_keranjang.xml"` untuk merender *list* lewat `"KeranjangAdapter.kt"` berdasarkan struktur model data `"KeranjangItem.kt"`. Logika sementaranya dikelola efisien oleh kelas `"KeranjangManager.kt"` dan `"KeranjangActivity.kt"`. Jika dilanjutkan ke pembayaran, sistem memproses total tagihan dan menampilkan antarmuka QRIS untuk pembayaran digital pada `"activity_pembayaran.xml"` (diatur oleh `"PembayaranActivity.kt"`). Untuk integrasi layanan QRIS, aplikasi berkomunikasi dengan server pihak ketiga melalui `"RetrofitClient.kt"` dan antarmuka API `"MidtransApi.kt"` beserta model datanya `"MidtransRequest.kt"` dan `"MidtransResponse.kt"`. Alur akan berlanjut ke tahap `"VerifikasiPembayaranActivity.kt"` (`"activity_verifikasi_pembayaran.xml"`) untuk memastikan transaksi sukses.

### 3.7 Halaman Struk dan Riwayat Transaksi

[Tempat Gambar]

Gambar 3.7 Tampilan Struk dan Riwayat Transaksi

Halaman ini berfungsi untuk menampilkan rekapitulasi penjualan setelah pembayaran berhasil, serta memberikan detail layaknya cetakan struk fisik. Antarmukanya menggunakan file `"activity_struk.xml"` (`"StrukActivity.kt"`) dan daftar riwayat pada `"activity_history.xml"` (`"HistoryActivity.kt"`). Visualisasi daftar riwayat dibantu oleh `"item_history.xml"` dan `"HistoryAdapter.kt"`. Jika pengguna menekan riwayat spesifik, akan muncul tampilan detail menggunakan `"DetailHistoryActivity.kt"` (`"activity_detail_history.xml"`) berserta `"DetailHistoryAdapter.kt"` (`"item_detail_history.xml"`). Di tahap ini pula, data transaksi disimpan secara permanen oleh `"DataManager.kt"` dan `"PharmaTicRepository.kt"` ke dalam Room Database lokal melalui perantara entitas `"Transaksi.kt"` dan `"TransaksiDetail.kt"` yang dieksekusi oleh `"TransaksiDao.kt"` dan `"TransaksiDetailDao.kt"`. Pada akhirnya seluruh data ini juga disinkronkan ke layanan awan Firebase.

### 3.8 Halaman Dashboard Admin

[Tempat Gambar]

Gambar 3.8 Tampilan Dashboard Admin

Jika pengguna masuk sebagai Admin, halaman utama yang muncul adalah Dashboard Admin. Halaman ini memberikan akses penuh terhadap kelola master data (obat, kasir, suplier) dan pelaporan. Tampilannya dimuat oleh `"activity_admin_dashboard.xml"`, dan logika operasionalnya dikelola di dalam file `"AdminDashboardActivity.kt"`.

### 3.9 Halaman Kelola Obat

[Tempat Gambar]

Gambar 3.9 Tampilan Kelola Obat

Halaman ini diperuntukkan bagi admin untuk menambah, mengubah, atau menghapus inventaris obat. Antarmuka daftar obat dirancang menggunakan RecyclerView pada `"activity_kelola_obat.xml"` (lengkap dengan penyertaan desain kosong `"layout_empty_state.xml"` jika tidak ada data) dan file desain item-nya `"item_obat_admin.xml"`. Data tersebut ditampilkan secara runut ke layar menggunakan bantuan kelas `"ObatAdminAdapter.kt"`. Logika bisnis utamanya berjalan di kelas `"KelolaObatActivity.kt"`. Untuk aksi spesifik penambahan dan modifikasi data, fungsionalitasnya dipisah menggunakan `"TambahObatActivity.kt"` (`"activity_tambah_obat.xml"`) dan `"EditObatActivity.kt"` (`"activity_edit_obat.xml"`). Keseluruhan pengolahan data persisten diatur menggunakan `"ObatViewModel.kt"` yang menghubungkan model `"Obat.kt"` dengan antarmuka akses data `"ObatDao.kt"`.

### 3.10 Halaman Kelola Kasir

[Tempat Gambar]

Gambar 3.10 Tampilan Kelola Kasir

Halaman ini menampilkan antarmuka bagi admin untuk meregistrasi, mengedit, atau menghapus akun para petugas kasir. Desain halamannya menggunakan `"activity_kelola_kasir.xml"` yang dipasangkan dengan desain item `"item_kasir.xml"` dan kelas `"KasirAdapter.kt"` untuk menampilkan daftarnya. Proses manajerial data akun ini dijalankan melalui `"KelolaKasirActivity.kt"`. Form untuk input registrasi dan pengubahan data ditangani oleh `"TambahKasirActivity.kt"` (`"activity_tambah_kasir.xml"`) dan `"EditKasirActivity.kt"` (`"activity_edit_kasir.xml"`), lalu diteruskan ke arsitektur datanya melalui `"KasirViewModel.kt"` yang mengelola objek entitas `"Kasir.kt"` dan berinteraksi dengan database lewat `"KasirDao.kt"`.

### 3.11 Halaman Kelola Suplier

[Tempat Gambar]

Gambar 3.11 Tampilan Kelola Suplier

Halaman ini memberikan kontrol bagi admin atas data para penyuplai obat. Tampilan daftar penyuplai diatur dalam `"activity_kelola_suplier.xml"` dengan memanfaatkan tampilan individu `"item_suplier.xml"` yang diikat datanya menggunakan `"SuplierAdapter.kt"`. Interaksi fungsionalitas CRUD data suplier dilakukan di dalam kelas `"KelolaSuplierActivity.kt"`. Proses input formulir suplier baru maupun modifikasi diproses melalui `"TambahSuplierActivity.kt"` (`"activity_tambah_suplier.xml"`) dan `"EditSuplierActivity.kt"` (`"activity_edit_suplier.xml"`), dibantu kelancaran datanya oleh `"SuplierViewModel.kt"` yang memproses model `"Suplier.kt"` melalui antarmuka `"SuplierDao.kt"`.

### 3.12 Halaman Laporan

[Tempat Gambar]

Gambar 3.12 Tampilan Laporan Transaksi

Halaman laporan menyajikan keseluruhan ringkasan dari semua transaksi yang telah terjadi di apotek, khusus untuk dipantau oleh Admin. Struktur visual antarmukanya dibentuk oleh `"activity_laporan.xml"`, dan penarikan seluruh data riwayat dari database beserta logikanya dikendalikan di dalam kelas `"LaporanActivity.kt"`.

## BAB IV PENUTUP

### 4.1 Kesimpulan

Pembuatan aplikasi PharmaTic memberikan solusi nyata terhadap permasalahan operasional apotek tradisional. Pendekatan digital berhasil memangkas waktu pencatatan stok dan perhitungan total belanja. Penerapan fitur seperti scan barcode terbukti sangat mempercepat proses pencarian barang di meja kasir. Selain itu, pemisahan hak akses antara admin dan kasir menjadikan pekerjaan lebih tertata. Arsitektur aplikasi yang menggabungkan penyimpanan lokal dan cloud memastikan apotek tetap dapat beroperasi dengan lancar. Secara keseluruhan, PharmaTic berhasil menjadi alat bantu manajemen apotek yang efisien, modern, dan andal.

### 4.2 Saran

Untuk meningkatkan kualitas aplikasi PharmaTic di masa mendatang, pengembangan dapat difokuskan pada penambahan fitur cetak struk fisik menggunakan koneksi bluetooth ke printer thermal kecil. Peningkatan lainnya dapat berupa penyediaan dasbor laporan penjualan dalam bentuk grafik bulanan agar pengelola apotek dapat melihat tren penjualan dengan lebih mudah. Selain itu, fitur pengingat kedaluwarsa obat dapat ditambahkan untuk melengkapi sistem peringatan stok yang sudah ada.

## DAFTAR PUSTAKA

[1] Dokumentasi Android Developers, "Panduan Arsitektur Aplikasi", Google.
[2] Dokumentasi Midtrans, "Integrasi Core API QRIS", Midtrans.
[3] Dokumentasi Firebase, "Cloud Firestore untuk Android", Google.
