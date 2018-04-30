package es.meatpi;

import es.meatpi.model.Product;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Before;
import org.junit.Test;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ProductFinderTest {

    private ProductFinder finder;

    @Before
    public void setUp() throws IOException, URISyntaxException {
        Document doc = Jsoup.parse(getClass().getResourceAsStream("/fruit.html"),
                "UTF-8",
                "fruit.html");
        finder = new ProductFinder(doc, new URI(""));
        Document descDoc = Jsoup.parse(getClass().getResourceAsStream("/description.html"),
                "UTF-8",
                "fruit.html");
        finder.setDescDoc(descDoc);
    }

    @Test
    public void createsListOfProductsFromPage() {
        List<Element> products = finder.getRawProducts();

        assertThat(products.size(), is(17));
    }

    @Test
    public void productHasTitle() {
        List<Product> products = finder.getProducts();
        assertThat(products.get(0).getTitle(), is("Sainsbury's Strawberries 400g"));
    }

    @Test
    public void productHasUnitPrice() {
        List<Product> products = finder.getProducts();
        assertThat(products.get(0).getUnitPrice(), is(1.75));
    }

    @Test
    public void canGetDescription() {
        String desc = finder.getDescription();
        assertThat(desc, is("by Sainsbury's strawberries"));
    }

    @Test
    public void productHasKcal() {
        assertThat(finder.getKcalPer100g(), is(33));
    }

}
