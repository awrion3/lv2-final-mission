package finalmission.member.controller;

import finalmission.member.auth.dto.request.MemberSignupRequest;
import finalmission.member.dto.MemberResponse;
import finalmission.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "회원 조회 API")
    @GetMapping
    public List<MemberResponse> findAllMember() {
        return memberService.findAllMember();
    }

    @Operation(summary = "회원 가입 API")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void signup(@RequestBody @Valid MemberSignupRequest request) {
        memberService.save(request);
    }
}
