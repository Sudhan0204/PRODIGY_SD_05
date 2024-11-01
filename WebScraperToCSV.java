import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.opencsv.CSVWriter;

public class WebScraperToCSV {
    public static void main(String[] args) {
    	
        String url = "https://mamaearth.in/product-category/perfume?utm_source=google&utm_medium=cpc&utm_term=&gad_source=1&gclid=CjwKCAjw-JG5BhBZEiwAt7JR632GLVBJdrAjGb2CF5uX6Vhdl1OjPx3gwIXT_iUkZ_9ZzIh23dluVxoC44sQAvD_BwE";
        String csvFile = "mamaearth_products1.csv";

        List<String[]> data = new ArrayList<>();

        data.add(new String[] { "Product Name", "Price", "Link" });

        try {
            Document doc = Jsoup.connect(url).get();
            Elements products = doc.select(".card"); // Change this selector if necessary
            for (Element product : products) {
                String productName = product.select(".tittle").text(); // Updated selector
                String productPrice = product.select(".price special").text();  // Updated selector
                String productLink = "https://mamaearth.in" + product.select("h5.card-title a").attr("href");
                data.add(new String[] { productName, productPrice, productLink });
            }

            try (CSVWriter writer = new CSVWriter(new FileWriter(csvFile))) {
                writer.writeAll(data);
                System.out.println("Data has been successfully written to " + csvFile);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
