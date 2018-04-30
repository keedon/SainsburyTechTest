package es.meatpi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import es.meatpi.model.Product;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class Scraper {

    private static final String URL = "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html";

    public static void main(String[] args) throws IOException, URISyntaxException {
        URI uri = new URI(URL);
        ProductFinder finder = new ProductFinder(Jsoup.connect(URL).get(), uri);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        List<Product> products = finder.getProducts();
        System.out.println(gson.toJson(products));
    }

}
