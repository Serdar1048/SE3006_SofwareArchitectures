# LAB 02 - Modular Monolith Architecture Raporu

## 📋 Proje Özeti
Lab 02, Java'da Modular Monolith mimarisini uygulamaya dönük bir laboratuvar çalışmasıdır. Sistem, bilgilerin gizlenmesi (Information Hiding) ve modüller arası haberleşme yapısı üzerinde odaklanmaktadır.

---

## ✅ Tamamlanan Görevler

### **TASK 1: Catalog Module İç Mantığı**
- ✅ `ProductRepository.findById(Long id)` - Ürünü ID'ye göre döndürür
- ✅ `ProductRepository.save(Product product)` - Ürünü veritabanına kaydeder
- ✅ `CatalogServiceImpl` - ProductRepository'yi constructor injection ile bağladı
- ✅ `checkAndReduceStock(Long productId, int quantity)` - Stok kontrol ve azaltma işlemini gerçekleştirir

### **TASK 2: Catalog Module Factory**
- ✅ `CatalogFactory.create()` - ProductRepository ve CatalogServiceImpl'i örnekleyin
- ✅ Sadece `CatalogService` arayüzünü dışa aktarır (encapsulation)

### **TASK 3: Orders Module Mantığı**
- ✅ `OrderService` - CatalogService ve OrderRepository dependency'lerini alır
- ✅ `placeOrder(Long productId, int quantity)` - Sipariş oluşturmadan önce katalog servisini çağırır
- ✅ `OrderController.handleUserRequest()` - Try-catch bloğu ile hata yönetimi
- ✅ Başarılı işlem için: `✅ Order Confirmed`
- ✅ Hata durumunda: `❌ ERROR: [hata mesajı]`

### **TASK 4: Orders Module Factory**
- ✅ `OrdersFactory.create(CatalogService catalogService)` - Orders modülünü bir bütün olarak kurar
- ✅ CatalogService parametresini alır ve Orders kontekstini oluşturur

### **TASK 5: Main Bootstrapping**
- ✅ Catalog modülü factory ile oluşturuldu
- ✅ Orders modülü factory ile oluşturuldu (Catalog modülü dependency olarak geçildi)
- ✅ 4 test senaryosu başarıyla çalıştırıldı

---

## 🏛️ Mimari Prensipleri Uygulanması

### **1. Information Hiding (Bilgilerin Gizlenmesi)**
```
✅ CatalogServiceImpl   → package-private (dışarıdan erişilemez)
✅ ProductRepository   → package-private (dışarıdan erişilemez)
✅ Product             → package-private (dışarıdan erişilemez)
✅ CatalogService      → public interface (sadece bu arayüz dışarı açık)
✅ CatalogFactory      → public (modül oluşturucusu)
```

### **2. Modüler Sınırlar (Module Boundaries)**
```
CATALOG MODÜLÜ:
├── CatalogService (public interface)
├── CatalogFactory (public)
└── Internal components (product, repository, service implementation) - package-private

ORDERS MODÜLÜ:
├── OrderController (public entry point)
├── OrdersFactory (public)
└── Internal components (order, repository, service) - package-private
```

### **3. Modüller Arası Haberleşme**
- Orders modülü, Catalog modülünün **sadece** `CatalogService` arayüzüne erişebilir
- Orders modülü ProductRepository'yi göremez (erişemez)
- Yalnızca `catalogService.checkAndReduceStock()` çağrı yapabilir

---

## 🧪 Test Sonuçları

```
🚀 System Starting in Modular Monolith Mode...
----------------------------------------------

--- Test Scenarios ---
>>> New Request: Product ID=1, Quantity=2
✅ Order Confirmed

>>> New Request: Product ID=2, Quantity=5
✅ Order Confirmed

>>> New Request: Product ID=1, Quantity=10
❌ ERROR: Stok yetersiz! Mevcut: 3

>>> New Request: Product ID=999, Quantity=1
❌ ERROR: Ürün bulunamadı: 999
```

### Test Senaryoları Açıklaması:
1. **Başarılı Sipariş 1**: MacBook Pro'dan 2 adet sipariş (5'ten 2 alındı, 3 kaldı)
2. **Başarılı Sipariş 2**: Logitech Mouse'dan 5 adet sipariş (20'den 5 alındı, 15 kaldı)
3. **Hata: Yetersiz Stok**: MacBook Pro'dan 10 adet sipariş istendi, ancak sadece 3 kaldı
4. **Hata: Ürün Bulunamadı**: Var olmayan ürün ID (999) ile sipariş oluşturmaya çalışıldı

---

## 📊 Mimari Diagram

