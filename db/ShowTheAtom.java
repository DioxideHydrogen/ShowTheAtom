package db;

import java.awt.HeadlessException;
import java.awt.Image;
import java.sql.SQLException;
import login.*;
import humanInterface.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import linkDB.*;
import javax.swing.*;

public class ShowTheAtom {
    private static String username;
    private static String pass;
    private static String dataMySQL;
    private static Boolean checked;
    public static void main(String[] args) throws HeadlessException, SQLException, NoSuchAlgorithmException, IOException {
        getLink deo = new getLink();
        searchDB dao = new searchDB();
        try {
            deo.criarTexto();
            dao.abrir();
        } catch (Exception e) {
        }
        ImageIcon error = new ImageIcon(ShowTheAtom.class.getResource("/imgs/error.gif"));
        ImageIcon icone1 = new ImageIcon(ShowTheAtom.class.getResource("/imgs/atom.gif"), "Logo");
        String[] login = {"Já sou cadastrado", "Não sou cadastrado"};
        int i = JOptionPane.showOptionDialog(null, "Já posssui uma conta?", "Login ou Registro", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, icone1, login, login[0]);
        if (i == 0) {
            JPanel p = new JPanel();
            JTextField email = new JTextField(10);
            email.setHorizontalAlignment(JTextField.CENTER);
            JPasswordField password = new JPasswordField(10);
            password.setHorizontalAlignment(JTextField.CENTER);
            p.add(new JLabel("<html><body>Email :"));
            p.add(email);
            p.add(new JLabel("Senha : "));
            p.add(password);
            ImageIcon icone2Ori = new ImageIcon(ShowTheAtom.class.getResource("/imgs/atom.png"));
            Image icon2Get = icone2Ori.getImage();
            Image newIcon2 = icon2Get.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);
            ImageIcon icone2 = new ImageIcon(newIcon2);
            JOptionPane.showConfirmDialog(null, p, "Login : ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, icone2);
            pass = new String(password.getPassword());
            username = email.getText();
            checked = loginDB.abrir(username, pass);
            if (checked == true) {
                HumanInterface.gui();
            } else {
                JOptionPane.showMessageDialog(null, "Credenciais Inválidas", "Erro no Login", JOptionPane.ERROR_MESSAGE, error);
            }
        } else if (i == 1) {
            JPanel p = new JPanel();
            JTextField email = new JTextField(10);
            email.setHorizontalAlignment(JTextField.CENTER);
            JPasswordField password = new JPasswordField(10);
            password.setHorizontalAlignment(JTextField.CENTER);
            JTextField data = new JTextField(10);
            data.setHorizontalAlignment(JTextField.CENTER);
            data.setEditable(false);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            dataMySQL = dateFormat.format(date);
            System.out.println(dataMySQL);
            data.setText(dataMySQL);
            p.add(new JLabel("Email :"));
            p.add(email);
            p.add(new JLabel("Senha : "));
            p.add(password);
            p.add(new JLabel("Data: "));
            p.add(data);
            ImageIcon icone2Ori = new ImageIcon(ShowTheAtom.class.getResource("/imgs/atom.png"));
            Image icon2Get = icone2Ori.getImage();
            Image newIcon2 = icon2Get.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);
            ImageIcon icone2 = new ImageIcon(newIcon2);
            JOptionPane.showConfirmDialog(null, p, "Resgistre-se : ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, icone2);
            pass = new String(password.getPassword());
            username = email.getText();
            checked = loginDB.registrando(username, pass, dataMySQL);
            if(checked == true){
                JOptionPane.showMessageDialog(null, "O Registro foi efetuado com sucesso, abra novamente o aplicativo", "Registro efetuado", JOptionPane.ERROR_MESSAGE, icone1);
                i = 1;
            } else {
                JOptionPane.showMessageDialog(null, "Houve um erro no registro", "Falha ao Registrar", JOptionPane.ERROR_MESSAGE, error);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Escolha uma opção válida", "Erro no check de Login ou Registro", JOptionPane.ERROR_MESSAGE, error);
        }
    }
 
    private Boolean login(String email, String password) throws IOException {
        ImageIcon error = new ImageIcon(ShowTheAtom.class.getResource("/imgs/error.gif"));
        try {
            checked = loginDB.abrir(email, password);
        } catch (SQLException | NoSuchAlgorithmException e) {
            checked = false;
            JOptionPane.showMessageDialog(null, e, "Error on Login", JOptionPane.ERROR_MESSAGE, error);
        }
        return checked;
    }
}
