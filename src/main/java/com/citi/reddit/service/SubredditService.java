package com.citi.reddit.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.citi.reddit.dto.SubredditDto;
import com.citi.reddit.exceptions.SpringRedditException;
import com.citi.reddit.mapper.SubredditMapper;
import com.citi.reddit.model.Subreddit;
import com.citi.reddit.repository.SubredditRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {

	private final SubredditRepository subredditRepository;
	private final SubredditMapper subredditMapper;

	@Transactional
	public SubredditDto save(SubredditDto dto) {
		// Subreddit subreddit = subredditRepository.save(mapSubredditDto(dto));
		Subreddit subreddit = subredditRepository.save(subredditMapper.mapDtoToSubreddit(dto));
		dto.setId(subreddit.getId());
		return dto;

	}

	/*
	 * private Subreddit mapSubredditDto(SubredditDto dto) { return
	 * Subreddit.builder().name(dto.getName()).description(dto.getDescription()).
	 * build(); }
	 */

	@Transactional(readOnly = true)
	public List<SubredditDto> getAll() {
		// return
		// subredditRepository.findAll().stream().map(this::mapToDto).collect(toList());
		return subredditRepository.findAll().stream().map(subredditMapper::mapSubredditToDto).collect(toList());

	}

	/*
	 * private SubredditDto mapToDto(Subreddit subreddit) { return
	 * SubredditDto.builder().name(subreddit.getName()).id(subreddit.getId())
	 * .numberOfPosts(subreddit.getPosts().size()).description(subreddit.
	 * getDescription()).build(); }
	 */

	public SubredditDto getSubredditById(Long id) {
		Optional<Subreddit> subredditOptional = subredditRepository.findById(id);
		Subreddit subreddit = subredditOptional.orElseThrow(() -> new SpringRedditException("No subreddit found with ID - " + id));
		return subredditMapper.mapSubredditToDto(subreddit);
	}
}
