# Jadwal Push Git - Aplikasi PharmaTic

## HARI 1

**Nopal**
- `Setup awal project Android dan konfigurasi Firebase`
  - `.gitignore`, `app/.gitignore`
  - `build.gradle.kts` (Project), `settings.gradle.kts`, `gradle.properties`
  - `app/build.gradle.kts`, `app/proguard-rules.pro`
  - `gradle/wrapper/gradle-wrapper.properties`, `gradlew`, `gradlew.bat`
  - `gradle/libs.versions.toml`
  - `app/src/main/AndroidManifest.xml`
  - `app/src/main/res/values/strings.xml`, `colors.xml`, `themes.xml`
  - `app/src/main/res/xml/backup_rules.xml`, `app/src/main/res/xml/data_extraction_rules.xml`
  - `app/src/main/res/drawable/*`
  - `app/src/main/res/mipmap-*`
  - `app/google-services.json`
  - `PharmaTicApp.kt`
  - `MainActivity.kt`, `activity_main.xml`
  - `SplashActivity.kt`, `activity_splash.xml`
  - `LoginActivity.kt`, `activity_login.xml`
  - `SessionManager.kt`

**Didy**
- `Membuat desain tampilan awal halaman dashboard`
  - `activity_dashboard.xml`

**Wiwik**
- `Membuat desain tampilan daftar obat untuk kasir`
  - `activity_obat.xml`, `item_obat.xml`

**Amel**
- `Membuat desain tampilan kelola obat untuk admin`
  - `activity_kelola_obat.xml`, `layout_empty_state.xml`, `item_obat_admin.xml`

## HARI 2

**Nopal**
- `Membuat struktur database lokal Room dan entitas data`
  - `PharmaTicDatabase.kt`, `PharmaTicRepository.kt`, `DataManager.kt`
  - `Kasir.kt`, `KasirDao.kt`
  - `Obat.kt`, `ObatDao.kt`
  - `Suplier.kt`, `SuplierDao.kt`
  - `Transaksi.kt`, `TransaksiDao.kt`
  - `TransaksiDetail.kt`, `TransaksiDetailDao.kt`
  - `KeranjangItem.kt`

**Didy**
- `Membuat desain tampilan halaman dashboard admin`
  - `activity_admin_dashboard.xml`

**Wiwik**
- `Membuat desain tampilan halaman keranjang belanja`
  - `activity_keranjang.xml`, `item_keranjang.xml`

**Amel**
- `Membuat desain tampilan form tambah dan edit obat`
  - `activity_tambah_obat.xml`, `activity_edit_obat.xml`

## HARI 3

**Didy**
- `Membuat desain tampilan kelola data suplier`
  - `activity_kelola_suplier.xml`, `item_suplier.xml`

**Wiwik**
- `Membuat desain tampilan halaman transaksi scanner kasir`
  - `activity_transaksi.xml`, `item_transaksi.xml`

**Amel**
- `Membuat desain tampilan kelola data akun kasir`
  - `activity_kelola_kasir.xml`, `item_kasir.xml`
  - `activity_tambah_kasir.xml`, `activity_edit_kasir.xml`

**Nopal**
- `Menambahkan fungsi logika untuk fitur kelola data obat dan kasir`
  - `KelolaObatActivity.kt`, `TambahObatActivity.kt`, `EditObatActivity.kt`, `ObatAdminAdapter.kt`
  - `KelolaKasirActivity.kt`, `TambahKasirActivity.kt`, `EditKasirActivity.kt`, `KasirAdapter.kt`

## HARI 4

**Didy**
- `Membuat desain tampilan form tambah dan edit suplier`
  - `activity_tambah_suplier.xml`, `activity_edit_suplier.xml`

**Wiwik**
- `Membuat desain tampilan pembayaran, verifikasi QRIS, dan cetak struk`
  - `activity_pembayaran.xml`, `activity_verifikasi_pembayaran.xml`
  - `activity_struk.xml`

**Amel**
- `Membuat desain tampilan daftar riwayat transaksi dan laporan harian`
  - `activity_history.xml`, `item_history.xml`
  - `activity_detail_history.xml`, `item_detail_history.xml`
  - `activity_laporan.xml`

**Nopal**
- `Menambahkan fungsi logika untuk navigasi dashboard, kelola suplier, dan riwayat`
  - `DashboardActivity.kt`, `AdminDashboardActivity.kt`
  - `KelolaSuplierActivity.kt`, `TambahSuplierActivity.kt`, `EditSuplierActivity.kt`, `SuplierAdapter.kt`
  - `HistoryActivity.kt`, `HistoryAdapter.kt`, `DetailHistoryActivity.kt`, `DetailHistoryAdapter.kt`, `LaporanActivity.kt`

## HARI 5

**Nopal**
- `Menambahkan fungsi logika keranjang, transaksi scanner, dan pembayaran kasir`
  - `ObatActivity.kt`, `ObatAdapter.kt`
  - `TransaksiActivity.kt`, `TransaksiAdapter.kt`
  - `KeranjangActivity.kt`, `KeranjangAdapter.kt`
  - `PembayaranActivity.kt`, `VerifikasiPembayaranActivity.kt`, `StrukActivity.kt`

**Nopal**
- `Integrasi keranjang, sistem ViewModel, dan API Midtrans untuk pembayaran digital`
  - `KeranjangManager.kt`
  - `KasirViewModel.kt`, `ObatViewModel.kt`, `SuplierViewModel.kt`, `TransaksiViewModel.kt`
  - `RetrofitClient.kt`, `MidtransApi.kt`, `MidtransRequest.kt`, `MidtransResponse.kt`
