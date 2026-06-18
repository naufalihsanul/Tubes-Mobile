# Modul Belajar PharmaTic — Presentasi Ready

> Target: Paham konsep + bisa jawab pertanyaan dosen.
> Metode: Satu konsep kecil dulu, baru tambah pelan-pelan.

---

## Daftar Isi

- [Modul 1: Activity & Intent](#modul-1-activity--intent)
- [Modul 2: XML Layout (Tampilan)](#modul-2-xml-layout-tampilan)
- [Modul 3: Model / Data Class](#modul-3-model--data-class)
- [Modul 4: Room Database (Entity + DAO + Database)](#modul-4-room-database)
- [Modul 5: ViewModel + LiveData](#modul-5-viewmodel--livedata)
- [Modul 6: RecyclerView + Adapter](#modul-6-recyclerview--adapter)
- [Modul 7: Retrofit (API Call)](#modul-7-retrofit-api-call)
- [Modul 8: Menghubungkan Semua Bagian](#modul-8-menghubungkan-semua-bagian)

---

# Modul 1: Activity & Intent

## 1.1 Apa itu Activity?

**Activity = satu halaman di HP kamu.**

Kalau kamu buka app, terus muncul halaman login — itu satu Activity.
Klik tombol "Masuk", pindah ke halaman dashboard — itu Activity lain.

Jadi di app kamu ada banyak Activity:
- LoginActivity → halaman login
- DashboardActivity → halaman utama setelah login
- KelolaObatActivity → halaman daftar obat
- Dan seterusnya...

## 1.2 Apa itu Intent?

**Intent = "surat perintah" untuk pindah dari satu Activity ke Activity lain.**

Ibarat kamu mau pergi dari rumah ke kantor, kamu butuh kendaraan.
Intent = kendaraannya.

## 1.3 Cara Kerja Intent (Konsep Saja)

```
Kamu di LoginActivity
    ↓
Klik tombol "Masuk"
    ↓
Buat Intent: "Tolong bawa saya ke DashboardActivity"
    ↓
Pindah ke DashboardActivity
```

## 1.4 Dua Jenis Intent

**1. Explicit Intent** — Tunjuk langsung nama tujuannya.
> "Bawa saya ke DashboardActivity" (sudah tahu persis mau ke mana)

**2. Implicit Intent** — Sebutkan aksi-nya, sistem yang cari.
> "Buka browser" (sistem yang pilih app browser-nya)

Di PharmaTic, kamu pakai **Explicit Intent** semua.

## 1.5 Bentuk Umum Explicit Intent

```kotlin
// Buat "kendaraan" menuju halaman tujuan
val intent = Intent(SekarangIni, Tujuan)

// Naik kendaraan (pindah halaman)
startActivity(intent)
```

Contoh nyata:
```kotlin
val intent = Intent(LoginActivity.this, DashboardActivity::class.java)
startActivity(intent)
```

**Baca-nya:** "Dari LoginActivity, bawa saya ke DashboardActivity."

## 1.6 Intent Bisa Bawa Data

Kadang kamu mau kirim data saat pindah halaman.
Contoh: setelah login, kirim nama user ke dashboard.

```kotlin
val intent = Intent(SekarangIni, Tujuan::class.java)
intent.putExtra("kunci", "isi data")
startActivity(intent)
```

Di halaman tujuan, ambil datanya:
```kotlin
val data = intent.getStringExtra("kunci")
```

**Analogi:** `putExtra` = masukkan surat ke dalam amplop. `getStringExtra` = buka amplop, baca suratnya.

---

## Cek Pemahaman Modul 1

Coba jawab tanpa lihat code:
1. Apa itu Activity?
2. Apa itu Intent?
3. Bagaimana cara pindah dari satu halaman ke halaman lain?
4. Bagaimana cara kirim data saat pindah halaman?

---
---

# Modul 2: XML Layout (Tampilan)

## 2.1 Kenapa Ada XML?

Di Android, **tampilan (UI) dan logic (kode) dipisah.**

- **XML** → mengatur tampilan (tombol di mana, warnanya apa, teks-nya apa)
- **Kotlin** → mengatur perilaku (kalau tombol diklik, lakukan apa)

Ibarat rumah:
- XML = denah rumah (di mana pintu, jendela, kamar)
- Kotlin = instalasi listrik & pipa (kalau saklar dinyalakan, lampu nyala)

## 2.2 Komponen Dasar di XML

| Komponen | Fungsi |
|----------|--------|
| `TextView` | Menampilkan teks |
| `EditText` | Kolom input (user bisa ketik) |
| `Button` | Tombol yang bisa diklik |
| `ImageView` | Menampilkan gambar |
| `RecyclerView` | Menampilkan daftar (list) yang bisa di-scroll |
| `LinearLayout` | Wadah yang menyusun anak-anaknya berbaris (vertikal/horizontal) |
| `ConstraintLayout` | Wadah yang lebih bebas posisi anak-anaknya |

## 2.3 Properti yang Sering Muncul

```xml
android:layout_width="match_parent"   → lebar = selebar parent-nya
android:layout_height="wrap_content"  → tinggi = secukupnya (tidak lebih)
android:text="Hello"                  → teks yang ditampilkan
android:id="@+id/namaId"             → nama unik supaya bisa diakses dari Kotlin
```

## 2.4 Kenapa `id` Penting?

Kalau di XML kamu kasih `android:id="@+id/tvNama"`,
maka di Kotlin kamu bisa akses TextView itu dengan:

```kotlin
val tvNama = findViewById<TextView>(R.id.tvNama)
tvNama.text = "Halo, Budi!"
```

**Tanpa `id`, Kotlin tidak bisa "menyentuh" komponen itu.**

## 2.5 Cara Kotlin Menghubungkan ke XML

Ada 2 cara:

**Cara lama (findViewById):**
```kotlin
val tombol = findViewById<Button>(R.id.btnMasuk)
tombol.setOnClickListener { ... }
```

**Cara modern (View Binding / Synthetic):**
```kotlin
binding.btnMasuk.setOnClickListener { ... }
```

Di PharmaTic, pakai cara lama (findViewById).

---

## Cek Pemahaman Modul 2

1. Kenapa tampilan dipisah ke XML?
2. Apa bedanya TextView dan EditText?
3. Apa fungsi `android:id`?
4. Bagaimana Kotlin bisa "menyentuh" komponen di XML?

---
---

# Modul 3: Model / Data Class

## 3.1 Apa itu Model?

**Model = cetakan untuk menyimpan data.**

Ibarat formulir pendaftaran. Formulir itu punya kolom-kolom:
- Nama: ...
- Umur: ...
- Alamat: ...

Di Kotlin, "formulir" itu disebut **data class**.

## 3.2 Contoh di PharmaTic

```kotlin
data class Obat(
    val id: Int = 0,
    val nama: String = "",
    val kategori: String = "",
    val harga: Int = 0,
    val stok: Int = 0
)
```

**Baca-nya:** "Ini cetakan untuk data Obat. Setiap Obat punya: id (angka), nama (teks), kategori (teks), harga (angka), stok (angka)."

## 3.3 Cara Pakai Model

```kotlin
// Buat satu data obat
val obat1 = Obat(1, "Paracetamol", "Tablet", 5000, 100)

// Ambil datanya
println(obat1.nama)     → "Paracetamol"
println(obat1.harga)    → 5000
```

## 3.4 Kenapa Perlu Model?

Supaya data terstruktur. Daripada kamu simpan data obat di 5 variabel terpisah:
```kotlin
// ❌ Berantakan
var namaObat1 = "Paracetamol"
var hargaObat1 = 5000
var namaObat2 = "Amoxicillin"
var hargaObat2 = 10000
```

Lebih baik pakai model:
```kotlin
// ✅ Rapi
val daftarObat = listOf(
    Obat(1, "Paracetamol", "Tablet", 5000, 100),
    Obat(2, "Amoxicillin", "Kapsul", 10000, 50)
)
```

## 3.5 Model di Project Kamu

| Model | Menyimpan apa? |
|-------|---------------|
| `Obat` | data obat (nama, harga, stok, dll) |
| `Kasir` | data kasir (nama, username, password) |
| `Suplier` | data supplier (nama, alamat, telepon) |
| `Transaksi` | data transaksi (tanggal, total, kasir) |
| `TransaksiDetail` | detail isi transaksi (obat apa saja yang dibeli) |

---

## Cek Pemahaman Modul 3

1. Apa itu data class / model?
2. Kenapa kita butuh model?
3. Sebutkan model-model yang ada di PharmaTic!

---
---

# Modul 4: Room Database

## 4.1 Apa itu Room Database?

**Room = cara simpan data di HP (seperti database mini di dalam app).**

Kalau kamu tutup app, buka lagi, datanya masih ada.
Data disimpan di dalam HP, bukan di server.

Ibarat: Room = buku catatan yang selalu ada di saku kamu.

## 4.2 Tiga Bagian Penting Room

### 1. Entity — "Tabel-nya"

Entity = struktur tabel. Kamu tentukan kolom-kolomnya apa saja.

```kotlin
@Entity(tableName = "obat")
data class ObatEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nama: String,
    val kategori: String,
    val harga: Int,
    val stok: Int
)
```

**Baca-nya:** "Buat tabel bernama 'obat' dengan kolom: id (otomatis naik), nama, kategori, harga, stok."

### 2. DAO — "Cara Akses Tabel-nya"

DAO = kumpulan perintah yang bisa kamu lakukan ke tabel.

```kotlin
@Dao
interface ObatDao {

    @Query("SELECT * FROM obat")
    fun ambilSemua(): LiveData<List<ObatEntity>>

    @Insert
    fun tambah(obat: ObatEntity)

    @Update
    fun ubah(obat: ObatEntity)

    @Delete
    fun hapus(obat: ObatEntity)
}
```

**Terjemahan:**
- `ambilSemua()` → ambil semua data di tabel obat
- `tambah()` → masukkan data baru
- `ubah()` → ubah data yang sudah ada
- `hapus()` → hapus data

Ini = **CRUD** (Create, Read, Update, Delete). Hampir semua fitur di app kamu pakai CRUD.

### 3. Database — "Penghubung Entity + DAO"

Database = tempat kumpulan tabel dan DAO-nya.

```kotlin
@Database(entities = [ObatEntity::class, KasirEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun obatDao(): ObatDao
    abstract fun kasirDao(): KasirDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "pharmatic_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
```

**Jangan hafal code-nya.** Pahami konsep-nya:
- `@Database` → daftar tabel apa saja yang ada
- `abstract fun obatDao()` → "saya mau akses tabel obat, mana DAO-nya?"
- `companion object` + `getInstance` → supaya database-nya cuma ada 1 (singleton), tidak dibuat ulang terus

## 4.3 Alur Lengkap Room

```
Kotlin Code (Activity/ViewModel)
    ↓ panggil
DAO (ambilSemua, tambah, ubah, hapus)
    ↓ eksekusi
Entity/Tabel (data tersimpan di HP)
```

---

## Cek Pemahaman Modul 4

1. Apa itu Room Database?
2. Jelaskan 3 bagian Room: Entity, DAO, Database!
3. Apa itu CRUD? Sebutkan 4 operasi CRUD!
4. Kenapa database pakai singleton (getInstance)?

---
---

# Modul 5: ViewModel + LiveData

## 5.1 Masalah yang Dipecahkan ViewModel

Tanpa ViewModel:
```
User putar HP (landscape) → Activity hancur → data hilang!
```

Dengan ViewModel:
```
User putar HP → Activity hancur → ViewModel tetap hidup → data aman!
```

**ViewModel = kotak penyimpanan data yang tetap hidup walau Activity hancur.**

## 5.2 Apa itu LiveData?

**LiveData = data yang bisa "dipantau". Kalau datanya berubah, UI otomatis ikut berubah.**

Ibarat kamu pasang CCTV di gudang:
- Gudang = database
- CCTV = LiveData
- Monitor = layar HP

Kalau ada barang masuk/keluar gudang (database berubah), monitor (layar) langsung tampil perubahan-nya. Kamu tidak perlu cek gudang manual.

## 5.3 Cara Kerja ViewModel + LiveData

```
Database (Room)
    ↓ data berubah
LiveData (di ViewModel)
    ↓ kirim notifikasi
Activity/Fragment (UI)
    ↓ tampilkan data terbaru
```

## 5.4 Bentuk Umum ViewModel

```kotlin
class ObatViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = AppDatabase.getInstance(application).obatDao()

    // LiveData = data yang bisa dipantau
    val semuaObat: LiveData<List<ObatEntity>> = dao.ambilSemua()

    fun tambah(nama: String, harga: Int, stok: Int) {
        val obatBaru = ObatEntity(nama = nama, harga = harga, stok = stok)
        viewModelScope.launch {
            dao.tambah(obatBaru)
        }
    }
}
```

**Konsep-nya:**
1. ViewModel ambil DAO dari database
2. `semuaObat` = LiveData yang isinya daftar obat. Kalau ada obat baru ditambah, LiveData otomatis "memberitahu" UI
3. `viewModelScope.launch` = "jalankan ini di background, jangan ganggu tampilan"

## 5.5 Cara Activity "Memantau" LiveData

```kotlin
// Di Activity
val viewModel = ViewModelProvider(this).get(ObatViewModel::class.java)

viewModel.semuaObat.observe(this) { daftarObat ->
    // Setiap kali data obat berubah, kode di sini jalan otomatis
    adapter.submitList(daftarObat)
}
```

**Baca-nya:** "Saya mau memantau `semuaObat`. Setiap kali datanya berubah, ambil daftar obat-nya, lalu kasih ke adapter untuk ditampilkan."

## 5.6 Kenapa Tidak Akses Database Langsung dari Activity?

Karena akses database = operasi berat. Kalau dilakukan di "thread utama" (yang mengurus tampilan), app akan **freeze/hang**.

ViewModel + `viewModelScope.launch` memastikan:
- Operasi berat → di background
- Tampilan → tetap lancar

---

## Cek Pemahaman Modul 5

1. Apa fungsi ViewModel?
2. Apa itu LiveData? Analogi-nya apa?
3. Kenapa tidak boleh akses database langsung dari Activity?
4. Apa fungsi `viewModelScope.launch`?

---
---

# Modul 6: RecyclerView + Adapter

## 6.1 Apa itu RecyclerView?

**RecyclerView = komponen untuk menampilkan daftar (list) yang bisa di-scroll.**

Contoh di app kamu:
- Daftar obat di KelolaObatActivity
- Daftar kasir di KelolaKasirActivity
- Daftar item di KeranjangActivity

Semua pakai RecyclerView.

## 6.2 Kenapa Tidak Pakai ScrollView Biasa?

Karena RecyclerView **hemat memori**.

Misal kamu punya 1000 data obat. ScrollView biasa = buat semua 1000 item sekaligus (berat!).
RecyclerView = cuma buat yang terlihat di layar saja. Yang lain "didaur ulang" (= recycle) saat di-scroll.

## 6.3 Tiga Bagian RecyclerView

### 1. Item Layout — "Satu kartu-nya seperti apa"

XML yang mengatur tampilan **satu item** saja.

```xml
<!-- item_obat.xml — tampilan SATU baris obat -->
<LinearLayout>
    <TextView android:id="@+id/tvNamaObat" />    <!-- nama obat -->
    <TextView android:id="@+id/tvHargaObat" />   <!-- harga obat -->
    <Button   android:id="@+id/btnHapus" />      <!-- tombol hapus -->
</LinearLayout>
```

### 2. ViewHolder — "Penampung sementara untuk satu item"

ViewHolder = wadah yang memegang tampilan satu baris.
Dia "pegang" semua komponen di item layout supaya tidak perlu cari-cari lagi.

```kotlin
class ObatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tvNama = view.findViewById<TextView>(R.id.tvNamaObat)
    val tvHarga = view.findViewById<TextView>(R.id.tvHargaObat)
    val btnHapus = view.findViewById<Button>(R.id.btnHapus)
}
```

### 3. Adapter — "Jembatan antara data dan tampilan"

Adapter = yang mengambil data, lalu "menempelkan"-nya ke ViewHolder.

```kotlin
class ObatAdapter(
    private val daftarObat: List<Obat>,
    private val onItemClick: (Obat) -> Unit
) : RecyclerView.Adapter<ObatViewHolder>() {

    // 1. Buat ViewHolder baru (saat perlu item baru)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ObatViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_obat, parent, false)
        return ObatViewHolder(view)
    }

    // 2. Isi data ke ViewHolder (sesuai posisi)
    override fun onBindViewHolder(holder: ObatViewHolder, position: Int) {
        val obat = daftarObat[position]
        holder.tvNama.text = obat.nama
        holder.tvHarga.text = "Rp ${obat.harga}"
        holder.btnHapus.setOnClickListener { onItemClick(obat) }
    }

    // 3. Beritahu berapa banyak item yang ditampilkan
    override fun getItemCount(): Int = daftarObat.size
}
```

## 6.4 Alur Lengkap RecyclerView

```
Data (List<Obat>)
    ↓
Adapter ambil data sesuai posisi
    ↓
Isi data ke ViewHolder (tvNama, tvHarga, dll)
    ↓
RecyclerView tampilkan ViewHolder di layar
    ↓
User scroll → ViewHolder yang tidak terlihat "didaur ulang" untuk data baru
```

## 6.5 Cara Memasang RecyclerView di Activity

```kotlin
// 1. Cari RecyclerView di layout
val recyclerView = findViewById<RecyclerView>(R.id.rvObat)

// 2. Atur layout manager (cara menyusun item)
recyclerView.layoutManager = LinearLayoutManager(this)

// 3. Pasang adapter
val adapter = ObatAdapter(daftarObat) { obat ->
    // Kalau item diklik, lakukan ini
    Toast.makeText(this, "Klik: ${obat.nama}", Toast.LENGTH_SHORT).show()
}
recyclerView.adapter = adapter
```

---

## Cek Pemahaman Modul 6

1. Apa itu RecyclerView? Kenapa tidak pakai ScrollView?
2. Jelaskan 3 bagian: Item Layout, ViewHolder, Adapter!
3. Apa yang dilakukan `onBindViewHolder`?
4. Apa yang dilakukan `onCreateViewHolder`?

---
---

# Modul 7: Retrofit (API Call)

## 7.1 Apa itu Retrofit?

**Retrofit = alat untuk komunikasi dengan server (API) dari Android.**

Ibarat kamu mau pesan makanan lewat GoFood:
- Kamu (app Android) → pesan lewat app → server restoran terima pesanan → kirim makanan balik

Retrofit = "kurir"-nya. Dia yang kirim permintaan ke server, dan bawa hasilnya balik.

## 7.2 Kenapa PharmaTic Pakai Retrofit?

Untuk **Midtrans** (payment gateway). Saat user bayar, app perlu "bicara" dengan server Midtrans untuk:
1. Buat transaksi → minta token pembayaran
2. User bayar → cek apakah pembayaran berhasil

## 7.3 Tiga Bagian Retrofit

### 1. Interface API — "Daftar perintah yang bisa dikirim ke server"

```kotlin
interface MidtransApi {
    @POST("v1/payment-methods")
    fun getPaymentToken(@Body request: MidtransRequest): Call<MidtransResponse>
}
```

**Baca-nya:** "Ada perintah bernama `getPaymentToken`. Kirim data request ke server, terima response dari server."

### 2. Request — "Surat yang dikirim ke server"

```kotlin
data class MidtransRequest(
    val amount: Int,
    val itemName: String
)
```

### 3. Response — "Balasan dari server"

```kotlin
data class MidtransResponse(
    val token: String,
    val status: String
)
```

## 7.4 Cara Setup Retrofit Client

```kotlin
object RetrofitClient {
    private const val BASE_URL = "https://api.midtrans.com/"

    val instance: MidtransApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MidtransApi::class.java)
    }
}
```

**Konsep:**
- `BASE_URL` = alamat server
- `GsonConverterFactory` = otomatis ubah JSON dari server ↔ data class Kotlin
- `lazy` = baru dibuat saat pertama kali dipakai (hemat)

## 7.5 Cara Memanggil API

```kotlin
RetrofitClient.instance.getPaymentToken(request)
    .enqueue(object : Callback<MidtransResponse> {
        override fun onResponse(call: Call<MidtransResponse>, response: Response<MidtransResponse>) {
            // Berhasil! Ambil token-nya
            val token = response.body()?.token
        }

        override fun onFailure(call: Call<MidtransResponse>, t: Throwable) {
            // Gagal (tidak ada internet, server down, dll)
            Toast.makeText(context, "Gagal koneksi", Toast.LENGTH_SHORT).show()
        }
    })
```

**`enqueue`** = kirim secara async (di background). Tidak blocking tampilan.

---

## Cek Pemahaman Modul 7

1. Apa itu Retrofit?
2. Apa itu API?
3. Jelaskan 3 bagian: Interface, Request, Response!
4. Kenapa pakai `enqueue` (bukan langsung)?

---
---

# Modul 8: Menghubungkan Semua Bagian

## 8.1 Arsitektur MVVM

PharmaTic pakai **MVVM** (Model - View - ViewModel). Ini cara data mengalir:

```
View (Activity + XML Layout)
    ↕ minta data / kirim aksi
ViewModel (simpan data + logic)
    ↕ ambil / simpan data
Model (Room Database / API)
```

**Kenapa dipisah?**
- Kalau UI butuh diubah → ubah XML saja, logic tidak tersentuh
- Kalau logic butuh diubah → ubah ViewModel saja, UI tidak tersentuh
- Masing-masing punya tugas sendiri

## 8.2 Contoh Lengkap: Fitur "Tambah Obat"

Mari kita telusuri alur lengkap dari tombol diklik sampai data tersimpan:

### Langkah 1: User klik tombol "Simpan" di Activity

```kotlin
btnSimpan.setOnClickListener {
    val nama = etNama.text.toString()
    val harga = etHarga.text.toString().toInt()
    val stok = etStok.text.toString().toInt()

    viewModel.tambahObat(nama, harga, stok)
}
```

### Langkah 2: ViewModel terima data, kirim ke DAO

```kotlin
fun tambahObat(nama: String, harga: Int, stok: Int) {
    viewModelScope.launch {
        val obat = ObatEntity(nama = nama, harga = harga, stok = stok)
        dao.tambah(obat)  // ← simpan ke database
    }
}
```

### Langkah 3: DAO simpan ke database (Room)

```kotlin
@Insert
fun tambah(obat: ObatEntity)  // ← data masuk ke tabel "obat"
```

### Langkah 4: LiveData mendeteksi perubahan, UI update otomatis

```kotlin
// Di Activity — otomatis jalan setelah data bertambah
viewModel.semuaObat.observe(this) { daftarObat ->
    adapter.submitList(daftarObat)  // ← RecyclerView update
}
```

### Alur Lengkap:

```
User klik "Simpan"
    → Activity ambil teks dari EditText
    → Activity panggil viewModel.tambahObat()
    → ViewModel buat ObatEntity
    → DAO simpan ke Room Database
    → LiveData detect perubahan
    → Activity terima data baru
    → Adapter update RecyclerView
    → Tampilan list obat terbaru muncul di layar
```

## 8.3 Peta Fitur PharmaTic

| Fitur | Activity | ViewModel | Database |
|-------|----------|-----------|----------|
| Login | LoginActivity | - | KasirDao (cek username & password) |
| Lihat daftar obat | KelolaObatActivity | ObatViewModel | ObatDao.ambilSemua() |
| Tambah obat | TambahObatActivity | ObatViewModel | ObatDao.tambah() |
| Edit obat | EditObatActivity | ObatViewModel | ObatDao.ubah() |
| Hapus obat | KelolaObatActivity | ObatViewModel | ObatDao.hapus() |
| Keranjang | KeranjangActivity | - | KeranjangManager |
| Bayar | PembayaranActivity | - | Midtrans API (Retrofit) |
| Struk | StrukActivity | - | TransaksiDao.tambah() |

## 8.4 Jawaban untuk Pertanyaan Dosen

**Q: "Apa fungsi app ini?"**
> PharmaTic adalah aplikasi kasir apotek berbasis Android. Fitur utamanya meliputi kelola data obat, kelola data kasir dan supplier, proses transaksi penjualan obat dengan keranjang belanja, integrasi pembayaran via Midtrans, serta pencetakan struk dan laporan transaksi.

**Q: "Mengapa pakai MVVM?"**
> MVVM memisahkan tampilan (View) dari logic bisnis (ViewModel) dan data (Model). Keuntungannya: kode lebih terstruktur, mudah di-maintain, dan ViewModel memastikan data tetap ada walau Activity dihancurkan sistem (misal saat rotasi layar).

**Q: "Mengapa pakai Room Database?"**
> Room digunakan untuk menyimpan data secara lokal di perangkat. Kelebihannya: data tetap ada walau app ditutup, mendukung operasi CRUD, dan terintegrasi dengan LiveData sehingga UI otomatis update saat data berubah.

**Q: "Bagaimana alur pembayaran?"**
> User memilih obat → masuk keranjang → klik bayar → app mengirim request ke server Midtrans via Retrofit untuk mendapat token pembayaran → user melakukan pembayaran → status pembayaran diverifikasi → struk ditampilkan.

**Q: "Jelaskan salah satu fitur secara detail!"**
(Pilih: Tambah Obat)
> User membuka halaman TambahObatActivity → mengisi nama, kategori, harga, dan stok → klik tombol Simpan → Activity mengirim data ke ObatViewModel → ViewModel membuat objek ObatEntity lalu menyimpannya ke database melalui ObatDao dengan operasi Insert → LiveData mendeteksi perubahan data → RecyclerView di halaman daftar obat otomatis menampilkan obat yang baru ditambahkan.

---

## Tips Presentasi

1. **Demo app langsung** — dosen lebih suka lihat app jalan
2. **Kuasai alur, bukan hafal code** — kalau diminta tunjukkan code, buka file-nya dan baca pelan-pelan
3. **Kalau tidak tahu**, jawab: "Bagian itu di-handle oleh ViewModel, saya perlu pelajari lebih dalam untuk detail implementasinya"
4. **Sebutkan teknologi**: Kotlin, Room, Retrofit, ViewModel + LiveData, MVVM, Midtrans
5. **Pahami minimal 1 fitur end-to-end** — supaya bisa dijelaskan dari awal sampai akhir

---

*Selamat presentasi! Kamu pasti bisa.* 🎯
