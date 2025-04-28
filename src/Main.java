// Main.java
// Programın başlangıç noktasıdır.
public class Main {
    public static void main(String[] args) {
        RentACarSystem sistem = new RentACarSystem();
        GirisSayfasi giris = new GirisSayfasi(sistem);
        giris.sayfayiBaslat();
    }
}
