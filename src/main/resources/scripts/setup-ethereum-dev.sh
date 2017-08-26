#!/usr/bin/env bash

# how to use truffle https://blog.zeppelin.solutions/the-hitchhikers-guide-to-smart-contracts-in-ethereum-848f08001f05

# install ethereum
sudo apt-get install software-properties-common
sudo add-apt-repository -y ppa:ethereum/ethereum
sudo apt-get update
sudo apt-get install ethereum


# get node
sudo apt-get install python-software-properties
curl -sL https://deb.nodesource.com/setup_6.x | sudo -E bash -
sudo apt-get install nodejs
npm install web3

# install solidity compiler

sudo apt-get install solc

# install truffle
npm install -g truffle@3.2.1

# install testrpc
npm install -g ethereumjs-testrpc

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