package es.meatpi;

import es.meatpi.model.Product;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class ProductFinder {
    private Document doc;
    private URI base;
    private Document descDoc;

    ProductFinder(Document doc, URI base) {
        this.doc = doc;
        this.base = base;
    }

    List<Element> getRawProducts() {
        Elements productItems = doc.select("#productsContainer .productLister li").select(".gridItem");
        return new ArrayList<>(productItems);
    }

    List<Product> getProducts() {
        return getRawProducts().stream()
                .map(this::getFields)
                .collect(Collectors.toList());
    }

    private void loadDescriptionPage(String href) throws IOException, URISyntaxException {
        descDoc = Jsoup.connect(base.resolve(new URI(href)).toString()).get();
    }

    String getDescription() {
        return descDoc.selectFirst(".productText p").text();
    }

    private Product getFields(Element element) {
        try {
            loadDescriptionPage(element.selectFirst("h3 a").attr("href"));
            String title = element.selectFirst("h3").text();
            String rawUnitPrice = element.selectFirst(".pricePerUnit").text();
            double unitPrice = Double.valueOf(rawUnitPrice.replaceAll("£([^/]+)/unit", "$1"));
            String description = getDescription();
            return new Product(title, getKcalPer100g(), unitPrice, description);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException("Failed to load description", e);
        }
    }

    void setDescDoc(Document descDoc) {
        this.descDoc = descDoc;
    }

    Integer getKcalPer100g() {
        Element calories = descDoc.selectFirst(".tableRow0");
        if (calories == null) {
            return null;
        }
        return Integer.valueOf(calories.text().replaceAll("kcal.*",""));
    }
}
