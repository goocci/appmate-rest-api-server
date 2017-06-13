package com.appmate.service.login;

import com.appmate.mapper.LoginMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by uujc0207 on 2017. 3. 27..
 */
@Service("loginService")
@Transactional
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginMapper loginMapper;

    // 페이스북토큰을 사용한 로그인 후 정보 저장
    public void facebookLogin(String facebook_token){

        String get_facebook_info_url = "https://graph.facebook.com/me?fields=id,name,work&access_token="+facebook_token;

        try {

            URL facebook_Info_url = new URL(get_facebook_info_url);

            HttpURLConnection facebook_info_con = (HttpURLConnection) facebook_Info_url.openConnection();
            facebook_info_con.setRequestMethod("GET");
            facebook_info_con.setRequestProperty("Accept", "application/json");

            BufferedReader facebook_info_response = new BufferedReader(new InputStreamReader((facebook_info_con.getInputStream())));

            String facebook_info_output;
            StringBuilder facebook_info_sb = new StringBuilder();
            while ((facebook_info_output = facebook_info_response.readLine()) != null) {
                System.out.println("페이스북 정보 : "+facebook_info_output);
                facebook_info_sb.append(facebook_info_output);
            }
            JSONObject json = new JSONObject(facebook_info_sb.toString());
            String id = json.getString("id");
            String name = json.getString("name");

            System.out.println("페이스북 아이디 : "+id);
            System.out.println("페이스북 이름 : "+name);

            System.out.println("페이스북 이미지 : "+"https://graph.facebook.com/"+id+"/picture?width=256");
            String picture_url = "https://graph.facebook.com/"+id+"/picture?width=256";

            // REGISTER USER
            loginMapper.createUser(id);

            // INSERT USER FACEBOOK INFO
            loginMapper.createImage(id, picture_url);
            loginMapper.createMyFavoriteApp(id);
            loginMapper.createMySkill(id);
            for(int i = 1 ; i < 4 ; i++){
                loginMapper.createMyFavoriteZone(id, i);
            }
            loginMapper.createCurrentLocation(id);
            loginMapper.createSkillWanted(id);

            facebook_info_con.disconnect();

            // Facebook Work List
            try{
                // work 있음
                JSONArray work = json.getJSONArray("work");
                System.out.println("페이스북 경력 : "+work);

                List<String> work_list = new ArrayList<String>();
                for(int i = 0; i < work.length(); i++){

                    if(work.getJSONObject(i).optString("start_date").equals("")){
                        work_list.add("0000-00");
                    } else {
                        work_list.add(work.getJSONObject(i).getString("start_date").substring(0,7));
                    }

                    if( work.getJSONObject(i).optString("end_date").equals("")){
                        work_list.add("0000-00");
                    } else {
                        work_list.add(work.getJSONObject(i).getString("end_date").substring(0,7));
                    }

                    Object employer_name = work.getJSONObject(i).getJSONObject("employer").get("name");
                    if(employer_name.toString().equals("")){
                        work_list.add("-");
                    } else {
                        work_list.add(employer_name.toString());
                    }

                    if(work.getJSONObject(i).optJSONObject("position") == null){
                        work_list.add("-");
                    } else {
                        Object position_name = work.getJSONObject(i).getJSONObject("position").get("name");
                        work_list.add(position_name.toString());
                    }

                }

                String[] work_array = new String[work_list.size()/4];
                String one_work = null;
                int work_array_i = 0;
                for(int i = 0 ; i < work_list.size() ; i++){
                    if(i % 4 == 0){
                        one_work = work_list.get(i).toString()+ "~";
                    } else if(i % 4 == 1){
                        one_work += work_list.get(i).toString() + " ";
                    } else if(i % 4 == 2){
                        one_work += work_list.get(i).toString() + " ";
                    } else {
                        one_work += work_list.get(i).toString();
                        work_array[work_array_i] = one_work;
                        work_array_i++;
                    }
                }


                System.out.println("페이스북 경력 : "+Arrays.toString(work_array));

                loginMapper.createAboutMe(id, Arrays.toString(work_array), name);

            } catch (JSONException e){
                // work 없음
                loginMapper.createAboutMe(id, "등록된 경력이 없습니다.", name);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
