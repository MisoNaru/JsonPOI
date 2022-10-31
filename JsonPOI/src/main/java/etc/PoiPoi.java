package etc;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class PoiPoi {
    public static void main(String[] args) throws IOException, ParseException {

        String clientId = "GUaWvVMcEKIocayHZo3l"; //애플리케이션 클라이언트 아이디
        String clientSecret = "209scpyuN0"; //애플리케이션 클라이언트 시크릿

        String text = null;
        try {
            text = URLEncoder.encode("감기", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패",e);
        }
        String apiURLBlog = "https://openapi.naver.com/v1/search/blog.json?query=" + text +"&sort=date&display=2";    // JSON 결과
        Map<String, String> requestHeaders = new HashMap<>();

        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        String responseBodyBlog = get(apiURLBlog,requestHeaders);

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(responseBodyBlog);
        JSONArray items = (JSONArray) jsonObject.get("items");

        ArrayList<String> itemsList = new ArrayList<>();

//        System.out.println("items:"+items.toString());

        HSSFWorkbook workBook = new HSSFWorkbook();

        CellStyle defaultStyle = workBook.createCellStyle();

        // 시트 생성 및 셀 높이 설정
        HSSFSheet sheet = workBook.createSheet();
        sheet.setDefaultRowHeightInPoints(30);


        for (int i=0; i< items.size(); i++) {
            JSONObject itemsInfo = (JSONObject) items.get(i);
            String link = (String) itemsInfo.get("link");
            String postdate = (String) itemsInfo.get("postdate");

            itemsList.add(link);
            itemsList.add(postdate);
            Row row = sheet.createRow(i);



            for (int j=0; j < itemsInfo.size(); j++) {
                Cell cell = row.createCell(j);
                cell.setCellStyle(defaultStyle);
//                cell.setCellValue("셀 생성 (" + i + ", " + j + ")");
                cell.setCellValue(itemsInfo.toString());
                sheet.setColumnWidth(j, 3000);
            }
            System.out.println("link" + i + ":" + link);
        }

        try {
            File xlsFile = new File("/Users/misonaru/Desktop/xxxtest.xls");
            FileOutputStream fileOut = new FileOutputStream(xlsFile);
            workBook.write(fileOut);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            workBook.close();
        }
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