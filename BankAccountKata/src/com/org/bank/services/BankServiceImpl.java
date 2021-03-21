package com.org.bank.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.org.bank.dto.AccountStatement;
import com.org.bank.dto.BankAccount;
import com.org.bank.dto.OperationType;
import com.org.bank.exception.FunctionnalException;
import com.org.bank.exception.TechnicalException;

public class BankServiceImpl implements IBankService {

	private Map<Long, List<BankAccount>> bankAccountList;
	
	public BankServiceImpl() {
		
	}

	@Override
	public void saveBankAccount(BankAccount bankAccount) throws TechnicalException, FunctionnalException {
		if(bankAccountList==null) {
			bankAccountList = new HashMap<Long, List<BankAccount>>();
		}
		
		/** check that the bank account is valid before saving it **/
		validateBankAccount(bankAccount);
		
		/** Get all the account of the user and add the new account **/
		List<BankAccount> accounts = bankAccountList.get(bankAccount.getUserId());
		if (accounts==null){
			accounts = new ArrayList<>();
		}
		
		if(!accounts.contains(bankAccount)) {
			accounts.add(bankAccount);
		}
		
		bankAccountList.put(bankAccount.getUserId(), accounts);
	}

	private void validateBankAccount(BankAccount bankAccount) throws FunctionnalException {

		if(bankAccount==null) {
			throw new FunctionnalException("Bank account cannot be null.");
		} else if(bankAccount.getUserId()==null) {
			throw new FunctionnalException("User ID cannot be null.");
		}
		
		if(bankAccount.getBalance()==null) {
			bankAccount.setBalance(BigDecimal.valueOf(0));
		}
		
		if(bankAccount.getOverDraftAllowed()==null) {
			bankAccount.setBalance(BigDecimal.valueOf(1000));
		}
		
	}

	@Override
	public void deposit(Long userId, Long bankAccountId, BigDecimal amount) throws TechnicalException, FunctionnalException {
		/** select the bank from user ID and bank ID **/
		BankAccount bankAccount = getBankAccountFromList(userId, bankAccountId);
		
		/** update the balance of the account **/
		BigDecimal oldBalance = bankAccount.getBalance();
		bankAccount.setBalance(oldBalance.add(amount));
		
		/** add the operation to the history **/
		addAccountStatement(userId, bankAccountId, OperationType.SAVE, amount, bankAccount.getBalance());
	}

	@Override
	public void withdrawal(Long userId, Long bankAccountId, BigDecimal amount) throws TechnicalException, FunctionnalException {
		/** select the bank from user ID and bank ID **/
		BankAccount bankAccount = getBankAccountFromList(userId, bankAccountId);
		
		
		BigDecimal oldBalance = bankAccount.getBalance();
		BigDecimal overDraftAllowed = bankAccount.getOverDraftAllowed();
		
		/** Add the balance and the overdraft, if the amount to withdraw are superior to the sum then throw a functionnal exception **/
		BigDecimal balanceAndOverDraft = oldBalance.add(overDraftAllowed);
		
		if(amount.compareTo(balanceAndOverDraft) > 0) {
			throw new FunctionnalException("Overdraft allowed reached.");
		}
		
		/** update the balance of the account **/
		bankAccount.setBalance(oldBalance.subtract(amount));

		addAccountStatement(userId, bankAccountId, OperationType.WITHDRAWAL, amount, bankAccount.getBalance());
	}

	private BankAccount getBankAccountFromList(Long userId, Long bankAccountId) throws FunctionnalException {
		List<BankAccount> accountLists = this.bankAccountList.get(userId);
		Optional<BankAccount> bankAccount = accountLists.stream().filter(bank -> 
    		bank.getUserId().equals(userId) && 
    		bank.getBankAccountId().equals(bankAccountId)).findAny();
		
		if(bankAccount.isPresent()) {
			return bankAccount.get();
		} else {
			throw new FunctionnalException("Bank account does not exit for userId : " + userId + " and bankAccountId : " + bankAccountId);
		}
	}

	@Override
	public BigDecimal getBalance(Long userId, Long bankAccountId) throws TechnicalException, FunctionnalException {
		/** select the bank from user ID and bank ID **/
		BankAccount bankAccount = getBankAccountFromList(userId, bankAccountId);
		return bankAccount.getBalance();
	}

	@Override
	public void addAccountStatement(Long userId, Long bankAccountId, OperationType operationType, BigDecimal amount, BigDecimal balance)
			throws TechnicalException, FunctionnalException {
		/** select the bank from user ID and bank ID **/
		BankAccount bankAccount = getBankAccountFromList(userId, bankAccountId);
		
		/** Add the account statement to the bank account history **/
		if(bankAccount.getAccountStatementList()==null) {
			bankAccount.setAccountStatementList(new ArrayList<>());
		}
		
		AccountStatement accountStatement = new AccountStatement();
		accountStatement.setAmount(amount);
		accountStatement.setOperationType(operationType);
		accountStatement.setBalance(bankAccount.getBalance());
		accountStatement.setDate(new Date());
		
		bankAccount.getAccountStatementList().add(accountStatement);
	}

	@Override
	public List<AccountStatement> getHistory(Long userId, Long bankAccountId) throws TechnicalException, FunctionnalException{
		/** select the bank from user ID and bank ID **/
		BankAccount bankAccount = getBankAccountFromList(userId, bankAccountId);
		return bankAccount.getAccountStatementList();
	}
}
