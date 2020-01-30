import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;


public class Main {
    public static void main(String[] args) {
        try {
            Document doc = Jsoup.connect("https://lenta.ru/").get();
            Elements elements = doc.select("img");
            elements.stream().map(e -> e.attr("src")).filter(u -> u.contains("https")).forEach(u -> download(u));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void download(String path) {

        String strImageName = path.substring(path.lastIndexOf("/") + 1);
        System.out.println("Saving: " + strImageName + ", from: " + path);

        try {
            URL urlImage = new URL(path);
            InputStream in = urlImage.openStream();

            byte[] buffer = new byte[4096];
            int n;

            OutputStream os = new FileOutputStream("images" + "/" + strImageName);

            while ((n = in.read(buffer)) != -1) {
                os.write(buffer, 0, n);
            }
            os.close();

            System.out.println("Image saved");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
