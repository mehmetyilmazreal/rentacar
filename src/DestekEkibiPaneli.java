// DestekEkibiPaneli.java
// Destek ekibi üyelerinin müşteri mesajlarını yönetmesini sağlar.
import java.util.Scanner;
import java.util.List;

public class DestekEkibiPaneli {
    private RentACarSystem sistem;
    private DestekEkibi destekUyesi;
    private Scanner scanner;

    public DestekEkibiPaneli(RentACarSystem sistem, DestekEkibi destekUyesi, Scanner scanner) {
        this.sistem = sistem;
        this.destekUyesi = destekUyesi;
        this.scanner = scanner;
    }

    public void paneliBaslat() {
        while (true) {
            System.out.println("\n=== Destek Ekibi Paneli ===");
            System.out.println("1. Bekleyen Mesajları Görüntüle");
            System.out.println("2. Aktif Sohbetleri Görüntüle");
            System.out.println("3. Sohbet Geçmişi");
            System.out.println("4. Yeni Destek Ekibi Üyesi Ekle");
            System.out.println("5. Çıkış");
            System.out.print("Seçiminiz: ");
            String secim = scanner.nextLine();
            
            switch (secim) {
                case "1":
                    bekleyenMesajlariGoruntule();
                    break;
                case "2":
                    aktifSohbetleriGoruntule();
                    break;
                case "3":
                    sohbetGecmisi();
                    break;
                case "4":
                    destekEkibiUyesiEkle();
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Geçersiz seçim!");
            }
        }
    }

    private void bekleyenMesajlariGoruntule() {
        System.out.println("\n=== Bekleyen Mesajlar ===");
        List<DestekSohbeti> aktifSohbetler = destekUyesi.getAktifSohbetler();
        
        if (aktifSohbetler.isEmpty()) {
            System.out.println("Bekleyen mesaj bulunmamaktadır.");
            return;
        }

        for (DestekSohbeti sohbet : aktifSohbetler) {
            List<ChatMesaji> okunmamisMesajlar = sohbet.getOkunmamisMesajlar(destekUyesi.getId());
            if (!okunmamisMesajlar.isEmpty()) {
                System.out.println("\nMüşteri: " + sohbet.getMusteriId());
                System.out.println("Okunmamış Mesajlar:");
                for (ChatMesaji mesaj : okunmamisMesajlar) {
                    System.out.println(mesaj);
                }
                
                System.out.print("\nMesajı yanıtlamak ister misiniz? (E/H): ");
                String yanit = scanner.nextLine();
                if (yanit.equalsIgnoreCase("E")) {
                    mesajYanitla(sohbet);
                }
            }
        }
    }

    private void aktifSohbetleriGoruntule() {
        System.out.println("\n=== Aktif Sohbetler ===");
        List<DestekSohbeti> aktifSohbetler = destekUyesi.getAktifSohbetler();
        
        if (aktifSohbetler.isEmpty()) {
            System.out.println("Aktif sohbet bulunmamaktadır.");
            return;
        }

        for (int i = 0; i < aktifSohbetler.size(); i++) {
            DestekSohbeti sohbet = aktifSohbetler.get(i);
            System.out.println((i + 1) + ". Müşteri: " + sohbet.getMusteriId());
        }

        System.out.print("\nGörüntülemek istediğiniz sohbetin numarasını girin (0: İptal): ");
        int secim = Integer.parseInt(scanner.nextLine());
        
        if (secim == 0) return;
        if (secim < 1 || secim > aktifSohbetler.size()) {
            System.out.println("Geçersiz seçim!");
            return;
        }

        DestekSohbeti secilenSohbet = aktifSohbetler.get(secim - 1);
        sohbetiGoruntule(secilenSohbet);
    }

