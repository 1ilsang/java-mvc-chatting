package dto;

import java.io.Serializable;

/**
 * NOTI
 * A. 왜 메시지를 자바빈패턴이 아닌 빌더패턴을 사용했는가?
 *   1. 여러 쓰레드가 읽는 메시지이므로 이뮤터블하게 강제하고 싶었다.
 *   2. 특히 유저이름/방 번호는 반드시 존재하고 불변이여야 한다는 제약이 있다.
 *    -> 빌더패턴으로 생성자중복을 피하고 일부 필드를 강제, 은닉 하고싶어서.
 *       무엇보다 한 번의 함수 실행으로 혹시모를 객체 동기화 문제를 피하고 싶었다.
 *
 * B. 빌더패턴에 Getter 의 존재는 옳은 것인가?
 *   1. 잘 모르겠다. 하지만 패턴을 맹신해 어렵게 돌아가고 싶지 않았다.
 *      이뮤터블하다는 성질만 잘 지키면 된다고 생각했다.
 *   2. 메시지를 각 방의 유저뷰에 뿌려주어야 했으므로 게터는 반드시 필요했다.
 */
public class MessageDTO implements Serializable {
    private int FLAG;
    private int roomNumber;
    private String userName;
    private String contents;

    private MessageDTO() {}
    private MessageDTO(Builder builder) {
        this.FLAG = builder.FLAG;
        this.roomNumber = builder.roomNumber;
        this.userName = builder.userName;
        this.contents = builder.contents;
    }

    public static class Builder implements IBuilderDTO {
        // Essential
        private final int roomNumber;
        private final String userName;
        // Selective(MUST Initialization)
        private int FLAG            = 0;
        private String contents     = "";

        public Builder(int roomNumber, String name) {
            this.roomNumber = roomNumber;
            this.userName = name;
        }
        public Builder flag(int n) {
            this.FLAG = n;
            return this;
        }
        public Builder contents(String contents) {
            this.contents = contents;
            return this;
        }

        @Override
        public MessageDTO build() {
            return new MessageDTO(this);
        }
    }

    public int getFLAG() {
        return FLAG;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getName() {
        return userName;
    }

    public String getContents() {
        return contents;
    }
}
