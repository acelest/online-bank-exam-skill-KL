package com.userfront.resource;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.userfront.domain.PrimaryTransaction;
import com.userfront.domain.SavingsTransaction;
import com.userfront.domain.User;
import com.userfront.service.TransactionService;
import com.userfront.service.UserService;

@RestController
@RequestMapping("/api")
@PreAuthorize("hasRole('ADMIN')")
@Api(value = "User Management", description = "Operations pertaining to users in Online Banking System")
public class UserResource {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionService transactionService;

    @ApiOperation(value = "View a list of all users", response = List.class)
    @RequestMapping(value = "/user/all", method = RequestMethod.GET)
    public List<User> userList() {
        return userService.findUserList();
    }

    @ApiOperation(value = "View a list of primary transactions for a specific user", response = List.class)
    @RequestMapping(value = "/user/primary/transaction", method = RequestMethod.GET)
    public List<PrimaryTransaction> getPrimaryTransactionList(
            @ApiParam(value = "Username of the user to retrieve transactions for", required = true)
            @RequestParam("username") String username) {
        return transactionService.findPrimaryTransactionList(username);
    }

    @ApiOperation(value = "View a list of savings transactions for a specific user", response = List.class)
    @RequestMapping(value = "/user/savings/transaction", method = RequestMethod.GET)
    public List<SavingsTransaction> getSavingsTransactionList(
            @ApiParam(value = "Username of the user to retrieve transactions for", required = true)
            @RequestParam("username") String username) {
        return transactionService.findSavingsTransactionList(username);
    }

    @ApiOperation(value = "Enable a user account")
    @RequestMapping("/user/{username}/enable")
    public void enableUser(
            @ApiParam(value = "Username of the account to enable", required = true)
            @PathVariable("username") String username) {
        userService.enableUser(username);
    }

    @ApiOperation(value = "Disable a user account")
    @RequestMapping("/user/{username}/disable")
    public void diableUser(
            @ApiParam(value = "Username of the account to disable", required = true)
            @PathVariable("username") String username) {
        userService.disableUser(username);
    }
}
