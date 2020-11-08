package com.bilyoner.assignment.balanceapi.controller.impl;

import com.bilyoner.assignment.balanceapi.controller.BalanceController;
import com.bilyoner.assignment.balanceapi.model.UpdateBalanceRequest;
import com.bilyoner.assignment.balanceapi.model.UserBalanceDTO;
import com.bilyoner.assignment.balanceapi.service.BalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/balances")
public class BalanceControllerImpl implements BalanceController {

    private final BalanceService balanceService;

    @GetMapping("{userId}")
    public ResponseEntity<UserBalanceDTO> getUserBalanceWithHistory(@PathVariable Long userId) {
        return ResponseEntity.ok(balanceService.getUserBalanceWithHistory(userId));
    }

    @PutMapping
    public void updateBalance(@Valid @RequestBody UpdateBalanceRequest updateBalanceRequest) {
        balanceService.updateBalance(updateBalanceRequest);
    }
}
