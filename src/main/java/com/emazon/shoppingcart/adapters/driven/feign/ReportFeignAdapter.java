package com.emazon.shoppingcart.adapters.driven.feign;

import com.emazon.shoppingcart.domain.spi.IReportClient;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ReportFeignAdapter implements IReportClient {

    private final IReportFeignClient reportFeignClient;

    @Override
    public void saveReport() {
        reportFeignClient.createReport();
    }

    @Override
    public void updateReport(String status) {
        reportFeignClient.updateReport(status);
    }
}
