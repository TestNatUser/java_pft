package com.javarush.task.task33.task3308;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlType(name = "shop")
@XmlRootElement
public class Shop {
    public Goods goods = new Goods();
    public int count=12;
    public double profit=123.4;
    @XmlAnyElement
    public String[] secretData = {"String1","String2","String3","String4","String5"};

    public Shop(){

    }

    @XmlType(name = "goods")
    @XmlRootElement
    static class Goods{
        @XmlAnyElement
        List<String> names = new ArrayList<>();

        Goods(){
           names.add("S1");
           names.add("S2");
        }
    }
}
