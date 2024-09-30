package com.emazon.shoppingcart.domain.spi;

import java.math.BigDecimal;

public interface IReportClient {

    void saveReport();
    void updateReport(String status);

}
