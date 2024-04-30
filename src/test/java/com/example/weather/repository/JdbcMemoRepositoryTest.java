package com.example.weather.repository;

import com.example.weather.domain.Memo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class JdbcMemoRepositoryTest {

    @Autowired
   JdbcMemoRepository jdbcMemoRepository;

    @Test
    void insertMemeTest(){
        //given
        Memo memo = new Memo(2, "this is memo");
        jdbcMemoRepository.save(memo);
        //when
        Memo memo1 = jdbcMemoRepository.findById(2).orElseThrow(() -> new NullPointerException());

        //then
        assertEquals(memo.getId(),memo1.getId());
        assertEquals(memo.getText(),memo1.getText());
    }

    @Test
    void findAllMemoTest (){
        //given
        List<Memo> all = jdbcMemoRepository.findAll();
        //when

        //then
        assertNotNull(all);
        assertEquals(all.size(),1);
    }
}