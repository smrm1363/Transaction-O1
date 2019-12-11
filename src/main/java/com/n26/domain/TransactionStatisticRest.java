package com.n26.domain;

import com.n26.domain.statistic.Statistic;
import com.n26.domain.statistic.StatisticService;
import com.n26.domain.statistic.TransactionFutureException;
import com.n26.domain.statistic.TransactionOldException;
import com.n26.domain.transaction.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;

@RestController
@RequestMapping()
public class TransactionStatisticRest extends HttpServlet {

    private final StatisticService statisticService;

    @Autowired
    public TransactionStatisticRest(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @PostMapping("/transactions")
    public ResponseEntity transactions(@RequestBody @Validated Transaction transaction) throws TransactionOldException, TransactionFutureException {
        statisticService.add(transaction);
        return new ResponseEntity(HttpStatus.CREATED);
    }
    @GetMapping("/statistics")
    public ResponseEntity<Statistic> getStatistics()
    {
        return new ResponseEntity<Statistic>(statisticService.getStatistics(), HttpStatus.OK);
    }
    @DeleteMapping("/transactions")
    public ResponseEntity delete()
    {
        statisticService.clear();
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
