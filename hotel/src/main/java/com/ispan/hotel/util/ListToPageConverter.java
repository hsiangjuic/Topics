package com.ispan.hotel.util;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class ListToPageConverter {

	//傳入一個List<T>和 pageable，可取得Page物件
	public static <T> Page<T> convertListToPage(List<T> list, Pageable pageable) {
		
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());
        List<T> sublist = list.subList(start, end);
        return new PageImpl<>(sublist, pageable, list.size());
    }
}
