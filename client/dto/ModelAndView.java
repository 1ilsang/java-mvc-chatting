package dto;

/**
 * FIXME 모든 요청 데이터를 여기다 담는데 더 좋은 방법은 없을까?
 * This is like a 'Request model object'
 */
public class ModelAndView {
    private int rno;
    private String text;
    private String userName;
    private String action;
    private String url;
    private String pw;
    private boolean flag;

    public ModelAndView() {
        this.flag = false;
        this.text = "";
        this.userName = "";
        this.action = "";
        this.url = "";
        this.pw = "";
    }
    public ModelAndView(String path) {
        this();
        this.url = path;
    }
    public void clear(){
        this.text = "";
        this.userName = "";
        this.action = "";
        this.url = "";
        this.pw = "";
        this.flag = false;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

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
