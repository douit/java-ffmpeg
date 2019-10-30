package xyz.carbule8.video.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import xyz.carbule8.video.pojo.Video;
import xyz.carbule8.video.pojo.VideoExample;

import java.util.List;

@Repository
public interface VideoMapper {
    long countByExample(VideoExample example);

    int deleteByExample(VideoExample example);

    int deleteByPrimaryKey(String vId);

    int insert(Video record);

    int insertSelective(Video record);

    List<Video> selectByExample(VideoExample example);

    Video selectByPrimaryKey(String vId);

    int updateByExampleSelective(@Param("record") Video record, @Param("example") VideoExample example);

    int updateByExample(@Param("record") Video record, @Param("example") VideoExample example);

    int updateByPrimaryKeySelective(Video record);

    int updateByPrimaryKey(Video record);
}