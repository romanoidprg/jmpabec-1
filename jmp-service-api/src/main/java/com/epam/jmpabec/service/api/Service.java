package com.epam.jmpabec.service.api;

import com.epam.jmpabec.dto.BankCard;
import com.epam.jmpabec.dto.Subscription;
import com.epam.jmpabec.dto.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface Service {
    void subscribe(BankCard card);
    Optional<Subscription> getSubscriptionByBankCardNumber(String number);
    List<User> getAllUsers();
    default double getAverageUsersAge() {
        return getAllUsers().stream().mapToLong(u ->  ChronoUnit.YEARS.between(u.getBirthday(), LocalDate.now())).average().orElse(0);
    };
    static boolean isPayableUser(User user) {
        return ChronoUnit.YEARS.between(user.getBirthday(), LocalDate.now()) > 18;
    };
    List<Subscription> getAllSubscriptionsByCondition(Predicate<Subscription> condition);
}
