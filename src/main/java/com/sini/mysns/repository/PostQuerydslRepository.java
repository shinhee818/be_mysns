package com.sini.mysns.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sini.mysns.api.controller.post.dto.FindPostCond;
import com.sini.mysns.api.controller.post.dto.FindPostSortType;
import com.sini.mysns.api.controller.post.dto.FindPostsResponse;
import com.sini.mysns.domain.PostCategory;
import com.sini.mysns.domain.member.QMember;
import com.sini.mysns.domain.post.Post;
import com.sini.mysns.domain.post.QPost;
import com.sini.mysns.domain.post.QPostImage;
import com.sini.mysns.domain.post.QPostTag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PostQuerydslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public FindPostsResponse findPosts(FindPostCond condition)
    {
        QPost post = QPost.post;
        QMember member = QMember.member;
        Pageable pageable = condition.getPageable();

        List<Post> posts =  jpaQueryFactory.select(post)
                .from(post)
                .join(post.member, member).fetchJoin()
                .where(
                        categoryEq(condition.getPostCategory()),
                        memberPostEq(condition.getFindMemberId())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(createOrderSpecifier(condition.getPostSortType()))
                .fetch();

        Long count = jpaQueryFactory.select(post.postId.count())
                .from(post)
                .where(
                        categoryEq(condition.getPostCategory()),
                        memberPostEq(condition.getFindMemberId())
                )
                .fetchOne();

        return FindPostsResponse.of(
                new PageImpl<>(
                        posts,
                        pageable,
                        count
                )
        );
    }

    private BooleanExpression categoryEq(PostCategory postCategory)
    {
        return postCategory != null ? QPost.post.postCategory.eq(postCategory) : null;
    }

    private BooleanExpression memberPostEq(Long memberId)
    {
        return memberId != null ? QPost.post.member.memberId.eq(memberId) : null;
    }

    private OrderSpecifier createOrderSpecifier(FindPostSortType postSortType)
    {
        return switch (postSortType) {
            case RECENT -> new OrderSpecifier<>(Order.DESC, QPost.post.registerDate);
            case VIEWS -> new OrderSpecifier<>(Order.DESC, QPost.post.viewCount);
            default -> new OrderSpecifier<>(Order.DESC, QPost.post.postId);
        };
    }

    public Optional<Post> findFetchPostById(@PathVariable("postId") Long postId){
        QPost post = QPost.post;
        QPostTag postTag = QPostTag.postTag;
        QMember member = QMember.member;
        QPostImage postImage = QPostImage.postImage;

        Post findOnePost = jpaQueryFactory.selectFrom(post)
                .distinct()
                .leftJoin(post.member,member).fetchJoin()
                .leftJoin(post.postImages,postImage).fetchJoin()
                .leftJoin(post.postTagList,postTag).fetchJoin()
                .leftJoin(postTag.tag).fetchJoin()
                .where(
                        post.postId.eq(postId)
                )
                .fetchOne();

        return Optional.ofNullable(findOnePost);
    }
}
