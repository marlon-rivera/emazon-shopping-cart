package com.emazon.shoppingcart.adapters.driven.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;

@FeignClient(name = "emazon-report", url = "http://localhost:8085/report")
public interface IReportFeignClient{

    @PostMapping("/")
    void createReport();

    @PostMapping("/update")
    void updateReport(String status);

}
