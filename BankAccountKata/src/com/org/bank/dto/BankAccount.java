package com.org.bank.dto;

import java.math.BigDecimal;
import java.util.List;

public class BankAccount {

	private Long bankAccountId;
	
	private Long userId;
	
	private BigDecimal balance;
	
	private BigDecimal overDraftAllowed;

	private List<AccountStatement> accountStatementList;
	
	public Long getBankAccountId() {
		return bankAccountId;
	}

	public void setBankAccountId(Long bankAccountId) {
		this.bankAccountId = bankAccountId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getOverDraftAllowed() {
		return overDraftAllowed;
	}

	public void setOverDraftAllowed(BigDecimal overDraftAllowed) {
		this.overDraftAllowed = overDraftAllowed;
	}

	public List<AccountStatement> getAccountStatementList() {
		return accountStatementList;
	}

	public void setAccountStatementList(List<AccountStatement> accountStatementList) {
		this.accountStatementList = accountStatementList;
	}

	@Override
	public boolean equals(Object obj) {

		BankAccount bankAccountToCompare = (BankAccount) obj;
		
		if (bankAccountToCompare !=null 
				&& bankAccountToCompare.getBankAccountId()!=null
				&& bankAccountToCompare.getBankAccountId().equals(this.bankAccountId)
				&& bankAccountToCompare.getUserId()!=null
				&& bankAccountToCompare.getUserId().equals(this.userId))
			return true;
		return false;
	}
	
	
}
