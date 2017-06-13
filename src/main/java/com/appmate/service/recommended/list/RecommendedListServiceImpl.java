package com.appmate.service.recommended.list;

import com.appmate.model.location.MyFavoriteZone;
import com.appmate.model.profile.MyFavoriteApp;
import com.appmate.model.profile.MySkill;
import com.appmate.model.recommended.list.RecommendedList;
import com.appmate.repository.location.CurrentLocationRepository;
import com.appmate.repository.location.MyFavoriteZoneRepository;
import com.appmate.repository.profile.*;
import com.appmate.service.location.MyFavoriteZoneService;
import com.appmate.service.profile.ImageService;
import com.appmate.service.profile.MySkillService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.List;

import static com.appmate.SEED256.Seed256.Decrypt;

/**
 * Created by uujc0207 on 2017. 3. 29..
 */
@Service("recommendedListService")
@Transactional
public class RecommendedListServiceImpl implements RecommendedListService{

    @Autowired
    private ImageService imageService;

    @Autowired
    private AboutMeRepository aboutMeRepository;

    @Autowired
    private MySkillRepository mySkillRepository;

    @Autowired
    private MyFavoriteAppRepository myFavoriteAppRepository;

    @Autowired
    private MyFavoriteZoneRepository myFavoriteZoneRepository;

    @Autowired
    private SkillWantedRepository skillWantedRepository;

    @Autowired
    private MyFavoriteZoneService myFavoriteZoneService;

