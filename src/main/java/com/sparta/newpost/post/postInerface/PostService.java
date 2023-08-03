package com.sparta.newpost.post.postInerface;

import com.sparta.newpost.post.dto.PostRequestDto;
import com.sparta.newpost.post.dto.PostResponseDto;
import com.sparta.newpost.post.dto.PostSearchDto;
import com.sparta.newpost.post.dto.PostSearchResponseDto;
import com.sparta.newpost.post.entity.Post;
import com.sparta.newpost.security.UserDetailsImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface PostService {

    /**
     * 게시글 생성
     * @param requestDto 게시글 생성 요청정보
     * @param user 게시글 생성 요청자
     * @return 게시글 생성 결과
     */

    void createPost(PostRequestDto requestDto, UserDetailsImpl user);

    /**
     * 게시글 전체 조회
     * @return 게시글 전체 조회 결과
     */

    List<PostResponseDto> getPosts(int page, int size, String sortBy, boolean isAsc);

    /**
     * 게시글 게별 조회
     * @param id 게시글 아이디값
     * @return 게시글 게별 죄회 값
     */
    PostResponseDto getPost(Long id);

    /**
     * 게시글 수정
     * @param id 게시글 아이디값
     * @param requestDto 게시글 수정 요청정보
     * @param user 게시글 수정 요청자
     * @return 없음
     */

    void chPost(Long id,PostRequestDto requestDto ,UserDetailsImpl user);

    /**
     * 게시글 삭제
     * @param id 게시글 아이디값
     * @param user 게시글 삭제 요청자
     * @return 없음
     */
    void delPost(Long id, UserDetailsImpl user);


    List<PostSearchResponseDto> getPostSearch(PostSearchDto searchDto, Pageable pageable);
}
