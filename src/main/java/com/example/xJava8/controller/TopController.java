package com.example.xJava8.controller;

import com.example.xJava8.entity.*;
import com.example.xJava8.form.CommentForm;
import com.example.xJava8.form.MessageForm;
import com.example.xJava8.form.UnlockForm;
import com.example.xJava8.form.UserForm;
import com.example.xJava8.model.LoginUserDetails;
import com.example.xJava8.service.CommentService;
import com.example.xJava8.service.MessageService;
import com.example.xJava8.service.TokenService;
import com.example.xJava8.service.UserService;
import com.example.xJava8.validator.groups.CreateTokenValidator;
import com.example.xJava8.validator.groups.EditUserValidator;
import com.example.xJava8.validator.groups.SignUpValidator;
import com.example.xJava8.validator.groups.UnlockValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.Conventions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Locale;

@Controller
@Validated
public class TopController {
    @Autowired
    private UserService userService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private MessageSource messageSource;



    @GetMapping("/home")
    public ModelAndView home(@AuthenticationPrincipal LoginUserDetails loginUserDetails,
                            @RequestParam(required = false) String start, @RequestParam(required = false) String end,
                            Model model, @PageableDefault(size = 5) Pageable pageable) {

        ModelAndView mav = new ModelAndView("top");

        // 仕組んだ例外:検索時に終了日のみ入力がない場合は例外を出すようにしている。
        if (StringUtils.hasText(start) && !StringUtils.hasText(end)) {
            throw new RuntimeException();
        }
        // アカウントロックの検出
        // 何らかの処理を行いNGカウントが10以上になりhomeにリダイレクトされば場合、強制的にログアウトして、ロックされたことを伝える
        if (loginUserDetails.getLoginUser().getNgCount() >= 10)  {
            return new ModelAndView("redirect:./login?lock");
        }

        List<MessageJoinUser> messages = messageService.selectAllJoinUser(start, end);
        List<CommentJoinUser> comments = commentService.selectAllJoinUser();
        User loginUser = loginUserDetails.getLoginUser();

        // RedirectAttributesを使用してformやBindingResultが送られてくるが、無い場合のみ空のformを格納する
        // そうしないと空のformが格納され、BindingResultも上書きされてしまう
        if (!model.containsAttribute("messageForm")) {
            mav.addObject("messageForm", new MessageForm());
        }
        if (!model.containsAttribute("commentForm")) {
            mav.addObject("commentForm", new CommentForm());
        }

        // TODO: ページング処理追加
        // 表示ページの開始・終了インデックスを定める
        int indexStart = (int) pageable.getOffset();
        int indexEnd = Math.min((indexStart + pageable.getPageSize()), messages.size());
        List<MessageJoinUser> pageContents = messages.subList(indexStart, indexEnd);

        Page<MessageJoinUser> pageList = new PageImpl<>(pageContents, pageable, messages.size());

//        mav.addObject("messages", messages);
        mav.addObject("comments", comments);
        mav.addObject("loginUser", loginUser);
        mav.addObject("start", start);
        mav.addObject("end", end);
        mav.addObject("page", pageList);
        mav.addObject("messages", pageContents);
        return mav;
    }

