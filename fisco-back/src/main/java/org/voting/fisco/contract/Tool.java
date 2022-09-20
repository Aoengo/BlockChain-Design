package org.voting.fisco.contract;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.fisco.bcos.sdk.abi.FunctionReturnDecoder;
import org.fisco.bcos.sdk.abi.TypeReference;
import org.fisco.bcos.sdk.abi.datatypes.Function;
import org.fisco.bcos.sdk.abi.datatypes.Type;
import org.fisco.bcos.sdk.abi.datatypes.Utf8String;
import org.fisco.bcos.sdk.abi.datatypes.generated.Bytes32;
import org.fisco.bcos.sdk.abi.datatypes.generated.Uint256;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple1;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.contract.Contract;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.model.CryptoType;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.model.callback.TransactionCallback;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

@SuppressWarnings("unchecked")
public class Tool extends Contract {
    public static final String[] BINARY_ARRAY = {"608060405234801561001057600080fd5b506105c1806100206000396000f300608060405260043610610057576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680639201de551461005c578063cfb5192814610106578063f76f950e1461018b575b600080fd5b34801561006857600080fd5b5061008b6004803603810190808035600019169060200190929190505050610231565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156100cb5780820151818401526020810190506100b0565b50505050905090810190601f1680156100f85780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34801561011257600080fd5b5061016d600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610430565b60405180826000191660001916815260200191505060405180910390f35b34801561019757600080fd5b506101b66004803603810190808035906020019092919050505061043e565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156101f65780820151818401526020810190506101db565b50505050905090810190601f1680156102235780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b60608060008060006060600060206040519080825280601f01601f1916602001820160405280156102715781602001602082028038833980820191505090505b50955060009450600093505b602084101561033b578360080260020a886001900402600102925060007f010000000000000000000000000000000000000000000000000000000000000002837effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff191614151561032e578286868151811015156102f557fe5b9060200101907effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916908160001a90535084806001019550505b838060010194505061027d565b846040519080825280601f01601f19166020018201604052801561036e5781602001602082028038833980820191505090505b509150600090505b8481101561042257858181518110151561038c57fe5b9060200101517f010000000000000000000000000000000000000000000000000000000000000090047f01000000000000000000000000000000000000000000000000000000000000000282828151811015156103e557fe5b9060200101907effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916908160001a9053508080600101915050610376565b819650505050505050919050565b600060208201519050919050565b6060600080606060008086141561048c576040805190810160405280600181526020017f3000000000000000000000000000000000000000000000000000000000000000815250945061058c565b8593505b6000841415156104b6578280600101935050600a848115156104ae57fe5b049350610490565b826040519080825280601f01601f1916602001820160405280156104e95781602001602082028038833980820191505090505b5091506001830390505b60008614151561058857600a8681151561050957fe5b066030017f01000000000000000000000000000000000000000000000000000000000000000282828060019003935081518110151561054457fe5b9060200101907effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916908160001a905350600a8681151561058057fe5b0495506104f3565b8194505b505050509190505600a165627a7a723058209c093563a3fe7d2685274ae2016ed0ff5deacd232088ea3703ea29d8bad347f30029"};

    public static final String BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {"608060405234801561001057600080fd5b506105c1806100206000396000f300608060405260043610610057576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063446a1fe11461005c5780634ef4f309146101065780637287846d1461018b575b600080fd5b34801561006857600080fd5b5061008b6004803603810190808035600019169060200190929190505050610231565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156100cb5780820151818401526020810190506100b0565b50505050905090810190601f1680156100f85780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34801561011257600080fd5b5061016d600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610430565b60405180826000191660001916815260200191505060405180910390f35b34801561019757600080fd5b506101b66004803603810190808035906020019092919050505061043e565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156101f65780820151818401526020810190506101db565b50505050905090810190601f1680156102235780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b60608060008060006060600060206040519080825280601f01601f1916602001820160405280156102715781602001602082028038833980820191505090505b50955060009450600093505b602084101561033b578360080260020a886001900402600102925060007f010000000000000000000000000000000000000000000000000000000000000002837effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff191614151561032e578286868151811015156102f557fe5b9060200101907effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916908160001a90535084806001019550505b838060010194505061027d565b846040519080825280601f01601f19166020018201604052801561036e5781602001602082028038833980820191505090505b509150600090505b8481101561042257858181518110151561038c57fe5b9060200101517f010000000000000000000000000000000000000000000000000000000000000090047f01000000000000000000000000000000000000000000000000000000000000000282828151811015156103e557fe5b9060200101907effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916908160001a9053508080600101915050610376565b819650505050505050919050565b600060208201519050919050565b6060600080606060008086141561048c576040805190810160405280600181526020017f3000000000000000000000000000000000000000000000000000000000000000815250945061058c565b8593505b6000841415156104b6578280600101935050600a848115156104ae57fe5b049350610490565b826040519080825280601f01601f1916602001820160405280156104e95781602001602082028038833980820191505090505b5091506001830390505b60008614151561058857600a8681151561050957fe5b066030017f01000000000000000000000000000000000000000000000000000000000000000282828060019003935081518110151561054457fe5b9060200101907effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916908160001a905350600a8681151561058057fe5b0495506104f3565b8194505b505050509190505600a165627a7a723058206e289e814acf9d0636c9c699551be90f096b8474ffd972603859d402431dcd410029"};

