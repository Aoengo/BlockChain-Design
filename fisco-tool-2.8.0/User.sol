
pragma solidity ^0.4.25;

// import {IterableMapping} from "./IterableMapping.sol";

import "./Table.sol";
import "./Tool.sol";


contract User{
    //用户操作
    int errorCode = -0xffff;
    int correctCode = -0xfffe;
    int nonExistence = -0xfffd;
    int existence = -0xfffc;
    string constant USER_TABLE = "User";
    Tool tool = new Tool();
    event LoginsertIncrement(string a,int b);
    // event LogStringIncrement(string b);
  
    constructor() {
	    createUser();
    }
    
    function findCondition(string table_name,string key_name,string key_value)internal returns(Table,Condition){
        TableFactory tf = TableFactory(0x1001);
		Table table = tf.openTable(USER_TABLE);
		Condition condition = table.newCondition();
		condition.EQ(key_name,key_value);
		return (table,condition);
    }
    function createUser()public returns(int){
        TableFactory tf = TableFactory(0x1001);
        int res = tf.createTable(USER_TABLE,"ID","UserName,Password");
        return res;
    }
    
    function insert(string id,string user_name,string password)public returns(int){
        TableFactory tf = TableFactory(0x1001);
        Table userTable = tf.openTable(USER_TABLE);
        if(userTable == address(0x0)){
            return errorCode;
        }
        int exist = check(id,"ID",id);
        LoginsertIncrement(id,exist);
        if(exist == nonExistence){
            Entry userEntry = userTable.newEntry();
            userEntry.set("ID",id);
            userEntry.set("UserName",user_name);
            userEntry.set("Password",password);
            int res = userTable.insert(id,userEntry);
            emit LoginsertIncrement("in",res);
            return res;
        }
        return existence;
    }
    function select(string main_value,string key_name,string key_value,string get_value)public returns(string){
        (Table table,Condition condition)= findCondition(USER_TABLE,key_name,key_value);
 		Entries entries = table.select(main_value, condition);
        if(entries.size() == 1){
            return entries.get(0).getString(get_value);
		}
		return "error"; 
    }
    function check(string main_value,string key_name,string key_value)public returns(int){
         (Table table,Condition condition)= findCondition(USER_TABLE,key_name,key_value);
  		Entries entries = table.select(main_value,condition);
        if(entries.size() == 0){
            return nonExistence; 
        }
        return existence;
    }
    function verify(string id,string password) public returns(int){
        if(tool.stringToBytes32(select(id,"ID",id,"Password")) == tool.stringToBytes32(password)){
            return correctCode;
        }
        return errorCode;
    }
    function remove(string main_value,string key_name,string key_value) public returns(int){
        (Table table,Condition condition)= findCondition(USER_TABLE,key_name,key_value);
        int count = table.remove(main_value,condition);
        if(count > 0){
            return correctCode;
        }
        return count;
    }
    function update(string main_value,string key_name,string key_value,string new_name,string new_value)public returns (int){
        (Table table,Condition condition)= findCondition(USER_TABLE,key_name,key_value);
        Entry entry = table.newEntry();
        entry.set(new_name,new_value);
        int count = table.update(main_value, entry,condition);
        if(count > 0){return correctCode;}
        return count;
    }

}