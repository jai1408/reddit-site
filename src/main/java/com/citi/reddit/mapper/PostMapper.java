package com.citi.reddit.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.citi.reddit.dto.PostRequest;
import com.citi.reddit.dto.PostResponse;
import com.citi.reddit.model.Post;
import com.citi.reddit.model.Subreddit;
import com.citi.reddit.model.User;

@Mapper(componentModel = "spring")
public interface PostMapper {

	@Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
	@Mapping(target = "description", source = "postRequest.description")
	public abstract Post mapPostRequestToPost(PostRequest postRequest, User user, Subreddit subreddit);
	
	
    @Mapping(target = "id", source = "postId")
    @Mapping(target = "subredditName", source = "subreddit.name")
    @Mapping(target = "userName", source = "user.username")
    public abstract PostResponse mapPostToPostResponse(Post post);
	
	

}
