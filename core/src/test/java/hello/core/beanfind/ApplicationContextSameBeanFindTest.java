package hello.core.beanfind;

import hello.core.AppConfig;
import hello.core.member.MemberRepositoy;
import hello.core.member.MemoryMemberRespositoy;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ApplicationContextSameBeanFindTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SameBeanConfig.class);

    @Test
    @DisplayName("타입으로 조회시 같은 타입이 둘이상 있으면, 중복오류가 발생한다.")
    void findBeanByTypeDuplicate()
    {
        Assertions.assertThrows(NoUniqueBeanDefinitionException.class , ()->ac.getBean(MemberRepositoy.class));
    }


    @Test
    @DisplayName("타입으로 조회시 같은 타입이 둘이상 있으면, 중복오류가 발생한다.")
    void findBeanByName()
    {
        MemberRepositoy memberRepositoy1 = ac.getBean("memberRepositoy1", MemberRepositoy.class);
        assertThat(memberRepositoy1).isInstanceOf(MemberRepositoy.class);

    }


    @Test
    @DisplayName("특정타입을 모두 조회하기")
    void findAllBeanByType()
    {
        Map<String, MemberRepositoy> beansOfType = ac.getBeansOfType(MemberRepositoy.class);
        for(String key : beansOfType.keySet())
        {
            System.out.println("key = " + key +" value = "+ beansOfType.get(key));
        }

        System.out.println("beansOfType = " + beansOfType);
        assertThat(beansOfType.size()).isEqualTo(2);
    }



    @Configuration
    static class SameBeanConfig
    {
        @Bean
        public MemberRepositoy memberRepositoy1()
        {
            return new MemoryMemberRespositoy();
        }

        @Bean
        public MemberRepositoy memberRepositoy2()
        {
            return new MemoryMemberRespositoy();
        }

    }



}
