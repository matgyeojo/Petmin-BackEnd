package org.matgyeojo.service;

import java.util.Optional;

import org.matgyeojo.dto.Chat;
import org.matgyeojo.repository.ChatRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

	@Autowired
	private ChatRepo chatRepo;

	public void markAsRead(Long chatId) {
		Optional<Chat> chatOptional = chatRepo.findById(chatId);
		if (chatOptional.isPresent()) {
			Chat chat = chatOptional.get();
			chat.setChatCheck(true);
			chatRepo.save(chat);
		}
	}
}