package com.fangcm.modules.demo.controller;

import com.fangcm.common.entity.PageVo;
import com.fangcm.common.entity.Result;
import com.fangcm.common.utils.PageUtil;
import com.fangcm.common.utils.ResultUtil;
import com.fangcm.modules.demo.entity.Todo;
import com.fangcm.modules.demo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

/**
 * Created by FangCM on 2018/5/23.
 */

@RestController
@RequestMapping("/todo")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Principal principal) {
        if (principal != null) {
            SecurityContextHolder.clearContext();
        }
        return "index";
    }

    @RequestMapping(value = "/getAllByPage", method = RequestMethod.GET)
    public Result<Page<Todo>> todolist(@ModelAttribute PageVo pageVo) {
        Page<Todo> page = todoService.getLogList(PageUtil.initPage(pageVo));
        return new ResultUtil<Page<Todo>>().setData(page);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/addTodo", method = RequestMethod.POST)
    public String addTodo(@Valid @ModelAttribute("todo") Todo todo, BindingResult result) {
        if (!result.hasErrors()) {
            todoService.save(todo);
        }
        return "redirect:/todolist";
    }

    @RequestMapping(value = "/updateTodo/{id}", method = RequestMethod.GET)
    public String updateTodo(@PathVariable Long id, Model model) {
        Optional<Todo> optionalTodo = todoService.findById(id);

        if (optionalTodo.isPresent()) {
            Todo todo = optionalTodo.get();

            model.addAttribute("todo", todo);
            return "update";
        } else {
            return "redirect:/todolist";
        }
    }

    @RequestMapping(value = "/updateTodo", method = RequestMethod.POST)
    public String updateTodo(@Valid @ModelAttribute("todo") Todo todo, BindingResult result) {
        if (!result.hasErrors()) {
            todoService.save(todo);
        }
        return "redirect:/todolist";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/delByIds", method = RequestMethod.DELETE)
    public Result<Object> deleteTodo(@RequestParam String[] ids) {
        for (String id : ids) {
            todoService.deleteById(id);
        }
        return new ResultUtil<Object>().setSuccessMsg("删除成功");
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public Result<User> getUserInfo() {

        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User u = userService.findByUsername(user.getUsername());
        u.setPassword(null);
        return new ResultUtil<User>().setData(u);
    }

}