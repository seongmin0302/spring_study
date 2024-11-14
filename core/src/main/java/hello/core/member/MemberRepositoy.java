package hello.core.member;

public interface MemberRepositoy {

    void save(Member member);

    Member findById(Long memberId);

}
