package com.tiutiu.prodiff.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class PdProj implements Serializable {
    private Long id;
    private String pdProjName;
    private String filePath;

    private Long createUser;
    private Long updateUser;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
