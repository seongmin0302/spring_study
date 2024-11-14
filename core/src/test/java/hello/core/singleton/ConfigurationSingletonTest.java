package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepositoy;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigurationSingletonTest {

    @Test
    void configurationTest()
    {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);


        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepositoy memberRepositoy = ac.getBean("memberRepository", MemberRepositoy.class);

        System.out.println("memberService -> memberRepository = " +memberService.getMemberRepositoy());

        System.out.println("orderService -> memberRepository  = " +orderService.getMemberRepositoy());

        System.out.println("memberRepositoy = " + memberRepositoy);


        Assertions.assertThat(memberService.getMemberRepositoy()).isSameAs(memberRepositoy);
        Assertions.assertThat(orderService.getMemberRepositoy()).isSameAs(memberRepositoy);


    }
    
    
    
    @Test
    void configurationDeep()
    {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        AppConfig bean = ac.getBean(AppConfig.class);
        System.out.println("bean.getClass() = " + bean.getClass());
    }
    
    
    
}
