package org.voting.fisco.controller;

import com.alibaba.fastjson.JSONArray;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple2;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple3;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import org.springframework.web.bind.annotation.*;
import org.voting.fisco.contract.Voting;
import org.voting.fisco.contract.VotingObject;
import com.alibaba.fastjson.JSONObject;
import org.voting.fisco.tool.Tool;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@SuppressWarnings("unchecked")
@RequestMapping("/user_space")
public class VoteController {
    /**
     * description:create new Proposals
     * method:setProposal
     * create time: 2:49 PM 2022/3/17
     * create by: gabriel
     */
    @PostMapping("/vote_create")
    public boolean setProposal(@RequestBody HashMap<String, Object> map) throws IllegalAccessException, ContractException, ParseException {
        List<HashMap<String, Object>> dataList;
        dataList = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH");

        Object id = map.get("ID");
        Object title = map.get("Title");
        Object voteInfo = map.get("VoteInfo");
        Object time = map.get("Time");
        Date time_obj = sdf.parse((String) time);

        JSONArray jsonArray = (JSONArray) JSONObject.toJSON(voteInfo);
        for(int i = 0;i<jsonArray.size();i++){
            JSONObject oneOfObject = jsonArray.getJSONObject(i);
            String name = oneOfObject.getString("name");
            HashMap<String, Object> map_mid = new HashMap<>();
            map_mid.put("name", name);
            dataList.add(map_mid);
        }


        long time_res = Tool.getTime(time_obj);
        BigInteger voteRes2 = VotingObject.voting.checkProposal(title.toString());
        VotingObject.voting.insertProposal(title.toString(), id.toString(), String.valueOf(time_res));
        for (int i = 0; i < jsonArray.size(); i++) {
            VotingObject.voting.insertProposalData(title.toString(), dataList.get(i).get("name").toString());
        }
        BigInteger voteRes1 = VotingObject.voting.checkProposal(title.toString());
        return !Objects.equals(voteRes1, voteRes2) && jsonArray.size() == VotingObject.voting.proposalDataGetnums(title.toString()).intValue();
    }

