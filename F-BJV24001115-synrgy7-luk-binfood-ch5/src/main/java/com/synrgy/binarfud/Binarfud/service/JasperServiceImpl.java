package com.synrgy.binarfud.Binarfud.service;

import com.synrgy.binarfud.Binarfud.model.Order;
import com.synrgy.binarfud.Binarfud.payload.OrderDto;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRSaver;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JasperServiceImpl implements JasperService {

    @Override
    public byte[] getReport(List<Order> orderList, String format) {
        JasperReport jasperReport;
        try {
            jasperReport = (JasperReport) JRLoader
                    .loadObject(ResourceUtils.getFile("order-list-report.jasper"));
        } catch (JRException | FileNotFoundException e) {
            try {
                File file = ResourceUtils.getFile("classpath:jasper/order-list-report.jrxml");
                jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
                JRSaver.saveObject(jasperReport, "order-list-report.jasper");
            } catch (FileNotFoundException | JRException ex) {
                throw new RuntimeException(ex);
            }
        }

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(orderList);

        Map<String, Object> params = new HashMap<>();
        params.put("total", String.valueOf(orderList.size()));

        JasperPrint jasperPrint;
        byte[] reportContent = null;

        try {
            jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);
            switch (format) {
                case "pdf" -> reportContent = JasperExportManager.exportReportToPdf(jasperPrint);
                case "xml" -> reportContent = JasperExportManager.exportReportToXml(jasperPrint).getBytes();
                default -> throw new RuntimeException();
            }
        } catch (JRException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return reportContent;
    }
}
