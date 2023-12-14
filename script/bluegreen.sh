#!/bin/bash

BEFORE_PORT=$1
NEW_PORT=$2
NEW_ACTUATOR_PORT=$3

echo "기존 포트 : $BEFORE_PORT"
echo "새로운 포트 : $NEW_PORT"
echo "새로운 ACTUATOR_PORT : $NEW_ACTUATOR_PORT"

# nginx 컨테이너 실행 확인
if ! sudo docker ps | grep "nginx"; then
    echo "nginx 컨테이너를 먼저 실행합니다."

    # nginx 컨테이너 실행
    sudo docker run -d --name nginx -p 80:80 nginx

    # 실행이 성공했는지 확인
    if [ $? -ne 0 ]; then
        echo "nginx 컨테이너 실행에 실패했습니다."
        exit 1
    fi
fi

count=0
for count in {0..20}
do
  echo "서버 상태 확인(${count}/20)";

  UP=$(curl -s http://127.0.0.1:"${NEW_ACTUATOR_PORT}"/actuator/health)

  if [ "${UP}" != '{"status":"up"}' ]
  	then
  		sleep 10
  		continue
  	else
  		break
  fi
done

if [ $count -eq 20 ]
then
  echo "새로운 서버 배포를 실패했습니다."
  exit 1
fi

echo "nginx 설정 변경"
export BACKEND_PORT=$NEW_PORT
echo "디버깅 BACKEND_PORT : $BACKEND_PORT"
cd nginx_config
envsubst "\${BACKEND_PORT}" < default.template > default.conf
sudo docker cp default.conf nginx:/etc/nginx/conf.d/
echo "nginx reload"
sudo docker exec nginx service nginx reload

echo "기존 서버 종료"
sudo docker stop gilgeorigoreuda-prod"$BEFORE_PORT"

echo "기존 서버 삭제"
sudo docker container prune -f