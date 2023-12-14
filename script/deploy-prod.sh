#!/bin/bash

if [ -e /home/ubuntu/tmp/deploy-prod.lock ]; then
    echo "Deployment is in progress"
    exit 1
fi

# deploy.sh가 실행 중이지 않다면 lock 파일 생성
touch /home/ubuntu/tmp/deploy-prod.lock

# blue 컨테이너를 체크한다.
BLUE_SERVER_UP=$(sudo docker compose -p compose-blue -f compose-blue.yml ps | grep Up)

# 컨테이너 스위칭
if [ -z "$BLUE_SERVER_UP" ]; then
    echo "compose-blue up"
    sudo docker compose -p compose-blue -f compose-blue.yml pull
    sudo docker compose -p compose-blue -f compose-blue.yml up -d
    BEFORE_SERVER_COLOR="green"
    AFTER_SERVER_COLOR="blue"
else
    echo "compose-green up"
    sudo docker compose -p compose-green -f compose-green.yml pull
    sudo docker compose -p compose-green -f compose-green.yml up -d
    BEFORE_SERVER_COLOR="blue"
    AFTER_SERVER_COLOR="green"
fi

sleep 10

# 네트워크가 존재 확인
NETWORK_NAME="gilgeorigoreuda-network"

sudo docker network inspect $NETWORK_NAME >/dev/null 2>&1

if [ $? -eq 0 ]; then
  echo "Network '$NETWORK_NAME' already exist"
else
  echo "Network '$NETWORK_NAME' does not exist, create network"
  sudo docker network create $NETWORK_NAME

  if [ $? -eq 0 ]; then
    echo "Network '$NETWORK_NAME' created successfully!"
  else
    echo "Failed to create network '$NETWORK_NAME'"
    exit 1
  fi
fi

# 새로운 버전 서버 실행 확인
NEW_SERVER_UP=$(sudo docker compose -p compose-${AFTER_SERVER_COLOR} -f compose-${AFTER_SERVER_COLOR}.yml ps | grep Up)

if [ -n "$NEW_SERVER_UP" ]; then
  # nginx 컨테이너 실행 확인
  NGINX_SERVER_UP=$(sudo docker compose -p compose-nginx -f compose-nginx.yml ps | grep Up)

  if [ -z "$NGINX_SERVER_UP" ]; then
    echo "compose-nginx up"
    sudo docker compose -p compose-nginx -f compose-nginx.yml up -d
    sleep 10
  else
    echo "compose-nginx is already up."
  fi

  # nginx.config를 컨테이너에 맞게 변경해주고 reload 한다
  envsubst '${AFTER_SERVER_COLOR}' < conf-prod/nginx.template > conf-prod/nginx.conf
  sudo docker compose -f compose-nginx.yml exec nginx nginx -s reload

  # 이전 컨테이너 종료
  sudo docker compose -p compose-${BEFORE_SERVER_COLOR} -f compose-${BEFORE_SERVER_COLOR}.yml down
  echo "$BEFORE_SERVER_COLOR down"

  # lock 파일 삭제
  rm -f /home/ubuntu/tmp/deploy-prod.lock
else
  echo "deploy fail"
  rm -f /home/ubuntu/tmp/deploy-prod.lock
  exit 1
fi

