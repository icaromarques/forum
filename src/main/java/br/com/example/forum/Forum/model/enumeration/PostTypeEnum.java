package br.com.example.forum.Forum.model.enumeration;

public enum PostTypeEnum {
    QUESTION(1),
    ANSWER(2);

    private Integer type;

    PostTypeEnum(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
