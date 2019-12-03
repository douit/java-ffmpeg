package xyz.carbule8.video.service;

import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.carbule8.video.mapper.AccountMapper;
import xyz.carbule8.video.pojo.Account;
import xyz.carbule8.video.pojo.AccountExample;
import xyz.carbule8.video.service.AccountService;
import xyz.carbule8.video.util.SystemUtils;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public List<Account> findAll() {
        return null;
    }

    @Override
    public Page<Account> findAll(xyz.carbule8.video.pojo.Page page) {
        return null;
    }

    @Override
    public List<Account> findAll(AccountExample example) {
        return null;
    }

    @Override
    public Page<Account> findAll(String query, xyz.carbule8.video.pojo.Page page) {
        return null;
    }

    @Override
    public Account findById(Integer id) throws AccountNotFoundException {
       return null;
    }

    @Override
    public Account findByUsernameAndPassword(String username, String password) throws AccountNotFoundException {
        password = SystemUtils.encryptPassword(password, username);
        AccountExample example = new AccountExample();
        example.createCriteria().andUsernameEqualTo(username).andPasswordEqualTo(password);
        List<Account> accounts = accountMapper.selectByExample(example);
        if (accounts.isEmpty()) {
            throw new AccountNotFoundException("用户名密码不正确");
        }
        return accounts.get(0);
    }

    @Override
    public void insert(Account account) {
        account.setPassword(SystemUtils.encryptPassword(account.getPassword(), account.getUsername()));
        accountMapper.insert(account);
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public void update(Account account) {

    }
}
