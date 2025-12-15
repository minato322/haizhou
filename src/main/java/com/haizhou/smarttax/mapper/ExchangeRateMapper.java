package com.haizhou.smarttax.mapper;

import com.haizhou.smarttax.entity.ExchangeRate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.time.LocalDate;

@Mapper
public interface ExchangeRateMapper {
    ExchangeRate findByLatest(@Param("fromCurrency") String fromCurrency, 
                              @Param("toCurrency") String toCurrency);
    ExchangeRate findByDate(@Param("fromCurrency") String fromCurrency, 
                            @Param("toCurrency") String toCurrency,
                            @Param("rateDate") LocalDate rateDate);
    int insert(ExchangeRate rate);
}
