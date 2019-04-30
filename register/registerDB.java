package register;

import db.ShowTheAtom;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import login.loginDB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class registerDB {

    private static final String USUARIO = "root";
    private static final String SENHA = "";
    private static String DATABASE = "";
    private static String URL = "jdbc:mysql://";
    private static final String DB = "/sta";
    private static Statement statement = null;
    private static Integer resultSet = null;
    private static Connection conn;
    private static Boolean checked;
    private static final ImageIcon error = new ImageIcon(registerDB.class.getResource("/imgs/error.gif"));
    public static String link() throws FileNotFoundException, IOException{
        String linha;
        try (BufferedReader buffRead = new BufferedReader(new FileReader("texto.txt"))) {
            linha = buffRead.readLine();
            System.out.println("========RegisterDB=========\n"+linha+"\n====================\n");
        }
        return linha;
    }
    public static Boolean registre(String email, String senha, String data) throws NoSuchAlgorithmException, SQLException, IOException {
        DATABASE = link();
        URL += DATABASE + DB;
        System.out.println(URL);
        try {
            conn = DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "Error MySQL", JOptionPane.ERROR_MESSAGE, error);
            System.exit(0);
        }
        statement = conn.createStatement();
        String senhaMD5 = loginDB.md5(senha);
        String sql = "INSERT INTO sta.login (email, senha, data) VALUES('"+email+"','"+senhaMD5+"','"+data+"')";
        System.out.println(sql);
        try {
            resultSet = statement.executeUpdate(sql);
            checked = true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "Error SQL", JOptionPane.ERROR_MESSAGE, error);
            System.out.println(e);
            System.exit(0);
        }
        return checked;

    }

}
