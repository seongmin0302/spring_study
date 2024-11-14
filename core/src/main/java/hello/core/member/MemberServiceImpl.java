package hello.core.member;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService {

    private final MemberRepositoy memberRepositoy;

    public MemberRepositoy getMemberRepositoy() {
        return memberRepositoy;
    }


    @Autowired
    public MemberServiceImpl(MemberRepositoy memberRepositoy) {
        this.memberRepositoy = memberRepositoy;
    }

    @Override
    public void join(Member member) {

        memberRepositoy.save(member);

    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepositoy.findById(memberId);
    }
}
