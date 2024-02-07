package reading.project.domain.challenge.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reading.project.domain.book.entity.Book;
import reading.project.domain.book.service.BookService;
import reading.project.domain.challenge.dto.request.ChallengeRequest;
import reading.project.domain.challenge.dto.response.ChallengeDetailResponse;
import reading.project.domain.challenge.entity.Challenge;
import reading.project.domain.challenge.entity.ChallengeMember;
import reading.project.domain.challenge.repository.ChallengeMemberRepository;
import reading.project.domain.challenge.repository.ChallengeRepository;
import reading.project.domain.challenge.dto.response.ChallengeMemberInfo;
import reading.project.domain.member.entity.Member;
import reading.project.domain.member.service.MemberService;
import reading.project.global.exception.CustomException;

import java.time.LocalDate;
import java.util.List;

import static reading.project.global.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChallengeService {
    private final ChallengeRepository challengeRepository;
    private final ChallengeMemberRepository challengeMemberRepository;
    private final BookService bookService;
    private final MemberService memberService;
    private static final int MIN_PARTICIPANT_COUNT = 1;

    @Transactional
    public Long createChallenge(Long bookId, Long loginId, ChallengeRequest request) {
        Book book = bookService.findBookById(bookId);
        Member member = memberService.findExistsMember(loginId);

        Challenge challenge = request.toEntity(loginId, book);
        challengeRepository.save(challenge);
        challengeMemberRepository.save(ChallengeMember.of(true, false, challenge, member));

        return challenge.getId();
    }

    @Transactional
    public void updateChallenge(Long challengeId, Long loginId, ChallengeRequest request) {
        Challenge challenge = findChallengeById(challengeId);
        Member member = memberService.findExistsMember(loginId);
        checkHost(challengeId, loginId);

        challenge.update(request.getContent(), request.getRecruitCount(), request.getStartDate(), request.getEndDate());
    }

    @Transactional
    public void deleteChallenge(Long challengeId, Long loginId) {
        Challenge challenge = findChallengeById(challengeId);
        Member member = memberService.findExistsMember(loginId);

        // 호스트만 삭제 가능
        checkHost(challengeId, loginId);

        // 이미 시작한 챌린지에 참여자가 있을 경우 삭제 불가
        if (challenge.getStartDate().isBefore(LocalDate.now()) && getParticipantCount(challengeId) > MIN_PARTICIPANT_COUNT) {
            throw new CustomException(PARTICIPANT_EXISTS);
        }

        challengeRepository.delete(challenge);
    }

    public ChallengeDetailResponse getChallenge(Long challengeId, Long loginId) {
        Challenge challenge = findChallengeById(challengeId);
        Member member = memberService.findExistsMember(loginId);

        return challengeRepository.getChallenge(challengeId, loginId);
    }

    public List<ChallengeMemberInfo> getParticipants(Long challengeId) {
        Challenge challenge = findChallengeById(challengeId);

        return challengeMemberRepository.getParticipants(challengeId);
    }

    public Challenge findChallengeById(Long challengeId) {
        return challengeRepository.findById(challengeId)
                .orElseThrow(() -> new CustomException(CHALLENGE_NOT_FOUND));
    }

    public Long findByChallengeIdAndMemberId(Long challengeId, Long memberId) {
        ChallengeMember challengeMember = challengeMemberRepository.findByChallengeIdAndMemberId(challengeId, memberId)
                .orElseThrow(() -> new CustomException(NOT_PARTICIPANT));

        return challengeMember.getId();
    }
    private void checkHost(Long challengeId, Long memberId) {
        Challenge challenge = findChallengeById(challengeId);

        if (!challenge.getHostId().equals(memberId)) {
            throw new CustomException(NOT_CHALLENGE_HOST);
        }
    }

    private Long getParticipantCount(Long challengeId) {
        return challengeMemberRepository.countByChallengeId(challengeId);
    }
}
