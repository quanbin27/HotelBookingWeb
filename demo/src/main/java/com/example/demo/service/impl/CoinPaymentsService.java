package com.example.demo.service.impl;

import okhttp3.OkHttpClient;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import org.apache.commons.codec.binary.Hex;
import java.util.HashMap;
import java.util.Map;


@Service
public class CoinPaymentsService {

    private static final String API_BASE_URL = "https://www.coinpayments.net/api.php";
    private static final String API_PUBLIC_KEY = "1ec2987b518b92d09599937bba64d1cb1245e2ff267a2b354ab93b0ce6ed2c7e";
    private static final String API_PRIVATE_KEY = "e23D265551dC3f2C04fEc2c77f50C43D930C348ea3acc03a0e431D9347b20036";
    private static final String HMAC_SHA512_ALGORITHM = "HmacSHA512";

    private final OkHttpClient httpClient;

    public CoinPaymentsService() {
        this.httpClient = new OkHttpClient();
    }

    public String createFixedPricePayment(double amount, String currency1, String currency2, String buyerEmail) throws Exception {
        String cmd = "create_transaction";

        // Tạo nonce (mã số duy nhất) cho mỗi cuộc gọi API
        long nonce = System.currentTimeMillis() / 1000;

        // Tạo dữ liệu POST
        Map<String, Object> postData = new HashMap<>();
        postData.put("version", "1");
        postData.put("key", API_PUBLIC_KEY);
        postData.put("cmd", cmd);
        postData.put("amount", amount);
        postData.put("currency1", currency1);
        postData.put("currency2", currency2);
        postData.put("buyer_email", buyerEmail);
        System.out.println((buildQueryString(postData)));
        // Tạo chữ ký HMAC từ dữ liệu POST và khóa bí mật API
        String hmac = generateHmac(API_PRIVATE_KEY,(buildQueryString(postData)));
        System.out.println(hmac);
        // Thêm chữ ký HMAC vào tiêu đề của yêu cầu HTTP
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("HMAC", hmac);

        // Gửi yêu cầu POST tới API CoinPayments
        HttpEntity<String> request = new HttpEntity<>(buildQueryString(postData), headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(API_BASE_URL, request, String.class);

        // Xử lý phản hồi từ API
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new Exception("Error occurred while calling CoinPayments API");
        }
    }



    public static String generateHmac(String apiKeySecret, String postData) throws Exception {
        Mac sha512Hmac = Mac.getInstance(HMAC_SHA512_ALGORITHM);
        byte[] secretKeyBytes = apiKeySecret.getBytes(StandardCharsets.UTF_8);
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKeyBytes, HMAC_SHA512_ALGORITHM);
        sha512Hmac.init(secretKeySpec);

        byte[] hmacData = sha512Hmac.doFinal(postData.getBytes(StandardCharsets.UTF_8));
        return Hex.encodeHexString(hmacData);
    }
    private String buildQueryString(Map<String, Object> postData) {
        StringBuilder queryString = new StringBuilder();
        postData.forEach((key, value) -> queryString.append(key).append("=").append(value).append("&"));
        return queryString.toString();
    }
}
