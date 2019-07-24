package com.ef.common.domain;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime created = LocalDateTime.now();

    private LocalDateTime updated;

}