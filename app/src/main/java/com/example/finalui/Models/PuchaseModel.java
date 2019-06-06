package com.example.finalui.Models;

import java.io.Serializable;

public class PuchaseModel implements Serializable {
    String slipno,purchaseddate,vendor,invoiceno,invoiceamt,paymentstatus,remarks;

    public PuchaseModel(String slipno, String purchaseddate, String vendor, String invoiceno, String invoiceamt, String paymentstatus, String remarks) {
        this.slipno = slipno;
        this.purchaseddate = purchaseddate;
        this.vendor = vendor;
        this.invoiceno = invoiceno;
        this.invoiceamt = invoiceamt;
        this.paymentstatus = paymentstatus;
        this.remarks = remarks;
    }

    public String getSlipno() {
        return slipno;
    }

    public void setSlipno(String slipno) {
        this.slipno = slipno;
    }

    public String getPurchaseddate() {
        return purchaseddate;
    }

    public void setPurchaseddate(String purchaseddate) {
        this.purchaseddate = purchaseddate;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getInvoiceno() {
        return invoiceno;
    }

    public void setInvoiceno(String invoiceno) {
        this.invoiceno = invoiceno;
    }

    public String getInvoiceamt() {
        return invoiceamt;
    }

    public void setInvoiceamt(String invoiceamt) {
        this.invoiceamt = invoiceamt;
    }

    public String getPaymentstatus() {
        return paymentstatus;
    }

    public void setPaymentstatus(String paymentstatus) {
        this.paymentstatus = paymentstatus;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
