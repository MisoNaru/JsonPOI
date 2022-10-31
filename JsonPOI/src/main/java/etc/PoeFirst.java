package etc;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PoeFirst {
    public static void main(String[] args) throws Exception {

        String clientId = "GUaWvVMcEKIocayHZo3l"; //애플리케이션 클라이언트 아이디
        String clientSecret = "209scpyuN0"; //애플리케이션 클라이언트 시크릿

        String[] arrayKeyword = {"감기", "몸살", "독감", "코막히다", "폐렴", "눈병", "결막염", "다래끼", "눈다래끼", "각막염", "천식"
                , "천명음", "알레르기성천식", "직업성천식", "심장성천식", "피부염", "아토피", "건선", "습진", "태열", "아토피성피부염", "영유아폐렴", "영유아수족구", "COPD"
                , "식중독", "장염", "배탈", "배탈나다", "노로바이러스"};

        // 1. 최종적으로는 시트에 각각 키워드를 넣을 것
        // 2. 최종적으로는 한 시트에 한 항목에 대한 게 전부 들어가야 함. 반복문을 다시 생각해보자.
        for (String s : arrayKeyword) {
            String text = null;
            try {
                text = URLEncoder.encode(s, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("검색어 인코딩 실패", e);
            }
            String texts = URLDecoder.decode(text, "UTF-8"); // 이렇게 해야 tesxts = 감기
            System.out.println("texts:" + texts);

            String apiURLBlog = "https://openapi.naver.com/v1/search/blog.json?query=" + text + "&sort=date&display=51";    // JSON 결과 size() 때문에 +1 해서 검색할 것
            String apiURLNews = "https://openapi.naver.com/v1/search/news.json?query=" + text + "&sort=date&display=51";    // JSON 결과 size() 때문에 +1 해서 검색할 것

            Map<String, String> requestHeaders = new HashMap<>();
            Map<String, String> requestHeadersNews = new HashMap<>();

            // 블로그 정보 MAP
            requestHeaders.put("X-Naver-Client-Id", clientId);
            requestHeaders.put("X-Naver-Client-Secret", clientSecret);
            String responseBodyBlog = get(apiURLBlog, requestHeaders);

            // 뉴스 정보 MAP
            requestHeadersNews.put("X-Naver-Client-Id", clientId);
            requestHeadersNews.put("X-Naver-Client-Secret", clientSecret);
            String responseBodyNews = get(apiURLNews, requestHeadersNews);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Calendar time = Calendar.getInstance();
            String datetime = sdf.format(time.getTime());

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(responseBodyBlog);
            JSONObject jsonObjectNews = (JSONObject) jsonParser.parse(responseBodyNews);

            JSONArray items = (JSONArray) jsonObject.get("items");
            JSONArray newses = (JSONArray) jsonObjectNews.get("items");


            XSSFWorkbook workbook = null;
            XSSFSheet sheet = null;
            XSSFRow row = null;
            XSSFCell cell = null;

            workbook = new XSSFWorkbook();
            sheet = workbook.createSheet("블로그");

            for (int i = 0; i < items.size(); i++) {
                JSONObject itemsObject = (JSONObject) items.get(i);

                row = sheet.createRow(i);

                if (i == 0) {
                    cell = row.createCell(0);
                    cell.setCellValue("SNS 구분");

                    cell = row.createCell(1);
                    cell.setCellValue("SNS ID");

                    cell = row.createCell(2);
                    cell.setCellValue("blog id 의 끝부분");

                    cell = row.createCell(3);
                    cell.setCellValue("blog id 의 끝부분");

//                sheet.addMergedRegion(new CellRangeAddress(0,0, 2,3));

                    cell = row.createCell(4);
                    cell.setCellValue("blog name");

                    cell = row.createCell(5);
                    cell.setCellValue("사용자 이미지 URL");

                    cell = row.createCell(6);
                    cell.setCellValue("title");

                    cell = row.createCell(7);
                    cell.setCellValue("description");

                    cell = row.createCell(8);
                    cell.setCellValue("SNS URL");

                    cell = row.createCell(9);
                    cell.setCellValue("생성일자");

                    cell = row.createCell(10);
//                cell.setCellValue("사용여부");
                    cell.setCellValue("생성일시");

                    cell = row.createCell(11);
                    cell.setCellValue("사용여부");
                } else {
                    cell = row.createCell(0);
                    cell.setCellValue("B");

                    cell = row.createCell(1);
                    cell.setCellValue(itemsObject.get("link").toString().substring(itemsObject.get("link").toString().lastIndexOf("/") + 1));

                    cell = row.createCell(2);
                    cell.setCellValue(itemsObject.get("bloggerlink").toString().substring(itemsObject.get("bloggerlink").toString().lastIndexOf("/") + 1));

                    cell = row.createCell(3);
                    cell.setCellValue(itemsObject.get("bloggerlink").toString().substring(itemsObject.get("bloggerlink").toString().lastIndexOf("/") + 1));

//                sheet.addMergedRegion(new CellRangeAddress(i,i, 2,3));

                    cell = row.createCell(4);
                    cell.setCellValue(itemsObject.get("bloggername").toString());

                    cell = row.createCell(5);
                    cell.setCellValue("");

                    cell = row.createCell(6);
                    cell.setCellValue(itemsObject.get("title").toString());

                    cell = row.createCell(7);
                    cell.setCellValue(itemsObject.get("description").toString());

                    cell = row.createCell(8);
                    cell.setCellValue(itemsObject.get("link").toString());

                    cell = row.createCell(9);
                    cell.setCellValue(itemsObject.get("postdate").toString());

                    cell = row.createCell(10);
                    cell.setCellValue(itemsObject.get("postdate").toString() + "0000");

                    cell = row.createCell(11);
                    cell.setCellValue("Y");
                }

            }

            sheet = workbook.createSheet("뉴스");
            // 뉴스 출력
            for (int i = 0; i < newses.size(); i++) {
                JSONObject newsObject = (JSONObject) newses.get(i);
                row = sheet.createRow(i);

                if (i == 0) {
                    cell = row.createCell(0);
                    cell.setCellValue("SNS 구분");

                    cell = row.createCell(1);
                    cell.setCellValue("SNS ID");

                    cell = row.createCell(2);
                    cell.setCellValue("사용자 ID");

                    cell = row.createCell(3);
                    cell.setCellValue("사용자명");

                    cell = row.createCell(4);
                    cell.setCellValue("표시명");

                    cell = row.createCell(5);
                    cell.setCellValue("이미지 URL");

                    cell = row.createCell(6);
                    cell.setCellValue("title");

                    cell = row.createCell(7);
                    cell.setCellValue("description");

                    cell = row.createCell(8);
                    cell.setCellValue("SNS URL");

                    cell = row.createCell(9);
                    cell.setCellValue("생성일자");

                    cell = row.createCell(10);
//                cell.setCellValue("사용여부");
                    cell.setCellValue("생성일시");

                    cell = row.createCell(11);
                    cell.setCellValue("사용여부");
                } else {
                    cell = row.createCell(0);
                    cell.setCellValue("N");

                    cell = row.createCell(1);
                    cell.setCellValue(newsObject.get("link").toString().substring(newsObject.get("link").toString().lastIndexOf("/") + 1));

                    cell = row.createCell(2);
                    cell.setCellValue("");

                    cell = row.createCell(3);
                    cell.setCellValue("");

                    cell = row.createCell(4);
                    cell.setCellValue("");

                    cell = row.createCell(5);
                    cell.setCellValue("");

                    cell = row.createCell(6);
                    cell.setCellValue(newsObject.get("title").toString());

                    cell = row.createCell(7);
                    cell.setCellValue(newsObject.get("description").toString());

                    cell = row.createCell(8);
                    cell.setCellValue(newsObject.get("link").toString());


                    // LocalDate & LocalDateTime
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
                    String cd = newsObject.get("pubDate").toString();

                    LocalDate dateTime = LocalDate.parse(cd, formatter);
                    LocalDateTime localDateTime = LocalDateTime.parse(cd, formatter);
                    DateTimeFormatter myPattern = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
                    String localDateTimeToString = localDateTime.format(myPattern);

                    System.out.println(dateTime);
                    System.out.println(localDateTimeToString);

                    cell = row.createCell(9);
                    cell.setCellValue(localDateTimeToString.substring(0, 10));

                    cell = row.createCell(10);
//                cell.setCellValue("Y");
                    cell.setCellValue(localDateTimeToString);


                    cell = row.createCell(11);
                    cell.setCellValue("Y");
                }
            }
            try {
                File xlsFile = new File("/Users/misonaru/Desktop/" + texts + "_" + datetime + ".xls");
                FileOutputStream fileOut = new FileOutputStream(xlsFile);
                workbook.write(fileOut);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                workbook.close();
            }
//        ArrayList<String> itemsList = new ArrayList<>();

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
        } catch (IOException e) {
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


}