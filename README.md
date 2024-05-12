# What is "Camela" ?
- Camera preview, 게임 등 초당 프레임이 순식간에 바뀌는 처리는 어떻게 해야하지? 고민하다가 만든 앱
- 카메라와 소통 중 📸

# Goal
- 더블 버퍼링 기법: UI 스레드 작업 시 버벅거림, 이미지 찢어짐, ANR 등을 해결한다.
- 커스텀 카메라를 만든다.

# Component
- Camera
- SurfaceView

# View 
![1  View Structure of Camela](https://github.com/kof99athena/camela/assets/128768118/ff426c12-9936-45c3-9c81-805c9b691e10)

# Double Buffering 
![2  double buffering](https://github.com/kof99athena/camela/assets/128768118/ae19e4a7-be8f-4fb7-903a-981c6db88e38)
