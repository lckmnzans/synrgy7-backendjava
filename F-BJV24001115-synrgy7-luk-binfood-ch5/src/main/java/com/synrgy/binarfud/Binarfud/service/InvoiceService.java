package com.synrgy.binarfud.Binarfud.service;

import java.util.UUID;

public interface InvoiceService {
    byte[] generateInvoice(String userId, String orderId);
}
