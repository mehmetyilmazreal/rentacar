// DestekSohbeti.java
// Müşteri ve destek ekibi arasındaki sohbet sistemini yönetir.
import java.util.ArrayList;
import java.util.List;

public class DestekSohbeti {
    private String musteriId;
    private String destekUyesiId;
    private List<ChatMesaji> mesajlar;
    private boolean aktif;

    public DestekSohbeti(String musteriId, String destekUyesiId) {
        this.musteriId = musteriId;
        this.destekUyesiId = destekUyesiId;
        this.mesajlar = new ArrayList<>();
        this.aktif = true;
    }

    // Yeni mesaj gönder
    public void mesajGonder(String gonderenId, String mesaj) {
        String aliciId = gonderenId.equals(musteriId) ? destekUyesiId : musteriId;
        ChatMesaji yeniMesaj = new ChatMesaji(gonderenId, aliciId, mesaj);
        mesajlar.add(yeniMesaj);
    }

    // Okunmamış mesajları getir
    public List<ChatMesaji> getOkunmamisMesajlar(String kullaniciId) {
        List<ChatMesaji> okunmamisMesajlar = new ArrayList<>();
        for (ChatMesaji mesaj : mesajlar) {
            if (mesaj.getAlici().equals(kullaniciId) && !mesaj.isOkundu()) {
                okunmamisMesajlar.add(mesaj);
                mesaj.mesajiOkundu();
            }
        }
        return okunmamisMesajlar;
    }

    // Tüm mesajları getir
    public List<ChatMesaji> getMesajGecmisi() {
        return new ArrayList<>(mesajlar);
    }

    // Sohbeti sonlandır
    public void sohbetiSonlandir() {
        this.aktif = false;
    }

    // Sohbet durumunu kontrol et
    public boolean isAktif() {
        return aktif;
    }

    // Getter metodları
    public String getMusteriId() {
        return musteriId;
    }

    public String getDestekUyesiId() {
        return destekUyesiId;
    }
} 