    public List<RecommendedList> matchPeople(String user_id){

        // 최종 추천 리스트
        List<RecommendedList> recommendedLists = new ArrayList<>();

        // Apps 점수 산출

        String my_favorite_apps = myFavoriteAppRepository.findOne(user_id).getMy_favorite_apps();

        List<MyFavoriteApp> myFavoriteApps = myFavoriteAppRepository.findAll();
        LinkedList<String> people_favorite_apps_list = new LinkedList<>();
        LinkedList<String> people_favorite_apps_user_list = new LinkedList<>();

        for(int i = 0 ; i < myFavoriteApps.size() ; i++){
            if(myFavoriteApps.get(i).getUser_id().equals(user_id)){
                continue;
            } else {
                people_favorite_apps_list.add(myFavoriteApps.get(i).getMy_favorite_apps());
                people_favorite_apps_user_list.add(myFavoriteApps.get(i).getUser_id());
            }
        }

        JSONArray result_apps_arr = calculateScore(my_favorite_apps, people_favorite_apps_list, people_favorite_apps_user_list);

        System.out.println("앱 점수 결과 : ");
        System.out.println(result_apps_arr);

        // Skills 점수 산출

        String skills_wanted = skillWantedRepository.findOne(user_id).getSkills_wanted();

        List<MySkill> mySkills = mySkillRepository.findAll();
        LinkedList<String> people_skills_list = new LinkedList<>();
        LinkedList<String > people_skills_user_list = new LinkedList<>();

        for(int i = 0 ; i < mySkills.size() ; i++){
            if(mySkills.get(i).getUser_id().equals(user_id)){
                continue;
            } else {
                people_skills_list.add(mySkills.get(i).getMy_skills());
                people_skills_user_list.add(mySkills.get(i).getUser_id());
            }
        }

        JSONArray result_skills_arr = calculateScore(skills_wanted, people_skills_list, people_skills_user_list);
        System.out.println("스킬 점수 결과 : ");
        System.out.println(result_skills_arr);

        // Distance 점수 산출

        String my_first_zone_latitude = myFavoriteZoneService.findMyZones(user_id).get(0).getLatitude();
        String my_first_zone_longitude = myFavoriteZoneService.findMyZones(user_id).get(0).getLongitude();

//        String my_current_location_latitude = currentLocationRepository.findOne(user_id).getLatitude();
//        String my_current_location_longitude = currentLocationRepository.findOne(user_id).getLongitude();

        double[] my_current_location_arr = new double[2];

        if((my_first_zone_latitude.isEmpty() && my_first_zone_longitude.isEmpty())
                ||
                (Double.parseDouble(my_first_zone_latitude) < 0 || Double.parseDouble(my_first_zone_longitude) < 0)){
            // first 즐겨찾는 장소 없음 -> 추천 리스트 없음
            my_current_location_arr[0] = 9999;
            my_current_location_arr[1] = 9999;
        } else {
            // first 즐겨찾는 장소 있음
//            my_current_location_arr[0] = Double.parseDouble(Decrypt(my_first_zone_latitude));
//            my_current_location_arr[1] = Double.parseDouble(Decrypt(my_first_zone_longitude));
            my_current_location_arr[0] = Double.parseDouble(my_first_zone_latitude);
            my_current_location_arr[1] = Double.parseDouble(my_first_zone_longitude);
        }

        List<MyFavoriteZone> myFavoriteZones = myFavoriteZoneRepository.findAll();
        LinkedList<String> people_zones_list = new LinkedList<>();
        LinkedList<String> people_zones_user_list = new LinkedList<>();

        // 리스트에 위도, 경도, 유저아이디 추가
        for(int i = 0 ; i < myFavoriteZones.size() ; i++){
            if(myFavoriteZones.get(i).getUser_id().equals(user_id)){
                continue;
            } else {
                people_zones_list.add(myFavoriteZones.get(i).getLatitude());
                people_zones_list.add(myFavoriteZones.get(i).getLongitude());
                people_zones_user_list.add(myFavoriteZones.get(i).getUser_id());
            }
        }

        String[] people_zones_user_arr = new String[people_zones_user_list.size()];
        double[][] people_zones_arr = new double[people_zones_list.size()/2][2];
        int arr_i = 0;
        int zones_i = 0;
        int user_i = 0;

        // 배열에 위도, 경도, 유저아이디 넣기
        while(true){
            if(zones_i == people_zones_list.size()){
                break;
            }

            // 위도 경도가 공백일 경우(즐겨찾는 장소가 없음
            if((people_zones_list.get(zones_i).toString().isEmpty() && people_zones_list.get(zones_i+1).toString().isEmpty())
                    || // 위도 경도가 음수(-)일 경우
                    (Double.parseDouble(people_zones_list.get(zones_i)) < 0 || Double.parseDouble(people_zones_list.get(zones_i+1)) < 0)){
                // My Favorite Zones 없는 사람
                people_zones_arr[arr_i][0] = 35.450356;
                people_zones_arr[arr_i][1] = 123.798697; // 황해...
                people_zones_user_arr[arr_i] = people_zones_user_list.get(user_i);
                arr_i++;
                zones_i += 2;
                user_i++;
            } else {
                // My Favorite Zones 있는 사람
                people_zones_arr[arr_i][0] = Double.parseDouble(people_zones_list.get(zones_i));
                people_zones_arr[arr_i][1] = Double.parseDouble(people_zones_list.get(zones_i+1));
                people_zones_user_arr[arr_i] = people_zones_user_list.get(user_i);
                arr_i++;
                zones_i += 2;
                user_i++;
            }
        }

        // 거리 계산 후 제이슨배열에 아이디와 거리(제이슨객체) 함께 넣기
        JSONArray result_location_arr = new JSONArray();
        int json_arr_i = 0;
        for(int i = 0 ; i < people_zones_arr.length ; i++){
            double d = getDistance(my_current_location_arr[0], my_current_location_arr[1], people_zones_arr[i][0], people_zones_arr[i][1]);

            if(i == 0){
                System.out.println(d);
                JSONObject result_location = new JSONObject();
                result_location.put("user_id", people_zones_user_arr[i]);
                result_location.put("distance_score", 20 - d); // 거리와 점수는 반비례(20km 초과 시 마이너스)
                result_location_arr.put(result_location);
                json_arr_i++;
            } else if(people_zones_user_arr[i].equals(people_zones_user_arr[i-1])) {
                if(Double.parseDouble(result_location_arr.getJSONObject(json_arr_i-1).get("distance_score").toString()) < 20 - d) {
                    result_location_arr.remove(json_arr_i-1);
                    JSONObject result_location = new JSONObject();
                    result_location.put("user_id", people_zones_user_arr[i]);
                    result_location.put("distance_score", 20 - d); // 거리와 점수는 반비례
                    result_location_arr.put(result_location);
                } else {
                    continue;
                }
            } else {
                JSONObject result_location = new JSONObject();
                result_location.put("user_id", people_zones_user_arr[i]);
                result_location.put("distance_score", 20 - d); // 거리와 점수는 반비례
                result_location_arr.put(result_location);
                json_arr_i++;
            }
        }

        for(int i = 0 ; i < result_location_arr.length() ; i++){
            System.out.println(result_location_arr.getJSONObject(i).toString());
        }
        // 20km 초과한 경우(마이너스 점수) 제거
        int a = 0;
        while(true) {
            System.out.println(a);
            if(a == result_location_arr.length()){ // 반복문 종료 조건
                break;
            }
            if(Double.parseDouble(result_location_arr.getJSONObject(a).get("distance_score").toString()) < 0) {
                result_location_arr.remove(a); // 마이너스인 경우 삭제
            } else {
                a++; // 마이너스가 아닌 경우 다음 Object로~
                continue;
            }
        }

        System.out.println("거리 점수 결과 : ");
        System.out.println(result_location_arr);

        // 총 점수 (Apps + Skills + Distance) 산출

        String apps_user_id, skills_user_id, distance_user_id, image_name, get_image_URL;
        double total_score;
        RecommendedList recommendedList;

        for(int i = 0 ; i < result_apps_arr.length() ; i++){
            for(int j = 0 ; j < result_skills_arr.length() ; j++){
                for(int k = 0 ; k < result_location_arr.length() ; k++){
                    apps_user_id = result_apps_arr.getJSONObject(i).get("user_id").toString();
                    skills_user_id = result_skills_arr.getJSONObject(j).get("user_id").toString();
                    distance_user_id = result_location_arr.getJSONObject(k).get("user_id").toString();
                    if(apps_user_id.equals(skills_user_id)){
                        if(skills_user_id.equals(distance_user_id)){
                            total_score = Integer.parseInt(result_apps_arr.getJSONObject(i).get("score").toString())
                                    + Integer.parseInt(result_skills_arr.getJSONObject(j).get("score").toString())
                                    + Double.parseDouble(result_location_arr.getJSONObject(k).get("distance_score").toString());;

                            recommendedList = new RecommendedList();
                            recommendedList.setUser_id(result_apps_arr.getJSONObject(i).get("user_id").toString());
                            recommendedList.setNick_name(aboutMeRepository.findOne(result_apps_arr.getJSONObject(i).get("user_id").toString()).getNick_name());
                            String all_skills = mySkillRepository.findOne(result_apps_arr.getJSONObject(i).get("user_id").toString()).getMy_skills();
                            if(all_skills.equals("등록된 나의 스킬이 없습니다.") || all_skills.equals("[]")){
                                // 등록된 스킬 없음
                                String[] empty_arr = new String[0];
                                recommendedList.setPosition(empty_arr);
                            } else {
                                // 등록된 스킬 있음
                                String all_skills2 = all_skills.substring(1,all_skills.length()-1); // [ ] 제거
                                String all_skills3 = all_skills2.replaceAll(" ",""); // 공백 제거
                                String[] skills_arr = all_skills3.split(","); // 배열 변환
                                recommendedList.setPosition(skills_arr);
                            }
                            get_image_URL = imageService.getUserImageURL(result_apps_arr.getJSONObject(i).get("user_id").toString()).getImage();
                            recommendedList.setImage(get_image_URL);
                            recommendedList.setTotal_score(Math.round(total_score*100d) / 100d); // 반올림
                            recommendedLists.add(recommendedList);
                        }
                    }
                }
            }
        }

        // Sorting
        Collections.sort(recommendedLists, new TotalScoreDescCompare());

        return recommendedLists;

    }

