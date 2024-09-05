package ktb.hackathon.ktbgratitudediary.entity;

import jakarta.persistence.*;

@Entity
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_diary_user_id"))
    private User user;
}
