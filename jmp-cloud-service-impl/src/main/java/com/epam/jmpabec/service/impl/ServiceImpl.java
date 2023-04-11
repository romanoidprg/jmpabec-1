package com.epam.jmpabec.service.impl;

import com.epam.jmpabec.dto.BankCard;
import com.epam.jmpabec.dto.Subscription;
import com.epam.jmpabec.dto.User;
import com.epam.jmpabec.service.api.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ServiceImpl implements Service {

    private final static Map<User, List<Subscription>> DB = new HashMap<>();

    @Override
    public void subscribe(BankCard card) {
        var subscriptions = DB.computeIfAbsent(card.getUser(), u -> new ArrayList<>());
        subscriptions.add(new Subscription(card.getNumber(), LocalDate.now()));
    }

    @Override
    public Optional<Subscription> getSubscriptionByBankCardNumber(String number) {
        return DB.values().stream().flatMap(Collection::stream).filter(s -> s.getBankcard().equalsIgnoreCase(number)).findFirst();
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(DB.keySet());
    }

    @Override
    public List<Subscription> getAllSubscriptionsByCondition(Predicate<Subscription> condition) {
        return DB.values().stream().flatMap(Collection::stream).filter(condition).collect(Collectors.toList());
    }
}
