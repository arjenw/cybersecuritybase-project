package sec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.Account;
import sec.project.domain.Note;
import sec.project.repository.AccountRepository;
import sec.project.repository.NoteRepository;

@Controller
public class NoteController {
  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private NoteRepository noteRepository;

  @RequestMapping(value = "/notes/{userId}", method = RequestMethod.GET)
  public String notes(Model model, @PathVariable Long userId) {
    // vulnerability: insecure direct object reference
    Account acc = accountRepository.getOne(userId);
    if (acc == null) return "redirect:/login";

    // vulnerability: XSS on title
    model.addAttribute("user", acc.getUsername());
    model.addAttribute("notes", acc.getNotes());
    return "notes";
  }

  @RequestMapping(value = "/note", method = RequestMethod.POST)
  public String add(Authentication authentication, @RequestParam String title, @RequestParam String text) {
    Account acc = accountRepository.findByUsername(authentication.getName());
    Note note = new Note();
    note.setTitle(title);
    note.setText(text);
    acc.getNotes().add(note);
    noteRepository.save(note);
    accountRepository.save(acc);
    return "redirect:/";
  }

  @RequestMapping(value = "/note/{noteId}", method = RequestMethod.POST)
  public String edit(Model model, @PathVariable Long noteId, @RequestParam String text) {
    Note note = noteRepository.findOne(noteId);
    note.setText(text);
    noteRepository.save(note);
    return "redirect:/";
  }

  @RequestMapping(value = "/note/{noteId}", method = RequestMethod.DELETE)
  public String delete(Model model, @PathVariable Long noteId) {
    noteRepository.delete(noteId);
    return "redirect:/";
  }
}
