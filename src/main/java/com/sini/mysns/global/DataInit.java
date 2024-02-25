package com.sini.mysns.global;

import com.sini.mysns.domain.PostCategory;
import com.sini.mysns.domain.member.Member;
import com.sini.mysns.domain.member.MemberRepository;
import com.sini.mysns.domain.post.Post;
import com.sini.mysns.domain.post.PostImage;
import com.sini.mysns.domain.post.PostRepository;
import com.sini.mysns.domain.post.PostTag;
import com.sini.mysns.domain.tag.Tag;
import com.sini.mysns.domain.tag.TagRepository;
import com.sini.mysns.global.config.security.MemberRole;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Profile("!test")
@RequiredArgsConstructor
@Service
public class DataInit {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final TagRepository tagRepository;
    private final PasswordEncoder encoder;

    @Transactional
    @PostConstruct
    public void init()
    {
        Member master = memberRepository.save(
                Member.builder()
                        .age(14)
                        .email("sini@sini.com")
                        .password(encoder.encode("sini"))
                        .memberName("sini")
                        .memberRoles(List.of(MemberRole.ADMIN, MemberRole.USER))
                        .build()
        );

        List<String> postsIamgeUrls = List.of(
                "42a1986f-af16-495f-bfb7-33b8a85b1a7c_homepicture.jpg",
                "b5a5e9ec-2841-4264-b72f-2fac9bdeda4a_homepicture.jpg"
        );

        for (int i = 0; i < 5; i++)
        {
            PostImage image1 = PostImage.builder().url(postsIamgeUrls.get(0)).postImageOrder(1).build();
            PostImage image2 = PostImage.builder().url(postsIamgeUrls.get(1)).postImageOrder(2).build();

            Tag tag1 = tagRepository.save(Tag.builder().tagContent("tag1:" + i).build());
            Tag tag2 = tagRepository.save(Tag.builder().tagContent("tag2:" + i).build());

            PostTag postTag1 = PostTag.builder().tag(tag1).build();
            PostTag postTag2 = PostTag.builder().tag(tag2).build();
            Post post =
                    Post.builder()
                            .title("title : " + i)
                            .content("content : " + i)
                            .member(master)
                            .postCategory(i < 3 ? PostCategory.BACKEND : PostCategory.FRONTEND)
                            .build();


            post.addPostImage(image1);
            post.addPostImage(image2);

            post.addPostTag(postTag1);
            post.addPostTag(postTag2);
            postRepository.save(post);
        }
    }
}
