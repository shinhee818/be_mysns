package com.sini.mysns.api.controller.member;

import com.sini.mysns.api.controller.member.dto.MemberRequest;
import com.sini.mysns.api.controller.member.dto.FindMemberResponse;
import com.sini.mysns.api.controller.member.dto.UpdateMemberRequest;
import com.sini.mysns.api.service.member.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public Long createMember(@Valid @RequestBody MemberRequest request)
    {
        return memberService.createMember(request.toServiceDto());
    }

    @GetMapping("/{memberId}")
    public FindMemberResponse findMember(@PathVariable(value = "memberId") Long memberId)
    {
        return memberService.findMember(memberId);
    }

    @PutMapping("/{memberId}")
    public Long updateMember(
            @PathVariable(value = "memberId") Long memberId,
            @RequestBody UpdateMemberRequest request)
    {
        return memberService.updateMember(request.toServiceDto(memberId));
    }

    @DeleteMapping("/{memberId}")
    public void deleteMember(@PathVariable(value = "memberId")
                             @Valid Long memberId)
    {
        memberService.deleteMember(memberId);
    }
}
