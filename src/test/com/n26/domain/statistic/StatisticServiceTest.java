package com.n26.domain.statistic;

import com.n26.domain.statistic.Statistic;
import com.n26.domain.statistic.StatisticService;
import com.n26.domain.statistic.TransactionFutureException;
import com.n26.domain.statistic.TransactionOldException;
import com.n26.domain.transaction.Transaction;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class StatisticServiceTest {

    StatisticService statisticService;
    @Before
    public void init()
    {
        statisticService=new StatisticService();
    }

    @Test
    public void addHappy() throws TransactionOldException, TransactionFutureException {
        Transaction transaction=new Transaction();
        transaction.setAmount(BigDecimal.valueOf(100));
        transaction.setTimestamp(LocalDateTime.now());
        statisticService.add(transaction);
    }
    @Test(expected = TransactionOldException.class)
    public void addUnhappyOldException() throws TransactionOldException, TransactionFutureException {
        Transaction transaction=new Transaction();
        transaction.setAmount(BigDecimal.valueOf(100));
        transaction.setTimestamp(LocalDateTime.now().minusSeconds(61));
        statisticService.add(transaction);
    }
    @Test(expected = TransactionFutureException.class)
    public void addUnhappyFutureException() throws TransactionOldException, TransactionFutureException {
        Transaction transaction=new Transaction();
        transaction.setAmount(BigDecimal.valueOf(100));
        transaction.setTimestamp(LocalDateTime.now().plusSeconds(61));
        statisticService.add(transaction);
    }

    @Test
    public void getStatistics() throws TransactionOldException, TransactionFutureException {
        Transaction transaction=new Transaction();
        transaction.setAmount(BigDecimal.valueOf(100));
        transaction.setTimestamp(LocalDateTime.now());
        statisticService.add(transaction);
        transaction.setAmount(BigDecimal.valueOf(50));
        transaction.setTimestamp(LocalDateTime.now().minusSeconds(10));
        statisticService.add(transaction);
        Statistic statistic=statisticService.getStatistics();
        assertEquals("75.00",statistic.getAvg());
        assertEquals(BigDecimal.valueOf(150),statistic.getSum());
        assertEquals(Long.valueOf(2L),statistic.getCount());
        assertEquals(BigDecimal.valueOf(100),statistic.getMax());
        assertEquals(BigDecimal.valueOf(50),statistic.getMin());

    }
    @Test
    public void clear() throws TransactionOldException, TransactionFutureException {
        Transaction transaction=new Transaction();
        transaction.setAmount(BigDecimal.valueOf(100));
        transaction.setTimestamp(LocalDateTime.now());
        statisticService.add(transaction);
        transaction.setAmount(BigDecimal.valueOf(50));
        transaction.setTimestamp(LocalDateTime.now().minusSeconds(10));
        statisticService.add(transaction);
        statisticService.clear();
        Statistic statistic=statisticService.getStatistics();
        assertEquals("0.00",statistic.getAvg());
        assertEquals(BigDecimal.valueOf(0),statistic.getSum());
        assertEquals(Long.valueOf(0),statistic.getCount());
        assertEquals(BigDecimal.valueOf(0),statistic.getMax());
        assertEquals(BigDecimal.valueOf(0),statistic.getMin());
    }
}