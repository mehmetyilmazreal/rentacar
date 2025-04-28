// Car.java
// Araç bilgilerini tutar ve araçla ilgili işlemleri içerir.
public class Car {
    private String marka;
    private String model;
    private int yil;
    private String plaka;
    private double gunlukFiyat;
    private String renk;
    private String vites;
    private String yakit;
    private int kilometre;
    private boolean kisMit;
    private boolean bakimGerekliMi;
    private boolean mevcut;

    // Yapıcı metod
    public Car(String marka, String model, int yil, String plaka, double gunlukFiyat, String renk, String vites, String yakit, int kilometre, boolean kisMit, boolean bakimGerekliMi) {
        this.marka = marka;
        this.model = model;
        this.yil = yil;
        this.plaka = plaka;
        this.gunlukFiyat = gunlukFiyat;
        this.renk = renk;
        this.vites = vites;
        this.yakit = yakit;
        this.kilometre = kilometre;
        this.kisMit = kisMit;
        this.bakimGerekliMi = bakimGerekliMi;
        this.mevcut = true; // Varsayılan olarak araç müsait
    }

    // Getter ve setter metodları
    public String getMarka() { return marka; }
    public String getModel() { return model; }
    public int getYil() { return yil; }
    public String getPlaka() { return plaka; }
    public double getGunlukFiyat() { return gunlukFiyat; }
    public String getRenk() { return renk; }
    public String getVites() { return vites; }
    public String getYakit() { return yakit; }
    public int getKilometre() { return kilometre; }
    public boolean isKisMit() { return kisMit; }
    public boolean isBakimGerekliMi() { return bakimGerekliMi; }
    public boolean isMevcut() { return mevcut; }
    public void setMevcut(boolean mevcut) { this.mevcut = mevcut; }

    // Bakım gerekliliğini günceller
    public void setBakimGerekliMi(boolean bakimGerekliMi) {
        this.bakimGerekliMi = bakimGerekliMi;
    }

    // Kilometreyi günceller
    public void kilometreEkle(int km) {
        this.kilometre += km;
    }

    // Aracı string olarak döndürür (dosya kaydı için)
    public String toString() {
        return marka + "," + model + "," + yil + "," + plaka + "," + gunlukFiyat + "," + renk + "," + vites + "," + yakit + "," + kilometre + "," + kisMit + "," + bakimGerekliMi;
    }
} 