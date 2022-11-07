package hellojpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.util.List;

public class JpaMainPersistence {

  public static void main(String[] args) {
    // Database 연결
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

    EntityManager em = emf.createEntityManager();

    // 실제 실행
    EntityTransaction tx = em.getTransaction();
    tx.begin();

    try {

      // 비영속
      Member member = new Member();
      member.setId(100L);
      member.setName("HelloJPA");

      ///////////////////////////////////

      //영속
      em.persist(member);

      // 1차 캐시에 있는 것을 조회 하기 때문에, 쿼리를 실행하지 않음
      Member findMember = em.find(Member.class, 100L);
      System.out.println("findMember.getId() = " + findMember.getId());
      System.out.println("findMember.getName() = " + findMember.getName());

      Member findMember1 = em.find(Member.class, 100L);
      Member findMember2 = em.find(Member.class, 100L);

      /* *
       * JPA가 영속 Entity의 동일성을 보장해줌
       * 1차 캐시로 반복 가능한 읽기(REPEATABLE READ) 등급의 트랜잭션 격리 수준을 데이터베이스가 아닌 애플리케이션
       * 차원에서 제공
       * */
      System.out.println("result = " + (findMember1 == findMember2));

      /*
      * (변경 감지)
      * 마치 객체 다루듯 값만 바뀌었는데도, 쿼리가 실행됨
      * 세부작동 설명
      * 1. commit하는 시점에 내부적으로 flush가 실행됨
      * 2. 1차 캐시 안에 있는 entity와 스냅샷(최초로 1차 캐시에 들어온 값이 저장됨)을 비교함
      * 3. entity의 값이 스냅샷과 바뀌었으면, update 쿼리를 쓰기 지연 sql 저장소에 만들어 둠
      * 4. 이후 커밋이 실행 될때 쿼리를 실행함
      * */
      Member findMember3 = em.find(Member.class, 100L);
      findMember3.setName("ZZZZ");

      /* *
      * 영속성 컨텍스트를 초기화 함
      * */
      em.clear();

      /* *
      *  커밋한는 순간 데이터베이스에 INSERT SQL을 보낸다
      *  세부작동 설명
      *  1. Entity를 1차 캐시 저장함과 동시에, Entity를 SQL로 분석하여 쓰기 지연 SQL 저장소에 저장
      *  2. commit()이 실행 되면, 쓰기 지연 SQL 저장소에 저장되 있는 것들이 플러시 되면서 SQL이 실행됨
      * */
      tx.commit();
    } catch (Exception e) {
      tx.rollback();
    } finally {
      em.close();
    }

    emf.close();

  }

}
