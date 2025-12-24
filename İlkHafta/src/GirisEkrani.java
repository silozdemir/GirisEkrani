   import javax.swing.*;
import java.awt.*;
import java.io.*;

public class GirisEkrani {
    public static void main(String[] args) {
        JFrame jf = new JFrame("Stok Bilgisi Giriş Sistemi");
        jf.setSize(400, 220);
        jf.setLocationRelativeTo(null);
        jf.setLayout(new GridLayout(3, 2, 10, 10));

        JTextField textKullanici = new JTextField();
        JPasswordField textSifre = new JPasswordField();
        JButton butonGiris = new JButton("Giriş");
        JButton butonKaydol = new JButton("Kaydol");

        jf.add(new JLabel(" Kullanıcı Adı:")); 
        jf.add(textKullanici);
        jf.add(new JLabel(" Şifre:")); 
        jf.add(textSifre);
        jf.add(butonGiris); 
        jf.add(butonKaydol);

  
        butonKaydol.addActionListener(e -> {
            JFrame kayit = new JFrame("Kayıt");
            kayit.setSize(300, 150);
            kayit.setLocationRelativeTo(null);
            kayit.setLayout(new GridLayout(3, 2, 5, 5));
            
            JTextField yeniAd = new JTextField();
            JPasswordField yeniSifre = new JPasswordField();
            JButton btnKaydet = new JButton("Kaydet");
            
            kayit.add(new JLabel(" Kullanıcı:")); 
            kayit.add(yeniAd);
            kayit.add(new JLabel(" Şifre:")); 
            kayit.add(yeniSifre);
            kayit.add(new JLabel("")); 
            kayit.add(btnKaydet);
            
            btnKaydet.addActionListener(ev -> {
                String ad = yeniAd.getText();
                String sifre = new String(yeniSifre.getPassword());
                if (!ad.isEmpty() && !sifre.isEmpty()) {
                    kullaniciKaydet(new User(ad, sifre));
                    JOptionPane.showMessageDialog(null, "Kayıt başarılı!");
                    kayit.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Lütfen tüm alanları doldurun!");
                }
            });
            
            kayit.setVisible(true);
        });

        
        butonGiris.addActionListener(e -> {
            String ad = textKullanici.getText();
            String sifre = new String(textSifre.getPassword());
            if (!ad.isEmpty() && !sifre.isEmpty()) {
                if (girisKontrol(ad, sifre)) {
                    JOptionPane.showMessageDialog(null, "Hoşgeldiniz!");
                } else {
                    JOptionPane.showMessageDialog(null, "Hatalı Giriş!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Kullanıcı adı ve şifre boş olamaz!");
            }
        });

        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);
    }

    public static void kullaniciKaydet(User user) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("user.txt", true))) {
            bw.write(user.kullaniciAdi + "," + user.sifre);
            bw.newLine();
        } catch (IOException e) { 
            e.printStackTrace(); 
        }
    }

    public static boolean girisKontrol(String ad, String sifre) {
        try (BufferedReader br = new BufferedReader(new FileReader("user.txt"))) {
            String satir;
            while ((satir = br.readLine()) != null) {
                String[] v = satir.split(",");
                if (v.length == 2 && v[0].equals(ad) && v[1].equals(sifre)) {
                    return true;
                }
            }
        } catch (IOException e) { }
        return false;
    }
}

class User {
    String kullaniciAdi;
    String sifre;

    public User(String ad, String sifre) {
        this.kullaniciAdi = ad;
        this.sifre = sifre;
    }
}
