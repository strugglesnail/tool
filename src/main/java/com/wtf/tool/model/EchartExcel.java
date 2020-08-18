package com.wtf.tool.model;

import com.wtf.tool.annotation.ImportExcel;

public class EchartExcel {

    @ImportExcel(index = 0)
    private String consName;

    @ImportExcel(index = 1)
    private Double accNumber;

    @ImportExcel(index = 2)
    private String enType;

    @ImportExcel(index = 3)
    private Double energy;


    public String getConsName() {
        return consName;
    }

    public void setConsName(String consName) {
        this.consName = consName;
    }

    public Double getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(Double accNumber) {
        this.accNumber = accNumber;
    }

    public String getEnType() {
        return enType;
    }

    public void setEnType(String enType) {
        this.enType = enType;
    }

    public Double getEnergy() {
        return energy;
    }

    public void setEnergy(Double energy) {
        this.energy = energy;
    }
}
