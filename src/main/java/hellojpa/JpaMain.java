package hellojpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.util.List;

public class JpaMain {

  public static void main(String[] args) {
    // Database 연결
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

    EntityManager em = emf.createEntityManager();

    // 실제 실행
    EntityTransaction tx = em.getTransaction();
    tx.begin();

    try {
      // 등록
      Member member = new Member();
      member.setId(4L);
      member.setName("HelloPKY");

      em.persist(member);

      // 조회
      Member findMember = em.find(Member.class, 4L);

      // 수정
      findMember.setName("HelloJPA");

      // 삭제
      em.remove(findMember);

      List<Member> result = em.createQuery("select m from Member as m", Member.class)
          .setFirstResult(1)
          .setMaxResults(1)
          .getResultList();

      for (Member member1 : result) {
        System.out.println("member1.name = " + member1.getName());
      }

      tx.commit();
    } catch (Exception e) {
      tx.rollback();
    } finally {
      em.close();
    }

    emf.close();

  }

}
