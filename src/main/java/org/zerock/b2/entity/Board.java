package org.zerock.b2.entity;

import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "t_board")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString(exclude = "boardImages")
public class Board extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bno;

    @Column(length = 200, nullable = false)
    private String title;

    private String content;

    private String writer;

    public void changeTitle(String title){
        this.title = title;
    }

    public void changeContent(String content){
        this.content = content;
    }

    public void addImage(String fileLink){

        BoardImage  image = BoardImage.builder().fileLink(fileLink)
                .ord(boardImages.size())
                .build();
        boardImages.add(image);
    }

    @ElementCollection(fetch = FetchType.LAZY)
    @BatchSize(size = 100)
    @Builder.Default
    private Set<BoardImage> boardImages = new HashSet<>();

    public void addImage(BoardImage boardImage){

        boardImage.fixOrd(boardImages.size());
        boardImages.add(boardImage);
    }

    public void clearImages() {

        boardImages.clear();
    }


}
