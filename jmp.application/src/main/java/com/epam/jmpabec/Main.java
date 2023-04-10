package com.epam.jmpabec;

import com.epam.jmpabec.bank.impl.BankImpl;
import com.epam.jmpabec.dto.BankCard;
import com.epam.jmpabec.dto.BankCardType;
import com.epam.jmpabec.dto.Subscription;
import com.epam.jmpabec.dto.User;
import com.epam.jmpabec.service.impl.ServiceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        final var dart = new User("Dart", "Veider", LocalDate.of(1920, 4, 1));
        final var sasha = new User("Sasha", "Pushkin", LocalDate.of(1799, 6, 6));
        final var chewbacca = new User("Chew", "Vuki", LocalDate.of(3545, 1, 1));
        var users = List.of(dart, sasha, chewbacca);
        var cards = new ArrayList<BankCard>();

        final var prior = new BankImpl();
        final var service = new ServiceImpl();

        users.forEach(u -> {
            cards.add(prior.createBankCard(u, BankCardType.DEBIT));
            cards.add(prior.createBankCard(u, BankCardType.CREDIT));
        });

        cards.forEach(c -> {
            try {
                Thread.sleep(2000);
                service.subscribe(c);
                System.out.println("Card with number " + c.getNumber() + " subscribed.");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        service.getAllUsers().forEach(u -> System.out.println(u.getName() + u.getSurname() + u.getBirthday()));
        cards.forEach(c -> System.out.println(c.getNumber()+c.getUser()));
        service.getSubscriptionByBankCardNumber(cards.get(0).getNumber()).ifPresentOrElse(Main::prSscr, Main::prNoSscr);
        service.getSubscriptionByBankCardNumber(cards.get(2).getNumber()).ifPresentOrElse(Main::prSscr, Main::prNoSscr);
        service.getSubscriptionByBankCardNumber(cards.get(5).getNumber()).ifPresentOrElse(Main::prSscr, Main::prNoSscr);
    }

    private static void prSscr(Subscription subscription) {
        System.out.println("Subscription found: card - " + subscription.getBankcard() + ", start Date - " + subscription.getStartDate());
    }

    private static void prNoSscr() {
        System.out.println("No subscription found");
    }
}