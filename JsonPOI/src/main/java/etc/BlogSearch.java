package etc;


import org.apache.poi.hssf.model.WorkbookRecordList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BlogSearch {

    public static void main(String[] args) throws Exception {
        String clientId = "GUaWvVMcEKIocayHZo3l"; //애플리케이션 클라이언트 아이디
        String clientSecret = "209scpyuN0"; //애플리케이션 클라이언트 시크릿

        String text = null;
        try {
            text = URLEncoder.encode("COPD", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패",e);
        }

        String apiURL = "https://openapi.naver.com/v1/search/blog.json?query=" + text +"&sort=date&display=50";    // JSON 결과
        //String apiURL = "https://openapi.naver.com/v1/search/blog.xml?query="+ text; // XML 결과

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        String responseBody = get(apiURL,requestHeaders);

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject)jsonParser.parse(responseBody);
        JSONArray items = (JSONArray) jsonObject.get("items");

        String link = "";
        String postdate = "";
        String description = "";
        String title = "";
        String bloggerlink = "";
        String bloggername = "";
        String originallink = "";
        String pubDate = "";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar time = Calendar.getInstance();
        String datetime = sdf.format(time.getTime());



        // workbook 생성
        XSSFWorkbook wb = new XSSFWorkbook();
        // 시트 생성
        XSSFSheet sheet = wb.createSheet("Blog");

        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("SNS구분");
        cell.setCellValue("bloggerlink");
        cell.setCellValue("blog name");
        cell.setCellValue("title");
        cell.setCellValue("description");
        cell.setCellValue("SNS URL");
        cell.setCellValue("생성일자");
        cell.setCellValue("생성일시");
        cell.setCellValue("사용여부");

        int cell_num = 0;
        // 엑셀 추가할 것
        for (int i = 0; i < items.size(); i++) {
            JSONObject itemsInfo = (JSONObject) items.get(i);

            String linkInfo = (String) itemsInfo.get("link");
            link += i + "." + linkInfo + "\r\n ";

            String postdateInfo = (String) itemsInfo.get("postdate");
            postdate += i + "." + postdateInfo + "\r\n ";

            String descriptionInfo = (String) itemsInfo.get("description");
            description += i + "." + descriptionInfo + "\r\n ";

            String titleInfo = (String) itemsInfo.get("title");
            title += i + "." + titleInfo + "\r\n ";

            String bloggerlinkInfo = (String) itemsInfo.get("bloggerlink");
            bloggerlink += i + "." +  bloggerlinkInfo + "\r\n ";

            String bloggernameInfo = (String) itemsInfo.get("bloggername");
            bloggername += i + "." +  bloggernameInfo + "\r\n ";

            String originallinkInfo = (String) itemsInfo.get("originallink");
            originallink += i + "." +  originallinkInfo + "\r\n ";


            String pubDateInfo = (String) itemsInfo.get("pubDate");
            pubDate += i + "." +  pubDateInfo + "\r\n ";

            for (int row_num = 1; row_num < items.size(); row_num++ ){
                sheet.createRow(row_num);
                row.createCell(cell_num);


                cell.setCellValue(link);
                cell.setCellValue(postdate);
                cell.setCellValue(title);
                cell.setCellValue(link);
                cell.setCellValue(link);
                cell.setCellValue(link);
                cell_num++;
            }
        }
        String filePath = "/Users/misonaru/Desktop";
        String fileNm = "file_test_" + datetime + ".xlsx";
        String localFile = filePath +fileNm;

        File file = new File(localFile);
        FileOutputStream fos = null;
        fos = new FileOutputStream(file);
        wb.write(fos);



//        System.out.println(responseBody);
//        System.out.println();
//        System.out.println("link:" + link );
//        System.out.println("postdate:" + postdate );
//        System.out.println("description:" + description );
//        System.out.println("title:" + title );
//        System.out.println("bloggerlink:" + bloggerlink );
//        System.out.println("bloggername:" + bloggername );
//        System.out.println("originallink:" + originallink );
//        System.out.println("pubDate:" + pubDate );

    }



    private static String get(String apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
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


    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }


    private static String readBody(InputStream body){
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