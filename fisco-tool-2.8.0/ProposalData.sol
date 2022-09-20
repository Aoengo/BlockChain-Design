pragma solidity ^0.4.25;

// import {IterableMapping} from "./IterableMapping.sol";
pragma experimental ABIEncoderV2;
import "./Table.sol";

import "./Tool.sol";

contract ProposalData{
    int errorCode = -0xffff;
    int correctCode = -0xfffe;
    int nonExistence = -0xfffd;
    int existence = -0xfffc;
    string constant PROPOSAL_DATA_TABLE = "ProposalData";
    Tool tool = new Tool();
    event LogIncrement(int a,string b);
    
    constructor() {
	    createProposalData();
    }
    function findCondition(string table_name,string key_name,string key_value)internal returns(Table,Condition){
        TableFactory tf = TableFactory(0x1001);
		Table table = tf.openTable(PROPOSAL_DATA_TABLE);
		Condition condition = table.newCondition();
		condition.EQ(key_name,key_value);
		return (table,condition);
    }
    function createProposalData()public returns(int){
        TableFactory tf = TableFactory(0x1001);
        int res = tf.createTable(PROPOSAL_DATA_TABLE,"ProposalTitle","Rank,BallotName,VoteCount");
        return res;
    }
    
    function insert(string proposal_title,string ballot_name)public returns(int){
        TableFactory tf = TableFactory(0x1001);
        Table proposalTable = tf.openTable(PROPOSAL_DATA_TABLE);
        if(proposalTable == address(0x0)){
            return errorCode;
        }
        int exist = check(proposal_title,ballot_name);
        int rank = getnums(proposal_title);
        int count = 0;
        if(exist == nonExistence){
            Entry proposalEntry = proposalTable.newEntry();
            proposalEntry.set("ProposalTitle",proposal_title);
            proposalEntry.set("Rank",rank);
            proposalEntry.set("BallotName",ballot_name);
            proposalEntry.set("VoteCount",count);
            int res = proposalTable.insert(proposal_title,proposalEntry);
            return res;
        }
        return existence;
    }
    
    
    function select(string main_value,string key_name,string key_value,string get_name)public returns(string){
        (Table table,Condition condition)= findCondition(PROPOSAL_DATA_TABLE,key_name,key_value);
		Entries entries = table.select(main_value, condition);
        if(entries.size() == 0){return "error"; }
        // emit LogIncrement(entries.size(),entries.get(0).getString(get_name));
		return entries.get(0).getString(get_name);
    }
    function selectInt(string main_value,string key_name,string key_value,string get_name)public returns(int){
        (Table table,Condition condition)= findCondition(PROPOSAL_DATA_TABLE,key_name,key_value);
		Entries entries = table.select(main_value, condition);
        if(entries.size() == 0){return errorCode; }
        // emit LogIncrement(entries.size(),entries.get(0).getString(get_name));
		return entries.get(0).getInt(get_name);
    }
    
    function getnums(string main_value)public view returns(int){
        (Table table,Condition condition) = findCondition(main_value,"ProposalTitle",main_value);
		Entries entries = table.select(main_value, condition);
		return entries.size();
    }
    function show(string title)public view returns(string[]){
        (Table table,Condition condition)= findCondition(PROPOSAL_DATA_TABLE,"ProposalTitle",title);
		Entries entries = table.select(title, condition);
		string[] memory title_list = new string[](uint256(entries.size()));
		for(int i = 0;i<entries.size(); i++){
		    title_list[uint256(i)] = entries.get(i).getString("BallotName"); 
		}
		return title_list;
    }
    
    function check(string main_value,string ballot_name)public returns(int){
        (Table table,Condition condition)= findCondition(PROPOSAL_DATA_TABLE,"BallotName",ballot_name);
 		Entries entries = table.select(main_value, condition);
 		if(entries.size() == 0){
            return nonExistence; 
		}
		return existence;
    }
    function remove(string main_value,string key_name,string key_value) public returns(int){
        (Table table,Condition condition)= findCondition(PROPOSAL_DATA_TABLE,key_name,key_value);
        int count = table.remove(main_value,condition);
        if(count > 0){return correctCode;}
        return count;
    }
    function update(string main_value,string key_name,string key_value,string new_name,string new_value)public returns (int){
        (Table table,Condition condition)= findCondition(PROPOSAL_DATA_TABLE,key_name,key_value);
        Entry entry = table.newEntry();
        entry.set(new_name,new_value);
        int count = table.update(main_value, entry,condition);
        if(count > 0){return correctCode;}
        return count;
    }
    function update(string main_value,string key_name,string key_value,string new_name,int new_value)public returns (int){
        (Table table,Condition condition)= findCondition(PROPOSAL_DATA_TABLE,key_name,key_value);
        Entry entry = table.newEntry();
        entry.set(new_name,new_value);
        int count = table.update(main_value, entry,condition);
        if(count > 0){return correctCode;}
        return count;
    }
    // function test()public returns(int,int,int,int,string[]){
    //     int res1 = insert("who is he","Bob");
    //     int res2 = insert("who is he","Gao");
    //     int res3 = check("who is he","Gao");
    //     int res4 = update("who is he","BallotName","Gao","BallotName","Lei");
    //     string[] memory res5 = show("who is he");
    //     return (res1,res2,res3,res4,res5);
    // }
    
}