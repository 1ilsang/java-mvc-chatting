package dto;

public class MessageDTO {
    private int roomNumber;
    private String name;
    private String contents;

    public MessageDTO(int roomNumber, String name, String contents) {
        this.roomNumber = roomNumber;
        this.name = name;
        this.contents = contents;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
