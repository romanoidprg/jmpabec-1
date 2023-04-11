package com.epam.jmpabec.bank.impl;

import com.epam.jmpabec.bank.api.Bank;
import com.epam.jmpabec.dto.*;

import java.util.UUID;

public class OldBankImpl implements Bank {
    @Override
    public BankCard createBankCard(User user, BankCardType type) {
        if (BankCardType.DEBIT.equals(type)) {
            return new DebitBankCard(UUID.randomUUID().toString(), user);
        }
        return new CreditBankCard(UUID.randomUUID().toString(), user);
    }

}
