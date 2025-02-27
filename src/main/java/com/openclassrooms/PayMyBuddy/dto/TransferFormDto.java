package com.openclassrooms.PayMyBuddy.dto;

import com.openclassrooms.PayMyBuddy.entity.DBUser;
import lombok.Data;

import java.util.List;

@Data
public class TransferFormDto {

    private List<DBUser> connections;
    private String description;
    private double amount;
}
