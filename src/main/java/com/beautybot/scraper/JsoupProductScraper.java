package com.beautybot.scraper;

import com.beautybot.domain.product.LeafProduct;
import com.beautybot.domain.product.ProductComponent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class JsoupProductScraper implements ProductScraper {
    private final String url;
    private final String itemSelector;
    private final String nameSelector;
    private final String priceSelector;

    /**
     * @param url URL della pagina da scrapare
     * @param itemSelector CSS selector per ogni prodotto
     * @param nameSelector CSS selector per il nome al suo interno
     * @param priceSelector CSS selector per il prezzo al suo interno
     */
    public JsoupProductScraper(String url,
                               String itemSelector,
                               String nameSelector,
                               String priceSelector) {
        this.url = url;
        this.itemSelector = itemSelector;
        this.nameSelector = nameSelector;
        this.priceSelector = priceSelector;
    }

    @Override
    public List<ProductComponent> scrape() throws Exception {
        Document doc = Jsoup.connect(url).get();
        List<ProductComponent> products = new ArrayList<>();

        for (Element item : doc.select(itemSelector)) {
            String nameText = item.selectFirst(nameSelector).text();
            String priceText = item.selectFirst(priceSelector).text()
                .replaceAll("[^\\d,\\.]", "")  // rimuove simboli non numerici
                .replace(",", ".");
            BigDecimal price = new BigDecimal(priceText);

            products.add(new LeafProduct(nameText, price));
        }
        return products;
    }
}
