package com.example.xJava8.controller;

import com.example.xJava8.entity.*;
import com.example.xJava8.form.CommentForm;
import com.example.xJava8.form.MessageForm;
import com.example.xJava8.form.SignUpForm;
import com.example.xJava8.form.UserForm;
import com.example.xJava8.model.LoginUserDetails;
import com.example.xJava8.service.CommentService;
import com.example.xJava8.service.MessageService;
import com.example.xJava8.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Conventions;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Controller
@Validated
public class TopController {
    @Autowired
    private UserService userService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/home")
    public ModelAndView home(@AuthenticationPrincipal LoginUserDetails user,
                            @RequestParam(required = false) String start, @RequestParam(required = false) String end,
                            Model model) {

        List<Message> hoge = messageService.test();

        ModelAndView mav = new ModelAndView("top");
        List<MessageJoinUser> messages = messageService.selectAllJoinUser(start, end);
        List<CommentJoinUser> comments = commentService.selectAllJoinUser();
        LoginUser loginUser = user.getLoginUser();

        if (!model.containsAttribute("messageForm")) {
            mav.addObject("messageForm", new MessageForm());
        }
        if (!model.containsAttribute("commentForm")) {
            mav.addObject("commentForm", new CommentForm());
        }
        mav.addObject("messages", messages);
        mav.addObject("comments", comments);
        mav.addObject("loginUser", loginUser);
        mav.addObject("start", start);
        mav.addObject("end", end);
        return mav;
    }

    @PostMapping("/tweet")
    public ModelAndView tweet(@AuthenticationPrincipal LoginUserDetails user,
                              @Validated @ModelAttribute MessageForm messageForm, BindingResult result,
                              RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView("redirect:");
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("messageForm", messageForm);
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + Conventions.getVariableName(messageForm), result);
            return mav;
        }
        messageForm.setUserId(String.valueOf(user.getLoginUser().getId()));
        messageService.doTweet(messageForm);
        return mav;
    }

    @PostMapping("/comment")
    public ModelAndView comment(@AuthenticationPrincipal LoginUserDetails user,
                                @Validated @ModelAttribute CommentForm commentForm, BindingResult result,
                                RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView("redirect:");
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("commentForm", commentForm);
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + Conventions.getVariableName(commentForm), result);
            return mav;
        }
        commentForm.setUserId(String.valueOf(user.getLoginUser().getId()));
        commentService.insert(commentForm);
        return mav;
    }

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @GetMapping("/signup")
    public ModelAndView signUp() {
        ModelAndView mav = new ModelAndView("signup");
        mav.addObject("signUpForm", new SignUpForm());

        return mav;
    }
    @PostMapping("/signup")
    public ModelAndView signUp(@Validated @ModelAttribute SignUpForm signUpForm, BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView();
            mav.addObject("signUpForm", signUpForm);
            return new ModelAndView("signup");
        }
        userService.signUp(signUpForm);

        return new ModelAndView("redirect:");
    }
    @GetMapping("/setting")
    public ModelAndView setting(@AuthenticationPrincipal LoginUserDetails user) {
        ModelAndView mav = new ModelAndView("setting");
        mav.addObject("userForm", user.getLoginUser());
        return mav;
    }
    @PostMapping("/setting")
    public ModelAndView setting(@AuthenticationPrincipal LoginUserDetails user,
                                @Validated @ModelAttribute UserForm userForm, BindingResult result) {

        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView();
            mav.addObject("userForm", userForm);
            return new ModelAndView("setting");
        }
        userService.userUpdate(userForm);

        // ログインユーザー情報の更新
        user.getLoginUser().setName(userForm.getName());
        user.getLoginUser().setEmail(userForm.getEmail());
        user.getLoginUser().setDescription(userForm.getDescription());
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(
                user, user.getPassword(), user.getAuthorities()));

        return new ModelAndView("redirect:");
    }
    @GetMapping("editTweet")
    public ModelAndView edit(@RequestParam(required = false) @NotNull @Pattern(regexp= "^[0-9]+$") String id) {
        ModelAndView mav = new ModelAndView("editTweet");

        MessageForm messageForm = messageService.selectById(Integer.valueOf(id));
        mav.addObject("messageForm", messageForm);
        return mav;
    }
    @PostMapping("editTweet")
    public ModelAndView edit(@Validated @ModelAttribute MessageForm messageForm, BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView();
            mav.addObject("messageForm", messageForm);
            return new ModelAndView("editTweet");
        }
        messageService.editTweet(messageForm);
        return new ModelAndView("redirect:");
    }
    @PostMapping("/deleteTweet")
    public ModelAndView delete(Integer id) {
        messageService.delete(id);
        return new ModelAndView("redirect:");
    }
    @GetMapping("editComment")
    public ModelAndView editComment(@RequestParam Integer id) {
        ModelAndView mav = new ModelAndView("editComment");
        CommentForm commentForm = commentService.selectById(id);
        mav.addObject("commentForm", commentForm);

        return mav;
    }

    @PostMapping("editComment")
    public ModelAndView editComment(@Validated @ModelAttribute CommentForm commentForm, BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView();
            mav.addObject("commentForm", commentForm);
            return new ModelAndView("editComment");
        }

        commentService.update(commentForm);
        return new ModelAndView("redirect:");
    }

    @PostMapping("/deleteComment")
    public ModelAndView deleteComment(Integer id) {
        commentService.delete(id);
        return new ModelAndView("redirect:");
    }
}
