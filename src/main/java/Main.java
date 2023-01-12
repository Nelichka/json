//import org.xml.sax.SAXException;
//
//import javax.xml.parsers.ParserConfigurationException;
//import java.io.File;
//import java.io.IOException;
//import java.text.ParseException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

public class Main {
    public static void main(String[] args) throws IOException, ParseException, ParserConfigurationException, SAXException, XPathExpressionException, ClassNotFoundException {
        int[] newPrices = new int[]{100, 200, 300, 150};
        String[] newProducts = new String[]{"Хлеб", "Яблоки", "Молоко", "Сыр"};
        Basket basket = new Basket(newPrices, newProducts);
        File csvFile = new File("log.csv");
        File jsonFile = new File("basket.json");
        File xmlFile = new File("shop.xml");
        ClientLog log = new ClientLog();
        basket.addToCart(3, 7);
        basket.addToCart(0, 4);
        basket.printCart();

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        String obj = gson.toJson(basket);
        try (FileWriter file = new FileWriter(jsonFile)) {
            file.write(obj);
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }


        File file = new File("basket.json");
        Basket newBasket = Basket.loadFromTxtFile(file);
        // basket.saveTxt(new File("basket.txt"));
        newBasket.addToCart(2, 5);
        // basket.saveTxt(new File("basket.txt"));
        newBasket.printCart();

        ClientLog allLog = new ClientLog();
        allLog.log(4, 4);
        allLog.log(3, 5);
        allLog.log(4, 2);
        allLog.exportAsCSV(new File("log.csv"));

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse("shop.xml");

        XPath xPath = XPathFactory.newInstance().newXPath();

        boolean doLoad = Boolean.parseBoolean(xPath
                .compile("/config/load/enabled")
                .evaluate(doc));
        String loadFileName = xPath
                .compile("/config/load/fileName")
                .evaluate(doc);
        String loadFormat = xPath
                .compile("/config/load/format")
                .evaluate(doc);

        //   Basket basket;
        if (doLoad) {
            File loadFile = new File(loadFileName);
            switch (loadFormat) {
                case "json":
                    basket = Basket.loadFromJson(loadFile);
                    break;
                case "txt":
                    basket = Basket.loadFromTxtFile(loadFile);
                    break;
                case "bin":
                    basket = Basket.loadFromBin(loadFile);
                    break;
            }
        } else {
            basket = new Basket(null, null);
        }

        boolean doSave = Boolean.parseBoolean(xPath
                .compile("/config/save/enabled")
                .evaluate(doc));
        String SaveFileName = xPath
                .compile("/config/save/fileName")
                .evaluate(doc);
        String saveFormat = xPath
                .compile("/config/save/format")
                .evaluate(doc);
        if (doSave) {
            File saveFile = new File(SaveFileName);
            switch (saveFormat) {
                case "json":
                    basket = Basket.saveJson(saveFile);
                    break;
                case "txt":
                    basket = Basket.saveTxt(saveFile);
                    break;
                case "bin":
                    basket = Basket.saveBin(saveFile);
                    break;
            }
        } else {
            basket = new Basket(null, null);
        }
    }

}


//        read(doc, "load");
//        if (enabled.equals("true")) {
//            if (jsonFile.exists()) {
//                if (format.equals("json")) {
//                    basket = Basket.loadFromTxtFile(new File("basket.json"));
//                    basket.printCart();
//                } else {
//                    basket = Basket.loadFromTxtFile(new File("basket.json"));
//                    basket.printCart();
//                }
//            }
//        }
//
//        basket.printCart();
//
//
//        read(doc, "save");
//        if (enabled.equals("true")) {
//            if (format.equals("json")) {
//                basket.saveTxt(new File("basket.json"));
//            } else {
//                basket.saveTxt(new File("basket.txt"));
//            }
//            log.exportAsCSV(csvFile);
//        }
//        read(doc, "log");
//        if (enabled.equals("true")) {
//            log.exportAsCSV(new File(fileName));
//        }
//    }
//
//    private static void read(Document node, String name) {
//        NodeList nodeList = node.getElementsByTagName(name);
//        for (int i = 0; i < nodeList.getLength(); i++) {
//            Node node_ = nodeList.item(i);
//            if (Node.ELEMENT_NODE == node_.getNodeType()) {
//                Element element = (Element) node_;
//                enabled = element.getElementsByTagName("enabled").item(0).getTextContent();
//                fileName = element.getElementsByTagName("fileName").item(0).getTextContent();
//                if (!name.equals("log")) {
//                    format = element.getElementsByTagName("format").item(0).getTextContent();
//                }
//            }
//        }
//    }
//}





