#!/usr/bin/env bash

user=adrian
VERSION=1.8.3
OS=linux
ARCH=amd64

sudo wget https://storage.googleapis.com/golang/go${VERSION}.${OS}-${ARCH}.tar.gz
sudo tar -C /usr/local -xzf go${VERSION}.${OS}-${ARCH}.tar.gz

sudo echo "export PATH=\$PATH:/usr/local/go/bin/" >> /home/${user}/.bashrc
sudo echo "export GOPATH=/usr/local/go/src/" >> /home/${user}/.bashrc

