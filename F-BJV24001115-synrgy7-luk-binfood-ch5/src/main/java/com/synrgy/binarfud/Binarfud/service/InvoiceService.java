package com.synrgy.binarfud.Binarfud.service;

import java.util.UUID;

public interface InvoiceService {
    void generateInvoice(UUID userId, UUID orderId);
}
