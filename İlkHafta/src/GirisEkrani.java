    import javax.swing.*;
	import java.awt.*;
	import java.awt.event.*;
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

        jf.add(new JLabel(" Kullanıcı Adı:")); jf.add(textKullanici);
        jf.add(new JLabel(" Şifre:")); jf.add(textSifre);
        jf.add(butonGiris); jf.add(butonKaydol);

        butonKaydol.addActionListener(e -> {
            String ad = textKullanici.getText();
            String sifre = new String(textSifre.getPassword());
            if (!ad.isEmpty() && !sifre.isEmpty()) {
                kullaniciKaydet(new User(ad, sifre));
                JOptionPane.showMessageDialog(null, "Kaydedildi!");
            }
        });

        
        butonGiris.addActionListener(e -> {
            if (girisKontrol(textKullanici.getText(), new String(textSifre.getPassword()))) {
                JOptionPane.showMessageDialog(null, "Hoşgeldiniz!");
            } else {
                JOptionPane.showMessageDialog(null, "Hatalı Giriş!");
            }
        });

        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);
    }

    public static void kullaniciKaydet(User user) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("user.txt", true))) {
            bw.write(user.kullaniciAdi + "," + user.sifre);
            bw.newLine();
        } catch (IOException e) { e.printStackTrace(); }
    }

    public static boolean girisKontrol(String ad, String sifre) {
        try (BufferedReader br = new BufferedReader(new FileReader("user.txt"))) {
            String satir;
            while ((satir = br.readLine()) != null) {
                String[] v = satir.split(",");
                if (v.length == 2 && v[0].equals(ad) && v[1].equals(sifre)) return true;
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



