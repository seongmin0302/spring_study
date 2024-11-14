package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDIscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepositoy;
import hello.core.member.MemoryMemberRespositoy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class OrderServiceImpl implements OrderService{

    private final MemberRepositoy memberRepositoy;
    private final DiscountPolicy discountPolicy;



    @Autowired
    public OrderServiceImpl(MemberRepositoy memberRepositoy, DiscountPolicy discountPolicy) {
        this.memberRepositoy = memberRepositoy;
        this.discountPolicy = discountPolicy;
    }



    public MemberRepositoy getMemberRepositoy() {
        return memberRepositoy;
    }



    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {

        Member member = memberRepositoy.findById(memberId);

        int discountPrice = discountPolicy.discount(member, itemPrice);


        return new Order(memberId,itemName,itemPrice,discountPrice);
    }
}
