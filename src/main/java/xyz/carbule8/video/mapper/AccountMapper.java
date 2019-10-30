package xyz.carbule8.video.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import xyz.carbule8.video.pojo.Account;
import xyz.carbule8.video.pojo.AccountExample;

import java.util.List;

@Repository
public interface AccountMapper {
    long countByExample(AccountExample example);

    int deleteByExample(AccountExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Account record);

    int insertSelective(Account record);

    List<Account> selectByExample(AccountExample example);

    Account selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Account record, @Param("example") AccountExample example);

    int updateByExample(@Param("record") Account record, @Param("example") AccountExample example);

    int updateByPrimaryKeySelective(Account record);

    int updateByPrimaryKey(Account record);
}