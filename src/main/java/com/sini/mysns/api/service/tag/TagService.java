package com.sini.mysns.api.service.tag;

import com.sini.mysns.api.service.tag.dto.CreateTagServiceRequest;
import com.sini.mysns.domain.tag.Tag;
import com.sini.mysns.domain.tag.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TagService {

    private final TagRepository tagRepository;

    public Long creatTag(CreateTagServiceRequest request)
    {
        Tag tag = Tag.builder()
                .tagContent("태그")
                .build();
        return tagRepository.save(tag).getTagId();
    }
}
