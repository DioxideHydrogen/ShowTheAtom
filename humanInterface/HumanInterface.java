package humanInterface;

import db.ShowTheAtom;
import java.awt.HeadlessException;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class HumanInterface {
    public static String link() throws FileNotFoundException, IOException{
        String linha;
        try (BufferedReader buffRead = new BufferedReader(new FileReader("texto.txt"))) {
            linha = buffRead.readLine();
            System.out.println("========HumanInterface=========\n"+linha+"\n====================\n");
        }
        return linha;
    }
    public static void conexao(Integer id, String nome) throws SQLException, IOException {
        String USUARIO = "root";
        String SENHA = "";
        String DATABASE = link();
        String URL = "jdbc:mysql://";
        String DB = "/sta";
        DATABASE = link();
        URL += DATABASE + DB;
        System.out.println(URL);
        Statement statement = null;
        ResultSet resultSet = null;
        Connection conn;
        String sql = null;
        String description = null;
        String element = null;
        Integer numatom = null;
        ImageIcon error = new ImageIcon(HumanInterface.class.getResource("/imgs/error.gif"));
        if (id == 0 && nome.intern() != "") {
            sql = "select * from sta.elements where element='" + nome + "'";
        } else if (id > 0 && id <= 103 && nome.intern() == "") {
            sql = "select * from sta.elements where id='" + id + "'";
        } else if (id > 0 && id <= 103 && nome.intern() != "") {
            sql = "select * from sta.elements where id='" + id + "' AND element='" + nome + "'";
        } else {
            JOptionPane.showMessageDialog(null, "Informe os parâmetros", "Error MariaDB", JOptionPane.ERROR_MESSAGE, error);
            System.exit(0);
        }
        try {
            conn = DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "Error MariaDB", JOptionPane.ERROR_MESSAGE, error);
            System.exit(0);
        }
        conn = DriverManager.getConnection(URL, USUARIO, SENHA);
        statement = conn.createStatement();
        System.out.println(sql);
        try {
            resultSet = statement.executeQuery(sql);
            System.out.println("SQL Query has been made with sucess!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "Error SQL", JOptionPane.ERROR_MESSAGE, error);
        }
        while (resultSet.next()) {
            ImageIcon icone3 = new ImageIcon(HumanInterface.class.getResource("/imgs/atom3.gif"));
            try {
                element = resultSet.getString("element");
                numatom = resultSet.getInt("numatom");
                description = resultSet.getString("description");
                String html = "<html><head><style>  @import url('https://fonts.googleapis.com/css?family=Raleway'); body { width: 500px; height: auto;} h1 { text-align: center; font-family: 'Raleway', sans-serif;}"
                        + " #numatom, #descricao {font-weight: bold;} #background { box-shadow: 2px 1px 1px gray; } </style>"
                        + "</head><body><h1>" + element + "</h1><div id='background'>"
                        + "<p>Número atômico: <label id='numatom'>" + numatom + "</label></p>"
                        + "<p>Descrição: <label id='descricao'>" + description + ""
                        + "";
                JOptionPane.showMessageDialog(null, html, element, JOptionPane.INFORMATION_MESSAGE, icone3);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e, "Erro na listagem dos Átomos", JOptionPane.ERROR_MESSAGE, error);
            }
        }
    }

    public static void gui() throws HeadlessException, SQLException, IOException {
        ImageIcon icone2Ori = new ImageIcon(HumanInterface.class.getResource("/imgs/atom.png"));
        Image icon2Get = icone2Ori.getImage();
        Image newIcon2 = icon2Get.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);
        ImageIcon icone2 = new ImageIcon(newIcon2);
        JPanel p = new JPanel();
        JTextField nome = new JTextField(10);
        JTextField numero = new JTextField(10);
        p.add(new JLabel("Nome do Átomo :"));
        p.add(nome);
        p.add(new JLabel("Número Atômico : "));
        p.add(numero);
        JOptionPane.showConfirmDialog(null, p, "ShowTheAtom: ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.OK_CANCEL_OPTION, icone2);
        String nomeA;
        Integer numeroA;
        try {
            nomeA = nome.getText();
        } catch (Exception e) {
            nomeA = "";
        }
        try {
            numeroA = new Integer(numero.getText());
        } catch (Exception e) {
            numeroA = 0;
        }
        System.out.println(nomeA);
        System.out.println("Nome passado: " + nomeA + "\nNúmero passado: " + numeroA);
        conexao(numeroA, nomeA);
    }
}
