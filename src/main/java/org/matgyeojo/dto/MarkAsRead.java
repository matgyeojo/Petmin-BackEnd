package org.matgyeojo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MarkAsRead {
	private Long chatId; // 읽음으로 표시할 메시지의 chatId
}
