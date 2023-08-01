//package org.matgyeojo.repository;
//
//import java.util.Optional;
//
//import org.springframework.data.jpa.repository.EntityGraph;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//public interface UserRepository extends JpaRepository<MOpOperator, MOpOperatorKey>{
//
//    @EntityGraph(attributePaths = "authorities")
//    Optional<MOpOperator> findOneWithAuthoritiesByLoginId(String loginId);
//    
//}
