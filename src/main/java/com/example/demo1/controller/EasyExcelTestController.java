package com.example.demo1.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.enums.CellExtraTypeEnum;
import com.example.demo1.entity.CustomerExcelModel;
import com.example.demo1.entity.DownloadData;
import com.example.demo1.listener.CustomerDataListener;
import com.example.demo1.listener.MultiHeaderListener;
import com.example.demo1.listener.MultiHeaderModel;
import com.example.demo1.repo.entityrepo.CustomRepo;
import com.example.demo1.service.CustomerSvc;
import com.example.demo1.utils.EasyExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

@RestController
@RequestMapping(value = "/easyExcel")
public class EasyExcelTestController {

    @Autowired
    private CustomRepo customRepo;

    private CustomerSvc customerSvc;

    @Autowired
    public void setCustomerSvc(CustomerSvc customerSvc) {
        this.customerSvc = customerSvc;
    }

    @GetMapping("/download")
    public void download(HttpServletResponse response) throws IOException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("测试download", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), DownloadData.class).sheet("模板1").doWrite(data());
    }

    @GetMapping("/download2")
    public void download2(HttpServletResponse response) throws IOException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("customer_download", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), CustomerExcelModel.class).sheet("customers").doWrite(customerSvc.selectCustomers());
    }

    @PostMapping("/upload")
    public String upload(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), CustomerExcelModel.class, new CustomerDataListener(customRepo)).sheet().doRead();
        return "success";
    }

    @PostMapping("/upload2")
    public String upload2(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), CustomerExcelModel.class, EasyExcelUtil.getListener(batchInsert(), 5)).sheet().doRead();
        return "success";
    }

    @PostMapping("/upload3")
    public String upload3(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), MultiHeaderModel.class, new MultiHeaderListener()).headRowNumber(2).extraRead(CellExtraTypeEnum.MERGE).sheet().doRead();
        return "success";
    }

    // Consumer是要对泛型对象进行的操作
    private Consumer<List<CustomerExcelModel>> batchInsert() {
        return customerList -> customerSvc.saveCustomers(customerList);
    }

    private List<DownloadData> data() {
        List<DownloadData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DownloadData data = new DownloadData();
            data.setString("字符串" + 0);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }
}
