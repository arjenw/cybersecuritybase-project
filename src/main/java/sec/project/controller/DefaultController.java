package sec.project.controller;

import static org.springframework.http.HttpMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.Account;
import sec.project.repository.AccountRepository;

@Controller
public class DefaultController {

  @Autowired
  private AccountRepository accountRepository;

  @RequestMapping("*")
  public String handleDefault(Authentication authentication) {
    Account acc = accountRepository.findByUsername(authentication.getName());
    if (acc == null) return "redirect:login";

    return "redirect:/notes/"+acc.getId();
  }

  @RequestMapping(value = "change-pw", method = RequestMethod.GET)
  public String getChangePw(Model model, Authentication authentication) {
    Account acc = accountRepository.findByUsername(authentication.getName());
    if (acc == null) return "redirect:login";

    model.addAttribute("user", acc.getUsername());
    model.addAttribute("password", acc.getPassword());
    return "changepw";
  }

  @RequestMapping(value = "change-pw", method = RequestMethod.POST)
  public String changePw(Authentication authentication, @RequestParam String pw) {
    Account acc = accountRepository.findByUsername(authentication.getName());
    if (acc == null) return "redirect:login";

    acc.setPassword(pw);
    accountRepository.save(acc);
    return "redirect:/";
  }

}
