package com.org.bank.services;

import java.math.BigDecimal;
import java.util.List;

import com.org.bank.dto.AccountStatement;
import com.org.bank.dto.BankAccount;
import com.org.bank.dto.OperationType;
import com.org.bank.exception.FunctionnalException;
import com.org.bank.exception.TechnicalException;

public interface IBankService {

	public void deposit(Long userId, Long bankAccount, BigDecimal amount) throws TechnicalException, FunctionnalException;
	
	public void withdrawal(Long userId, Long bankAccount, BigDecimal amount) throws TechnicalException, FunctionnalException;
	
	public BigDecimal getBalance(Long userId, Long bankAccount) throws TechnicalException, FunctionnalException;
	
	public void saveBankAccount(BankAccount bankAccount) throws TechnicalException, FunctionnalException;
	
	public void addAccountStatement(Long userId, Long bankAccount, OperationType operationType, BigDecimal amount, BigDecimal balance) throws TechnicalException, FunctionnalException;
	
	public List<AccountStatement> getHistory(Long userId, Long bankAccountId) throws TechnicalException, FunctionnalException;
}
