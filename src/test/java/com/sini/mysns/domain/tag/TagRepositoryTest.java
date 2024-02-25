package com.sini.mysns.domain.tag;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DataJpaTest
class TagRepositoryTest {

    @Autowired
    TagRepository tagRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    void findAll()
    {
        // given
        tagRepository.saveAll(
                List.of(
                        Tag.builder().tagContent("content").build(),
                        Tag.builder().tagContent("content1").build(),
                        Tag.builder().tagContent("content2").build()
                )
        );

        entityManager.flush();
        entityManager.clear();

        // when
        List<Tag> tags = tagRepository.findAllInContent(List.of("content", "content1"));

        // then
        assertThat(tags).extracting(Tag::getTagContent)
                .contains("content", "content1");
    }
}