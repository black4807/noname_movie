package it.company.noname.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import org.springframework.stereotype.Service;

import it.company.noname.domain.MovieRequestVO;
import it.company.noname.domain.MovieVO;

@Service
public class movieServiceImpl implements movieService {

	@Override
	public List<MovieVO> searchMovie(MovieRequestVO movieRequestVO) {
		String clientId = "";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "";//애플리케이션 클라이언트 시크릿값";
        try {
            String text = URLEncoder.encode(movieRequestVO.getQuery(), "UTF-8");
            String amount = (movieRequestVO.getDisplay() != null ? "&display=" + movieRequestVO.getDisplay() : "");
            String pageNum = (movieRequestVO.getStart() != null ? "&start=" + movieRequestVO.getStart() : "");
            String genre = (movieRequestVO.getGenre() != null ? "&genre=" + movieRequestVO.getGenre() : "");
            String country = (movieRequestVO.getCountry() != null ? "&country=" + movieRequestVO.getCountry() : "");
            
            
            String apiURL = "https://openapi.naver.com/v1/search/movie?query="+ text + amount + pageNum + genre + country; // json 결과
            System.out.println("apiURL : " + apiURL);
            
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
           
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));

            }
            
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                //response.append(inputLine);
                System.out.println("inputLine: " + inputLine);
            }
            br.close();
            System.out.println(response.toString());
        } catch (Exception e) {
            System.out.println(e);
        }

		return null;
	}

}