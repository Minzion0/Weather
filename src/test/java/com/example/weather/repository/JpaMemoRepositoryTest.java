package com.example.weather.repository;

import com.example.weather.domain.Memo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
public class JpaMemoRepositoryTest {

    @Autowired
    JpaMemoRepository jpaMemoRepository;

    @Test
    void insetJapMemo (){
        //given
        Memo memo = new Memo(8, "this is Jpa memo");
        //when
        jpaMemoRepository.save(memo);
        Memo memo1 = jpaMemoRepository.findById(memo.getId()).get();
        //then
        assertEquals(memo.getId(),memo1.getId());
        assertEquals(memo.getText(),memo1.getText());


    }
}
