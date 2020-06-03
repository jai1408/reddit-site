package com.citi.reddit.mapper;

import com.citi.reddit.dto.CommentsDto;
import com.citi.reddit.model.Comment;
import com.citi.reddit.model.Post;
import com.citi.reddit.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "text", source = "dto.text")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "post", source = "post")
    @Mapping(target ="user", source = "user")
    Comment mapDtoToComment(CommentsDto dto, Post post, User user);

    @Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
    @Mapping(target = "userName", expression = "java(comment.getUser().getUsername())")
    CommentsDto mapCommentToDto(Comment comment);
}
