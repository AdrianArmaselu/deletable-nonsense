#!/bin/bash
CURRENTDIR="$(pwd)"
cd composer-data/fabric-dev-servers/fabric-scripts/hlfv1/composer/creds
sudo tar -cv * | tar x -C /home/adrian/.composer-credentials

# Docker stop function
function stop()
{
P1=$(docker ps -q)
if [ "${P1}" != "" ]; then
  echo "Killing all running containers"  &2> /dev/null
  docker kill ${P1}
fi

P2=$(docker ps -aq)
if [ "${P2}" != "" ]; then
  echo "Removing all containers"  &2> /dev/null
  docker rm ${P2} -f
fi
}

if [ "$1" == "stop" ]; then
 echo "Stopping all Docker containers" >&2
 stop
 exit 0
fi

stop

WORKDIR="${CURRENTDIR}/docker/composer-data/"
cd "${WORKDIR}"

# run the fabric-dev-scripts to get a running fabric
sudo ./fabric-dev-servers/downloadFabric.sh
sudo ./fabric-dev-servers/startFabric.sh
sudo ./fabric-dev-servers/createComposerProfile.sh

cd "${CURRENTDIR}"
sudo ./cli.js
