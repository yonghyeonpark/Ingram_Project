package com.yonghyeon.ingram.service;

import com.yonghyeon.ingram.domain.bookmark.BookmarkRepository;
import com.yonghyeon.ingram.domain.image.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;

    @Transactional
    public void bookmark(Long principalId, Long imageId) {
        bookmarkRepository.mBookmark(principalId, imageId);
    }

    @Transactional
    public void unBookmark(Long principalId, Long imageId) {
        bookmarkRepository.mUnBookmark(principalId, imageId);
    }

    @Transactional(readOnly = true)
    public List<Image> bookmarkImages(Long principalId) {

        return bookmarkRepository.mBookmarkImages(principalId);
    }
}
