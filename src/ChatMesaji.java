// ChatMesaji.java
// Müşteri ve destek ekibi arasındaki mesajları temsil eder.
import java.time.LocalDateTime;

public class ChatMesaji {
    private String gonderen;
    private String alici;
    private String mesaj;
    private LocalDateTime zaman;
    private boolean okundu;

    public ChatMesaji(String gonderen, String alici, String mesaj) {
        this.gonderen = gonderen;
        this.alici = alici;
        this.mesaj = mesaj;
        this.zaman = LocalDateTime.now();
        this.okundu = false;
    }

    // Getter metodları
    public String getGonderen() { return gonderen; }
    public String getAlici() { return alici; }
    public String getMesaj() { return mesaj; }
    public LocalDateTime getZaman() { return zaman; }
    public boolean isOkundu() { return okundu; }

    // Mesajı okundu olarak işaretle
    public void mesajiOkundu() {
        this.okundu = true;
    }

    @Override
    public String toString() {
        return "[" + zaman + "] " + gonderen + ": " + mesaj;
    }
} 