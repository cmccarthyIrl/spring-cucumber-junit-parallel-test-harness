package com.cmccarthy.utils;

import com.sun.org.apache.xml.internal.dtm.ref.DTMNodeList;
import io.restassured.response.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.w3c.dom.Node;
import org.w3c.tidy.Tidy;

import javax.xml.xpath.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@SuppressWarnings("unused")
public class Helpers {

    public static String createUniqueIdentifier() {
        UUID customerNameUuid = UUID.randomUUID();
        return customerNameUuid.toString().replace("-", "");
    }

    public static String getStringByXpath(Node tidyDOM, XPath xPath, String stringXPath) throws XPathExpressionException {
        XPathExpression compiledXpath = xPath.compile(stringXPath);
        return ((DTMNodeList) compiledXpath.evaluate(tidyDOM, XPathConstants.NODESET)).getDTMIterator().toString();
    }

    public static String extractByXpath(String responseToString, String xPathExpression) throws XPathExpressionException {
        Tidy tidy = new Tidy();
        tidy.setQuiet(true);
        tidy.setShowWarnings(false);
        tidy.setLiteralAttribs(true);
        org.w3c.dom.Document tidyDOM = tidy.parseDOM(new ByteArrayInputStream(responseToString.getBytes(StandardCharsets.UTF_8)), null);

        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xPath = xPathFactory.newXPath();

        return Helpers.getStringByXpath(tidyDOM, xPath, xPathExpression);
    }

    public synchronized static Document getDocumentFromResponse(Response loginAsResponse) throws IOException {
        final InputStream json = loginAsResponse.asInputStream();
        final BufferedReader br = new BufferedReader(new InputStreamReader(json));
        final StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        return Jsoup.parse(sb.toString());
    }
}
