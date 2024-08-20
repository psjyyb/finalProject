package com.arion.app.group.main.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arion.app.group.main.chat.domain.Messages;

public interface ChatMessageRepository extends JpaRepository<Messages, Long> {
	
}