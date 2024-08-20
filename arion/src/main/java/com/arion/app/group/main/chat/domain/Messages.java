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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chat_messages_seq_gen")
    @SequenceGenerator(name = "chat_messages_seq_gen", sequenceName = "chat_messages_seq", allocationSize = 1)
    private Long messageNo;

    private String senderId;
    private String content;
    private int employeeNo;
    private LocalDateTime sendAt = LocalDateTime.now();
    private int roomNo;
    
    
}