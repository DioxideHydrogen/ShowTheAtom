package login;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.security.*;
import java.math.*;
import register.*;
import javax.swing.*;

public class loginDB {
    public static String link() throws FileNotFoundException, IOException{
        String linha;
        try (BufferedReader buffRead = new BufferedReader(new FileReader("texto.txt"))) {
            linha = buffRead.readLine();
            System.out.println("========LoginDB=========\n"+linha+"\n====================\n");
        }
        return linha;
    }
    //private static final String LINK = "";
    private static final String USUARIO = "root";
    private static final String SENHA = "";
    private static String DATABASE = "";
    private static String URL = "jdbc:mysql://";
    private static final String DB = "/sta";
    private static Statement statement = null;
    private static ResultSet resultSet = null;
    private static Connection conn;
    private static Boolean checked;

    public static Boolean abrir(String email, String password) throws SQLException, NoSuchAlgorithmException, IOException {
        DATABASE = link();
        URL += DATABASE + DB;
        System.out.println(URL);
        try {
            conn = DriverManager.getConnection(URL, USUARIO, SENHA);
            System.out.println("MySQL Connection has been stabilished!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "Error MariaDB", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        statement = conn.createStatement();
        String sql = "select * from sta.login where email='" + email + "'";
        try {
            resultSet = statement.executeQuery(sql);
            System.out.println("SQL Query has been made with sucess!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "Error SQL", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        resultSet = statement.executeQuery(sql);
        String passMD5 = md5(password);
        checked = checarEmail(email, passMD5, resultSet);
        return checked;
    }

    public static String md5(String s) throws NoSuchAlgorithmException {
        ImageIcon error = new ImageIcon(loginDB.class.getResource("/imgs/error.gif"));
        if (s == "" || s.intern() == "") {
            JOptionPane.showMessageDialog(null, "Nenhuma senha foi passada", "Erro encriptando a senha", JOptionPane.ERROR_MESSAGE, error);
            System.exit(0);
        }
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.update(s.getBytes(), 0, s.length());
        String senha = new BigInteger(1, m.digest()).toString(16);
        return senha;
    }

    public static Boolean checarEmail(String email, String password, ResultSet resultSet) throws SQLException {
        ImageIcon error = new ImageIcon(loginDB.class.getResource("/imgs/error.gif"));
        if (resultSet.next()) {
            System.out.println("Houve resultados");
            String emailOriginal = new String(resultSet.getString("email"));
            String passwordOriginal = new String(resultSet.getString("senha"));
            System.out.println("Email passado: " + email + "\nSenha passada: " + password);
            System.out.println("Email original: " + emailOriginal + "\nSenha original: " + passwordOriginal);
            Boolean checked;
            if ((String) emailOriginal.intern() == (String) email.intern() && (String) passwordOriginal.intern() == (String) password.intern()) {
                System.out.println("Igual");
                checked = true;
            } else {
                JOptionPane.showMessageDialog(null, "Login inválido, tente novamente.", "Credenciais Inválidas", JOptionPane.ERROR_MESSAGE, error);
                checked = false;
                System.exit(0);
            }
            return checked;
        } else {
            System.out.println("Sem resultados");
            return false;
        }
    }
    public static Boolean registrando(String email, String senha, String Data) throws NoSuchAlgorithmException, SQLException, IOException{
        checked = registerDB.registre(email, senha, Data);
        return checked;
    }

}
