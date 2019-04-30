package db;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import linkDB.*;
import javax.swing.JOptionPane;

public class searchDB {

    private static final String USUARIO = "root";
    private static final String SENHA = "";
    private static String DATABASE = "";
    private static String URL = "jdbc:mysql://";
    private static final String DB = "/sta";
    private Statement statement = null;
    private ResultSet resultSet = null;
    public Connection conn;
    ImageIcon error = new ImageIcon(ShowTheAtom.class.getResource("/imgs/error.gif"));
    public static String link() throws FileNotFoundException, IOException{
        String linha;
        try (BufferedReader buffRead = new BufferedReader(new FileReader("texto.txt"))) {
            linha = buffRead.readLine();
            System.out.println("========SearchDB=========\n"+linha+"\n====================\n");
        }
        return linha;
    }
    public void abrir() throws Exception {
        DATABASE = link();
        System.out.println("Link da DB: "+DATABASE);
        URL += DATABASE + DB;
        System.out.println(URL);
        try {
            conn = DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "Error MySQL", JOptionPane.ERROR_MESSAGE, error);
            System.exit(0);
        }
       
        statement = conn.createStatement();
        // Result set get the result of the SQL query
        String sql = "select * from sta.elements";
        try {
            resultSet = statement.executeQuery(sql);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "Error SQL", JOptionPane.ERROR_MESSAGE, error);
        }
        try {
            writeResultSet(resultSet);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "Error na função WriteResults", JOptionPane.ERROR_MESSAGE, error);
            System.exit(0);
        }

    }
    public void writeResultSet(ResultSet resultSet) throws SQLException {
        ArrayList<String> atomos = new ArrayList<String>();
        ArrayList<Integer> idAtom = new ArrayList<Integer>();
        ArrayList<Integer> numAtom = new ArrayList<Integer>();
        ArrayList<String> descriptioN = new ArrayList<String>();
        //Map<String, String> var = new HashMap<String, String>();
        while (resultSet.next()) {
            Integer id = resultSet.getInt("id");
            String element = resultSet.getString("element");
            Integer numatom = resultSet.getInt("numatom");
            String description = resultSet.getString("description");
            //atomos.put("element", element);
            idAtom.add(id);
            atomos.add(element);
            numAtom.add(numatom);
            descriptioN.add(description);
        }

    }
}
