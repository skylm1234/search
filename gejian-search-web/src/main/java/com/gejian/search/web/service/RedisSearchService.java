package com.gejian.search.web.service;

import com.gejian.common.security.service.GeJianUser;
import com.gejian.common.security.util.SecurityUtils;
import com.gejian.search.common.constant.RedisConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author ：lijianghuai
 * @date ：2021-09-06 15:12
 * @description：
 */

@Component
@Slf4j
@RequiredArgsConstructor
public class RedisSearchService {

    private final StringRedisTemplate redisTemplate;

    public void setHotSearch(String content){
        Optional.ofNullable(content).ifPresent(c -> {
            redisTemplate.opsForZSet().incrementScore(RedisConstant.KEY_PREFIX + RedisConstant.SEARCH_SCORE,c,1.0D);
            redisTemplate.opsForHash().put(RedisConstant.KEY_PREFIX + RedisConstant.SEARCH_LATEST_AT,c,String.valueOf(System.currentTimeMillis()));

        });
    }

    public List<String> getHotSearchList(Integer size){
        Set<String> scores =  redisTemplate.opsForZSet().reverseRangeByScore(RedisConstant.KEY_PREFIX + RedisConstant.SEARCH_SCORE, 0, Double.MAX_VALUE);
        List<String> result = new ArrayList<>();
        Long now = System.currentTimeMillis();
        for(String score : scores){
            if(result.size() == size){
                break;
            }
            Long time = Long.parseLong(redisTemplate.<String,String>opsForHash().get(RedisConstant.KEY_PREFIX + RedisConstant.SEARCH_LATEST_AT,score));
            if ((now - time) < RedisConstant.MONTH_MILLIS) {
                result.add(score);
            } else {
                redisTemplate.opsForZSet().add(RedisConstant.KEY_PREFIX + RedisConstant.SEARCH_SCORE, score, 0D);
            }
        }
        return result;
    }

    public void setHistorySearch(String content){
        final GeJianUser user = SecurityUtils.getUser();
        if(user != null){
            redisTemplate.opsForZSet().incrementScore(RedisConstant.KEY_PREFIX + RedisConstant.SEARCH_HISTORY + user.getId(),content,System.currentTimeMillis());
        }
    }

    public Set<String> getHistorySearch(int size,int current){
        final GeJianUser user = SecurityUtils.getUser();
        if(user == null){
            return Collections.emptySet();
        }
        int offset = (current - 1 ) * size;
        return redisTemplate.opsForZSet().reverseRangeByScore(RedisConstant.KEY_PREFIX + RedisConstant.SEARCH_HISTORY + user.getId(), 0, Double.MAX_VALUE,offset,size);
    }

    public void deleteHistorySearch(String content){
        final GeJianUser user = SecurityUtils.getUser();
        if(user != null){
            redisTemplate.opsForZSet().remove(RedisConstant.KEY_PREFIX + RedisConstant.SEARCH_HISTORY + user.getId(),content);

        }
    }
}
