package com.bilyoner.assignment.balanceapi.service;

import com.bilyoner.assignment.balanceapi.model.UpdateBalanceRequest;
import com.bilyoner.assignment.balanceapi.model.UserBalanceDTO;

public interface BalanceService {

    UserBalanceDTO getUserBalanceWithHistory(Long userId);

    void updateBalance(UpdateBalanceRequest updateBalanceRequest);

}
