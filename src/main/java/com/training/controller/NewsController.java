package com.training.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.training.model.News;
import com.training.service.NewsService;

import lombok.RequiredArgsConstructor;

import static com.training.utils.Constant.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("news")
public class NewsController {
    private final NewsService newsService;
    String size = "5";

    @GetMapping("/listNews")
    public String listNews(Model model) {
        return listNews(model, 1, 5, "newsId", "asc", "");
    }

    @GetMapping("/listNews/{page}")
    public String pageList(Model model, @PathVariable(value = "page") int page,
                           @RequestParam(value = "size", required = false, defaultValue = "5") int size,
                           @RequestParam(value = "sortField", required = false) String sortField,
                           @RequestParam(value = "sortDir", required = false) String sortDir,
                           @RequestParam(value = "keyword", required = false) String keyword) {
        return listNews(model, page, size, sortField, sortDir, keyword);
    }

    @GetMapping("/search")
    public String searchNews(Model model, @RequestParam(value = "size", required = false, defaultValue = "5") int size,
                             @RequestParam(value = "keyword", required = false) String keyword) {
        return listNews(model, 1, size, "newsId", "asc", keyword);
    }

    public String listNews(Model model, int page, int size, String sortField, String sortDir, String keyword) {
        List<Integer> pagesizes = Arrays.asList(5, 25, 50, 100);
        Page<News> pages = newsService.findPaginated(page, size, sortField, sortDir, keyword);
        List<News> news = pages.getContent();
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pages.getTotalPages());
        model.addAttribute("totalItems", pages.getTotalElements());
        model.addAttribute("elementt", pages.getNumberOfElements() + size * (page - 1));
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("keyword", keyword);
        model.addAttribute("size", size);
        model.addAttribute("firstE", size * (page - 1) + 1);
        model.addAttribute("pagesizes", pagesizes);
        model.addAttribute("news", news);
        return NEWS_LIST_NEWS;
    }

    @GetMapping(value = "addNews")
    public String addNews(Model model) {
        News news = new News();
        model.addAttribute("news", news);
        return NEWS_ADD_NEWS;
    }

    @PostMapping(value = "addNews")
    public String saveNews(Model model, @ModelAttribute("news") @Valid @RequestBody News newsAttr, BindingResult result,
                           MultipartFile multipartFile, RedirectAttributes redirectAttributes)
            throws IOException {
        if (result.hasErrors()) {
            return NEWS_ADD_NEWS;
        }
        News news = new News();
        news = newsAttr;
        news.setActive(0);
        news.setContent(newsAttr.getContent());
        news.setPreview(newsAttr.getPreview());
        news.setTitle(newsAttr.getTitle());
        news.setPostDate(newsAttr.getPostDate());
        newsService.save(news);
        return NEWS_REDIRECT_LIST_NEWS;
    }

    @GetMapping(value = "updateNews")
    public String showUpdateNews(Model model, @RequestParam("id") UUID id) {
        model.addAttribute("news", newsService.findById(id));
        model.addAttribute("ava", newsService.findById(id).get());
        return NEWS_ADD_NEWS;
    }

    @GetMapping(value = "/deleteNews")
    public String deleteNews(Model model, @RequestParam("id") UUID id, RedirectAttributes redirectAttributes) {
        News news = newsService.findById(id).get();
        news.setActive(1);
        newsService.save(news);
        return NEWS_REDIRECT_LIST_NEWS;
    }

    @PostMapping(value = "deleteListNews")
    public String deleteListNews(Model model, @RequestParam(value = "idChecked", required = false) List<UUID> ids,
                                 RedirectAttributes redirectAttributes) {
        if (!(ids == null || ids.isEmpty())) {
            for (UUID id : ids) {
                News news = newsService.findById(id).get();
                news.setActive(1);
                newsService.save(news);
                redirectAttributes.addFlashAttribute("deleteSuccess", "Delete Success");
            }
            return NEWS_REDIRECT_LIST_NEWS;
        }
        redirectAttributes.addFlashAttribute("errorDelete", "No data deleteted!");
        return NEWS_REDIRECT_LIST_NEWS;
    }
}
