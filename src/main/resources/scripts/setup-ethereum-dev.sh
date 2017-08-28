#!/usr/bin/env bash

# install on ubuntu
# how to use truffle https://blog.zeppelin.solutions/the-hitchhikers-guide-to-smart-contracts-in-ethereum-848f08001f05

# how to deploy and call smart contract in ethereum :https://ethereum.stackexchange.com/questions/3514/how-to-call-a-contract-method-using-the-eth-call-json-rpc-api

# install ethereum
sudo apt-get install software-properties-common
sudo add-apt-repository -y ppa:ethereum/ethereum
sudo apt-get update
sudo apt-get install ethereum


# get node
sudo apt-get install python-software-properties
curl -sL https://deb.nodesource.com/setup_6.x | sudo -E bash -
sudo apt-get install nodejs
sudo apt install npm
npm install web3

# install solidity compiler
sudo apt-get install solc

# install truffle
npm install -g truffle@3.2.1

# install testrpc
sudo npm install -g ethereumjs-testrpc

# Build a smart contract
mkdir solidity-experiments
cd solidity-experiments
truffle init
truffle compile
truffle migrate

truffle create contract MyContract
cd contracts
#make contract
# add it to the migrate.json var MyContract = artifacts.require("MyContract");
truffle migrate --reset
truffle console

#geth -—networkid="12345" console

#check local testrpc network
#ipc
curl -X POST --data '{"jsonrpc":"2.0","method":"net_version","params":[],"id":1}' localhost:8545
#http
curl -X POST --data '{"jsonrpc":"2.0","method":"net_version","params":[],"id":74}' localhost:8545

#send coins
curl -d '{"jsonrpc":"2.0","method":"eth_sendTransaction","params": [{"from":"0x0f91747e3a5df28d81ab30b2d8216c93263c0cf3", "to":"0xbbd220f66e989a493c4d48531ea1e283abc385de", "value": 1e18}], "id":1}' -X POST http://localhost:8545/