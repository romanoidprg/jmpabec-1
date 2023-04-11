module jmp.cloud.bank.impl {
    requires transitive jmp.bank.api;
    requires jmp.dto;
    exports com.epam.jmpabec.bank.impl;
    provides com.epam.jmpabec.bank.api.Bank with com.epam.jmpabec.bank.impl.OldBankImpl, com.epam.jmpabec.bank.impl.NewBankImpl;
}