    // 두 지점 간의 실제 거리 구하는 메소드(위도, 경도)
    private double getDistance(double sLat, double sLong, double dLat, double dLong)
    {
        final int radius=6371009;
        double uLat=Math.toRadians(sLat-dLat);
        double uLong=Math.toRadians(sLong-dLong);

        double a = Math.sin(uLat/2) * Math.sin(uLat/2) +
                Math.cos(Math.toRadians(sLong)) * Math.cos(Math.toRadians(dLong)) *
                        Math.sin(uLong/2) * Math.sin(uLong/2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double distance = radius * c;

        return distance/1000;
    }

    // 앱 or 스킬 비교 점수 산출 메소드
    private JSONArray calculateScore(String my_interest, LinkedList<String> people_interest_list, LinkedList<String> people_user_list){

        String my_interest2 = my_interest.substring(1, my_interest.length()-1); // [ ] 제거
        String my_interest3 = my_interest2.replaceAll(" ", ""); // 공백 제거
        String[] my_interest_arr;
        String no_interest;

        if(my_interest3.isEmpty()){
            // 내가 관심있는 것이 없음
            no_interest = "None!";
            my_interest_arr = no_interest.split(",");
        } else {
            // 내가 관심있는 것이 있음
            my_interest_arr = my_interest3.split(",");
        }

        String[][] people_interest_arr = new String[people_interest_list.size()][];
        String[] people_interest_user_arr = new String[people_user_list.size()];
        String people_interest;
        String people_interest2;
        String people_interest3;

        for(int i = 0 ; i < people_interest_list.size() ; i++){
            people_interest = people_interest_list.get(i).toString();
            people_interest2 = people_interest.substring(1, people_interest.length()-1); // [ ] 제거
            people_interest3 = people_interest2.replaceAll(" ", ""); // 공백 제거
            people_interest_arr[i] = people_interest3.split(",");
            people_interest_user_arr[i] = people_user_list.get(i);
        }

        JSONArray result_interest_arr = new JSONArray();

        for(int i = 0 ; i < people_interest_arr.length ; i++){
            int a = 0;
            for(int j = 0 ; j < my_interest_arr.length ; j++){
                for(int k = 0 ; k < people_interest_arr[i].length ; k++){
                    if(my_interest_arr[j].equals(people_interest_arr[i][k])){
                        a++;
                    }
                }
            }
            // 5점 만점, 40%
            JSONObject result_interest = new JSONObject();
            result_interest.put("user_id", people_interest_user_arr[i]);
            result_interest.put("score", a * 8);
            result_interest_arr.put(result_interest);
        }

        return result_interest_arr;
    }

}

// 총 점수를 내림차순으로 정렬하는 클래스
class TotalScoreDescCompare implements Comparator<RecommendedList> {

    /**
     * 내림차순(DESC)
     */
    @Override
    public int compare(RecommendedList arg0, RecommendedList arg1) {
        // TODO Auto-generated method stub
        return arg0.getTotal_score() > arg1.getTotal_score() ? -1 : arg0.getTotal_score() < arg1.getTotal_score() ? 1:0;
    }

}