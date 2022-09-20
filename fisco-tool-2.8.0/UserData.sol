
pragma solidity ^0.4.25;

// import {IterableMapping} from "./IterableMapping.sol";
pragma experimental ABIEncoderV2;
import "./Table.sol";

import "./Tool.sol";
contract UserData{
    //用户操作
    int errorCode = -0xffff;
    int correctCode = -0xfffe;
    int nonExistence = -0xfffd;
    int existence = -0xfffc;
    string constant USER_DATA_TABLE = "UserData";
    Tool tool = new Tool();
    event LoginsertIncrement(int b);
    
  
    constructor() {
	    createUserData();
    }
    function findCondition(string table_name,string key_name,string key_value)internal returns(Table,Condition){
        TableFactory tf = TableFactory(0x1001);
		Table table = tf.openTable(USER_DATA_TABLE);
		Condition condition = table.newCondition();
		condition.EQ(key_name,key_value);
		return (table,condition);
    }
    function createUserData()public returns(int){
        TableFactory tf = TableFactory(0x1001);
        int res = tf.createTable(USER_DATA_TABLE,"ID","VotedTitle,VotedBallot");
        emit LoginsertIncrement(res);
        return res;
    }
    function insert(string id,string proposal_title,string proposal_ballot)public returns(int){
        TableFactory tf = TableFactory(0x1001);
        Table userTable = tf.openTable(USER_DATA_TABLE);
        if(userTable == address(0x0)){
            return errorCode;
        }
        if(check(id,proposal_title) == nonExistence){
            Entry userEntry = userTable.newEntry();
            userEntry.set("ID",id);
            userEntry.set("VotedTitle",proposal_title);
            userEntry.set("VotedBallot",proposal_ballot);
            int res = userTable.insert(id,userEntry);
            return res;
        }
        return existence;
    }
    function check(string main_value,string proposal_title)public returns(int){
        (Table table,Condition condition)= findCondition(USER_DATA_TABLE,"VotedTitle",proposal_title);
 		Entries entries = table.select(main_value, condition);
 		if(entries.size() == 0){
            return nonExistence; 
		}
		return existence;
    }
    function select(string main_value,string key_name,string key_value,string get_name)public returns(string){
        (Table table,Condition condition)= findCondition(USER_DATA_TABLE,key_name,key_value);
		Entries entries = table.select(main_value, condition);
        if(entries.size() == 0){return "error"; }
        // emit LogIncrement(entries.size(),entries.get(0).getString(get_name));
		return entries.get(0).getString(get_name);
    }
    function remove(string main_value,string key_name,string key_value) public returns(int){
        (Table table,Condition condition)= findCondition(USER_DATA_TABLE,key_name,key_value);
        int count = table.remove(main_value,condition);
        if(count > 0){
            return correctCode;
        }
        return count;
    }
    function show(string id)public view returns(string[],string[]){
        (Table table,Condition condition) = findCondition(USER_DATA_TABLE,"ID",id);
        Entries entries = table.select(id, condition);
		string[] memory title_list = new string[](uint256(entries.size()));
		string[] memory ballot_list = new string[](uint256(entries.size()));
		for(int i = 0;i<entries.size(); i++){
		    title_list[uint256(i)] = entries.get(i).getString("VotedTitle"); 
		    ballot_list[uint256(i)] = entries.get(i).getString("VotedBallot");
		}
		return (title_list,ballot_list);
    }
}