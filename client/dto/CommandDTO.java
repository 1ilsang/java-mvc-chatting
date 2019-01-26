package dto;

/**
 * FIXME 모든 요청 데이터를 여기다 담는데 더 좋은 방법은 없을까?
 * This is like a 'Request model object'
 */
public class CommandDTO {
    private int rno;
    private String text;
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getRno() {
        return rno;
    }

    public void setRno(int rno) {
        this.rno = rno;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
