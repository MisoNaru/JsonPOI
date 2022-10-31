package com.practice;// 네이버 검색 API 예제 - 블로그 검색


import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PracticeForAll {

    public static void main(String[] args) throws Exception {
        String clientId = "GUaWvVMcEKIocayHZo3l"; //애플리케이션 클라이언트 아이디
        String clientSecret = "209scpyuN0"; //애플리케이션 클라이언트 시크릿

        String[] diseaseCode_14 = {"copd", "기관지염", "만성기관지", "만성폐쇄성", "패색성폐질환", "폐기종", "폐쇄성질환", "폐쇄성폐질환"};

        String responseBlog = null;
        String text = null;
//        JSONParser jsonParser = null;
//        JSONObject jsonObjectBlog = null;

        /* 엑셀 */
        XSSFWorkbook workbook = null;
        XSSFSheet sheet = null;
        XSSFRow row = null;
        XSSFCell cell = null;

        for (int i = 0; i < diseaseCode_14.length; i++) {
            try {
                text = URLEncoder.encode(diseaseCode_14[i], "UTF-8");
            } catch (Exception e) {
                throw new RuntimeException("검색어 인코딩 실패", e);
            }

            String texts = URLDecoder.decode(text, "UTF-8");

            String apiURL = "https://openapi.naver.com/v1/search/blog?query=" + text + "&sort=date&display=2";    // JSON 결과
            Map<String, String> requestHeaders = new HashMap<>();
            requestHeaders.put("X-Naver-Client-Id", clientId);
            requestHeaders.put("X-Naver-Client-Secret", clientSecret);
            responseBlog = get(apiURL, requestHeaders);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Calendar time = Calendar.getInstance();
            String datetime = sdf.format(time.getTime());

            System.out.println("texts:" + texts);

//            System.out.println("*****************************************************************************");
//            System.out.println("responseBlog:"+responseBlog);

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(responseBlog);
            JSONArray items = (JSONArray) jsonObject.get("items");
            System.out.println("*****************************************************************************");
            System.out.println("items:" + items);


            for (int j = 0; j < items.size(); j++) {
                JSONObject blogObject = (JSONObject) items.get(j);

                String SnsID = (String) blogObject.get("link");
                SnsID = SnsID.substring(SnsID.lastIndexOf("/") + 1);

                String bloggerlink = (String) blogObject.get("bloggerlink");
                bloggerlink = bloggerlink.substring(bloggerlink.lastIndexOf("/") + 1);

                String bloggername = (String) blogObject.get("bloggername");
                bloggername = bloggername.substring(bloggername.lastIndexOf("/") + 1);

                String title = (String) blogObject.get("title");
                String description = (String) blogObject.get("description");
                String link = (String) blogObject.get("link");
                String postdate = (String) blogObject.get("postdate");
                String postDateTime = postdate + "0000";

                LocalDate now = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                now.format(formatter);

//                System.out.println("*****************************************************************************");
//                System.out.println("blogObject:"+blogObject);
//                System.out.println("*****************************************************************************");

                System.out.println("SnsID:" + SnsID);
                System.out.println("bloggerlink:" + bloggerlink);
                System.out.println("bloggername:" + bloggername);
                System.out.println("title:" + title);
                System.out.println("description:" + description);
                System.out.println("link:" + link);
                System.out.println("now:" + LocalDate.now().format(formatter).toString());
                System.out.println("itemsize:" + LocalDate.now().format(formatter).toString());

                /* 오늘 날짜와 동일한 postdate만 뽑기 */
                if (Objects.equals(postdate, now.format(formatter).toString())) {
                    System.out.println("postdate:" + postdate);
                }
                /* 오늘 날짜와 동일한 postdatetime만 뽑기 */
                if (Objects.equals(postDateTime.substring(0, 8), now.format(formatter).toString())) {
                    System.out.println("postDateTime:" + postDateTime);
                }
                System.out.println("*****************************************************************************");
            }
        }
    }

    private static String readBody(InputStream body) {
        InputStreamReader streamReader = new InputStreamReader(body);


        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();


            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }


            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e);
        }
    }

    private static String get(String apiUrl, Map<String, String> requestHeaders) {
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }


            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 오류 발생
                return readBody(con.getErrorStream());
            }
        } catch (Exception e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }


    private static HttpURLConnection connect(String apiUrl) {
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }


}