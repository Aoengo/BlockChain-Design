pragma solidity ^0.4.25;

// import {IterableMapping} from "./IterableMapping.sol";

import "./Table.sol";


contract Proposal{
    int errorCode = -0xffff;
    int correctCode = -0xfffe;
    int nonExistence = -0xfffd;
    int existence = -0xfffc;
    string constant PROPOSAL_TABLE = "Proposal";
    event LogIncrement(int a,string b);
    uint time;
    constructor() {
	    createProposal();
    }
    function findCondition(string table_name,string key_name,string key_value)internal returns(Table,Condition){
        TableFactory tf = TableFactory(0x1001);
		Table table = tf.openTable(PROPOSAL_TABLE);
		Condition condition = table.newCondition();
		condition.EQ(key_name,key_value);
		return (table,condition);
    }
    
    function createProposal()public returns(int){
        TableFactory tf = TableFactory(0x1001);
        int res = tf.createTable(PROPOSAL_TABLE,"ProposalTitle","BelongUserID,ParticipatorsNums,DeadlineTime");
        return res;
    }
    
    function insert(string proposal_title,string belong_to,string time)public returns(int){
        TableFactory tf = TableFactory(0x1001);
        Table proposalTable = tf.openTable(PROPOSAL_TABLE);
        if(proposalTable == address(0x0)){
            return errorCode;
        }
        int exist = check(proposal_title,"ProposalTitle",proposal_title);
        int count = 0;
        if(exist == nonExistence){
            Entry proposalEntry = proposalTable.newEntry();
            proposalEntry.set("ProposalTitle",proposal_title);
            proposalEntry.set("BelongUserID",belong_to);
            proposalEntry.set("ParticipatorsNums",count);
            proposalEntry.set("DeadlineTime",time);
            int res = proposalTable.insert(proposal_title,proposalEntry);
            return res;
        }
        return existence;
    }
    
    function select(string main_value,string key_name,string key_value,string get_name)public returns(string){
        (Table table,Condition condition)= findCondition(PROPOSAL_TABLE,key_name,key_value);
		Entries entries = table.select(main_value, condition);
        if(entries.size() == 0){return "error"; }
        // emit LogIncrement(entries.size(),entries.get(0).getString(get_name));
		return entries.get(0).getString(get_name);
    }
    
    function selectInt(string main_value,string key_name,string key_value,string get_name)public returns(int){
        (Table table,Condition condition)= findCondition(PROPOSAL_TABLE,key_name,key_value);
		Entries entries = table.select(main_value, condition);
        if(entries.size() == 0){return errorCode; }
		return entries.get(0).getInt(get_name);
    }
    
    function check(string main_value,string key_name,string key_value)public returns(int){
        (Table table,Condition condition)= findCondition(PROPOSAL_TABLE,key_name,key_value);
  		Entries entries = table.select(main_value,condition);
        if(entries.size() == 0){
            return nonExistence; 
        }
        return existence;
    }
    
    function remove(string main_value,string key_name,string key_value) public returns(int){
        (Table table,Condition condition)= findCondition(PROPOSAL_TABLE,key_name,key_value);
        int count = table.remove(main_value,condition);
        if(count > 0){return correctCode;}
        return count;
    }
    function update(string main_value,string key_name,string key_value,string new_name,string new_value)public returns (int){
        (Table table,Condition condition)= findCondition(PROPOSAL_TABLE,key_name,key_value);
        Entry entry = table.newEntry();
        entry.set(new_name,new_value);
        int count = table.update(main_value, entry,condition);
        if(count > 0){return correctCode;}
        return count;
    }
    function update(string main_value,string key_name,string key_value,string new_name,int new_value)public returns (int){
        (Table table,Condition condition)= findCondition(PROPOSAL_TABLE,key_name,key_value);
        Entry entry = table.newEntry();
        entry.set(new_name,new_value);
        int count = table.update(main_value, entry,condition);
        if(count > 0){return correctCode;}
        return count;
    }
    function getTime() view  returns(uint){
        return now;
    }
    function compareTime(string title)public returns(bool){
        uint nowTime = getTime();
        uint time = uint(selectInt(title,"ProposalTitle",title,"DeadlineTime"));
        if(nowTime < time){
            return true;
        }
        return false;
    }
    
    
}