    /**
     * description:get Proposals basic info from blockchain
     * method:getProposalInfo
     * create time: 2:49 PM 2022/3/17
     * create by: gabriel
     */
    @PostMapping("/vote_info")
    public List<HashMap<String, Object>> getProposalInfo(@RequestBody HashMap<String, String> map) throws ContractException {
        List<HashMap<String, Object>> proposalInfo;
        proposalInfo = new ArrayList<>();
        List<String> title_res = VotingObject.voting.userProposalTitle(map.get("ID"));
        if (title_res.size() == 0) {
            return null;
        }
        for (String title_re : title_res) {
            HashMap<String, Object> map_mid = new HashMap<>();
            map_mid.put("title", title_re);
            map_mid.put("nums", VotingObject.voting.userProposalPartNums(title_re).intValue());
            proposalInfo.add(map_mid);
        }
        return proposalInfo;
    }
    /**
     * description:get Proposal Detail info from blockchain
     * method:getProposalDetailInfo
     * create time: 2:51 PM 2022/3/17
     * create by: gabriel
     */
    @PostMapping("/vote_detail_info")
    public List<HashMap<String, Object>> getProposalDetailInfo(@RequestBody HashMap<String, String> map) throws ContractException {
        Tuple3<List<String>, List<BigInteger>, List<BigInteger>> ProposalInfo;
        List<HashMap<String, Object>> infoList;
        infoList = new ArrayList<>();
        ProposalInfo = VotingObject.voting.userProposalInfo(map.get("title"));
        for (int i = 0; i < ProposalInfo.getValue1().size(); i++) {
            HashMap<String, Object> map_mid = new HashMap<>();
            map_mid.put("title", ProposalInfo.getValue1().get(i));
            map_mid.put("rank", ProposalInfo.getValue2().get(i));
            map_mid.put("vote_count", ProposalInfo.getValue3().get(i));
            infoList.add(map_mid);
        }
        return infoList;
    }
    /**
     * description:delete proposal all information
     * method:resetProposal
     * create time: 2:51 PM 2022/3/17
     * create by: gabriel
     */
    @PostMapping("/vote_reset")
    public BigInteger resetProposal(@RequestBody HashMap<String, String> map) {
        TransactionReceipt receipt = VotingObject.voting.deleteProposal(map.get("title"), map.get("id"));
        return new BigInteger(receipt.getOutput().substring(2, receipt.getOutput().length()), 16);
    }
    /**
     * description:get all Proposals Project List
     * method:getProposalList
     * create time: 2:52 PM 2022/3/17
     * create by: gabriel
     */
    @PostMapping("/vote_proposals")
    public Tuple2<List<String>, List<String>> getProposalList() throws ContractException {
        return VotingObject.voting.getProposalTitle();
    }
    /**
     * description:get all Ballots List
     * method:getBallotList
     * create time: 2:52 PM 2022/3/17
     * create by: gabriel
     */
    @PostMapping("/vote")
    public List<String> getBallotList(@RequestBody HashMap<String, String> map) throws ContractException {
        return VotingObject.voting.getProposalBallot(map.get("title"));
    }
    /**
     * description:chose a ballot to vote
     * method:voteChose
     * create time: 2:53 PM 2022/3/17
     * create by: gabriel
     */
    @PostMapping("/vote_chose")
    public BigInteger voteChose(@RequestBody HashMap<String, String> map) throws ContractException {
        TransactionReceipt res = VotingObject.voting.userVoting(map.get("title"), map.get("name"), map.get("id"));
        return new BigInteger(res.getOutput().substring(2, res.getOutput().length()), 16);
    }
    /**
     * description:get the result of vote
     * method:voteResult
     * create time: 2:53 PM 2022/3/17
     * create by: gabriel
     */
    @PostMapping("/vote_result")
    public List<HashMap<String, Object>> voteResult(@RequestBody HashMap<String, String> map) throws ContractException {
        //给id
        Tuple2<List<String>, List<String>> ProposalInfo;
        List<HashMap<String, Object>> infoList;
        infoList = new ArrayList<>();
        ProposalInfo = VotingObject.voting.userCheckProposal(map.get("id"));
        for (int i = 0; i < ProposalInfo.getValue1().size(); i++) {
            HashMap<String, Object> map_mid = new HashMap<>();
            map_mid.put("title", ProposalInfo.getValue1().get(i));
            map_mid.put("ballot", ProposalInfo.getValue2().get(i));
            map_mid.put("state", VotingObject.voting.checkProposal(ProposalInfo.getValue1().get(i)));
            infoList.add(map_mid);
        }
        return infoList;
    }
    /**
     * description:delete the invalid Proposal from yourself space
     * method:voteUserResultDelete
     * create time: 2:54 PM 2022/3/17
     * create by: gabriel
     */
    @PostMapping("/vote_result_delete")
    public BigInteger voteUserResultDelete(@RequestBody HashMap<String, String> map) {
        TransactionReceipt receipt = VotingObject.voting.userDeleteVoteInfo(map.get("title"), map.get("id"));
        return new BigInteger(receipt.getOutput().substring(2, receipt.getOutput().length()), 16);
    }

    /**
     * description:get the result of Proposal and get your Vote Ballot
     * method:voteUserResultCheck
     * create time: 2:55 PM 2022/3/17
     * create by: gabriel
     */
    @PostMapping("/vote_result_check")
    public HashMap<Object, Object> voteUserResultCheck(@RequestBody HashMap<String, String> map) throws ContractException {
        Tuple3<List<String>, List<BigInteger>, List<BigInteger>> ProposalInfo;
        List<HashMap<String, Object>> infoList;
        infoList = new ArrayList<>();
        HashMap<Object, Object> result = new HashMap<>();
        ProposalInfo = VotingObject.voting.userProposalInfo(map.get("title"));
        for (int i = 0; i < ProposalInfo.getValue1().size(); i++) {
            HashMap<String, Object> map_mid = new HashMap<>();
            map_mid.put("ballot", ProposalInfo.getValue1().get(i));
            map_mid.put("rank", ProposalInfo.getValue2().get(i));
            map_mid.put("vote_count", ProposalInfo.getValue3().get(i));
            infoList.add(map_mid);
        }
        String voteTar = VotingObject.voting.userFindBallotInfo(map.get("title"),map.get("id"));
        result.put("proposal",infoList);
        result.put("voteWho",voteTar);
        return result;
    }

    @PostMapping("/user_vote_state")
    public boolean userVoteStateCheck(@RequestBody HashMap<String, String> map) throws ContractException {
        return VotingObject.voting.userGetVoteState(map.get("id"),map.get("title"));
    }
}
