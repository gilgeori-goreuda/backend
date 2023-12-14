#!/bin/bash

# deploy.sh가 실행 중인지 확인
if [ -e /home/ubuntu/tmp/deploy-dev.lock ]; then
    echo "Deployment is in progress"
    exit 1
fi

# deploy.sh가 실행 중이지 않다면 lock 파일 생성
touch /home/ubuntu/tmp/deploy-dev.lock

# 이전 컨테이너 종료
sudo docker compose -p compose-dev -f compose-dev.yml down
echo "이전 compose-dev down"

# 도커 네트워크 이름
NETWORK_NAME="gilgeorigoreuda-network"

# 도커 네트워크가 존재하는지 확인
sudo docker network inspect $NETWORK_NAME >/dev/null 2>&1

if [ $? -eq 0 ]; then
  echo "Network '$NETWORK_NAME' already exists, nothing to do here..."
else
  echo "Network '$NETWORK_NAME' does not exist, creating..."
  sudo docker network create $NETWORK_NAME

  if [ $? -eq 0 ]; then
    echo "Network '$NETWORK_NAME' created successfully!"
  else
    echo "Failed to create network '$NETWORK_NAME'"
    exit 1
  fi
fi

# 새로운 컨테이너 실행
sudo docker compose -p compose-dev -f compose-dev.yml pull backend-dev
sudo docker compose -p compose-dev -f compose-dev.yml up -d
echo "새로운 compose-dev up"

sleep 10

# 새로운 컨테이너가 제대로 떴는지 확인
EXIST=$(sudo docker compose -p compose-dev -f compose-dev.yml ps | grep Up)
if [ -n "$EXIST" ]; then
  echo "deploy success"
  rm -f /home/ubuntu/tmp/deploy-dev.lock
else
  echo "deploy fail"
  rm -f /home/ubuntu/tmp/deploy-dev.lock
  exit 1
fi