    @PostMapping("/tweet")
    public ModelAndView tweet(@AuthenticationPrincipal LoginUserDetails loginUserDetails,
                              @Validated @ModelAttribute MessageForm messageForm, BindingResult result,
                              RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView("redirect:./home");
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("messageForm", messageForm);
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + Conventions.getVariableName(messageForm), result);
            return mav;
        }
        messageForm.setUserId(String.valueOf(loginUserDetails.getLoginUser().getId()));
        messageService.doTweet(messageForm);
        return mav;
    }

    @PostMapping("/comment")
    public ModelAndView comment(@AuthenticationPrincipal LoginUserDetails loginUserDetails,
                                @Validated @ModelAttribute CommentForm commentForm, BindingResult result,
                                RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView("redirect:./home");
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("commentForm", commentForm);
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + Conventions.getVariableName(commentForm), result);
            return mav;
        }
        commentForm.setUserId(String.valueOf(loginUserDetails.getLoginUser().getId()));
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
        mav.addObject("userForm", new UserForm());

        return mav;
    }
    @PostMapping("/signup")
    public ModelAndView signUp(@Validated(SignUpValidator.class) @ModelAttribute UserForm userForm, BindingResult result) {

        // アカウント名重複チェック
        List<User> duplicationUser = userService.selectByName(userForm.getName());
        if (!duplicationUser.isEmpty()) {
            FieldError fieldError = new FieldError(result.getObjectName(), "name",
                    userForm.getName(), false, null, null,
                    messageSource.getMessage("e.004", null, Locale.JAPAN));
            result.addError(fieldError);
        }

        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView("signup");
            mav.addObject("userForm", userForm);
            return mav;
        }
        userService.signUp(userForm);

        return new ModelAndView("redirect:./login");
    }
    @GetMapping("/editUser")
    public ModelAndView editUser(@AuthenticationPrincipal LoginUserDetails loginUserDetails) {
        ModelAndView mav = new ModelAndView("editUser");
        mav.addObject("userForm", loginUserDetails.getLoginUser());
        return mav;
    }
    @PostMapping("/editUser")
    public ModelAndView editUser(@AuthenticationPrincipal LoginUserDetails loginUserDetails,
                                 @Validated(EditUserValidator.class) @ModelAttribute UserForm userForm, BindingResult result) {

        // アカウント名重複チェック
        List<User> duplicationUser = userService.selectByName(userForm.getName());
        if (!duplicationUser.isEmpty() && !duplicationUser.get(0).getId().equals(userForm.getId())) {
            FieldError fieldError = new FieldError(result.getObjectName(), "name",
                    messageSource.getMessage("e.004", null, Locale.JAPAN));
            result.addError(fieldError);
        }
        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView();
            mav.addObject("userForm", userForm);
            return new ModelAndView("editUser");
        }
        userService.userUpdate(userForm);

        return new ModelAndView("redirect:./home");
    }
    @GetMapping("/editTweet")
    public ModelAndView edit(@RequestParam(required = false) Integer id) {
        ModelAndView mav = new ModelAndView("editTweet");

        MessageForm messageForm = messageService.selectById(id);
        mav.addObject("messageForm", messageForm);
        return mav;
    }
    @PostMapping("/editTweet")
    public ModelAndView edit(@Validated @ModelAttribute MessageForm messageForm, BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView();
            mav.addObject("messageForm", messageForm);
            return new ModelAndView("editTweet");
        }
        messageService.editTweet(messageForm);
        return new ModelAndView("redirect:./home");
    }
    @PostMapping("/deleteTweet")
    public ModelAndView delete(Integer id) {
        messageService.delete(id);
        return new ModelAndView("redirect:./home");
    }
    @GetMapping("/editComment")
    public ModelAndView editComment(@RequestParam(required = false) Integer id) {
        ModelAndView mav = new ModelAndView("editComment");
        CommentForm commentForm = commentService.selectById(id);
        mav.addObject("commentForm", commentForm);

        return mav;
    }

    @PostMapping("/editComment")
    public ModelAndView editComment(@Validated @ModelAttribute CommentForm commentForm, BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView();
            mav.addObject("commentForm", commentForm);
            return new ModelAndView("editComment");
        }

        commentService.update(commentForm);
        return new ModelAndView("redirect:./home");
    }

    @PostMapping("/deleteComment")
    public ModelAndView deleteComment(Integer id) {
        commentService.delete(id);
        return new ModelAndView("redirect:./home");
    }

    // RedirectAttributeのaddFlashAttributeはModelでも受け取れる
    @GetMapping("/unlock")
    public ModelAndView unlockView(@AuthenticationPrincipal LoginUserDetails loginUser, Model model) {
        ModelAndView mav = new ModelAndView("unlock");
        UnlockForm unlockForm = new UnlockForm();

        if (!model.containsAttribute("unlockForm")) {
            if (loginUser != null) {
                // 凍結解除かログインした状態で直接遷移、ユーザ名あり
                unlockForm.setName(loginUser.getUsername());
            }
            // トークン生成成功時にユーザ名を保持する

            // 生成されたトークンを格納、無ければ空
            unlockForm.setToken((String) model.getAttribute("token"));
            mav.addObject("unlockForm", unlockForm);
        }
        return mav;
    }

    @PostMapping("/unlock")
    public ModelAndView unlock(@Validated(UnlockValidator.class) @ModelAttribute UnlockForm unlockForm, BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return new ModelAndView("unlock");
        }

        // エラーとして、トークンありで期限切れ、トークンが見つからないこれを分ける必要があるかどうか
        Token activeToken = tokenService.findByNameAndToken(unlockForm.getName(), unlockForm.getToken());

        // 基本的には見つからない場合、無効なトークンとしてエラー処理
        if (activeToken == null) {
            // ユーザー名のみ保持
            unlockForm.setToken("");
            redirectAttributes.addFlashAttribute("unlockForm", unlockForm);
            return new ModelAndView("redirect:./unlock?invalidToken");
        }
        // この辺の途中で処理止まったときのトランザクション制御必要？
        // NGカウントリセット
        userService.resetNgCount(activeToken.getUserId());
        // トークン削除：リセットしたユーザのトークン全て削除したい
        tokenService.deleteByUserId(activeToken.getUserId());

        return new ModelAndView("redirect:./login?unlockSuccess");
    }

    @PostMapping("createToken")
    public ModelAndView createToken(@Validated(CreateTokenValidator.class) @ModelAttribute UnlockForm unlockForm, BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("unlockForm", unlockForm);
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + Conventions.getVariableName(unlockForm), result);
            return new ModelAndView("redirect:./unlock");
        }

        // 1. ユーザ名でDB検索
        // NGカウント数を見て凍結・ロックされているかどうかも見ないといけない
        User tokenUser = userService.findByName(unlockForm.getName()).orElse(null);
        // 2. あればトークン生成、なければエラー
        if (tokenUser == null) {
            return new ModelAndView("redirect:./unlock?notFound");
        } else if (tokenUser.getNgCount() < 5) {
            return new ModelAndView("redirect:./unlock?notLocked");
        }

        // トークン生成、リダイレクト先にユーザ名とトークンを渡す。
        unlockForm.setToken(tokenService.createUnlockToken(tokenUser.getId()));
        redirectAttributes.addFlashAttribute("unlockForm", unlockForm);
//        String token = tokenService.createUnlockToken(tokenUser.getId());
//        redirectAttributes.addFlashAttribute("token", token);
        return new ModelAndView("redirect:./unlock");
    }
}