    private void sohbetGecmisi() {
        System.out.println("\n=== Sohbet Geçmişi ===");
        List<DestekSohbeti> tumSohbetler = destekUyesi.getAktifSohbetler();
        
        if (tumSohbetler.isEmpty()) {
            System.out.println("Sohbet geçmişi bulunmamaktadır.");
            return;
        }

        for (int i = 0; i < tumSohbetler.size(); i++) {
            DestekSohbeti sohbet = tumSohbetler.get(i);
            System.out.println((i + 1) + ". Müşteri: " + sohbet.getMusteriId() + 
                             " - Durum: " + (sohbet.isAktif() ? "Aktif" : "Tamamlandı"));
        }

        System.out.print("\nGörüntülemek istediğiniz sohbetin numarasını girin (0: İptal): ");
        int secim = Integer.parseInt(scanner.nextLine());
        
        if (secim == 0) return;
        if (secim < 1 || secim > tumSohbetler.size()) {
            System.out.println("Geçersiz seçim!");
            return;
        }

        DestekSohbeti secilenSohbet = tumSohbetler.get(secim - 1);
        sohbetiGoruntule(secilenSohbet);
    }

    private void sohbetiGoruntule(DestekSohbeti sohbet) {
        System.out.println("\n=== Sohbet Detayları ===");
        System.out.println("Müşteri: " + sohbet.getMusteriId());
        System.out.println("\nMesaj Geçmişi:");
        
        List<ChatMesaji> mesajGecmisi = sohbet.getMesajGecmisi();
        for (ChatMesaji mesaj : mesajGecmisi) {
            String gonderen = mesaj.getGonderen().equals(destekUyesi.getId()) ? "Siz" : "Müşteri";
            System.out.println(gonderen + ": " + mesaj.getMesaj());
        }

        while (true) {
            System.out.println("\n1. Mesaj Gönder");
            System.out.println("2. Sohbeti Sonlandır");
            System.out.println("0. Geri Dön");
            System.out.print("Seçiminiz: ");
            String secim = scanner.nextLine();

            switch (secim) {
                case "1":
                    mesajYanitla(sohbet);
                    break;
                case "2":
                    sohbetiSonlandir(sohbet);
                    return;
                case "0":
                    return;
                default:
                    System.out.println("Geçersiz seçim!");
            }
        }
    }

    private void mesajYanitla(DestekSohbeti sohbet) {
        System.out.print("\nMesajınızı yazın (çıkmak için 'q' yazın): ");
        String mesaj = scanner.nextLine();
        
        if (mesaj.equalsIgnoreCase("q")) {
            return;
        }

        sohbet.mesajGonder(destekUyesi.getId(), mesaj);
        System.out.println("Mesajınız gönderildi.");
    }

    private void sohbetiSonlandir(DestekSohbeti sohbet) {
        System.out.print("Sohbeti sonlandırmak istediğinizden emin misiniz? (E/H): ");
        String onay = scanner.nextLine();
        
        if (onay.equalsIgnoreCase("E")) {
            destekUyesi.sohbetiSonlandir(sohbet.getMusteriId());
            System.out.println("Sohbet sonlandırıldı.");
        }
    }

    private void destekEkibiUyesiEkle() {
        System.out.println("\n=== Yeni Destek Ekibi Üyesi Ekle ===");
        System.out.print("ID: ");
        String id = scanner.nextLine();
        System.out.print("Ad: ");
        String ad = scanner.nextLine();
        System.out.print("Soyad: ");
        String soyad = scanner.nextLine();
        System.out.print("Telefon: ");
        String telefon = scanner.nextLine();
        System.out.print("E-posta: ");
        String email = scanner.nextLine();
        System.out.print("Departman: ");
        String departman = scanner.nextLine();
        System.out.print("Şifre: ");
        String sifre = scanner.nextLine();
        
        DestekEkibi yeniUye = new DestekEkibi(id, ad, soyad, telefon, email, departman, sifre);
        sistem.destekEkibiUyesiEkle(yeniUye);
        System.out.println("Yeni destek ekibi üyesi başarıyla eklendi!");
    }
} 