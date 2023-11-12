package reading.project.domain.member.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import reading.project.domain.member.dto.SearchFollow;
import reading.project.domain.member.entity.Follow;
import reading.project.domain.member.entity.Member;

import java.util.List;

public interface MemberRepositoryCustom {

    List<Follow> findByFollowing(Long id, Member member);
    Slice<SearchFollow> findByFollowingList(Long id, Long cursorId, Pageable pageable);
    Slice<SearchFollow> findByFollowerList(Long id, Long cursorId, Pageable pageable);
}