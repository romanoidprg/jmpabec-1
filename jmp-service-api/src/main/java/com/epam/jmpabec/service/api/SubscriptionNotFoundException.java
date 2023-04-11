package com.epam.jmpabec.service.api;

public class SubscriptionNotFoundException extends Exception{
    public SubscriptionNotFoundException(){
        super("Subscription is not found!");
    }
}
