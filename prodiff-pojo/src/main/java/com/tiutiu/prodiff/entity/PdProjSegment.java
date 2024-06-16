package com.tiutiu.prodiff.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class PdProjSegment implements Serializable {
    private String phoneSeq;
    private String phoneDuration;
    private String f0Seq;
}
