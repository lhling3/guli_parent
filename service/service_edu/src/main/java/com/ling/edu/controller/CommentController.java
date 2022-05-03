package com.ling.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ling.commonutils.JwtUtils;
import com.ling.commonutils.MemberVo;
import com.ling.commonutils.R;
import com.ling.edu.client.UcenterClient;
import com.ling.edu.pojo.Comment;
import com.ling.edu.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-04-30
 */
@RestController
@RequestMapping("/eduservice/comment")

public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private UcenterClient ucenterClient;

    /**
     * 分页查询当前课程的评论
     * @param page
     * @param limit
     * @param courseId
     * @return
     */
    @GetMapping("getPageCommentList/{page}/{limit}/{courseId}")
    public R getPageCommentList(@PathVariable long page, @PathVariable long limit,
                                @PathVariable String courseId){
        Page<Comment> commentPage = new Page<>(page,limit);
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        commentService.page(commentPage,wrapper);
        List<Comment> commentList = commentPage.getRecords();

        Map<String, Object> map = new HashMap<>();
        map.put("items", commentList);
        map.put("current", commentPage.getCurrent());
        map.put("pages", commentPage.getPages());
        map.put("size", commentPage.getSize());
        map.put("total", commentPage.getTotal());
        map.put("hasNext", commentPage.hasNext());
        map.put("hasPrevious", commentPage.hasPrevious());
        return R.ok().data(map);
    }

    @PostMapping("addComment")
    public R addComment(@RequestBody Comment comment, HttpServletRequest request){
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if(StringUtils.isEmpty(memberId)) {
            return R.error().code(28004).message("请登录");
        }
        MemberVo memberVo = ucenterClient.getMemberById(memberId);
        comment.setMemberId(memberVo.getId());
        comment.setNickname(memberVo.getNickname());
        comment.setAvatar(memberVo.getAvatar());
        boolean isAdd = commentService.save(comment);
        if(isAdd){
            return R.ok();
        }else{
            return R.error();
        }
    }
}

