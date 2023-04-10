package com.epam.jmpabec.bank.impl;

import com.epam.jmpabec.bank.api.Bank;
import com.epam.jmpabec.dto.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiFunction;

public class BankImpl implements Bank {
    private final Map<BankCardType, BiFunction<String, User, BankCard>> creators = new HashMap<>();
    public BankImpl() {
        creators.put(BankCardType.CREDIT, CreditBankCard::new);
        creators.put(BankCardType.DEBIT, DebitBankCard::new);
    }
    @Override
    public BankCard createBankCard(User user, BankCardType type) {
        return creators.getOrDefault(type, this::throwIfTypeIsUnknown).apply(UUID.randomUUID().toString(), user);
    }

    private BankCard throwIfTypeIsUnknown(String number, User user) {
        throw new IllegalArgumentException("Unknown card type.");
    }

    public void addCreatorForCardType(BankCardType type, BiFunction<String, User, BankCard> creator) {
        if (type != null && creator != null) creators.put(type, creator);
    }
}
