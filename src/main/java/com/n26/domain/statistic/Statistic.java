package com.n26.domain.statistic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.n26.util.PriceJsonSerializer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

/**
 * This is an entity for keeping statistic
 */
public class Statistic {
    private BigDecimal sum =new BigDecimal(0);
    private BigDecimal max = new BigDecimal(0);
    private BigDecimal min = new BigDecimal(0);
    private Long count = 0L;
    @JsonIgnore
    private LocalDateTime lastUpdate = LocalDateTime.now();
    @JsonSerialize(using= PriceJsonSerializer.class)
    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    /**
     *
     * @return the average of statistic
     */
    public String getAvg() {
        if(count==0)
            return BigDecimal.valueOf(0).setScale(2,RoundingMode.UNNECESSARY).toString();
        return sum.divide(BigDecimal.valueOf(count),2,RoundingMode.HALF_UP).toString();
    }


    @JsonSerialize(using= PriceJsonSerializer.class)
    public BigDecimal getMax() {
        return max;
    }

    public void setMax(BigDecimal max) {
        this.max = max;
    }
    @JsonSerialize(using= PriceJsonSerializer.class)
    public BigDecimal getMin() {
        return min;
    }

    public void setMin(BigDecimal min) {
        this.min = min;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
