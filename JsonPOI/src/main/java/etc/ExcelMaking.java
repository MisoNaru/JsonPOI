package etc;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
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

public class ExcelMaking {

    public static void main(String[] args) throws Exception {
        String clientId = "GUaWvVMcEKIocayHZo3l"; //애플리케이션 클라이언트 아이디
        String clientSecret = "209scpyuN0"; //애플리케이션 클라이언트 시크릿

        String[] diseaseCode_14 = {"copd", "기관지염", "만성기관지", "만성폐쇄성", "패색성폐질환", "폐기종", "폐쇄성질환", "폐쇄성폐질환"};

        String[] blogHeaders = {"SNS 구분", "SNS ID", "blog id 의 끝부분", "blog id 의 끝부분", "blog name", "사용자 이미지 URL", "title + description", "SNS URL", "생성일자", "생성일시", "사용여부"};

//        ArrayList<JSONArray>blogArray = new ArrayList<>();
        JSONArray blogArray = new JSONArray();



        // Workbook 생성
        Workbook xlsxWb = new XSSFWorkbook(); // Excel 2007 이상

        // *** Sheet-------------------------------------------------
        // Sheet 생성
        Sheet sheet1 = xlsxWb.createSheet("firstSheet");
        Row row = null;
        Cell cell = null;

        for (int i = 0; i < diseaseCode_14.length; i++) {
            String text = null;
            text = URLEncoder.encode(diseaseCode_14[i], "UTF-8");

            String texts = URLDecoder.decode(text, "UTF-8"); // 이렇게 해야 tesxts = 감기

            String apiURLBlog = "https://openapi.naver.com/v1/search/blog.json?query=" + text + "&sort=date&display=2";    // JSON 결과 size() 때문에 +1 해서 검색할 것

            Map<String, String> requestBlogs = new HashMap<>();

            // 블로그 정보 MAP
            requestBlogs.put("X-Naver-Client-Id", clientId);
            requestBlogs.put("X-Naver-Client-Secret", clientSecret);
            String responseBodyBlog = get(apiURLBlog, requestBlogs);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Calendar time = Calendar.getInstance();
            String datetime = sdf.format(time.getTime());

            JSONParser jsonParser = new JSONParser();
            JSONObject blogObject = (JSONObject) jsonParser.parse(responseBodyBlog);

            JSONArray items = (JSONArray) blogObject.get("items");

            blogArray.add(items);
            blogArray.add(texts);

            for (int j = 0; j < blogArray.size(); j++) {
                JSONObject blogItemObject = (JSONObject) blogArray.get(j);

                String[] arrayB = {
                        blogItemObject.get("link").toString().substring(blogItemObject.get("link").toString().lastIndexOf("/") + 1)
                        , blogItemObject.get("bloggerlink").toString().substring(blogItemObject.get("bloggerlink").toString().lastIndexOf("/") + 1)
                        , blogItemObject.get("bloggerlink").toString().substring(blogItemObject.get("bloggerlink").toString().lastIndexOf("/") + 1)
                        , blogItemObject.get("bloggername").toString()
                        , ""
                        , blogItemObject.get("title").toString() + " " + blogItemObject.get("description").toString()
                        , blogItemObject.get("link").toString()
                        , blogItemObject.get("postdate").toString()
                        , blogItemObject.get("postdate").toString() + "0000"
                        , "Y"
                };
                // 첫 번째 줄
                row = sheet1.createRow(i);
                if (i == 0) {
                    for (int k = 0; k < blogHeaders.length; k++) {
                        cell = row.createCell(k);
                        cell.setCellValue(new XSSFRichTextString(blogHeaders[k]));
                    }
                }
                // --------- //
                else {
                    for (int k = 0; k < arrayB.length; k++) {
                        cell = row.createCell(k);
                        cell.setCellValue(new XSSFRichTextString(arrayB[k]));
                    }
                }
            }
        }


        // excel 파일 저장
        try {
            File xlsFile = new File("/Users/misonaru/Desktop/test1101.xls");
            FileOutputStream fileOut = new FileOutputStream(xlsFile);
            xlsxWb.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
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
