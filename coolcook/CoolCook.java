package coolcook;

import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import sun.net.www.http.HttpClient;

import java.util.Iterator;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Enumeration;
import java.util.HashMap;

/**
 * Created by apreda on 29.04.2017.
 */
@WebServlet(value = "/coolcook")
public class CoolCook extends HttpServlet {

    private static final String DEFAULT_EMISSION_VALUE = "0.10";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EmissionsResponse emissionsResponse = getEmissionsResponse(request);
        response.setContentType("application/json");
        // Actual logic goes here.
        PrintWriter out = response.getWriter();
        out.println(new Gson().toJson(emissionsResponse));
        System.out.println(new Gson().toJson(emissionsResponse));

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EmissionsResponse emissionsResponse = getEmissionsResponse(request);
        response.setContentType("application/json");
        // Actual logic goes here.
        PrintWriter out = response.getWriter();
        out.println(new Gson().toJson(emissionsResponse));
        System.out.println(new Gson().toJson(emissionsResponse));
    }

    public static void main(String[] args) throws IOException {
//        EmissionsResponse emissionsResponse = getEmissionsResponse();
    }

    private static EmissionsResponse getEmissionsResponse(HttpServletRequest request) throws IOException {
        String[] categories = {"Herbs", "Dairy"};

        HashMap<String, Float> distanceIncreasePercentage = new HashMap<>();
        distanceIncreasePercentage.put("Asia", 1.1f);
        distanceIncreasePercentage.put("Africa", 1.2f);
        distanceIncreasePercentage.put("Australia", 1.8f);
        distanceIncreasePercentage.put("Europe", 1.01f);
        distanceIncreasePercentage.put("North America", 1.6f);
        distanceIncreasePercentage.put("South America", 1.6f);

        HashMap<String, Float> products = new HashMap<>();
        products.put("Tomatoes", 0.11f);
        products.put("Garlic", 0.43f);
        products.put("Basil", 0.09f);
        products.put("Mozzarella", 4.46f);
        products.put("Tuna", 1.56f);
        products.put("Apples", 0.11f);
        products.put("Banana", 0.12f);
        products.put("Grapes", 0.38f);
        products.put("Sugar", 1.79f);
        products.put("Yoghurt", 0.41f);
        products.put("Peppermint, Black Mitchum Variety, Yogurt", 20.42f);

//        String url = "http://www.foodemissions.com/foodemissions/Calculator.aspx";
//
//        CloseableHttpClient client = HttpClientBuilder.create().build();
//        HttpGet requestInformation = new HttpGet(url);
//        URI uri = null;
//        try {
//            uri = new URIBuilder(requestInformation.getURI()).addParameter("ctl00$MainContent$ScriptManager",
//                    "ctl00$MainContent$UpdatePanel1|ctl00$MainContent$calculate")
//                    .addParameter("ctl00$MainContent$category:", categories[0])
//                    .addParameter("ctl00$MainContent$product", products[0])
//                    .addParameter
//                            ("ctl00$MainContent$transport", "1400")
//                    .addParameter("ctl00$MainContent$quantity", "1.0")
//                    .addParameter("ctl00$MainContent$wastepct", "0.0")
//                    .addParameter("__VIEWSTATE",
//                            "/wEPDwULLTEyNjczNDE1NTYPZBYCZg9kFgICAw9kFgICBw9kFgQCAg9kFgJmD2QWAgIBD2QWBgIBDxAPFgIeC18hRGF0YUJvdW5kZ2QQFQ4MQmVhbnMvUHVsc2VzBURhaXJ5DkZydWl0cy9CZXJyaWVzBkdyYWlucwVIZXJicwxNZWF0L1BvdWx0cnkYTWlzY2VsbGFuZW91cyBGb29kIENyb3BzCk51dHMvU2VlZHMET2lscw9Qcm9jZXNzZWQgRm9vZHMKUm9vdCBDcm9wcwdTZWFmb29kBlR1YmVycwpWZWdldGFibGVzFQ4MQmVhbnMvUHVsc2VzBURhaXJ5DkZydWl0cy9CZXJyaWVzBkdyYWlucwVIZXJicwxNZWF0L1BvdWx0cnkYTWlzY2VsbGFuZW91cyBGb29kIENyb3BzCk51dHMvU2VlZHMET2lscw9Qcm9jZXNzZWQgRm9vZHMKUm9vdCBDcm9wcwdTZWFmb29kBlR1YmVycwpWZWdldGFibGVzFCsDDmdnZ2dnZ2dnZ2dnZ2dnZGQCAw8QDxYCHwBnZBAVAghDaWxhbnRybyFQZXBwZXJtaW50LCBCbGFjayBNaXRjaHVtIFZhcmlldHkVAghDaWxhbnRybyFQZXBwZXJtaW50LCBCbGFjayBNaXRjaHVtIFZhcmlldHkUKwMCZ2dkZAIZD2QWCgIBDw8WAh4EVGV4dAUHQ0EsIFVTQWRkAgMPDxYCHwEFBWZyZXNoZGQCBQ8PFgIfAQUEMC4wOWRkAgcPDxYCHwEFBDAuMDlkZAIJDw8WAh8BBQQwLjAwZGQCBA8PZA8QFgFmFgEWAh4OUGFyYW1ldGVyVmFsdWUFBUhlcmJzFgFmZGRkJh3wQLIa5YYSMWfLxfCh1T8q13CiYUPh7/NCMATmFPE=")
//                    .addParameter("__VIEWSTATEGENERATOR", "A05136C4")
//                    .addParameter("__ASYNCPOST", "true").addParameter
//                            ("ctl00$MainContent$calculate", "Calculate food emissions").build();
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//
//        requestInformation.setURI(uri);
//
//        HttpResponse responseInformation = client.execute(requestInformation);
//
//        System.out.println("Response Code : "
//                + responseInformation.getStatusLine().getStatusCode());
//
//        BufferedReader rd = new BufferedReader(
//                new InputStreamReader(responseInformation.getEntity().getContent()));
//
//        StringBuffer result = new StringBuffer();
//        String line = "";
//        while ((line = rd.readLine()) != null) {
//            result.append(line);
//        }
//
//        Document document = Jsoup.parse(result.toString());
//        Elements prodEmissions = document.select("#ctl00_MainContent_prodEmissions");
        String prodEmissionsValue = DEFAULT_EMISSION_VALUE;
//
//        if (prodEmissions.iterator().hasNext()) {
//            prodEmissionsValue = prodEmissions.next().text();
//        }
//
//        Elements transportEmissions = document.select
//                ("#ctl00_MainContent_transEmissions");
//
        String transportEmissionsValue = DEFAULT_EMISSION_VALUE;
//        if (transportEmissions.iterator().hasNext()) {
//            transportEmissionsValue = transportEmissions.next().text();
//        }

        String distance = "1";
        String product = "1";
        String quantity = "1";
        Map<String, String[]> parameterNames = request.getParameterMap();
        StringBuffer toPutInObject = new StringBuffer();
        Iterator it = parameterNames.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            toPutInObject.append(pair.getKey() + " = " + pair.getValue());
            switch (pair.getKey().toString()) {
                case "continent-list":
                    distance = distanceIncreasePercentage.get(pair.getKey())
                            .toString();
                    break;
                case "quantity":
                    quantity = pair.getValue().toString();
                    break;
                case "ingredient-list":
                    product = products.get(pair.getValue()).toString();
                    break;
            }
            it.remove(); // avoids a ConcurrentModificationException
        }
        prodEmissionsValue = String.valueOf(Float.parseFloat(prodEmissionsValue) *
                Float.parseFloat(product) * Float.parseFloat(quantity));
        EmissionsResponse emissionsResponse = new EmissionsResponse
                (prodEmissionsValue, String.valueOf(Float.parseFloat
                        (transportEmissionsValue) *
                        Float.parseFloat(distance)));
        return emissionsResponse;
    }
}
