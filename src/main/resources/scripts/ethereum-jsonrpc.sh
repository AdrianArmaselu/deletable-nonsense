#!/usr/bin/env bash


# Send a transaction to the MetaCoin contract
# To fill in the parameters, get
#   1 Wallet address - account setup
#   2 Contract Address - from truffle deploy (or use jsonrpc)
#   3 Data -
# truffle console
# need to find out how to get the contract ABI, but for now use the solidity browser
# Run keccak hash of method signature
# var contract = web3.eth.contract(contractABI).at(contractAddress);
# var callData = contract.functionName.getData(functionParameters);
# function: sendCoin(address,uint)
# first 4 bytes: 38f633e9
# make sure you unlock account first before running
# first parameter:  0x000000000000000000000000ce4a4db7210980bc6ab6b06cda7550a9913a95ac
# second parameter: 0x0000000000000000000000000000000000000000000000000000000000000001


params: [{
  "from": "0x03f1a20df57564e5fbdc819365fd26a5914c4306",
  "to": "0xfd3f582466cab4e48ce8d42c48629848b9418bb6",
  "gas": 0x123333,
  "gasPrice": 0x10000000,
  "data": "0x38f633e9000000000000000000000000ce4a4db7210980bc6ab6b06cda7550a9913a95ac0000000000000000000000000000000000000000000000000000000000000001"
}]

curl -X POST --data '{"jsonrpc":"2.0","method":"eth_sendTransaction","params":[{"from":"0x03f1a20df57564e5fbdc819365fd26a5914c4306","to":"0xfd3f582466cab4e48ce8d42c48629848b9418bb6","data":"0x38f633e9000000000000000000000000ce4a4db7210980bc6ab6b06cda7550a9913a95ac0000000000000000000000000000000000000000000000000000000000000001"}],"id":1}' http://localhost:8545
curl -X POST --data '{"jsonrpc":"2.0","method":"eth_sendTransaction","params":[{"from":"0x03f1a20df57564e5fbdc819365fd26a5914c4306","to":"0xfd3f582466cab4e48ce8d42c48629848b9418bb6"}],"id":1}' http://localhost:8545
