#!/usr/bin/env bash

# Install docker-engine
apt-get update
apt-get install --no-install-recommends \
    apt-transport-https \
    curl \
    software-properties-common
apt-get install -y --no-install-recommends \
    linux-image-extra-$(uname -r) \
    linux-image-extra-virtual
curl -fsSL 'https://sks-keyservers.net/pks/lookup?op=get&search=0xee6d536cf7dc86e2d7d56f59a178ac6c6238f52e' | apt-key add -
add-apt-repository \
   "deb https://packages.docker.com/1.13/apt/repo/ \
   ubuntu-$(lsb_release -cs) \
   main"
apt-get update
apt-get -y install docker-engine


# Install docker-composer
curl -L https://github.com/docker/compose/releases/download/1.14.0/docker-compose-`uname -s`-`uname -m` -o /usr/local/bin/docker-compose
chmod +x /usr/local/bin/docker-compose


#curl -sSL https://hyperledger.github.io/composer/install-hlfv1.sh | bash
./install-hlfv1.sh
