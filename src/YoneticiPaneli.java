// YoneticiPaneli.java
// Yönetici işlemleri menüsü.
import java.util.Scanner;

public class YoneticiPaneli {
    private RentACarSystem sistem;
    private Scanner scanner;

    public YoneticiPaneli(RentACarSystem sistem, Scanner scanner) {
        this.sistem = sistem;
        this.scanner = scanner;
    }

    public void paneliBaslat() {
        while (true) {
            System.out.println("\n--- Yönetici Paneli ---");
            System.out.println("1. Araç Ekle");
            System.out.println("2. Araçları Listele");
            System.out.println("3. Müşterileri Listele");
            System.out.println("4. Araç Sil");
            System.out.println("5. Çıkış");
            System.out.print("Seçiminiz: ");
            String secim = scanner.nextLine();
            switch (secim) {
                case "1":
                    aracEkle();
                    break;
                case "2":
                    araclariListele();
                    break;
                case "3":
                    musterileriListele();
                    break;
                case "4":
                    arabaSil();
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Geçersiz seçim!");
            }
        }
    }

    private void aracEkle() {
        System.out.print("Marka: ");
        String marka = scanner.nextLine();
        System.out.print("Model: ");
        String model = scanner.nextLine();
        System.out.print("Yıl: ");
        int yil = Integer.parseInt(scanner.nextLine());
        System.out.print("Plaka: ");
        String plaka = scanner.nextLine();
        System.out.print("Günlük Fiyat: ");
        double fiyat = Double.parseDouble(scanner.nextLine());
        System.out.print("Renk: ");
        String renk = scanner.nextLine();
        System.out.print("Vites: ");
        String vites = scanner.nextLine();
        System.out.print("Yakıt: ");
        String yakit = scanner.nextLine();
        System.out.print("Kilometre: ");
        int km = Integer.parseInt(scanner.nextLine());
        System.out.print("Kış Lastiği Var mı? (true/false): ");
        boolean kisMit = Boolean.parseBoolean(scanner.nextLine());
        System.out.print("Bakım Gerekli mi? (true/false): ");
        boolean bakim = Boolean.parseBoolean(scanner.nextLine());
        Car yeni = new Car(marka, model, yil, plaka, fiyat, renk, vites, yakit, km, kisMit, bakim);
        sistem.arabaEkle(yeni);
        System.out.println("Araç başarıyla eklendi!");
    }

    private void araclariListele() {
        System.out.println("\n--- Araçlar ---");
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

    private void musterileriListele() {
        System.out.println("\n--- Müşteriler ---");
        int i = 1;
        for (Customer c : sistem.getMusteriler()) {
            System.out.println(i + ". " + c.getAd() + " " + c.getSoyad() + " - TC: " + c.getTcKimlikNo() + " - Tel: " + c.getTelefon());
            i++;
        }
    }

    private void arabaSil() {
        araclariListele();
        System.out.print("Silmek istediğiniz aracın numarasını girin: ");
        int secim = Integer.parseInt(scanner.nextLine());
        if (secim < 1 || secim > sistem.getArabalar().size()) {
            System.out.println("Geçersiz seçim!");
            return;
        }
        Car silinecek = sistem.getArabalar().get(secim - 1);
        sistem.arabaSil(silinecek);
        System.out.println("Araç başarıyla silindi!");
    }
} 