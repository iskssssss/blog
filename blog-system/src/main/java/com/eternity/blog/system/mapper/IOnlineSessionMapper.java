package com.eternity.blog.system.mapper;

import com.eternity.blog.system.domain.session.OnlineSessionModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description
 * @Author eternity
 * @Date 2020/4/19 10:37
 */
@Mapper
@Repository
public interface IOnlineSessionMapper {

    /**
     * 添加session信息
     *
     * @param sessionModel session
     * @return 结果
     */
   int insertSession(OnlineSessionModel sessionModel);

    /**
     * 删除session信息
     *
     * @param sessionId sessionId
     * @return 结果
     */
   int deleteSession(String sessionId);

    /**
     * 修改session信息
     *
     * @param sessionModel session
     * @return 结果
     */
   int updateSession(OnlineSessionModel sessionModel);

    /**
     * 查询session信息
     *
     * @param sessionId sessionId
     * @return session
     */
   OnlineSessionModel selectSessionBySessionId(String sessionId);

    /**
     * 查询过期session信息
     *
     * @param expiredDate 过期时间
     * @return session
     */
   List<OnlineSessionModel> selectExpiredSessionByDate(String expiredDate);

}
