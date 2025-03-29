package com.userfront.resource;

import com.userfront.domain.PrimaryAccount;
import com.userfront.domain.SavingsAccount;
import com.userfront.domain.User;
import com.userfront.service.TransactionService;
import com.userfront.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/transfer")
@Api(value = "Transfer Management", description = "Operations pertaining to transfers in Online Banking System")
public class TransferResource {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    @ApiOperation(value = "Transfer money between accounts")
    @PostMapping("/between-accounts")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> betweenAccounts(
            @RequestBody Map<String, Object> request,
            Authentication authentication
    ) {
        try {
            String fromAccount = (String) request.get("fromAccount");
            String toAccount = (String) request.get("toAccount");
            Double amount = Double.parseDouble(request.get("amount").toString());

            User user = userService.findByUsername(authentication.getName());
            PrimaryAccount primaryAccount = user.getPrimaryAccount();
            SavingsAccount savingsAccount = user.getSavingsAccount();

            transactionService.betweenAccountsTransfer(fromAccount, toAccount, amount.toString(), primaryAccount, savingsAccount);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
} 