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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class RoadToPOI_Three {
    public static void main(String[] args) throws Exception {

        String clientId = "GUaWvVMcEKIocayHZo3l"; //애플리케이션 클라이언트 아이디
        String clientSecret = "209scpyuN0"; //애플리케이션 클라이언트 시크릿

        String[] diseaseCode_1 = {"sars", "감기", "감염성비염", "급성기관염", "급성기관지염", "급성바이러스형비인두염", "급성부비동염", "급성비염"
                , "급성비인두염", "급성상기도염", "급성상인두염", "급성인두염", "급성중이염", "급성축농증", "급성코인두염", "급성편도선염"
                , "급성편도염", "급성후두염", "독감", "모세기관지염", "몸살", "몸살기", "몸으슬으슬하다", "바이러스성기관지염", "상기도감염", "신종인플루엔자", "신종플루"
                , "열감기", "인플루엔자", "인후두염", "인후염", "인후통", "중증급성호흡기증후군", "코막히다", "편도붓다", "편도선붓다", "편도선아프다", "편도아프다", "폐렴"
        };

        String[] diseaseCode_2 = {"각결막염", "각막궤양", "각막염", "결막부종", "결막염", "결막하출혈", "눈다래끼", "눈다래키", "눈다레끼", "다래끼"
                , "다래키", "다레끼", "맥립종", "산립종", "아폴로눈병", "콩다래끼", "포도막염"
        };

        String[] diseaseCode_3 = {"급성설사", "노로걸리다", "노로바이러스", "대장균감염", "로타바이러스", "바이러스성장감염", "배탈", "배탈나다"
                , "복통심하다", "살모넬라감염", "살모넬라증", "살모넬로시스", "설사계속하다", "설사동반하다", "설사병", "설사병나다", "설사증"
                , "세균성장감염", "시겔라균", "시겔라균감염", "시겔라증", "식중독", "아메바이질", "아메바종", "아메바증", "아베마증", "유행성바이러스설사"
                , "장결장염", "장염", "장출혈성대장균감염증", "장티푸스", "파라티푸스감염"};

        String[] diseaseCode_4 = {"감염성천식", "기관지성천식", "내인성천식", "복합성천식", "심장성천식", "아토피성천식", "알레르기성천식"
                , "외인성천식", "직업성천식", "천명음", "천식", "혼합성천식"
        };

        String[] diseaseCode_5 = {"건선", "습진", "아토피", "아토피성피부염", "알레르기성접촉피부염", "알레르기성피부염", "알레르기접촉피부염"
                , "알레르기피부염", "장미색비강진", "콜린성두드러기", "태열", "피부그림증", "피부묘기증", "한랭두드러기"
        };

        String[] diseaseCode_11 = {"거미막출혈", "거미막하출혈", "경뇌막", "경동맥협착", "경동맥협착증", "경막하출혈", "뇌경막하출혈", "뇌경색"
                , "뇌꽈리", "뇌내출혈", "뇌동맥", "뇌동맥경화", "뇌동맥경화증", "뇌동맥류", "뇌동정맥기형", "뇌동정맥루", "뇌색전증", "뇌실질내출혈"
                , "뇌실질출혈", "뇌연화증", "뇌일혈", "뇌정맥", "뇌정맥류", "뇌졸중", "뇌중풍", "뇌출혈", "뇌허혈", "뇌혈관", "뇌혈류검사", "뇌혈류장애"
                , "뇌혈전", "뇌혈전증", "모야모야병", "모야모야질병", "목동맥협착", "목동맥협착증", "중풍", "지주막출혈", "지주막하출혈"
        };

        String[] diseaseCode_12 = {"폐포염증", "포도상구균폐렴", "포도알균폐렴", "호산구성폐렴", "호흡기분비물전파되다", "호흡기세포융합바이러스"
                , "흉곽함몰", "흉부통증", "흡인성폐렴", "흡인폐렴", "rs바이러스", "간질성폐렴", "간질폐렴", "감염성폐렴", "객담", "결핵성폐렴"
                , "경기관지폐생검", "곰팡이전염폐렴", "과민성폐렴", "그람음성간균", "급성간질성폐렴", "급성폐렴", "기관지폐렴", "기관지확장증", "대엽성폐렴"
                , "독감바이러스성폐렴", "레지오넬라증", "레지오넬라폐렴", "리케차성폐렴", "림프구간질성폐렴", "만성폐렴", "모세기관지염", "무기폐", "미생물감염성페렴"
                , "미생물감염성폐렴", "바이러스성폐렴", "방사선폐렴", "병조성폐렴", "보행폐렴", "분비물전파되다", "비감염성폐렴", "빈호흡", "상기도증후군"
                , "색가래", "세균성폐렴", "세기관지염", "소아폐렴", "소엽 폐렴", "소엽폐렴", "신종인플루엔자폐렴", "알레르기성폐렴", "약제유발성폐렴", "연쇄상구균폐렴"
                , "염증성호흡기질환", "원발성이형폐렴", "이형폐렴", "중증폐렴", "지역사회획득폐렴", "진균성폐렴", "진균증에서의폐렴", "침강폐렴", "크라미디아폐렴"
                , "클레브지엘라폐렴", "태변흡인증후군", "폐기종", "폐농양", "폐렴", "폐렴간균", "폐렴구균", "폐렴사슬알균폐렴", "폐렴혐기성세균", "폐염증", "폐침윤", "폐포성폐렴"
        };

        String[] diseaseCode_13 = {"71수족구병", "바이러스71수족구병", "발수포성발진", "소수포", "소수포구내염", "손발입병", "손수포성발진", "수족구"
                , "수족구병", "수포구내염", "수포성발진", "에코바이러스", "엔테로바이러스", "입안궤양", "입안물집", "장바이러스", "콕사키바이러스", "통증성피부병변", "호흡기분비물"
        };

        String[] diseaseCode_14 = {"copd", "기관지염", "만성기관지", "만성폐쇄성", "패색성폐질환", "폐기종", "폐쇄성질환", "폐쇄성폐질환"};

        // 1. 최종적으로는 시트에 각각 키워드를 넣을 것
        // 2. 최종적으로는 한 시트에 한 항목에 대한 게 전부 들어가야 함. 반복문을 다시 생각해보자.
        for (String s : diseaseCode_14) {
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