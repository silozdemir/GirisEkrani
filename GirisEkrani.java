import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class GirisEkrani {
    public static void main(String[] args) {
        JFrame jf = new JFrame();
        jf.setTitle("Giriş Ekranı");
        jf.setSize(400, 220);
        jf.setLocation(400, 200);
        jf.getContentPane().setLayout(new GridLayout(3, 2, 10, 10));

        JLabel labelKullanici = new JLabel("Kullanıcı Adı:");
        JTextField textKullanici = new JTextField();

        JLabel labelSifre = new JLabel("Şifre:");
        JPasswordField textSifre = new JPasswordField();

        JButton butonGiris = new JButton("Giriş");
        JButton butonKaydol = new JButton("Kaydol");

        jf.getContentPane().add(labelKullanici);
        jf.getContentPane().add(textKullanici);
        jf.getContentPane().add(labelSifre);
        jf.getContentPane().add(textSifre);
        jf.getContentPane().add(butonGiris);
        jf.getContentPane().add(butonKaydol);

        // Giriş Butonu İşlevi
        butonGiris.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String kAdi = textKullanici.getText();
                String sifre = new String(textSifre.getPassword());
                
                if (girisKontrol(kAdi, sifre)) {
                    JOptionPane.showMessageDialog(null, "Giriş Başarılı! Hoşgeldiniz " + kAdi);
                } else {
                    JOptionPane.showMessageDialog(null, "Hatalı Giriş!");
                }
            }
        });

        // Kaydol Butonu İşlevi
        butonKaydol.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String kAdi = textKullanici.getText();
                String sifre = new String(textSifre.getPassword());
                
                if (!kAdi.isEmpty() && !sifre.isEmpty()) {
                    User yeniUser = new User(kAdi, sifre); // User nesnesi oluşturuluyor
                    kullaniciKaydet(yeniUser);
                    JOptionPane.showMessageDialog(null, "Başarıyla Kaydedildi: " + kAdi);
                } else {
                    JOptionPane.showMessageDialog(null, "Alanları boş bırakmayın!");
                }
            }
        });

        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);
    }

    // Parametre olarak User nesnesi alır
    public static void kullaniciKaydet(User user) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("user.txt", true))) {
            bw.write(user.toString()); // User sınıfındaki toString() metodunu kullanır
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Yazma hatası: " + e.getMessage());
        }
    }

    public static boolean girisKontrol(String kullaniciAdi, String sifre) {
        try (BufferedReader br = new BufferedReader(new FileReader("user.txt"))) {
            String satir;
            while ((satir = br.readLine()) != null) {
                String[] veriler = satir.split(",");
                if (veriler[0].equals(kullaniciAdi) && veriler[1].equals(sifre)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.err.println("Dosya bulunamadı veya okunamadı.");
        }
        return false;
    }
}