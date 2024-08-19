package com.arion.app.group.main.chat.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import lombok.Data;
@Data
@Entity
public class Messages{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chat_message_seq")
    private Long messageNo;

    private String senderId;
    private String content;
    private LocalDateTime sendAt = LocalDateTime.now();
    private int roomNo;
    
    
}