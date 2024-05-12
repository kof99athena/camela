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
- 싱글 버퍼링  
  UI 스레드에서 View를 그리고 완성되기까지 그 만드는 모습이 노출된다. 버벅거림, 이미지 찢어짐 등 치명적인 화면 상태가 User에게 노출될 수 있다.  
  또한 View를 그리는 작업 진행 시, UI 스레드는 다른 작업을 할 수 없다. 그려야 할 그림이 많을수록 ANR이 발생할 수도 있다.
- 더블 버퍼링  
  UI 스레드가 아닌 User가 볼 수 없는 가상 공간 Surface에 그림을 그린다. 그림을 다 완성 한 후, View에 완성된 그림을 보내준다.  
  User는 완성된 그림만 보기 때문에 버벅거림 등을 느끼지 못한다.  
  
