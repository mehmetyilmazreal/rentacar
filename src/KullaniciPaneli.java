// KullaniciPaneli.java
// Kullanıcı işlemleri menüsü.
import java.util.Scanner;

public class KullaniciPaneli {
    private RentACarSystem sistem;
    private Customer musteri;
    private Scanner scanner;

    public KullaniciPaneli(RentACarSystem sistem, Customer musteri, Scanner scanner) {
        this.sistem = sistem;
        this.musteri = musteri;
        this.scanner = scanner;
    }

    public void paneliBaslat() {
        while (true) {
            System.out.println("\n--- Kullanıcı Paneli ---");
            System.out.println("1. Araçları Listele");
            System.out.println("2. Araç Kirala");
            System.out.println("3. Aktif Kiralamalarım");
            System.out.println("4. Profil Bilgilerim");
            System.out.println("5. Çıkış");
            System.out.print("Seçiminiz: ");
            String secim = scanner.nextLine();
            switch (secim) {
                case "1":
                    araclariListele();
                    break;
                case "2":
                    aracKirala();
                    break;
                case "3":
                    aktifKiralamalarim();
                    break;
                case "4":
                    profilBilgilerim();
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Geçersiz seçim!");
            }
        }
    }

    private void araclariListele() {
        System.out.println("\n--- Mevcut Araçlar ---");
        int i = 1;
        for (Car c : sistem.getArabalar()) {
            System.out.println(i + ". " +
                "Marka: " + c.getMarka() +
                ", Model: " + c.getModel() +
                ", Yıl: " + c.getYil() +
                ", Plaka: " + c.getPlaka() +
                ", Günlük Fiyat: " + c.getGunlukFiyat() + " TL" +
                ", Renk: " + c.getRenk() +
                ", Vites: " + c.getVites() +
                ", Yakıt: " + c.getYakit() +
                ", Kilometre: " + c.getKilometre() +
                ", Kış Lastiği: " + (c.isKisMit() ? "Var" : "Yok") +
                ", Bakım Gerekli: " + (c.isBakimGerekliMi() ? "Evet" : "Hayır")
            );
            i++;
        }
    }

    private void aracKirala() {
        araclariListele();
        System.out.print("Kiralamak istediğiniz aracın numarası: ");
        int secim = Integer.parseInt(scanner.nextLine());
        if (secim < 1 || secim > sistem.getArabalar().size()) {
            System.out.println("Geçersiz seçim!");
            return;
        }
        Car secili = sistem.getArabalar().get(secim - 1);
        System.out.print("Kaç gün kiralamak istiyorsunuz?: ");
        int gun = Integer.parseInt(scanner.nextLine());
        System.out.print("Ödeme yöntemi (Nakit/Kredi Kartı/Banka Kartı): ");
        String odemeYontemi = scanner.nextLine();
        double depozito = secili.getGunlukFiyat() * 0.2;
        double toplam = secili.getGunlukFiyat() * gun;
        sistem.arabaKirala(secili, musteri, java.time.LocalDate.now(), java.time.LocalDate.now().plusDays(gun), depozito, "", odemeYontemi);
        System.out.println("Kiralama başarılı! Toplam ücret: " + toplam + " TL, Depozito: " + depozito + " TL, Ödeme Yöntemi: " + odemeYontemi);
    }

    private void aktifKiralamalarim() {
        System.out.println("\n--- Aktif Kiralamalarım ---");
        int i = 1;
        for (Rental r : sistem.getKiralamalar()) {
            if (r.getMusteri().equals(musteri) && r.getDurum() == Durum.AKTIF) {
                System.out.println(i + ". " + r.getAraba().getMarka() + " " + r.getAraba().getModel() + " - " + r.getBaslangicTarihi() + " - " + r.getBitisTarihi());
                i++;
            }
        }
        if (i == 1) System.out.println("Aktif kiralamanız yok.");
    }

    private void profilBilgilerim() {
        System.out.println("\n--- Profil Bilgilerim ---");
        System.out.println("Ad: " + musteri.getAd());
        System.out.println("Soyad: " + musteri.getSoyad());
        System.out.println("TC: " + musteri.getTcKimlikNo());
        System.out.println("Telefon: " + musteri.getTelefon());
        System.out.println("Email: " + musteri.getEmail());
        System.out.println("Adres: " + musteri.getAdres());
        System.out.println("Ehliyet No: " + musteri.getEhliyetNo());
        System.out.println("Ehliyet Sınıfı: " + musteri.getEhliyetSinifi());
        System.out.println("Doğum Tarihi: " + musteri.getDogumTarihi());
        System.out.println("Toplam Harcama: " + musteri.getToplamHarcamasi());
        System.out.println("Sadık Müşteri: " + (musteri.isSadikMusteri() ? "Evet" : "Hayır"));
    }
} 