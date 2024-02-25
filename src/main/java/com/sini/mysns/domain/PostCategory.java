package com.sini.mysns.domain;

import lombok.Getter;

@Getter
public enum PostCategory {
    BACKEND("백엔드"),
    FRONTEND("프론트 엔드"),
    ;

    private final String content;

    PostCategory(String content) {
        this.content = content;
    }
}
