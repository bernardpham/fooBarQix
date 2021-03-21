package com.org.bank.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;

import com.org.bank.dto.AccountStatement;
import com.org.bank.dto.BankAccount;
import com.org.bank.exception.FunctionnalException;
import com.org.bank.exception.TechnicalException;
import com.org.bank.services.BankServiceImpl;
import com.org.bank.services.IBankService;

public class BankServiceTest {

	private static final long USER_ID_1 = 123L;
	private static final long BANK_ID_1 = 1000L;
	private static final long USER_ID_2 = 222L;
	private static final long BANK_ID_2 = 2000L;
	
	private IBankService bankService = new BankServiceImpl();
	
	private void initBankDatas() throws TechnicalException, FunctionnalException {
		bankService.saveBankAccount(bankMock1());
		bankService.saveBankAccount(bankMock2());
	}
	
	private BankAccount bankMock1() {
		BankAccount bankAccount = new BankAccount();
		bankAccount.setUserId(USER_ID_1);
		bankAccount.setBalance(BigDecimal.valueOf(1000));
		bankAccount.setBankAccountId(BANK_ID_1);
		bankAccount.setOverDraftAllowed(BigDecimal.valueOf(3000));
		return bankAccount;
	}
	
	private BankAccount bankMock2() {
		BankAccount bankAccount = new BankAccount();
		bankAccount.setUserId(USER_ID_2);
		bankAccount.setBalance(BigDecimal.valueOf(-2000));
		bankAccount.setBankAccountId(BANK_ID_2);
		bankAccount.setOverDraftAllowed(BigDecimal.valueOf(3000));
		return bankAccount;
	}
	
	/**
	 * User Stories
		US 1:
		In order to save money
		As a bank client
		I want to make a deposit in my account
	 * @throws FunctionnalException 
	 * @throws TechnicalException 
	 **/
	@Test
	public void saveTest() {
		try {
			initBankDatas();
		} catch (TechnicalException | FunctionnalException e1) {
			fail();
		}
		
		BigDecimal amount = new BigDecimal(1000);
		
		try {
			bankService.deposit(USER_ID_1, BANK_ID_1, amount);
			BigDecimal balance = bankService.getBalance(USER_ID_1, BANK_ID_1);
			assertEquals(BigDecimal.valueOf(2000), balance);
		} catch (TechnicalException | FunctionnalException e) {
			fail();
		}
	}

	
/**
US 2:

In order to retrieve some or all of my savings
As a bank client
I want to make a withdrawal from my account
**/
	
	@Test
	public void withdrawalTest() {
		try {
			initBankDatas();
		} catch (TechnicalException | FunctionnalException e1) {
			fail();
		}
		
		
		try {
			BigDecimal amount = new BigDecimal(1000);
			bankService.withdrawal(USER_ID_1, BANK_ID_1, amount);
			BigDecimal balance = bankService.getBalance(USER_ID_1, BANK_ID_1);
			assertEquals(BigDecimal.valueOf(0), balance);
		} catch (TechnicalException | FunctionnalException e) {
			fail();
		}
	
		BigDecimal amount = new BigDecimal(10000);
		assertThrows(FunctionnalException.class, ()-> bankService.withdrawal(USER_ID_2, BANK_ID_2, amount));
		
	}
	
	/**
		US 3:
		
		In order to check my operations
		As a bank client
		I want to see the history (operation, date, amount, balance) of my operations
	 */
	
	@Test 
	public void checkHistory() {
		
		try {
			initBankDatas();
		} catch (TechnicalException | FunctionnalException e1) {
			fail();
		}
		
		try {
			List<AccountStatement> accountStatements = bankService.getHistory(USER_ID_1, BANK_ID_1);
			assertNull(accountStatements);
			
			BigDecimal amount = new BigDecimal(1000);
			bankService.deposit(USER_ID_1, BANK_ID_1, amount);
			
			accountStatements = bankService.getHistory(USER_ID_1, BANK_ID_1);
			assertNotNull(accountStatements);
			assertEquals(BigDecimal.valueOf(2000), accountStatements.get(0).getBalance());
			assertEquals(amount, accountStatements.get(0).getAmount());
		} catch (TechnicalException | FunctionnalException e) {
			fail();
		}
	}
}
