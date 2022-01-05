package study.datajpa.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.datajpa.repository.ItemRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemTest {

    @Autowired ItemRepository itemRepository;
    @Test
    public void test() throws Exception{
        //given
        Item item = new Item("A");
        //when
        itemRepository.save(item);
        //then
    }
}