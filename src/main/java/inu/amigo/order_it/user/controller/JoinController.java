package inu.amigo.order_it.user.controller;

import inu.amigo.order_it.user.dto.JoinDto;
import inu.amigo.order_it.user.service.JoinService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@Tag(name = "Join API")
@Slf4j
@RestController
public class JoinController {

    private final JoinService joinService;

    @Autowired
    public JoinController(JoinService joinService) {
        this.joinService = joinService;
    }

    @Operation(summary = "회원가입")
    @Parameter(name = "username", description = "유저이름")
    @Parameter(name = "password", description = "비밀번호")
    @ApiResponse(responseCode = "200", description = "로그인 페이지로 리다이렉트")
    @PostMapping("/joinProc")
    public RedirectView joinProcess(JoinDto joinDto) {
        joinService.joinProcess(joinDto);
        return new RedirectView("/login");
    }

    @ExceptionHandler
    public String controllerExceptionHandler(Exception e) {
        log.error(e.getMessage());
        return "/404";
    }
}
