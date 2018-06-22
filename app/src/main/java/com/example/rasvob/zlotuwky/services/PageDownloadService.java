package com.example.rasvob.zlotuwky.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PageDownloadService {
    private static String RATE_URL = "http://www.kurzy.cz/kurzy-men/nejlepsi-kurzy/PLN-polsky-zloty/";

    private OkHttpClient client;

    public PageDownloadService(OkHttpClient client) {
        this.client = client;
    }

    public double getRate() throws IOException, XPathExpressionException, SAXException, ParserConfigurationException {
        String download = download(RATE_URL);

        if (download != null) {
            return parseRate(download);
        }
        return 0;
    }

    private String download(String url) throws IOException {
        Request request = new Request.Builder()
                .url(RATE_URL)
                .build();
        Response response = client.newCall(request).execute();
        if (response.body() != null) {
            return response.body().string();
        }

        return null;
    }

    private double parseRate(String html) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        org.jsoup.nodes.Document document = Jsoup.parse(html);
        Element element = document.selectFirst("#leftcolumn > div.ecb > table.pd.rowcl.leftcolumnwidth > tbody > tr:nth-child(7) > td:nth-child(4)");
        String text = element.text();

        return Double.parseDouble(text);
    }
}
