package com.synrgy.binarfud.Binarfud.payload;

import com.synrgy.binarfud.Binarfud.model.Users;
import lombok.Data;

import java.util.List;

@Data
public class OrderDto {
    private String userName;
    private String destinationAddress;
    private List<OrderDetailDto> orderDetailDtoList;
}
