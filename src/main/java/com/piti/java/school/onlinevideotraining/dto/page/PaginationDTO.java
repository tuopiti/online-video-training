package com.piti.java.school.onlinevideotraining.dto.page;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaginationDTO {
	private int numberOfElements;
	private int number;
	private int size;
	private long totalElements;
	private int totalPages;
	private boolean empty;
	private boolean first;
	private boolean last;
}
