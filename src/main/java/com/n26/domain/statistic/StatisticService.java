package com.n26.domain.statistic;

import com.n26.domain.transaction.Transaction;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This is the logic of the program
 */
@Component
public class StatisticService {
    /**
     * We just wanted the last 60 second statistics.
     * While the length of the array is constant, both memory and time complexity are constant and are O(1)
     */
    private Statistic[] statistics=new Statistic[60];

    /**
     * This is the add method.It adds the transaction information to our statistics.
     * The method should be thread safe because of changing data in the statistics
     * @param transaction is the input transaction
     * @throws TransactionOldException may happen if the transaction is old
     * @throws TransactionFutureException may happen if the transaction is for the future
     */
    public synchronized void add(Transaction transaction) throws TransactionOldException, TransactionFutureException {
        if(transaction.getTimestamp().isBefore(LocalDateTime.now().minusSeconds(60)))
            throw new TransactionOldException("transaction is older than 60 seconds");
        else if(transaction.getTimestamp().isAfter(LocalDateTime.now()))
            throw new  TransactionFutureException("the transaction date is in the future");
        if(!transaction.getTimestamp().isBefore(LocalDateTime.now().minusSeconds(60)))
        {
            int arrayIndex = transaction.getTimestamp().getSecond();
            Statistic statistic;
            if(statistics[arrayIndex]==null)
            {
                statistic = new Statistic();
                statistic=fillStatisticWithTransaction(statistic,transaction);
            }
            else
            {
                statistic=statistics[arrayIndex];
                if(statistic.getLastUpdate().isBefore(LocalDateTime.now().minusSeconds(60)))
                {
                    statistic=fillStatisticWithTransaction(statistic,transaction);
                }
                else {
                    statistic.setSum(statistic.getSum().add(transaction.getAmount()));
                    statistic.setMax(transaction.getAmount().max(statistic.getMax()));
                    statistic.setMin(transaction.getAmount().min(statistic.getMin()));
                    statistic.setCount(statistic.getCount()+1);
                    statistic.setLastUpdate(transaction.getTimestamp());
                }

            }
            statistics[arrayIndex]=statistic;
        }
    }
    private Statistic fillStatisticWithTransaction(Statistic statistic,Transaction transaction)
    {
        statistic.setSum(transaction.getAmount());
        statistic.setMax(transaction.getAmount());
        statistic.setMin(transaction.getAmount());
        statistic.setCount(1L);
        statistic.setLastUpdate(transaction.getTimestamp());
        return statistic;
    }

    /**
     * This method deletes all the statistics
     */
    public void clear()
    {
        statistics=new Statistic[60];
    }

    /**
     * This method is for getting the statistics. It also updates the old information.
     * The method should be thread safe because of changing data in the statistics
     * @return the statistics of the last 60 seconds
     */
    public synchronized Statistic getStatistics()
    {
       Statistic statistic= new Statistic();
        List<Statistic> statisticLists=Arrays.stream(statistics).filter(statistic1 -> statistic1 !=null)
                .filter(statistic1 -> statistic1.getLastUpdate().isAfter(LocalDateTime.now().minusSeconds(60))).collect(Collectors.toList());
       statistic.setSum(statisticLists.stream().map(Statistic::getSum).reduce(BigDecimal::add)
               .orElse(BigDecimal.valueOf(0)));
       statistic.setCount(statisticLists.stream().map(Statistic::getCount).reduce((count1, count2) -> count1+count2)
               .orElse(0L));
       statistic.setMax(statisticLists.stream().map(Statistic::getMax).max(BigDecimal::compareTo)
               .orElse(BigDecimal.valueOf(0)));
       statistic.setMin(statisticLists.stream().map(Statistic::getMin).min(BigDecimal::compareTo)
               .orElse(BigDecimal.valueOf(0)));
       return statistic;
    }
}
