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
# var callData = contract.sendCoin.getData(functionParameters);
# function: sendCoin(address,uint)
# first 4 bytes: 38f633e9
# make sure you unlock account first before running
# first parameter:  0x000000000000000000000000ce4a4db7210980bc6ab6b06cda7550a9913a95ac
# second parameter: 0x0000000000000000000000000000000000000000000000000000000000000001
# testrpc mnemonic example: truly board portion gym obtain duck turn cage chef ill belt village
# contract address: 0x788bdba0be1d58dffe2c783ce9bc952e0aa667e6
params: [{
  "from": "0x03f1a20df57564e5fbdc819365fd26a5914c4306",
  "to": "0xfd3f582466cab4e48ce8d42c48629848b9418bb6",
  "gas": "0x76c0",
  "gasPrice": "0x9184e72a000",
  "data": "0x38f633e9000000000000000000000000ce4a4db7210980bc6ab6b06cda7550a9913a95ac0000000000000000000000000000000000000000000000000000000000000001"
}]

curl -X POST --data '{"jsonrpc":"2.0","method":"eth_sendTransaction","params":[{"gasPrice": "0x9184e72a000","gas":"0x76c0","from":"0x03f1a20df57564e5fbdc819365fd26a5914c4306","to":"0xfd3f582466cab4e48ce8d42c48629848b9418bb6","data":"0x38f633e9000000000000000000000000ce4a4db7210980bc6ab6b06cda7550a9913a95ac0000000000000000000000000000000000000000000000000000000000000001"}],"id":1}' http://localhost:8545
curl -X POST --data '{"jsonrpc":"2.0","method":"eth_sendTransaction","params":[{"from":"0x36e16b3d4a49a539cbee8a0a9601bf750dfbe5d5","to":"0x8ee1fc291ce219ec550a276a115534c953f8c14a","data":"0x90b98a11000000000000000000000000f623e1bc51ad2e7e9b53a13be5b2bb8a000000000000000000000000000000000000000000000000000000000000000000000001"}],"id":1}' http://localhost:8545
curl -X POST --data '{"jsonrpc":"2.0","method":"eth_call","params":[{"from":"0x03f1a20df57564e5fbdc819365fd26a5914c4306","to":"0xfd3f582466cab4e48ce8d42c48629848b9418bb6","data":"0x38f633e9000000000000000000000000ce4a4db7210980bc6ab6b06cda7550a9913a95ac0000000000000000000000000000000000000000000000000000000000000001"}],"id":1}' http://localhost:8545
curl -X POST --data '{"jsonrpc":"2.0","method":"eth_sendTransaction","params":[{"data":"0x38f633e9000000000000000000000000ce4a4db7210980bc6ab6b06cda7550a9913a95ac0000000000000000000000000000000000000000000000000000000000000001"}],"id":1}' http://localhost:8545
curl -X POST --data '{"jsonrpc":"2.0","method":"eth_sendTransaction","params":[{"from":"0x03f1a20df57564e5fbdc819365fd26a5914c4306","to":"0xfd3f582466cab4e48ce8d42c48629848b9418bb6"}],"id":1}' http://localhost:8545
curl -X POST --data '{"jsonrpc":"2.0","method":"eth_sendTransaction","params":[],"id":1}' http://localhost:8545

#0x2642b48d5ef9377c7021a397af43132ac2343517

var contract = web3.eth.contract([{"constant":false,"inputs":[{"name":"addr","type":"address"}],"name":"getBalanceInEth","outputs":[{"name":"","type":"uint256"}],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":false,"inputs":[{"name":"receiver","type":"address"},{"name":"amount","type":"uint256"}],"name":"sendCoin","outputs":[{"name":"sufficient","type":"bool"}],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":false,"inputs":[{"name":"addr","type":"address"}],"name":"getBalance","outputs":[{"name":"","type":"uint256"}],"payable":false,"stateMutability":"nonpayable","type":"function"},{"inputs":[],"payable":false,"stateMutability":"nonpayable","type":"constructor"},{"anonymous":false,"inputs":[{"indexed":true,"name":"_from","type":"address"},{"indexed":true,"name":"_to","type":"address"},{"indexed":false,"name":"_value","type":"uint256"}],"name":"Transfer","type":"event"}]).at(0x788bdba0be1d58dffe2c783ce9bc952e0aa667e6);
var callData = contract.sendCoin.getData(0xf666b558fb318a08ffaf89a8aca5f431e2b3b31b,1);


# Instantiate smart contract
curl --data '{"jsonrpc":"2.0","method": "eth_sendTransaction", "params": [{"from": "0xeb85a5557e5bdc18ee1934a89d8bb402398ee26a", "gas": "0xb8a9", "data": "0x6060604052605f8060106000396000f3606060405260e060020a6000350463c6888fa18114601a575b005b60586004356007810260609081526000907f24abdb5865df5079dcc5ac590ff6f01d5c16edbc5fab4e195d9febd1114503da90602090a15060070290565b5060206060f3"}], "id": 6}' localhost:8545
