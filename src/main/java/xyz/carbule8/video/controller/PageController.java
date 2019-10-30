package xyz.carbule8.video.controller;

import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import xyz.carbule8.video.config.VideoConfig;
import xyz.carbule8.video.pojo.Video;
import xyz.carbule8.video.service.VideoService;

@Controller
public class PageController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private VideoConfig videoConfig;

    @GetMapping("/upload")
    public String uploadPage() {
        return "upload";
    }

    @GetMapping({"/list", "/list/page/{page}"})
    public String indexPage(@PathVariable(value = "page", required = false) Integer page,
                            @ModelAttribute("query") String query,
                            Model model) {
        Page<Video> videos = videoService.findAll(query, new xyz.carbule8.video.pojo.Page(page, 20));
        model.addAttribute("data", videos);
        return "list";
    }

    @GetMapping({"/", "/page/{page}"})
    public String index2Page(@PathVariable(value = "page", required = false) Integer page,
                            @ModelAttribute("query") String query,
                            Model model) {
        Page<Video> videos = videoService.findAllComplete(new xyz.carbule8.video.pojo.Page(page, 20));
        model.addAttribute("data", videos);
        model.addAttribute("accessUrl", videoConfig.getAccessUrl());
        return "index";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

}
