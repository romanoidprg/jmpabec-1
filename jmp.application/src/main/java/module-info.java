module jmp.application {
    requires transitive jmp.bank.api;
    requires jmp.cloud.service.impl;
    requires jmp.dto;
    uses com.epam.jmpabec.bank.api.Bank;
}