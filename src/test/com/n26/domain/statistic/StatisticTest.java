package com.n26.domain.statistic;

import com.n26.domain.statistic.Statistic;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class StatisticTest {

    @Test
    public void getAvg() {
        Statistic statistic = new Statistic();
        statistic.setSum(BigDecimal.valueOf(100));
        statistic.setCount(2L);
        assertEquals("50.00",statistic.getAvg());
    }
}