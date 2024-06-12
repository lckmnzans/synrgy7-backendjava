package com.synrgy.binarfud.Binarfud.payload;

import com.synrgy.binarfud.Binarfud.model.Users;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class OrderDto {
    private String orderId;
    private String userName;
    private String destinationAddress;
    private List<OrderDetailDto> orderDetailList;

    public void setUser(Users user) {
        this.userName = user.getUsername();
    }

    public void setId(UUID id) {
        this.orderId = id.toString();
    }
}
