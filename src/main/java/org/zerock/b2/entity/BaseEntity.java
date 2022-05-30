package org.zerock.b2.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(value = {AuditingEntityListener.class})   //
@Getter
public class BaseEntity {

    @CreatedDate
    @Column(name = "regdate", updatable = false)    // 생성할 때만 갱신 가능하고 이후에 갱신 불가능
    private LocalDateTime regDate;

    @LastModifiedDate
    private LocalDateTime modDate;   //

}
