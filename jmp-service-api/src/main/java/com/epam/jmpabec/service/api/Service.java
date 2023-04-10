package com.epam.jmpabec.service.api;

import com.epam.jmpabec.dto.BankCard;
import com.epam.jmpabec.dto.Subscription;
import com.epam.jmpabec.dto.User;

import java.util.List;
import java.util.Optional;

public interface Service {
    void subscribe(BankCard card);
    Optional<Subscription> getSubscriptionByBankCardNumber(String number);
    List<User> getAllUsers();
}
