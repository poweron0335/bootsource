package ch1;

public class TvMain {
    public static void main(String[] args) {
        // tv 객체 생성
        // SamsungTv samsungTv = new SamsungTv();

        LgTv tv = new LgTv();
        // Tv tv = new LgTv();
        tv.setSpeaker(new BritzSpeaker());

        tv.powerOn();
        // NullPointerException
        tv.volumeUp();
        tv.volumeDown();
        tv.powerOff();
    }
}
