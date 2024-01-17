package inu.amigo.order_it.user;

import inu.amigo.order_it.user.JoinDto;
import inu.amigo.order_it.user.JoinService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Slf4j
@RestController
public class JoinController {

    private final JoinService joinService;

    @Autowired
    public JoinController(JoinService joinService) {
        this.joinService = joinService;
    }

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
