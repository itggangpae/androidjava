package kakao.itstudy.adapterview;

import java.io.Serializable;

public class DataStructure implements Serializable {
    private int icon;
    private String name;
    private String description;

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //객체를 문자열로 변환해주는 메소드
    //디버깅을 빠르게 하기 위해서 주로 생성
    //각 프로퍼티의 값들을 확인하고자 할 때 각 프로퍼티마다 getter를 호출해야하는데 이것은 자원의 낭비
    @Override
    public String toString() {
        return "DataStructure{" +
                "icon=" + icon +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
