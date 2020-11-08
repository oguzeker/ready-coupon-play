package com.bilyoner.assignment.balanceapi.controller;

import com.bilyoner.assignment.balanceapi.configuration.SwaggerConfiguration;
import com.bilyoner.assignment.balanceapi.model.UpdateBalanceRequest;
import com.bilyoner.assignment.balanceapi.model.UserBalanceDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Api(tags = {
        SwaggerConfiguration.TAG_BALANCE
})
public interface BalanceController {

    @ApiOperation(value = "Get User Balance and History Records", notes = "This endpoint retrieves user balance " +
            "entities including their history records")
    ResponseEntity<UserBalanceDTO> getUserBalanceWithHistory(@ApiParam(value = "Id of the user balance entity to " +
            "retrieve balance info of", example = "1") @PathVariable Long userId);

    @ApiOperation(value = "Update User Balance", notes = "This endpoint updates balance of the user")
    void updateBalance(@ApiParam(value = "Details of the user balance entity to update")
                       @Valid @RequestBody UpdateBalanceRequest updateBalanceRequest);

}
