package com.yanzhen.demo.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class TestEasyExcel {
    public static void main(String[] args) {
        String filename = "f:/yz.xlsx";
//        EasyExcel.write(filename,DemoData.class).sheet("闫震创建的").doWrite(TestEasyExcel.get());
        EasyExcel.read(filename,DemoData.class,new EasyExcelListener()).sheet().doRead();
    }
    public static List<DemoData> get(){
        List<DemoData> list = new ArrayList<>();
        for (int i = 0;i < 10;i++){
            DemoData demoData = new DemoData();
            demoData.setSname("yzz"+i);
            demoData.setSno(i);
            list.add(demoData);
        }
        return list;
    }
}
