package org.voting.fisco.controller;

import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import org.springframework.web.bind.annotation.*;
import org.voting.fisco.contract.VotingObject;
import org.voting.fisco.tool.Tool;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Objects;


@RestController
public class UserInitController {
    int errorCode = -0xffff;
    int correctCode = -0xfffe;
    int nonExistence = -0xfffd;
    int existence = -0xfffc;
    /**
     * description:register new User
     * method:register
     * create time: 2:47 PM 2022/3/17
     * create by: gabriel
     */
    @RequestMapping(value="/sign_up",method = RequestMethod.POST)
    public boolean register(@RequestBody HashMap<String, String> map) throws ContractException {
        BigInteger res1 = VotingObject.voting.checkUser(map.get("username"));
        VotingObject.voting.insertUser(map.get("username"), "222", map.get("password"));
        BigInteger res2 = VotingObject.voting.checkUser(map.get("username"));
        return !Objects.equals(res1, res2);
    }
    /**
     * description:login the Account
     * method:login
     * create time: 2:48 PM 2022/3/17
     * create by: gabriel
     */
    @RequestMapping(value="/sign_in",method = RequestMethod.POST)
    public boolean login(@RequestBody HashMap<String, String> map) throws ContractException {
        BigInteger res = VotingObject.voting.verifyUser(map.get("username"),map.get("password"));
        return res.intValue() == correctCode;
    }
}
