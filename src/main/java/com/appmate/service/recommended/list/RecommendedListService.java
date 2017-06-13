package com.appmate.service.recommended.list;

import com.appmate.model.recommended.list.RecommendedList;

import java.util.List;

/**
 * Created by uujc0207 on 2017. 3. 29..
 */
public interface RecommendedListService {

    public List<RecommendedList> matchPeople(String user_id);
}
