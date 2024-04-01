package ch1;

public class LgTv implements Tv {

    // 멤버 변수 초기화
    // 기본형 : int long, boolean....
    // 1) 정수타입 : 0
    // 2) 불린타입 : false
    // 3) 실수타입 : 0.0
    // 참조형(대문자, 배열) : null 로 초기화
    private Speaker speaker; // == private BritzSpeaker britzSpeaker = null;

    // private String str;

    // 멤버 변수 초기화 방법
    // 1) setter 메소드 이용
    // 2) 생성자 이용
    public LgTv() {
    }

    public LgTv(Speaker speaker) {
        this.speaker = speaker;
    }

    @Override
    public void powerOn() {
        System.out.println(("LgTv - 전원 On"));
    }

    @Override
    public void powerOff() {
        System.out.println(("LgTv - 전원 Off"));

    }

    @Override
    public void volumeUp() {
        // System.out.println(("LgTv - volume up"));
        speaker.volumeUp();

    }

    @Override
    public void volumeDown() {
        // System.out.println(("LgTv - volume down"));
        speaker.volumeDown();

    }

    public void setSpeaker(Speaker speaker) {
        this.speaker = speaker;
    }

}
