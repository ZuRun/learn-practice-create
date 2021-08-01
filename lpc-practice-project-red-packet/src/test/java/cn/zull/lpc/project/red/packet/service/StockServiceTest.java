package cn.zull.lpc.project.red.packet.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author jared.zu
 * @date 2021/2/12 20:25:23
 */
@RunWith(SpringRunner.class)
public class StockServiceTest {

    @Test
    public void test() {
        List mockList = Mockito.mock(List.class);
        mockList.add("a");
        mockList.clear();

        Mockito.when(mockList.add(1)).thenAnswer();
        System.out.println(mockList.get(2));
    }
}