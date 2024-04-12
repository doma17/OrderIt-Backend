1. sewoojni.jar을 사용하기 위해선 자바 환경변수 상에 LKPOSTOT.dll을 경로에 추가해줘야 한다
2. 프로젝트 구조, 모듈, main에 종속요소에 sewoojni.jar 추가
3. build.gradle에 'implementation fileTree(dir: 'libs', includes: ['*.jar'])' 추가