```
┌─────────────────────────────────────────────────────────┐
│                        MODULAR MONOLITH                  │
├─────────────────────────────────────────────────────────┤
│                                                           │
│  ┌──────────────── CATALOG MODULE ──────────────┐        │
│  │                                               │        │
│  │  ┌─────────────────────────────────┐         │        │
│  │  │   CatalogService (Interface)    │         │        │
│  │  │   └─ checkAndReduceStock()      │         │        │
│  │  └─────────────────────────────────┘         │        │
│  │            ▲                                   │        │
│  │            │                                   │        │
│  │  ┌─────────┴──────────────────────┐           │        │
│  │  │ CatalogServiceImpl              │           │        │
│  │  ├─ ProductRepository (injected)  │           │        │
│  │  └─────────────────────────────────┘           │        │
│  │            │                                   │        │
│  │            ▼                                   │        │
│  │  ┌──────────────────────────────┐             │        │
│  │  │ ProductRepository            │             │        │
│  │  ├─ findById()                  │             │        │
│  │  ├─ save()                      │             │        │
│  │  └──────────────────────────────┘             │        │
│  │            │                                   │        │
│  │            ▼                                   │        │
│  │  ┌──────────────────────────────┐             │        │
│  │  │ Product Database             │             │        │
│  │  └──────────────────────────────┘             │        │
│  │                                               │        │
│  └───────────────────────────────────────────────┘        │
│                     ▲                                      │
│                     │ (CatalogService interface)           │
│                     │                                      │
│  ┌──────────────── ORDERS MODULE ───────────────┐        │
│  │                                               │        │
│  │  ┌──────────────────────────────────┐        │        │
│  │  │  OrderController (public)        │        │        │
│  │  │  └─ handleUserRequest()          │        │        │
│  │  └──────────────────────────────────┘        │        │
│  │            │                                  │        │
│  │            ▼                                  │        │
│  │  ┌──────────────────────────────────┐        │        │
│  │  │ OrderService                     │        │        │
│  │  ├─ CatalogService (injected)      │        │        │
│  │  ├─ OrderRepository (injected)     │        │        │
│  │  └─ placeOrder()                   │        │        │
│  │  └──────────────────────────────────┘        │        │
│  │            │                                  │        │
│  │            ▼                                  │        │
│  │  ┌──────────────────────────────────┐        │        │
│  │  │ OrderRepository                  │        │        │
│  │  ├─ save()                          │        │        │
│  │  └──────────────────────────────────┘        │        │
│  │            │                                  │        │
│  │            ▼                                  │        │
│  │  ┌──────────────────────────────────┐        │        │
│  │  │ Order Database                   │        │        │
│  │  └──────────────────────────────────┘        │        │
│  │                                               │        │
│  └───────────────────────────────────────────────┘        │
│                                                           │
└─────────────────────────────────────────────────────────┘
```

---

## 💡 Öğrenilen Dersler

1. **Package-Private Access Modifier**: Modülleri gizli tutmak için etkili bir araçtır
2. **Factory Pattern**: Karmaşık object oluşturmayı encapsulate eder
3. **Dependency Injection**: Bağımlılıkları gevşek bağlamak (loose coupling) sağlar
4. **Interface-Based Design**: Modüller arasında kontrat belirtir
5. **Modular Architecture**: Kodun bakımını ve test edilebilirliğini artırır

---

## 🔗 Dosyalar ve Değişiklikler

| Dosya | Durum | Yapılan İşlem |
|-------|-------|---------------|
| `ProductRepository.java` | ✅ Tamamlandı | findById(), save() implemente edildi |
| `CatalogServiceImpl.java` | ✅ Tamamlandı | Dependency injection, checkAndReduceStock() |
| `CatalogFactory.java` | ✅ Tamamlandı | create() metodu implemente edildi |
| `OrderService.java` | ✅ Tamamlandı | Dependency injection, placeOrder() |
| `OrderController.java` | ✅ Tamamlandı | Dependency injection, try-catch handling |
| `OrdersFactory.java` | ✅ Tamamlandı | create() metodu implemente edildi |
| `Main.java` | ✅ Tamamlandı | Factory bootstrapping ve test senaryoları |

---

## ✨ Sonuç

Modular Monolith mimarisi başarıyla uygulanmıştır. Sistem:
- ✅ Modüller arası iletişimi **arayüzler** üzerinden sağlıyor
- ✅ İç detayları gizliyor (package-private)
- ✅ Tight coupling'i ortadan kaldırıyor
- ✅ Ölçeklenebilir ve bakımlanabilir yapıda
- ✅ Tüm test senaryoları başarıyla çalışıyor

---

**Lab Tamamlama Tarihi**: Nisan 20, 2026

**Durum**: ✅ BAŞARILI
