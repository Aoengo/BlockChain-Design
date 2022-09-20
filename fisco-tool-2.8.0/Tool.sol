pragma solidity ^0.4.25;

contract Tool{
    function bytes32ToString(bytes32 _bytes32) public returns(string memory){
        bytes memory bytesString = new bytes(32);
        uint charCount = 0 ;
        for(uint i = 0 ; i<32;i++){
            byte char = byte(bytes32(uint(_bytes32) *2 **(8*i)));
            if(char !=0){
                bytesString[charCount] = char;
                charCount++;
            }
        }
        bytes memory bytesStringTrimmed = new bytes(charCount);
        for(uint j=0;j<charCount;j++){
            bytesStringTrimmed[j]=bytesString[j];
        }
        return string(bytesStringTrimmed);
    }


    function stringToBytes32(string memory source) public returns(bytes32 result){
        assembly{
            result := mload(add(source,32))
        }
    }    
    function uint2str(uint i) public returns (string c) {
        if (i == 0) return "0";
        uint j = i;
        uint length;
        while (j != 0){
            length++;
            j /= 10;
        }
        bytes memory bstr = new bytes(length);
        uint k = length - 1;
        while (i != 0){
            bstr[k--] = byte(48 + i % 10);
            i /= 10;
        }
        c = string(bstr);
    }
}