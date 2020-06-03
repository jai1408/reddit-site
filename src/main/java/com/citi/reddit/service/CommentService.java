package com.citi.reddit.service;

import com.citi.reddit.dto.CommentsDto;
import com.citi.reddit.exceptions.PostNotFoundException;
import com.citi.reddit.mapper.CommentMapper;
import com.citi.reddit.model.Comment;
import com.citi.reddit.model.NotificationEmail;
import com.citi.reddit.model.Post;
import com.citi.reddit.model.User;
import com.citi.reddit.repository.CommentRepository;
import com.citi.reddit.repository.PostRepository;
import com.citi.reddit.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class CommentService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final MailService mailService;
    private MailContentBuilder mailContentBuilder;

    public void save(CommentsDto commentsDto) {
        Post post = postRepository.findById(commentsDto.getPostId()).orElseThrow(
                () -> new PostNotFoundException(commentsDto.getPostId().toString()));
        Comment comment = commentMapper.mapDtoToComment(commentsDto, post, authService.getCurrentUser());
        commentRepository.save(comment);
        String message = mailContentBuilder.build(post.getUser().getUsername() +" posted a comment !!");
        sendCommentNotificationMail(message, post.getUser());
    }

    private void sendCommentNotificationMail(String message, User user) {
        mailService.sendMail(new NotificationEmail(user.getUsername() +" commented on your post", user.getEmail(), message));
    }

    public List<CommentsDto> getAll(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new PostNotFoundException(id.toString()));
        return commentRepository.findByPost(post).stream().map(commentMapper::mapCommentToDto).collect(Collectors.toList());
    }

    public List<CommentsDto> getAllForUser(String userName) {
        User user = userRepository.findByUsername(userName).orElseThrow(
                () -> new UsernameNotFoundException(userName));
        return commentRepository.findAllByUser(user).stream().map(commentMapper::mapCommentToDto).collect(Collectors.toList());

    }
}