    public static final String SM_BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"constant\":false,\"inputs\":[{\"name\":\"_bytes32\",\"type\":\"bytes32\"}],\"name\":\"bytes32ToString\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"source\",\"type\":\"string\"}],\"name\":\"stringToBytes32\",\"outputs\":[{\"name\":\"result\",\"type\":\"bytes32\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"i\",\"type\":\"uint256\"}],\"name\":\"uint2str\",\"outputs\":[{\"name\":\"c\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"}]"};

    public static final String ABI = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", ABI_ARRAY);

    public static final String FUNC_BYTES32TOSTRING = "bytes32ToString";

    public static final String FUNC_STRINGTOBYTES32 = "stringToBytes32";

    public static final String FUNC_UINT2STR = "uint2str";

    protected Tool(String contractAddress, Client client, CryptoKeyPair credential) {
        super(getBinary(client.getCryptoSuite()), contractAddress, client, credential);
    }

    public static String getBinary(CryptoSuite cryptoSuite) {
        return (cryptoSuite.getCryptoTypeConfig() == CryptoType.ECDSA_TYPE ? BINARY : SM_BINARY);
    }

    public TransactionReceipt bytes32ToString(byte[] _bytes32) {
        final Function function = new Function(
                FUNC_BYTES32TOSTRING, 
                Arrays.<Type>asList(new Bytes32(_bytes32)),
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] bytes32ToString(byte[] _bytes32, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_BYTES32TOSTRING, 
                Arrays.<Type>asList(new Bytes32(_bytes32)),
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForBytes32ToString(byte[] _bytes32) {
        final Function function = new Function(
                FUNC_BYTES32TOSTRING, 
                Arrays.<Type>asList(new Bytes32(_bytes32)),
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple1<byte[]> getBytes32ToStringInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_BYTES32TOSTRING, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<byte[]>(

                (byte[]) results.get(0).getValue()
                );
    }

    public Tuple1<String> getBytes32ToStringOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_BYTES32TOSTRING, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<String>(

                (String) results.get(0).getValue()
                );
    }

    public TransactionReceipt stringToBytes32(String source) {
        final Function function = new Function(
                FUNC_STRINGTOBYTES32, 
                Arrays.<Type>asList(new Utf8String(source)),
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] stringToBytes32(String source, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_STRINGTOBYTES32, 
                Arrays.<Type>asList(new Utf8String(source)),
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForStringToBytes32(String source) {
        final Function function = new Function(
                FUNC_STRINGTOBYTES32, 
                Arrays.<Type>asList(new Utf8String(source)),
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple1<String> getStringToBytes32Input(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_STRINGTOBYTES32, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<String>(

                (String) results.get(0).getValue()
                );
    }

    public Tuple1<byte[]> getStringToBytes32Output(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_STRINGTOBYTES32, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<byte[]>(

                (byte[]) results.get(0).getValue()
                );
    }

    public TransactionReceipt uint2str(BigInteger i) {
        final Function function = new Function(
                FUNC_UINT2STR, 
                Arrays.<Type>asList(new Uint256(i)),
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] uint2str(BigInteger i, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_UINT2STR, 
                Arrays.<Type>asList(new Uint256(i)),
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForUint2str(BigInteger i) {
        final Function function = new Function(
                FUNC_UINT2STR, 
                Arrays.<Type>asList(new Uint256(i)),
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple1<BigInteger> getUint2strInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_UINT2STR, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<BigInteger>(

                (BigInteger) results.get(0).getValue()
                );
    }

    public Tuple1<String> getUint2strOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_UINT2STR, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<String>(

                (String) results.get(0).getValue()
                );
    }

    public static Tool load(String contractAddress, Client client, CryptoKeyPair credential) {
        return new Tool(contractAddress, client, credential);
    }

    public static Tool deploy(Client client, CryptoKeyPair credential) throws ContractException {
        return deploy(Tool.class, client, credential, getBinary(client.getCryptoSuite()), "");
    }
}
