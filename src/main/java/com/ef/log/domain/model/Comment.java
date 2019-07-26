package com.ef.log.domain.model;

import com.ef.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Comment extends BaseEntity {

    private String ip;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Long count;

    private Long threshold;

    private String description;

}
