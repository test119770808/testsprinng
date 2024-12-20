package com.thehecklers.ch3test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class Ch3testApplication {

    public static void main(String[] args) {
        SpringApplication.run(Ch3testApplication.class, args);
    }

}

@RestController
@RequestMapping("/members")
class MembersApiController {
    private List<Member> members = new ArrayList<>();

    public MembersApiController() {
        members.addAll(List.of(
            new Member("testuser1","user1", 15, "010-1111-1111", "testuser1@test.com"),
            new Member("testuser2","user2", 19, "010-1111-1111", "testuser1@test.com"),
            new Member("testuser3","user3", 23, "010-1111-1111", "testuser1@test.com"),
            new Member("testuser4","user4", 36, "010-1111-1111", "testuser1@test.com"),
            new Member("testuser5","user5", 25, "010-1111-1111", "testuser1@test.com"),
            new Member("testuser6","user6", 18, "010-1111-1111", "testuser1@test.com"),
            new Member("testuser7","user7", 35, "010-1111-1111", "testuser1@test.com")
        ));
    }

    @GetMapping
    public List<Member> getMembers() {
        return members;
    }

    @GetMapping("/{userId}")
    public Optional<Member> getUserId(@PathVariable String userId) {
        for (Member member : members) {
            if(member.getUserid().equals(userId)) {
                return Optional.of(member);
            }
        }
        return Optional.empty();
    }

    @PostMapping("/new")
    public Member createUser(@RequestBody Member member) {
        member.setCreateDate(new Date());
        members.add(member);
        return member;
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Member> updateUser(@PathVariable String userId, @RequestBody Member member) {
        int membersIndex = -1;

        for (Member mem : members) {
            if(mem.getUserid().equals(userId)) {
                membersIndex = members.indexOf(mem);
                member.setCreateDate(members.get(membersIndex).getCreateDate());
                members.set(membersIndex,member);
            }
        }
        return (membersIndex == -1)
                ? new ResponseEntity<>(createUser(member), HttpStatus.CREATED)
                : new ResponseEntity<>(member, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable String userId) {
        members.removeIf(member -> member.getUserid().equals(userId));
    }

}


// Member 도메인
class Member {
    private String userid;
    private String username;
    private int age;
    private String phone;
    private String email;
    private Date createDate;

    public Member() {}
    public Member(String userid, String username, int age, String phone, String email, Date createDate) {
        this.userid = userid;
        this.username = username;
        this.age = age;
        this.phone = phone;
        this.email = email;
        this.createDate = createDate;
    }
    public Member(String userid, String username, int age, String phone, String email) {
        this(userid, username, age, phone, email, new Date());
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Date getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
