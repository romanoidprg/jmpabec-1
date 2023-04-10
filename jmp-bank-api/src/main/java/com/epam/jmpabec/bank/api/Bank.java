package com.epam.jmpabec.bank.api;

import com.epam.jmpabec.dto.BankCard;
import com.epam.jmpabec.dto.BankCardType;
import com.epam.jmpabec.dto.User;

public interface Bank {
    BankCard createBankCard(User user, BankCardType type);
}
