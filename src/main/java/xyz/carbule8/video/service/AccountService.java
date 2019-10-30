package xyz.carbule8.video.service;

import com.github.pagehelper.Page;
import xyz.carbule8.video.pojo.Account;
import xyz.carbule8.video.pojo.AccountExample;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

public interface AccountService {
    List<Account> findAll();

    Page<Account> findAll(xyz.carbule8.video.pojo.Page page);

    List<Account> findAll(AccountExample example);

    Page<Account> findAll(String query, xyz.carbule8.video.pojo.Page page);

    Account findById(Integer id) throws AccountNotFoundException;

    Account findByUsernameAndPassword(String username, String password) throws AccountNotFoundException;

    void insert(Account account);

    void delete(Integer id);

    void update(Account account);
}
