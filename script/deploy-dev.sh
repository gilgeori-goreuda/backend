#!/bin/bash

# deploy.sh가 실행 중인지 확인
if [ -e /home/ubuntu/tmp/deploy-dev.lock ]; then
    echo "deployment is in progress"
    exit 1
fi

# deploy.sh가 실행 중이지 않다면 lock 파일 생성
touch /home/ubuntu/tmp/deploy-dev.lock

# 이전 컨테이너 중지
sudo docker compose -p compose-dev -f compose-dev.yml stop
echo "old dev-server stopped"

# 도커 네트워크 이름
NETWORK_NAME="gilgeorigoreuda-network"

# 도커 네트워크 존재 확인
sudo docker network inspect $NETWORK_NAME >/dev/null 2>&1

if [ $? -eq 0 ]; then
    echo "network '$NETWORK_NAME' already exists"
else
    echo "network '$NETWORK_NAME' does not exist, create network"
    sudo docker network create $NETWORK_NAME

    if [ $? -eq 0 ]; then
        echo "network '$NETWORK_NAME' created successfully!"
    else
        echo "failed to create network '$NETWORK_NAME'"
        rm -f /home/ubuntu/tmp/deploy-dev.lock
        exit 1
    fi
fi

# 새로운 버전 서버 실행 확인
sudo docker compose -p compose-dev -f compose-dev.yml pull
sudo docker compose -p compose-dev -f compose-dev.yml up -d
echo "new dev server up"

# 새로운 컨테이너가 제대로 떴는지 확인
NEW_SERVER_UP=$(sudo docker compose -p compose-dev -f compose-dev.yml ps | grep Up)
if [ -n "$NEW_SERVER_UP" ]; then
    echo "deploy success"

    # nginx 컨테이너 실행 중이라면 reload 수행
    if sudo docker inspect -f '{{.State.Running}}' nginx; then
        echo "nginx is running. performing reload..."
        sudo docker exec nginx nginx -s reload
    else
        echo "nginx is not running. starting NGINX..."
        sudo docker-compose -p compose-dev -f compose-dev.yml up -d nginx
    fi

    rm -f /home/ubuntu/tmp/deploy-dev.lock
else
    echo "deploy fail"
    rm -f /home/ubuntu/tmp/deploy-dev.lock
    exit 1
fi