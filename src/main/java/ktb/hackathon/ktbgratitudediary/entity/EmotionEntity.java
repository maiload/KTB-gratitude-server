package ktb.hackathon.ktbgratitudediary.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Entity
@Table(name = "emotion")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmotionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int per;

    @ElementCollection
    @CollectionTable(name = "emotion_descriptions", joinColumns = @JoinColumn(name = "emotion_id"))
    @Column(name = "description")
    private List<String> desc;

    @Column(nullable = false)
    private String color;

    @ManyToOne(optional = false)
    @JoinColumn(name = "diary_id", foreignKey = @ForeignKey(name = "fk_emotion_diary_id"))
    private Diary diary;

    public void addDesc(List<String> desc) {
        this.desc = desc;
    }

    public void addDiary(Diary diary) {
        this.diary = diary;
    }

    private EmotionEntity(String name, int per, String color, Diary diary) {
        this.name = name;
        this.per = per;
        this.color = color;
        this.diary = diary;
    }

    public static EmotionEntity of(String name, int per, String color, Diary diary) {
        return new EmotionEntity(name, per, color, diary);
    }
}
