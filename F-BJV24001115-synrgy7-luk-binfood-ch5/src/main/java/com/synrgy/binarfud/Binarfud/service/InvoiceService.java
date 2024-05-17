package com.synrgy.binarfud.Binarfud.service;

public interface InvoiceService {
    byte[] generateInvoice(String userId, String orderId);
}
