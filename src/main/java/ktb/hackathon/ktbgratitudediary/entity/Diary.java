package ktb.hackathon.ktbgratitudediary.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;


@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "user_id"),
        @Index(columnList = "createdAt")
})
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_diary_user_id"))
    private User user;

    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL)
    private Set<EmotionEntity> emotionEntities = new LinkedHashSet<>();

    private int happiness;

    private int weather;

    @Column(columnDefinition = "TEXT")
    private String vectorImage;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Template template;

    private String rType;
    private String jType;
    private String mType;
    private String dType;
    private String totalDesc;
    private String totalTitle;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public void addEmotionEntities(LinkedHashSet<EmotionEntity> emotionEntities) {
        this.emotionEntities = emotionEntities;
    }

    private Diary(String title,
                String content,
                User user,
                int happiness,
                int weather,
                String vectorImage,
                Template template,
                String rType,
                String jType,
                String mType,
                String dType,
                String totalDesc,
                String totalTitle) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.happiness = happiness;
        this.weather = weather;
        this.vectorImage = vectorImage;
        this.template = template;
        this.rType = rType;
        this.jType = jType;
        this.mType = mType;
        this.dType = dType;
        this.totalDesc = totalDesc;
        this.totalTitle = totalTitle;
    }

    public static Diary of(
            Long id,
            String title,
            String content,
            User user,
            int happiness,
            int weather,
            String vectorImage,
            Template template,
            String rType,
            String jType,
            String mType,
            String dType,
            String totalDesc,
            String totalTitle
    ) {
        return new Diary(title,
                content,
                user,
                happiness,
                weather,
                vectorImage,
                template,
                rType,
                jType,
                mType,
                dType,
                totalDesc,
                totalTitle);
    }
}
