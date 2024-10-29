package com.login.model;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name; // 사람 이름

    @Column(name = "user_id", nullable = false, unique = true)
    private String userId; // 아이디

    @Column(name = "user_password", nullable = false)
    private String userPassword; // 비밀번호

    @Column(name = "created", nullable = false, updatable = false)
    private LocalDate created; // 계정 생성일

    @Column(name = "validity_days", nullable = false)
    private Integer validityDays; // 유효기간 (일)

    // 계정이 현재 유효한지 확인하는 메서드 (유효기간 체크)
    public boolean isAccountValid() {

        // 유효기간이 0일인 경우 항상 유효한 계정으로 간주
        if (validityDays == 0) {
            return true;
        }

        return created.plusDays(validityDays).isAfter(LocalDate.now());
    }

    // 엔티티가 저장되기 전에 호출되어 created에 현재 날짜를 자동으로 설정
    @PrePersist
    protected void onCreate() {
        this.created = LocalDate.now();
    }
}

