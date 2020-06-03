package com.citi.reddit.service;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.citi.reddit.dto.PostRequest;
import com.citi.reddit.dto.PostResponse;
import com.citi.reddit.exceptions.PostNotFoundException;
import com.citi.reddit.exceptions.SubredditNotFoundException;
import com.citi.reddit.mapper.PostMapper;
import com.citi.reddit.model.Post;
import com.citi.reddit.model.Subreddit;
import com.citi.reddit.model.User;
import com.citi.reddit.repository.PostRepository;
import com.citi.reddit.repository.SubredditRepository;
import com.citi.reddit.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
public class PostService {

	private final PostRepository postRepository;
	private final PostMapper postMapper;
	private final SubredditRepository subredditRepository;
	private final AuthService authService;
	private final UserRepository userRepository;

	public void save(PostRequest postRequest) {
		Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
				.orElseThrow(() -> new SubredditNotFoundException(postRequest.getSubredditName()));
		User user = authService.getCurrentUser();
		postRepository.save(postMapper.mapPostRequestToPost(postRequest, user, subreddit));
	}

	public List<PostResponse> getAllPosts() {
		return postRepository.findAll().stream().map(postMapper::mapPostToPostResponse).collect(toList());
	}

	public PostResponse getPost(Long id) {
		return postMapper.mapPostToPostResponse(
				postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id.toString())));
	}

	public List<PostResponse> getPostsBySubreddit(Long id) {
		return postRepository
				.findAllBySubreddit(subredditRepository.findById(id)
						.orElseThrow(() -> new SubredditNotFoundException(id.toString())))
				.stream().map(postMapper::mapPostToPostResponse).collect(toList());
	}

	public List<PostResponse> getPostsByUsername(String username) {
		return postRepository
				.findByUser(userRepository.findByUsername(username)
						.orElseThrow(() -> new UsernameNotFoundException(username)))
				.stream().map(postMapper::mapPostToPostResponse).collect(toList());
	}

}
