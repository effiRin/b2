package org.zerock.b2.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Reply", indexes = {
        @Index(name = "idx_reply_board_bno", columnList = "board_bno")})
@Getter
@ToString(exclude = "board")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reply extends BaseEntity {    // BaseEntity 상속해주면 등록일, 수정일은 알아서 됨

    @Id // id가 없으면 에러가 남.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // autoincrement
    private Long rno; // enterprise급은 Integer 안씀. 기본이 Long
    private String replyText;
    private String replyer;

    @ManyToOne(fetch = FetchType.LAZY)  // 다대일 관계 + fetch타입은 LAZY
    private Board board;
// board와 연관관계를 걸어주고, 테스트 코드로 데이터를 넣어주는 건 문제 없음. but 조회할 때 문제.

    public void changeText(String text){  // 댓글 수정을 위해
        this.replyText = text;   // setter 같은 개념인데 entity에선 set을 쓰지 않음.
    }

}

// 엔티티 건들면 가끔 complie. java 해줘야 에러 해결됨 (Qboard 만들어줘야 해서)