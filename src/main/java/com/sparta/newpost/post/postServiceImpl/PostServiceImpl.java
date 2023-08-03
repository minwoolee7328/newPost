package com.sparta.newpost.post.postServiceImpl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.newpost.comment.repository.CommentRepository;
import com.sparta.newpost.exception.PostNotFoundException;
import com.sparta.newpost.post.dto.PostRequestDto;
import com.sparta.newpost.post.dto.PostResponseDto;
import com.sparta.newpost.post.dto.PostSearchDto;
import com.sparta.newpost.post.dto.PostSearchResponseDto;
import com.sparta.newpost.post.entity.Post;
import com.sparta.newpost.post.entity.QPost;
import com.sparta.newpost.post.postInerface.PostService;
import com.sparta.newpost.post.repository.PostRepository;

import com.sparta.newpost.security.UserDetailsImpl;
import com.sparta.newpost.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public void createPost(PostRequestDto requestDto, UserDetailsImpl user) {

        //유효한 토큰 인지
        if(!user.isEnabled()){
            throw new IllegalArgumentException("유효한 토큰이 아닙니다.");
        }

        User inUser = user.getUser();

        Post post = new Post(requestDto.getTitle(), requestDto.getContent(),inUser);

        postRepository.save(post);

    }

    @Override
    public List<PostResponseDto> getPosts(int page, int size, String sortBy, boolean isAsc) {

        // 페이지 정렬
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Post> postList = postRepository.findAll(pageable);
        List<PostResponseDto> postResponseDtoList = new ArrayList<>();

        for( Post post :postList){
            PostResponseDto postResponseDto = new PostResponseDto(post,post.getCommentList());
            postResponseDtoList.add(postResponseDto);
        }

        return postResponseDtoList;
    }

    @Override
    public PostResponseDto getPost(Long id) {
        Optional<Post> post = postRepository.findById(id);

        if(!post.isPresent()){
            throw new PostNotFoundException("해당 게시글이 존재하지 않습니다.");
        }

        return new PostResponseDto(post.get(),post.get().getCommentList());

    }

    @Override
    @Transactional
    public void chPost(Long id,PostRequestDto requestDto ,UserDetailsImpl user) {
        Optional<Post> post = postRepository.findById(id);

        if(!post.isPresent()){
            throw new PostNotFoundException("해당 게시글이 존재하지 않습니다.");
        }

        if(!post.get().getUser().getUsername().equals(user.getUsername())){
            throw new IllegalArgumentException("해당 게시글의 작성자가 아닙니다.");
        }

        String title = requestDto.getTitle();
        String content = requestDto.getContent();

        post.get().update(title,content);

    }

    @Override
    public void delPost(Long id, UserDetailsImpl user) {
        Optional<Post> post = postRepository.findById(id);

        if(!post.isPresent()){
            throw new IllegalArgumentException("해당 게시글이 존재하지 않습니다.");
        }

        if(!post.get().getUser().getUsername().equals(user.getUsername())){
            throw new IllegalArgumentException("해당 게시글의 작성자가 아닙니다.");
        }

        postRepository.delete(post.get());

    }

    // 사용자가 입력한 내용의 게시글을 반환
    @Override
    public List<PostSearchResponseDto> getPostSearch(PostSearchDto searchDto, Pageable pageable) {

       QPost post = QPost.post;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(post.title.contains(searchDto.getTitle()));

        var searchData =   jpaQueryFactory.select(post)
                                    .from(post)
                                    .where(builder)
                                    .offset(pageable.getOffset())
                                    .limit(pageable.getPageSize())
                                    .fetch();

        JPAQuery<Long> count = jpaQueryFactory.select(post.count())
                .from(post)
                .where(builder);

        Page<Post> postList = PageableExecutionUtils.getPage(searchData, pageable, count::fetchOne);

        List<PostSearchResponseDto> searchResponseDtoList = new ArrayList<>();

        for(Post searchDatas : postList){
            searchResponseDtoList.add(new PostSearchResponseDto(searchDatas.getTitle()));
        }

        return searchResponseDtoList;
    }
}
