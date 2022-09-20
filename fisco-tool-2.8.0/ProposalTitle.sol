pragma solidity ^0.4.25;

// import {IterableMapping} from "./IterableMapping.sol";

import "./Table.sol";
pragma experimental ABIEncoderV2;

contract ProposalTitle{
    int errorCode = -0xffff;
    int correctCode = -0xfffe;
    int nonExistence = -0xfffd;
    int existence = -0xfffc;
    string constant PROPOSAL_TITLE_TABLE = "ProposalTitle";
    event LogIncrement(int a,string b);
    
    constructor() {
	    createProposalTitle();
    }
    function findCondition(string table_name,string key_name,string key_value)internal returns(Table,Condition){
        TableFactory tf = TableFactory(0x1001);
		Table table = tf.openTable(PROPOSAL_TITLE_TABLE);
		Condition condition = table.newCondition();
		condition.EQ(key_name,key_value);
		return (table,condition);
    }
    
    function createProposalTitle()public returns(int){
        TableFactory tf = TableFactory(0x1001);
        int res = tf.createTable(PROPOSAL_TITLE_TABLE,"Index","Title,BelongUserID,DeadlineTime");
        return res;
    }
    
    function insert(string title,string belong_to,string time)public returns(int){
        TableFactory tf = TableFactory(0x1001);
        Table proposalTable = tf.openTable(PROPOSAL_TITLE_TABLE);
        if(proposalTable == address(0x0)){
            return errorCode;
        }
        
        int exist = check(title);
        if(exist == nonExistence){
            Entry proposalEntry = proposalTable.newEntry();
            proposalEntry.set("Index","TAG");
            proposalEntry.set("Title",title);
            proposalEntry.set("BelongUserID",belong_to);
            proposalEntry.set("DeadlineTime",time);
            int res = proposalTable.insert("TAG",proposalEntry);
            return res;
        }
        return existence;
        
    }
    
    function getnums()public returns(int){
        (Table table,Condition condition) = findCondition("TAG","Index","TAG");
		Entries entries = table.select("TAG", condition);
		return entries.size();
    }
    function select(string key_name,string key_value,string get_name)public returns(string[]){
        (Table table,Condition condition)= findCondition(PROPOSAL_TITLE_TABLE,key_name,key_value);
		Entries entries = table.select("TAG", condition);
        string[] memory title_list = new string[](uint256(entries.size()));
		for(int i = 0;i<entries.size(); i++){
		    title_list[uint256(i)] = entries.get(i).getString(get_name); 
		}
		return title_list;
    }
    function show(string get_name,string get_time)public returns(string[],string[]){
        (Table table,Condition condition) = findCondition(PROPOSAL_TITLE_TABLE,"Index","TAG");
        Entries entries = table.select("TAG", condition);
		string[] memory title_list = new string[](uint256(entries.size()));
		string[] memory time_list = new string[](uint256(entries.size()));
		for(int i = 0;i<entries.size(); i++){
		    title_list[uint256(i)] = entries.get(i).getString(get_name); 
		    time_list[uint256(i)] = entries.get(i).getString(get_time); 
		}
		return (title_list,time_list);
    }
    
    function check(string title)public returns(int){
        (Table table,Condition condition)= findCondition(PROPOSAL_TITLE_TABLE,"Title",title);
  		Entries entries = table.select("TAG",condition);
        if(entries.size() == 0){
            return nonExistence; 
        }
        return existence;
    }
    
    function remove(string title) public returns(int){
        (Table table,Condition condition)= findCondition(PROPOSAL_TITLE_TABLE,"Title",title);
        int count = table.remove("TAG",condition);
        if(count > 0){return correctCode;}
        return errorCode;
    }
//     function update(string main_value,string key_name,string key_value,string new_name,string new_value)public returns (int){
//         (Table table,Condition condition)= findCondition(PROPOSAL_TABLE,key_name,key_value);
//         Entry entry = table.newEntry();
//         entry.set(new_name,new_value);
//         int count = table.update(main_value, entry,condition);
//         if(count > 0){return errorCode;}
//         return count;
//     }
    // function test()public returns(int){
    //     return remove("1","UserName","1");
    // }
    
    
}