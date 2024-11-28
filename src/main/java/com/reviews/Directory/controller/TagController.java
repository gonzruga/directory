package com.reviews.Directory.controller;


import com.reviews.Directory.dto.TagDto;
import com.reviews.Directory.entity_model.Tag;
import com.reviews.Directory.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class TagController {

    @Autowired
    private final TagService service;

    // CREATE - POST

    @GetMapping("/tagForm")
    public String tagForm (Model model) {
        TagDto tag = new TagDto();
        model.addAttribute("tag", tag);
        return "tag-form";
    }

    @PostMapping("/tagSubmit")
    public String saveTag(@ModelAttribute TagDto tag, Model model) {
        model.addAttribute("tag", tag);
        service.saveTag(tag);
        return "redirect:/tagList";
    }

    // READ - GET

    @GetMapping("/tagList")  //With filter
    public String findAllTags(Model model, @Param("keyword") String keyword){
        model.addAttribute("tag", service.listAll(keyword));
        model.addAttribute("keyword", keyword);
        return "tag-list";
    }

    @GetMapping("/tagPage/{id}")
    public String tagPage(@PathVariable long id, Model model) {
        model.addAttribute("tag", service.getTagById(id));
        return "tag-page";
    }

    // UPDATE - PUT
    @GetMapping("/tagEdit/{id}")
    public String editTag(@PathVariable long id, Model model) {
        Tag tag = new Tag();
        tag = service.getTagById(id);
        model.addAttribute("tag", tag);
        return "tag-edit";
    }

    @PostMapping("tagUpdate/{id}")
    public String updateTag(@ModelAttribute TagDto tag, @PathVariable long id) {
        service.updateTag(tag);
        return "redirect:/tagPage/{id}";
    }

    // DELETE
    @GetMapping("/tagDelete/{id}")
    public String deleteTag(@PathVariable long id) {
        service.deleteTag(id);
        return "redirect:/tagList";
    }

}
