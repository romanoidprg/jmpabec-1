package com.epam.jmpabec.app;

import com.epam.jmpabec.bank.api.Bank;
import com.epam.jmpabec.dto.BankCard;
import com.epam.jmpabec.dto.BankCardType;
import com.epam.jmpabec.dto.Subscription;
import com.epam.jmpabec.dto.User;
import com.epam.jmpabec.service.api.Service;
import com.epam.jmpabec.service.api.SubscriptionNotFoundException;
import com.epam.jmpabec.service.impl.ServiceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        final var dart = new User("Dart", "Veider", LocalDate.of(1920, 4, 1));
        final var sasha = new User("Sasha", "Pushkin", LocalDate.of(2020, 6, 6));
        final var chewbacca = new User("Chew", "Vuki", LocalDate.of(233, 1, 1));
        var users = List.of(dart, sasha, chewbacca);
        var cards = new ArrayList<BankCard>();

        ServiceLoader<Bank> banks = ServiceLoader.load(Bank.class);
        System.out.println("Bank implementations:");
        banks.forEach(b -> System.out.println(b.getClass().toString()));
        Bank prior = banks.iterator().next();
        final var service = new ServiceImpl();

        users.forEach(u -> {
            cards.add(prior.createBankCard(u, BankCardType.DEBIT));
            cards.add(prior.createBankCard(u, BankCardType.CREDIT));
        });

        cards.forEach(c -> {
            try {
                Thread.sleep(500);
                service.subscribe(c);
                System.out.println("Card with number " + c.getNumber() + " subscribed.");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        System.out.println("All users:");
        service.getAllUsers().forEach(System.out::println);

        System.out.println("Playable users:");
        service.getAllUsers().stream().filter(Service::isPayableUser).collect(Collectors.toUnmodifiableList()).forEach(System.out::println);
        System.out.println();

        System.out.println("Average users age is: " + service.getAverageUsersAge());
        System.out.println();

        cards.forEach(c -> System.out.println(c.getNumber() + " " + c.getUser()));
        System.out.println();

        service.getSubscriptionByBankCardNumber(cards.get(0).getNumber()).ifPresentOrElse(Main::prSscr, Main::prNoSscr);
        service.getSubscriptionByBankCardNumber(cards.get(2).getNumber()).ifPresentOrElse(Main::prSscr, Main::prNoSscr);
        service.getSubscriptionByBankCardNumber(cards.get(5).getNumber()).ifPresentOrElse(Main::prSscr, Main::prNoSscr);
        System.out.println();

        try {
            service.getSubscriptionByBankCardNumber("no_existable_number").orElseThrow(SubscriptionNotFoundException::new);
        } catch (SubscriptionNotFoundException e) {
            System.out.println(e.getMessage());;
        }
        System.out.println();

        System.out.println("Subscriptions created in spring:");
        Predicate<Subscription> condition = s -> s.getStartDate().getMonth().getValue() >= 2;
        service.getAllSubscriptionsByCondition(condition).forEach(Main::prSscr);
    }

    private static void prSscr(Subscription subscription) {
        System.out.println("Subscription found: card - " + subscription.getBankcard() + ", start Date - " + subscription.getStartDate());
    }

    private static void prNoSscr() {
        System.out.println("No subscription found");
    }
}