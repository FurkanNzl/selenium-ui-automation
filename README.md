# SauceDemo UI Test Otomasyonu

Selenium 4 + Java + TestNG + Maven ile yazilmis, **Page Object Model (POM)** mimarisine uygun ornek bir UI test otomasyon projesi. Hedef site: [SauceDemo (Swag Labs)](https://www.saucedemo.com).

## Bu projede ne var?

- **Page Object Model** mimarisi (her sayfa ayri bir sinif)
- **Explicit wait** kullanimi (`Thread.sleep` YOK -> daha hizli ve guvenilir testler)
- **Data-driven testing** (TestNG `@DataProvider` ile ayni testi farkli verilerle kosma)
- **Config dosyasi** ile ayarlarin (URL, tarayici, bekleme suresi) koddan ayrilmasi
- **ThreadLocal WebDriver** (ileride paralel calistirmaya hazir altyapi)
- Uctan uca (E2E) satin alma senaryosu

## Gereksinimler

| Arac   | Surum |
|--------|-------|
| JDK    | 17 veya uzeri |
| Maven  | 3.8+ (IntelliJ ile birlikte gelir) |
| Chrome | Guncel bir surum |
| IDE    | IntelliJ IDEA (onerilir) |

> **Driver indirmene GEREK YOK!** Selenium 4.6+ ile gelen **Selenium Manager**, tarayicina uygun chromedriver'i otomatik indirir. Sadece Chrome kurulu olsun yeter.

## Kurulum ve Calistirma

### 1. Projeyi ac
Zip'i cikar -> IntelliJ'de **File > Open** -> proje klasorunu sec. IntelliJ, Maven bagimliliklarini (Selenium, TestNG) otomatik indirir. Ilk acilista biraz surebilir.

### 2. Testleri calistir

**Yol A - Terminalden (tum suite):**
```
mvn test
```

**Yol B - IntelliJ'den:**
- `testng.xml` dosyasina sag tikla -> **Run**
- veya tek bir test sinifina/metoduna sag tikla -> **Run**

### 3. Raporu gor
Calistirma sonrasi TestNG raporu surada olusur:
```
test-output/index.html
```

## Proje Yapisi

```
selenium-ui-automation/
├── pom.xml                      -> Maven yapilandirmasi + bagimliliklar
├── testng.xml                   -> Hangi testlerin calisacagini tanimlayan suite
└── src/
    ├── main/java/com/furkan/saucedemo/
    │   ├── config/
    │   │   └── ConfigReader.java     -> config.properties'i okur
    │   ├── driver/
    │   │   └── DriverFactory.java    -> WebDriver'i olusturur/kapatir
    │   ├── base/
    │   │   └── BasePage.java         -> Ortak metotlar (click, type, wait...)
    │   └── pages/                    -> Her sayfa = bir Page Object
    │       ├── LoginPage.java
    │       ├── ProductsPage.java
    │       ├── CartPage.java
    │       ├── CheckoutInfoPage.java
    │       ├── CheckoutOverviewPage.java
    │       └── CheckoutCompletePage.java
    └── test/
        ├── java/com/furkan/saucedemo/
        │   ├── base/
        │   │   └── BaseTest.java     -> Kurulum/yikim (@BeforeMethod / @AfterMethod)
        │   └── tests/
        │       ├── LoginTest.java          -> Login senaryolari (data-driven)
        │       ├── ProductsCartTest.java   -> Urun & sepet senaryolari
        │       └── CheckoutE2ETest.java    -> Uctan uca satin alma
        └── resources/
            └── config.properties    -> URL, tarayici, bekleme suresi
```

## Mimari Neden Boyle? (Katmanlar)

**1. Page Object Model (pages/)**
Her web sayfasi icin ayri bir sinif. Sayfanin locator'lari ve o sayfada yapilabilecek islemler (login yap, sepete ekle...) tek yerde toplanir. Site degisirse sadece ilgili Page Object'i guncellersin, onlarca testi tek tek duzeltmezsin.

**2. BasePage**
Tum sayfalarin ortak ihtiyaclari (tikla, yaz, bekle) burada. Tekrari onler (DRY).

**3. DriverFactory**
Tarayici acma/kapama mantigi testlerden ayri. `ThreadLocal` sayesinde paralel calistirmaya hazir.

**4. ConfigReader + config.properties**
Sabit degerleri (URL vs.) koddan ayirir. Farkli ortam/tarayici icin kodu degistirmezsin, sadece properties'i.

**5. BaseTest**
Her testten once tarayici acilir, sonra kapanir. Testler birbirinden bagimsiz ve temiz calisir.

## Config Ayarlari

`src/test/resources/config.properties`:

| Ayar | Aciklama | Ornek |
|------|----------|-------|
| `base.url` | Test edilecek site | `https://www.saucedemo.com` |
| `browser` | Tarayici | `chrome` / `firefox` |
| `headless` | Arayuzsuz calistir | `true` / `false` |
| `explicit.wait.seconds` | Bekleme suresi (sn) | `10` |

## SauceDemo Test Kullanicilari

| Kullanici | Sifre | Not |
|-----------|-------|-----|
| `standard_user` | `secret_sauce` | Normal kullanici |
| `locked_out_user` | `secret_sauce` | Girisi engelli (hata verir) |
| `problem_user` | `secret_sauce` | Bilerek bozuk davranislar |
| `performance_glitch_user` | `secret_sauce` | Yavas yuklenir |
| `error_user` / `visual_user` | `secret_sauce` | Ileri seviye hatali senaryolar |
