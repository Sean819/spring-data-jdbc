package com.springjdbc.learn.service;

import com.springjdbc.learn.mapper.UserMapper;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserService {

    @Autowired
    UserMapper userMapper;

    public User getUserById(long id){
        User user = userMapper.getById(id);
        if(user == null){
            throw new RuntimeException("User not found by id");
        }
        return user;
    }
//    private JdbcTemplate jdbcTemplate;
//
//    private MailService mailService;
////    @Autowired
////    public void setMailService(MailService mailService) {
////        UserService.mailService = mailService;
////    }
////    @Autowired
////    public void setHikariDataSource(HikariDataSource dataSource) {
////        UserService.hikariDataSource = dataSource;
////    }
//
//    public UserService(@Autowired MailService mailService,@Autowired JdbcTemplate jdbcTemplate) {
//        this.mailService = mailService;
//        this.jdbcTemplate = jdbcTemplate;
//    }
//    public User getUserById(long id) {
//        return jdbcTemplate.execute((Connection conn) -> {
//            try(var ps = conn.prepareStatement("select * from users where id=?")){
//                ps.setObject(1,id);
//                try(ResultSet rs = ps.executeQuery()){
//                    if(rs.next()){
//                        return new User(rs.getLong("id"),
//                                rs.getString("email"),
//                                rs.getString("passwword"),
//                                rs.getString("name"));
//                    }
//                    throw new RuntimeException("user not found by id.");
//                }
//            }
//        });
//    }
//    public User getUserByName(String name) {
//        return jdbcTemplate.execute("select * from users where name=?",(PreparedStatement ps) -> {
//            ps.setObject(1,name);
//            try (var rs = ps.executeQuery()) {
//                if (rs.next()) {
//                    return new User( // new User object:
//                            rs.getLong("id"), // id
//                            rs.getString("email"), // email
//                            rs.getString("password"), // password
//                            rs.getString("name")); // name
//                }
//                throw new RuntimeException("user not found by id.");
//            }
//        });
//    }
//    public User getUserByEmail(String email){
//        return jdbcTemplate.queryForObject("select * from users where email=?", new Object[]{email},
//                (ResultSet rs, int rowNum) -> {
//            return new User(
//                    rs.getLong("id"),
//                    rs.getString("email"),
//                    rs.getString("password"),
//                    rs.getString("name"));
//        });
//    }
//    public long getUsersCount() {
//        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users", null, (ResultSet rs, int rowNum) -> {
//            // SELECT COUNT(*)查询只有一列，取第一列数据:
//            return rs.getLong(1);
//        });
//    }
//    public List<User> getUsers(int pageIndex) {
//        int limit = 100;
//        int offset = limit * (pageIndex - 1);
//        return jdbcTemplate.query("SELECT * FROM users LIMIT ? OFFSET ?", new Object[] { limit, offset },
//                new BeanPropertyRowMapper<>(User.class));
//    }
//
//    public void updateUser(User user){
//        if(1 != jdbcTemplate.update("update user set name=? where id=?", user.getName(), user.getId())){
//            throw new RuntimeException("User not found by id");
//        }
//    }
//    @Transactional
//    public User register(String email, String password, String name){
//        KeyHolder holder = new GeneratedKeyHolder();
//        if(1 != jdbcTemplate.update((conn) -> {// 参数1:PreparedStatementCreator
//            // 创建PreparedStatement时，必须指定RETURN_GENERATED_KEYS:
//            var ps = conn.prepareStatement("INSERT INTO users(email,password,name) VALUES(?,?,?)",
//                    Statement.RETURN_GENERATED_KEYS);
//            ps.setObject(1, email);
//            ps.setObject(2, password);
//            ps.setObject(3, name);
//            return ps;
//        }, holder)){// 参数2:KeyHolder
//            throw new RuntimeException("Insert failed.");
//        }
//        // 从KeyHolder中获取返回的自增值:
//        return new User(holder.getKey().longValue(), email, password, name);
//    }

//    public User login(String email, String password) throws SQLException {
////        for (User user : users) {
////            if (user.getEmail().equalsIgnoreCase(email) && user.getPassword().equals(password)) {
////                mailService.sendLoginMail(user);
////                return user;
////            }
////        }
//        try(Connection conn = hikariDataSource.getConnection()){
//            try(PreparedStatement ps = conn.prepareStatement("select * from dbuser where dbemail=? and dbpassword=?;")){
//                ps.setObject(1,email);
//                ps.setObject(2,password);
//                try(ResultSet rs = ps.executeQuery()){
//                    if(rs.next()){
//                        User user = new User(rs.getInt("id"),rs.getString("dbemail"),
//                                rs.getString("dbpassword"),rs.getString("dbname"));
//                        mailService.sendLoginMail(user);
//                        System.out.println(user);
//                        return user;
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        throw new RuntimeException("login failed.");
//    }

//    public User getUser(long id) {
//        return this.users.stream().filter(user -> user.getId() == id).findFirst().orElseThrow();
//    }

//    @MetricTime("register")
//    public User register(String email, String password, String name) throws SQLException {
////        users.forEach((user) -> {
////            if (user.getEmail().equalsIgnoreCase(email)) {
////                throw new RuntimeException("email exist.");
////            }
////        });
////        User user = new User(users.stream().mapToLong(u -> u.getId()).max().getAsLong() + 1, email, password, name);
////        users.add(user);
////        mailService.sendRegistrationMail(user);
//        User user;
//        try (Connection conn = hikariDataSource.getConnection()) {
//            PreparedStatement ps = conn.prepareStatement("select * from dbuser where dbemail=?;");
//            ps.setObject(1, email);
//            try (ResultSet rs = ps.executeQuery()) {
//                if (!rs.next()) {
//                    ps = conn.prepareStatement("insert into dbuser (dbemail, dbpassword, dbname) values (?,?,?)", Statement.RETURN_GENERATED_KEYS);
//                    ps.setObject(1, email);
//                    ps.setObject(2, password);
//                    ps.setObject(3, name);
//                    ps.executeUpdate();
//                    try (ResultSet rs1 = ps.getGeneratedKeys()) {
//                        if (rs1.next()) {
//                            System.out.println("333333333");
//                            long id = rs1.getLong(1); // 注意：索引从1开始
//                            user = new User(4, email, password, name);
//                            mailService.sendRegistrationMail(user);
//                            return user;
//                        }
//                    }catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    throw new RuntimeException("email exist.");
//                }
//            } finally {
//                ps.close();
//            }
//        }
//        throw new RuntimeException("register failed.");
//    }

}
