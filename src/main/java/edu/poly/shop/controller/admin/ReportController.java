package edu.poly.shop.controller.admin;

import edu.poly.shop.model.Report;
import edu.poly.shop.service.OrderService;
import edu.poly.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/report/data")
public class ReportController {
    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);

    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    @GetMapping()
    public Map<String, Object> getReport() {
        Map<String, Object> response = new HashMap<>();

        List<Report> revenueByMonth = orderService.getRevenueByMonth();
        if (revenueByMonth != null && !revenueByMonth.isEmpty()) {
            String[] labels = revenueByMonth.stream()
                                            .filter(Objects::nonNull)
                                            .map(report -> {
                                                if (report.getPeriod() == null) {
                                                    logger.warn("Report period is null for report: {}", report);
                                                    return "";
                                                }
                                                return report.getPeriod();
                                            })
                                            .toArray(String[]::new);
            double[] data = revenueByMonth.stream()
                                          .filter(Objects::nonNull)
                                          .mapToDouble(report -> {
                                              if (report.getTotalRevenue() == null) {
                                                  logger.warn("Report totalRevenue is null for report: {}", report);
                                                  return 0.0;
                                              }
                                              return report.getTotalRevenue();
                                          })
                                          .toArray();
            response.put("labels", labels);
            response.put("data", data);
        } else {
            logger.warn("revenueByMonth is null or empty");
            response.put("labels", new String[0]);
            response.put("data", new double[0]);
        }

        List<Object[]> countProCate = productService.getTotalProductsAndCategories();
        if (countProCate != null && !countProCate.isEmpty() && countProCate.get(0) != null) {
            int totalProducts = ((Number) countProCate.get(0)[0]).intValue();
            int totalCategories = ((Number) countProCate.get(0)[1]).intValue();
            response.put("totalpro", totalProducts);
            response.put("totalcate", totalCategories);
        } else {
            logger.warn("countProCate is null or empty");
            response.put("totalpro", 0);
            response.put("totalcate", 0);
        }

        return response;
    }
}
