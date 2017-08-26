#!/usr/bin/env bash

# Install docker-engine
sudo apt-get update
sudo apt-get install --no-install-recommends \
    apt-transport-https \
    curl \
    software-properties-common
sudo apt-get install -y --no-install-recommends \
    linux-image-extra-$(uname -r) \
    linux-image-extra-virtual
sudo curl -fsSL 'https://sks-keyservers.net/pks/lookup?op=get&search=0xee6d536cf7dc86e2d7d56f59a178ac6c6238f52e' | sudo apt-key add -
sudo add-apt-repository \
   "deb https://packages.docker.com/1.13/apt/repo/ \
   ubuntu-$(lsb_release -cs) \
   main"
sudo apt-get update
sudo apt-get -y install docker-engine


# Install docker-composer
sudo curl -L https://github.com/docker/compose/releases/download/1.14.0/docker-compose-`uname -s`-`uname -m` -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose

usermod -aG docker $(whoami)

#curl -sSL https://hyperledger.github.io/composer/install-hlfv1.sh | bash
./install-hlfv1.sh
