package hellojpa;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        //DB 트렌젝션시작
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{

            //팀이름 지정
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);
            System.out.println("============1===========");

            //멤버의 팀 지정
            Member member = new Member();
            member.setUsername("Member1");
            member.setTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

            //Member의 팀찾기

            Member findMember = em.find(Member.class, member.getId());

            Team findteam = findMember.getTeam();

            System.out.println("findteam.getName() = " + findteam.getName());
            
            List<Member> members = findMember.getTeam().getMembers();

            for(Member m:members){
                System.out.println("m.getUsername() = " + m.getUsername());
            }
            tx.commit();

        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }
}
