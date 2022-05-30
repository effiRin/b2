package org.zerock.b2.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="t_board")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Board extends BaseEntity {  // 모든 entity는 id (pk)를 가져야 한다.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bno;  // pk는 항상 객체 타입을 쓴다.

    @Column(length = 200, nullable = false)
    private String title;
    private String content;
    private String writer;

//    @CreationTimestamp
//    private LocalDateTime regDate;
//
//    @UpdateTimestamp
//    private LocalDateTime updateDate;    // BaseEntity 에서 만들었으므로 지우기

    public void changeTitle(String title){
        this.title = title;
    }

    public void changeContent(String content){
        this.content = title;
    }
}