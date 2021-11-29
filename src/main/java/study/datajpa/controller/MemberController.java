package study.datajpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.datajpa.entity.Member;
import study.datajpa.repository.MemberRepository;

import javax.annotation.PostConstruct;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/members/{id}")
    public String findMember(@PathVariable("id") Long id){
        Member member = memberRepository.findById(id).get();
        return member.getUsername();
    }

    @GetMapping("/members2/{id}")
    public String findMember2(@PathVariable("id") Member member){
        //Member member = memberRepository.findById(id).get();
        return member.getUsername();
    }

    @GetMapping("/members")
    public Page<Member> list (Pageable pageable) {
        Page<Member> page = memberRepository.findAll(pageable);
        return page;
        //return memberRepository.findAll(pageable); //인라인 방법
    }

    @PostConstruct
    public void init(){
        //memberRepository.save(new Member("userA"));
        for (int i = 0; i < 100; i++ ) {
            memberRepository.save(new Member("user" + i, i));
        }
        //http://localhost:8080/members?page=0 ->  id 1부터 20까지 반환 (default - 20개)
        //http://localhost:8080/members?page=1&size=3 -> id 4,5,6 반환
        //http://localhost:8080/members?page=0&size=3&sort=id,desc -> id 역순으로 3건 반환 (100,99,98)
    }
}
