// SPDX-License-Identifier: GPL-3.0

pragma solidity ^0.4.25;

pragma experimental ABIEncoderV2;
// import {IterableMapping} from "./IterableMapping.sol";

import "./Table.sol";
import "./User.sol";
import "./Proposal.sol";
import "./UserData.sol";
import "./ProposalData.sol";
import "./ProposalTitle.sol";
contract Voting{
    int errorCode = -0xffff;
    int correctCode = -0xfffe;
    int nonExistence = -0xfffd;
    int existence = -0xfffc;
    // struct User{
    //     string test1;
    //     string test2;
    // }
//  event createEvent(bytes32 userID, bytes32 title,ProposalData[] ballot);
// 	event insertEvent(bytes32 UserID, bytes32 courseName, int score);
// 	event updateEvent(bytes32 UserID, bytes32 courseName, int score);
// 	event removeEvent(bytes32 UserID, bytes32 courseName);
 	event LogIncrement(int a);
    Tool tool = new Tool();
	User user = new User();
	UserData userData = new UserData();
	Proposal proposal = new Proposal();
	ProposalData proposalData = new ProposalData();
	ProposalTitle proposalTitle = new ProposalTitle();
	function insertUser(string id,string name,string password) public returns(int){
	    return user.insert(id,name,password);
	}
	function checkUser(string main_value)external view returns(int){
	    return user.check(main_value,"ID",main_value);
	}
	function verifyUser(string id,string password)external view returns(int){
	    return user.verify(id,password);
	}
	//ok
	function insertProposal(string title,string belongTo,string time)external returns(int){
	    int res1 = proposalTitle.insert(title,belongTo,time);
	    int res2 = proposal.insert(title,belongTo,time);
	    if(res1 ==1 && res2 ==1){return correctCode;}
	    return errorCode;
	}
	function checkProposal(string main_value)external view returns(int){
	    return proposal.check(main_value,"ProposalTitle",main_value);
	}
	
	function insertProposalData(string title,string ballot_name)external returns(int){
	    return proposalData.insert(title,ballot_name);
	}
	//参选项目用数量校验
	function proposalDataGetnums(string title)external view returns(int){
	    return proposalData.getnums(title);
	}
	
	//ok,知道标题就知道属于谁
	function getProposal(string title,string key_name,string key_value,string belongTo)public returns(string){
	    return proposal.select(title,key_name,key_value,belongTo);
	}
	function getProposalTitle()public view returns(string[],string[]){
	    return proposalTitle.show("Title","DeadlineTime");
	}
	function getProposalBallot(string title) public view returns(string[]){
	    return proposalData.show(title);
	}
	//id 是投票人id
	function userVoting(string title,string ballot,string id)external returns(int){
	    if(proposal.compareTime(title) == true){
	        if(userData.check(id,title) == nonExistence && userData.insert(id,title,ballot) == 1){
    	       int res = proposal.selectInt(title,"ProposalTitle",title,"ParticipatorsNums");
    	       int vote_count = proposalData.selectInt(title,"BallotName",ballot,"VoteCount");
    	       proposal.update(title,"ProposalTitle",title,"ParticipatorsNums",++res);
    	       proposalData.update(title,"BallotName",ballot,"VoteCount",++vote_count);
    	       return -correctCode;
	        }   
	    }
	    return -errorCode;
	}
	function userProposalTitle(string id)public view returns(string[]){
	    return proposalTitle.select("BelongUserID",id,"Title");
	}
	
	function userProposalPartNums(string title)public view returns(int){
        return proposal.selectInt(title,"ProposalTitle",title,"ParticipatorsNums");
	}
	function userProposalInfo(string title)public view returns(string[],int[],int[]){
	    int length = proposalData.getnums(title);
	    string[] memory ballot_res = new string[](uint(length));
	    int[] memory rank_res = new int[](uint(length));
	    int[] memory vote_count_res = new int[](uint(length));
	    ballot_res = proposalData.show(title);
	    for(uint i=0;i<uint(length);i++){
	        rank_res[i] = proposalData.selectInt(title,"BallotName",ballot_res[i],"Rank");
	        vote_count_res[i] = proposalData.selectInt(title,"BallotName",ballot_res[i],"VoteCount");
	    }
	    return (ballot_res,rank_res,vote_count_res);
	}
	function deleteProposal(string title,string id)external returns(bool){
	    if(proposal.remove(title,"BelongUserID",id) == correctCode){
	        int res1 = proposalData.remove(title,"ProposalTitle",title);
	        int res2 = proposalTitle.remove(title);
	        if(res1 != 0 &&res2 != 0){
	            return true;
	        }
	        return false;
	    }
	    return false;
	}
	function userCheckProposal(string id)external view returns(string[],string[]){
	    return userData.show(id);
	}
	function userDeleteVoteInfo(string title,string id) external returns(int){
	    return -userData.remove(id,"VotedTitle",title);
	}
	function userFindBallotInfo(string title,string id)external view returns(string){
	    return userData.select(id,"VotedTitle",title,"VotedBallot");
	}
	function userGetVoteState(string id,string title)external view returns(bool){
	    if(userData.check(id,title) == nonExistence){
	        return true;
	    }
	    return false;
	}

}

