package linkDB;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class getLink {

    public void criarTexto() throws MalformedURLException, IOException {
        String site = "http://showtheatom.000webhostapp.com/mysql/llink.txt";
        URL url = new URL(site);
        File file = new File("texto.txt");
        InputStream is = url.openStream();
        FileOutputStream fos = new FileOutputStream(file);
        int bytes = 0;
        String urldaDb = "";
        while ((bytes = is.read()) != -1) {
            fos.write(bytes);
            urldaDb += bytes;
        }
        is.close();
        fos.close();
    }

}
