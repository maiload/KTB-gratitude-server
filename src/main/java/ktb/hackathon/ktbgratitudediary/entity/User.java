package ktb.hackathon.ktbgratitudediary.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;

@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "loginId", unique = true),
        @Index(columnList = "createdAt")
})
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String loginId;

    @Column(nullable = false)
    String password;

    @Column(nullable = false)
    LocalDate birthDate;

    @Column(nullable = false)
    String nickname;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany
    private LinkedHashSet<Diary> diaries = new LinkedHashSet<>();

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @CreatedDate
    @Column(nullable = false, updatable = false)
    LocalDateTime createdAt;

    private User(String loginId, String password, LocalDate birthDate, String nickname, Role role) {
        this.loginId = loginId;
        this.password = password;
        this.birthDate = birthDate;
        this.nickname = nickname;
        this.role = role;
    }

    public static User of(String loginId, String password, LocalDate birthDate, String nickname, Role role) {
        return new User(loginId, password, birthDate, nickname, role);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return this.getId() != null && Objects.equals(this.getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.getId());
    }

}
